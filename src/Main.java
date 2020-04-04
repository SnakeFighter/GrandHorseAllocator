import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.util.ArrayList;

public class Main {

    // List of player names in age order
    private static final String[] PLAYER_LIST;

    static {
        PLAYER_LIST = new String[]{"Florence",
                "Canadian Youngster 1",
                "Canadian Youngster 2",
                "Canadian Youngster 3",
                "Georgina",
                "Thomas",
                "Emily",
                "Edward",
                "Zoé",
                "James",
                "Julia",
                "Charles",
                "Anne",
                "John"};
    }

    private static final String[] HORSE_LIST;

    static {
        HORSE_LIST = new String[]{
                "Tiger Roll",
                "Any Second Now",
                "Burrows Saint",
                "Definitly Red",
                "Walk In The Mill",
                "Kimberlite Candy",
                "Magic Of Light",
                "Potters Corner ",
                "Elegant Escape",
                "Anibale Fly",
                "Bristol De Mai",
                "Ok Corral",
                "Alpha Des Obeaux",
                "Ballyoptic",
                "Talkischeap",
                "Pleasant Company",
                "Yala Enki",
                "Vintage Clouds",
                "Acapella Bourgeois",
                "Sub Lieutenant",
                "Beware The Bear",
                "The Storyteller",
                "Jury Duty",
                "Total Recall",
                "Top Ville Ben",
                "Death Duty",
                "Dounikos",
                "Jett Jett",
                "Kildisart",
                "Peregrine Run",
                "Crievehill",
                "Valtor",
                "Ramses De Teillee",
                "Saint Xavier",
                "Aso",
                "Shattered Love",
                "Tout Est Permis",
                "Warriors Tale",
                "Double Shuffle"
        };
    }

    public static void main(String[] args) {


        System.out.println("Grand Horse Allocator 2020.\n" +
                "Copyright Serene Sky Software 2020. All rights reserved.\n" +
                "Seriously.\n" +
                "All of them.");

        ArrayList<Player> playerArrayList = generatePlayerList();
        ArrayList<Horse> horseList = generateHorseList();

        Player currentPlayer = playerArrayList.get(0);
        while (areThereHorsesUnallocated(horseList)) {
            // We need to assign a random horse to the player. We'll try by brute force, so might initially try to assign an already allocated horse.
            boolean successfulAssign = false;
            while (!successfulAssign) {
                int randomInt = getRandomInt(HORSE_LIST.length);
                Horse horseToAssign = horseList.get(randomInt);
                // If the horse is unassigned, assign it!
                if (!horseToAssign.isAllocated()) {
                    System.out.println("Allocating " + horseToAssign.getName() + " to " + currentPlayer.getName());
                    horseToAssign.setAllocatedPlayer(currentPlayer);
                    horseToAssign.setAllocated(true);
                    currentPlayer.addAHorse(horseToAssign);
                    // Signal that we can move to the next player in the cycle.
                    successfulAssign = true;
                }
                //System.out.println("Assigned...");
            }
            //System.out.println("Unallocated? "+areThereHorsesUnallocated(horseList));

            //System.out.println("Player cycled: "+currentPlayer.getName());
            // Move to the next player
            int nextPlayerIndex = playerArrayList.indexOf(currentPlayer) + 1;
            try {
                currentPlayer = playerArrayList.get(nextPlayerIndex);
            } catch (IndexOutOfBoundsException e) {
                currentPlayer = playerArrayList.get(0);
            }
        }

        System.out.println("\nPlayer List:");
        ShowListByPlayers (playerArrayList);
        System.out.println("\nHorse List");
        ShowListByHorses (horseList);
    }

    private static void ShowListByHorses(ArrayList<Horse> horseList) {
        for (Horse iHorse: horseList
             ) {
            System.out.println(iHorse.getName()+": "+iHorse.getAllocatedPlayer().getName());
        }
    }

    private static void ShowListByPlayers(ArrayList<Player> playerArrayList) {
        for (Player iPlayer:playerArrayList
             ) {
            System.out.println("Player: "+iPlayer.getName());
            for (Horse iHorse:iPlayer.getAllocatedHorses()
                 ) {
                System.out.print(iHorse.getName()+", ");
            }
            System.out.print("\n");
        }
    }



    private static int getRandomInt(int length) {
        int random = (int) (Math.random() * length);
        return random;
    }

    private static ArrayList<Horse> generateHorseList() {
        // Generate list of horses.
        ArrayList<Horse> horseArrayList = new ArrayList<Horse>();
        for (int i = 0; i < HORSE_LIST.length; i++) {
            Horse iHorse = new Horse();
            iHorse.setName(HORSE_LIST[i]);
            horseArrayList.add(iHorse);
        }
        // Print some confirmatory info
        System.out.println("Total runners: " + horseArrayList.size());
        for (Horse iHorse : horseArrayList
                ) {
            System.out.println("Horse name: " + iHorse.getName());
        }
        return horseArrayList;
    }

    private static ArrayList<Player> generatePlayerList() {
        ArrayList<Player> playerList = new ArrayList<Player>();
        for (int i = 0; i < PLAYER_LIST.length; i++) {
            Player iPlayer = new Player();
            iPlayer.setName(PLAYER_LIST[i]);
            playerList.add(iPlayer);
        }
        System.out.println("Total Players: " + playerList.size());
        for (Player iPlayer : playerList
                ) {
            System.out.println("Player name: " + iPlayer.getName());
        }
        return playerList;
    }

    /**
     * Cycles through all horses, returns true if any are unallocated. False otherwise.
     *
     * @param horseArrayList
     * @return
     */
    private static boolean areThereHorsesUnallocated(ArrayList<Horse> horseArrayList) {
        //System.out.println("Checking for unallocated horses.");
        for (Horse iHorse : horseArrayList) {
            try {
                Player allocatedPlayer = iHorse.getAllocatedPlayer();
                //System.out.println("Allocated player: "+allocatedPlayer.getName());
                String playerName = allocatedPlayer.getName();
            } catch (NullPointerException e) {
                //System.out.println("Unallocated horse: " + iHorse.getName());
                return true;
            }
        }
        // If we've got this far, all horses are allocated!
        return false;
    }
}
