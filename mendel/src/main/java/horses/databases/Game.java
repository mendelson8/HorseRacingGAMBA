
package horses.databases;


import java.util.Map;

        public class Game {
            private int game_id;
            private int num_of_players;
            private Map<String, BetInfo> players; // Updated to use PlayerStats
            private String phase;

            public Game(int game_id, int num_of_players, Map<String, BetInfo> players, String phase) {
                this.game_id = game_id;
                this.num_of_players = num_of_players;
                this.players = players;
                this.phase = phase;
            }

            public int getGame_id() {
                return game_id;
            }

            public void setGame_id(int game_id) {
                this.game_id = game_id;
            }

            public int getNum_of_players() {
                return num_of_players;
            }

            public void setNum_of_players(int num_of_players) {
                this.num_of_players = num_of_players;
            }

            public Map<String, BetInfo> getPlayers() {
                return players;
            }

            public void setPlayers(Map<String, BetInfo> players) {
                this.players = players;
            }

            public String getPhase() {
                return phase;
            }

            public void setPhase(String phase) {
                this.phase = phase;
            }

            @Override
            public String toString() {
                return "Game{" +
                        "game_id=" + game_id +
                        ", num_of_players=" + num_of_players +
                        ", players=" + players +
                        ", phase='" + phase + '\'' +
                        '}';
            }
        }
