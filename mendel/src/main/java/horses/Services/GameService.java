package horses.Services;

import horses.databases.Game;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class GameService {
    private final List<Game> games = new ArrayList<>();
    private int number_of_games = 0;

    public List<Game> getGames() {
        return games;
    }

    public int addGame() {
        Game newGame = new Game(++number_of_games, 0, new HashMap<>(), "Betting");
        games.add(newGame);
        return newGame.getGame_id();
    }

    public boolean joinGame(String username, int gameId) {
        for (Game game : games) {
            if (game.getGame_id() == gameId) {
                game.getPlayers().put(username, 0);
                game.setNum_of_players(game.getNum_of_players() + 1);
                return true;
            }
        }
        return false;
    }
}