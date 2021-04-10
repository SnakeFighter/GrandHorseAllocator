//import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Main {

    // List of player names in age order
    private static final String[] PLAYER_LIST;
    private static final String SAVE_FILENAME = "Z:\\Charlie\\Documents\\Grand Horse Allocation 2021.txt";

    static {
        PLAYER_LIST = new String[]{"Finley",
                "Florence",
                "Owen",
                "Eleanor",
                "Louise",
                "Thomas",
                "Emily",
                "Edward",
                "Zoé",
                "James",
                "Julia",
                "Anne",
                "John",
                "Charles"
        };
    }

    private static final String[] HORSE_LIST;

    static {
        HORSE_LIST = new String[]{
                "Cloth Cap",
                "Minella Times",
                "Burrows Saint",
                "Any Second Now",
                "Kimberlite Candy",
                "Discorama",
                "Magic Of Light",
                "Farclass",
                "Potters Corner",
                "Anibale Fly",
                "Bristol De Mai",
                "Milan Native",
                "Takingrisks",
                "Mister Malarky",
                "Lord du Mesnil",
                "Acapella Bourgeois",
                "Canelo",
                "Viex Lion Rouge",
                "Class Conti",
                "Shattered Love",
                "Yala Enki",
                "Definitly Red",
                "Hogan's Height",
                "Jett",
                "Balko Des Flos",
                "Alpha Des Obeaux",
                "Talkischeap",
                "Lake View Lad",
                "Chris's Dream",
                "Blaklion",
                "The Long Mile",
                "Give Me A Copper",
                "OK Corral",
                "Ballyoptic",
                "Sub Lieutenant",
                "Minellacelebration",
                "Double Shuffle",
                "Cabaret Queen",
                "Tout Est Permis",
                "Ami Desbois"
                // Reserves: "Some Neck", "Kauto Riko", "Fagan",
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

        if (doValidation(horseList, playerArrayList)) {

            FileWriter saveFileWriter = openFile ();

            System.out.println("\nPlayer List:");
            ShowListByPlayers(playerArrayList, saveFileWriter);
            System.out.println("\nHorse List");
            ShowListByHorses(horseList, saveFileWriter);

            closeFile(saveFileWriter);

        }
    }

    // Opens file for writing, returns a FileWriter
    private static FileWriter openFile() {
        try {
            if (Files.exists(Paths.get(SAVE_FILENAME))) {
                Files.delete(Paths.get(SAVE_FILENAME));
            }
            Files.createFile(Paths.get(SAVE_FILENAME));
            //doErrorMessage("Created new data save file.");
            FileWriter saveFileWriter = new FileWriter(SAVE_FILENAME);
            return saveFileWriter;
        } catch (IOException e) {
            e.printStackTrace();
            //doErrorMessage("File write error.");
        }
        return null;
    }

    // Closes file.
    private static void closeFile (FileWriter saveFileWriter) {
        try {
            saveFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compares each horse with its assigned player, and checks that the player has that horse allocated.
     *
     * @param horseList
     * @param playerArrayList
     */
    private static boolean doValidation(ArrayList<Horse> horseList, ArrayList<Player> playerArrayList) {
        // Go through list of horses
        for (Horse iHorse : horseList) {
            Player assignedPlayer = iHorse.getAllocatedPlayer();
            //System.out.println("Verifying: "+assignedPlayer.getName());
            // Check that the allocated player has this horse assigned.
            boolean hasThisHorseAssigned = false;
            for (Horse jHorse : assignedPlayer.getAllocatedHorses()) {
                //System.out.println("Checking..."+jHorse.getName());
                if (jHorse.equals(iHorse)) {
                    hasThisHorseAssigned = true;
                    //System.out.println("Verified "+jHorse.getName());
                    //break;
                }
            }
            if (!hasThisHorseAssigned) {
                System.out.println("Error with horse: " + iHorse);
                return false;
            }
        }
        System.out.println("Verification OK.");
        return true;
    }

    private static void ShowListByHorses(ArrayList<Horse> horseList, FileWriter saveFileWriter) {

        // Blank lines between two list sections for prettiness.
        try {
            saveFileWriter.append("\n\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Horse iHorse : horseList
        ) {
            System.out.println(iHorse.getName() + ": " + iHorse.getAllocatedPlayer().getName());
            try {
                saveFileWriter.append(iHorse.getName() + ": " + iHorse.getAllocatedPlayer().getName()+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void ShowListByPlayers(ArrayList<Player> playerArrayList, FileWriter saveFileWriter) {
        for (Player iPlayer : playerArrayList
        ) {
            try {
                System.out.println("Player: " + iPlayer.getName());
                saveFileWriter.write("\nPlayer: " + iPlayer.getName()+"\n");

                for (Horse iHorse : iPlayer.getAllocatedHorses()
                ) {
                    System.out.print(iHorse.getName() + ", ");
                    saveFileWriter.write(iHorse.getName() + ", ");
                }
                System.out.print("\n");
                saveFileWriter.write("\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
