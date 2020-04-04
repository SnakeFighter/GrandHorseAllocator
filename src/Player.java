import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Horse> allocatedHorses = new ArrayList<Horse>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Horse> getAllocatedHorses() {
        return allocatedHorses;
    }

    public void setAllocatedHorses(ArrayList<Horse> allocatedHorses) {
        this.allocatedHorses = allocatedHorses;
    }

    public void addAHorse (Horse horseToAdd) {
        this.allocatedHorses.add(horseToAdd);
    }
}
