/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package a2;

/**
 *
 * @author hrehman.bscs13seecs
 */
import java.util.*;

public class A2 {

    int[][] table = new int[10][10];
    Map<Integer, Integer> ladder = new HashMap<>();     //Ladder Constraints
    Map<Integer, Integer> snake = new HashMap<>();      //Snake Constraints
    int players;                                       //Number of players
    int[] players_score = new int[4];                   //Score of each player
    int[] player_wins = new int[4];                     //Number of wins a player had
    boolean[] player_turn = new boolean[4];             //Turn of player
    int rounds;                                         //Rounds of current game

    A2(int num_players) {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                table[row][col] = (int) (row * 10 + col);   //Setting the board
                table[row][col]++;
            }
        }
        //Populating Ladder constraints
        ladder.put(1, 38);
        ladder.put(4, 14);
        ladder.put(9, 31);
        ladder.put(21, 42);
        ladder.put(28, 84);
        ladder.put(51, 67);
        ladder.put(72, 91);
        ladder.put(80, 99);
        //Populating Snake constraints
        snake.put(17, 7);
        snake.put(54, 34);
        snake.put(62, 19);
        snake.put(64, 60);
        snake.put(87, 36);
        snake.put(93, 73);
        snake.put(95, 75);

        players = num_players;      //Assigning Number of players given by users
        rounds = 0;
        for (int i = 0; i < 4; i++) {
            players_score[i] = 0;       //setting up scores and wins to zero
            player_wins[i] = 0;
            player_turn[i] = false;
        }

    }

    int play() {
        int random = 0;
        int i = 0;
        for (i = 0; i < 4; i++) {
            players_score[i] = 0;       //Setting players_scores to zero each time play() is called
            player_turn[i] = false;
        }
        while (players_score[0] != 100 && players_score[1] != 100 && players_score[2] != 100 && players_score[3] != 100) {
            for (i = 0; i < players; i++) {
                player_turn[i] = true;
                random = (int) (Math.random() * 6 + 1);   //Randomly generating dice values
                rounds++;
                if ((players_score[i] + random) < 100) {
                    players_score[i] += random;
                    for (Map.Entry<Integer, Integer> entry : ladder.entrySet()) {
                        //Checking if ladder constraint is applicable
                        if (players_score[i] == entry.getKey()) {
                            players_score[i] = entry.getValue();
                            break;

                        }
                    }
                    for (Map.Entry<Integer, Integer> entry : snake.entrySet()) {
                        //Checking if Snake constraints are applicable
                        if (players_score[i] == entry.getKey()) {
                            players_score[i] = entry.getValue();
                            break;
                        }

                    }

                }
                if (players_score[i] + random == 100) { //Win Point
                    players_score[i] += random;
                    break;
                }
                player_turn[i] = false;
                if (random == 6 && players_score[i] + random <= 100) {
                    i--;
                }
            }
        }
        for (i = 0; i < players; i++) {
            //printing scores of each player
            //System.out.println("Player " + (i + 1) + " Score: " + players_score[i]);
        }
        for (i = 0; i < players; i++) {
            if (players_score[i] == 100) {
                player_wins[i]++;
                //Success message
                //System.out.println("----Player " + (i + 1) + " Wins the Game----");
                players_score[i] = 0;
                //System.out.println("Rounds for this game: "+rounds);
                return rounds;
            }
        }
        //System.out.println(rounds);
        return rounds;
    }

    public static void main(String[] args) {
        A2 playerr = new A2(2);
        //Board Design
        System.out.println("Welcome to Snake and Ladder Board Game!");
        System.out.print("__________________________________________");
        System.out.println("________________________________________");
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                System.out.print(playerr.table[row][col]);
                for (Map.Entry<Integer, Integer> entry : playerr.ladder.entrySet()) {
                    //Checking if ladder constraint is applicable
                    if (playerr.table[row][col] == entry.getKey()) {
                        System.out.print("-> " + entry.getValue());
                        break;
                    }
                }
                for (Map.Entry<Integer, Integer> entry : playerr.snake.entrySet()) {
                    //Checking if ladder constraint is applicable
                    if (playerr.table[row][col] == entry.getKey()) {
                        System.out.print("-> " + entry.getValue());
                        break;
                    }
                }

                System.out.print("\t");
            }
            System.out.println("");
        }
        System.out.print("__________________________________________");
        System.out.println("________________________________________");
        System.out.println();
        for (int j = 2; j <= 4; j++) {
            long startTime = System.currentTimeMillis();
            //creating an object of the game class with 4 players
            System.out.println("Playing game with " + j + " Players.");
            A2 player = new A2(j);
            int roundss = 0;
            int total_rounds = 0;
            int max_round = 0;
            int min_round = 10000;
            //calculating Average maximum and minimum number of rounds for 100 games
            for (int i = 0; i < 100; i++) {
                roundss = player.play();
                total_rounds += roundss;
                if (min_round > roundss) {
                    min_round = roundss;
                }
                if (roundss > max_round) {
                    max_round = roundss;
                }
            }
            //Calculating winner of 100 games with most wins
            int max_wins = 0;
            int winner = 0;
            for (int i = 0; i < 4; i++) {
                if (max_wins < player.player_wins[i]) {
                    max_wins = player.player_wins[i];
                    winner = i;
                }
            }
            //printing out the results
            System.out.println("Average Rounds: " + (int) (total_rounds / 100));
            System.out.println("Minimum Rounds: " + (int) min_round);
            System.out.println("Maximum Rounds: " + (int) max_round);
            System.out.println("Winner is Player " + (winner + 1) + " with Win Count = " + (int) player.player_wins[winner] + " in 100 games");
            System.out.println();
            System.out.println("Memory and Computation Statistics for " + j + " players game: ");
            Runtime runtime = Runtime.getRuntime();

            System.out.println("Allocated memory: " + runtime.totalMemory() / 1024);
            System.out.println("Used memory: " + runtime.freeMemory() / 1024);
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Total Time taken: " + totalTime + "ms");
            System.out.println("--------------------------------------------------------");

        }
    }

}
