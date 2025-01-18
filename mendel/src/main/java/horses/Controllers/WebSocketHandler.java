package horses.Controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import horses.Dtos.Message;
import horses.Services.GameService;
import horses.configurations.JwtUtil;
import horses.databases.BetInfo;
import horses.databases.Game;
import horses.Services.GameService;
import io.jsonwebtoken.Claims;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> webSocketSessions =
            Collections.synchronizedList(new ArrayList<>());
    private final GameService gameService;
    public WebSocketHandler(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println(session.getId() + " Connected");
        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId() + " Disconnected");
        webSocketSessions.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws IOException {
        String payload = textMessage.getPayload(); // Get JSON payload
        System.out.println("Received message: " + payload);
        List<Game> games = gameService.getGames();
        try {
            // Parse JSON into a Java object
            Message incomingMessage = objectMapper.readValue(payload, Message.class);

            // Extract and validate the token
            String authToken = incomingMessage.getAuthToken();
            if (authToken == null || authToken.isEmpty()) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new Message("error", "Authentication token is missing",0,0))));
                return;
            }

            Claims claims = JwtUtil.parseToken(authToken);
            if (claims == null) {
                session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new Message("error", "Invalid or expired token",0,0))));
                return;
            }

            // Set the authentication context
            String username = claims.getSubject();
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("message type: " + incomingMessage.getType());
            // Handle message content


            if ("get-game-id".equalsIgnoreCase(incomingMessage.getType())) {
                System.out.println(games.size());

                for (int i = 0; i < games.size(); i++) {
                    System.out.println("b");
                    Game game = games.get(i);
                    if (game.getPlayers().containsKey(username)) {
                        System.out.println("c");
                        Message responseMessage = new Message("game-id", "Player is in the game"+ game.getGame_id(), game.getGame_id(),0);
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseMessage)));
                    }
                }
            } else if ("bet".equalsIgnoreCase(incomingMessage.getType())) {
                Game game = games.get(Integer.parseInt(incomingMessage.getContent()) - 1);
                if (game.getPlayers().containsKey(username)) {
                    int bet = incomingMessage.getValue();
                    if (bet >= 0 && bet <= 100) {
                        game.getPlayers().put(username, new BetInfo(bet, incomingMessage.getHorse()));
                        Message responseMessage = new Message("response", "Bet placed: ", bet,0);
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseMessage)));
                    } else {
                        Message responseMessage = new Message("error", "Invalid bet: ", bet,0);
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseMessage)));
                    }
                }

            } else if ("get-betters-list".equalsIgnoreCase(incomingMessage.getType())) {
                Game game = games.get(incomingMessage.getValue() - 1);
                if (game.getPlayers().containsKey(username)) {
                    Message responseMessage = new Message("betters-list", game.getPlayers().toString(),0,0);
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(responseMessage)));
                }
            }
        } catch (Exception e) {
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(new Message("error", "Error processing message: " + e.getMessage(), 0,0))));
        }
    }
}