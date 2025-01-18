package horses.Controllers;

import horses.configurations.JwtUtil;
import horses.databases.Game;
import horses.Services.GameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;


@RestController
public class CreateGame {

    private final GameService gameService;

    public CreateGame(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/create")
    public ResponseEntity<?> CreateGame(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) {
        Authentication authentication = JwtUtil.authenticateUser(authorizationHeader, request);
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        int gameId = gameService.addGame();
        return JoinGame(authorizationHeader, gameId, request);
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        List<Game> games = gameService.getGames();
        return games.size() != 0 ? ResponseEntity.ok(games) : ResponseEntity.ok("There are no games");
    }

    @GetMapping("/join/{game_id}")
    public ResponseEntity<?> JoinGame(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int game_id, HttpServletRequest request) {
        Authentication authentication = JwtUtil.authenticateUser(authorizationHeader, request);
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        String username = authentication.getName();
        boolean joined = gameService.joinGame(username, game_id);
        if (joined) {
            return ResponseEntity.status(200).body("Game found and user joined");
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }
    }
}
