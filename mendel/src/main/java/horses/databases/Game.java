package horses.databases;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
public record Game(
        int game_id,
        int num_of_players,
        Map<String,Integer> players
        //tablica 3xN N graczy nazwa gracz numer konia wartosc betu
        // faza gry
        //koniec betowania
        // mapa [5,int]
) {}
