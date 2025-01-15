package horses.Controllers;

import horses.configurations.JwtUtil;
import horses.databases.Game;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/game")
public class CreateGame {
    public int number_of_games = 0;
    List<Game> games = new ArrayList<Game>();
    private static final String SECRET_KEY = "your_secret_key";



    @GetMapping("/create")
    public ResponseEntity<?> CreateGame(@RequestHeader("Authorization") String authorizationHeader, HttpServletRequest request) {
        System.out.println(authorizationHeader);
        Authentication authentication = JwtUtil.authenticateUser(authorizationHeader, request);
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        games.add(new Game(number_of_games + 1, 0, new HashMap<>()));
        number_of_games++;
        return JoinGame(authorizationHeader, number_of_games, request);
    }



    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return games.size() != 0 ? ResponseEntity.ok(games) : ResponseEntity.ok("There are no games");
    }
    @GetMapping("/list/{game_id}")
    public ResponseEntity<?> getGameById(@PathVariable int game_id) {
        if (game_id > 0 && game_id <= games.size()) {
            return ResponseEntity.ok(games.get(game_id - 1));
        } else {
            return ResponseEntity.status(404).body("Game not found");
        }
    }
    @GetMapping("/join/{game_id}")
    public ResponseEntity<?> JoinGame(@RequestHeader("Authorization") String authorizationHeader, @PathVariable int game_id, HttpServletRequest request) {
        Authentication authentication = JwtUtil.authenticateUser(authorizationHeader,request);
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("User is not authenticated");
        }

        String username = authentication.getName();
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.game_id() == game_id) {
                game.players().put(username, 0);
                Game newGame = new Game(game.game_id(), game.num_of_players() + 1, game.players());
                games.set(i, newGame);
                return ResponseEntity.status(200).body("Game found and user joined");
            }
        }
        return ResponseEntity.status(404).body("Game not found");
    }
    @PostMapping("/bet/{game_id}")
    public String Bet(Authentication authentication, @PathVariable int game_id, @RequestBody String bet) {
        int betint = Integer.parseInt(bet);
        for (int i = 0; i < games.size(); i++) {
            Game game = games.get(i);
            if (game.game_id() == game_id) {
                if (game.players().containsKey(authentication.getName())) {
                    game.players().put(authentication.getName(), betint);
                    Game newGame = new Game(game.game_id(), game.num_of_players(), game.players());
                    games.set(i, newGame);
                    return "Bet Successfully";
                }
            }
        }
        return "Not found game";
    }
}
