public class Horse {
    private String name;
    private boolean isAllocated = false;
    private Player allocatedPlayer = null;

    public Player getAllocatedPlayer() {
        return allocatedPlayer;
    }

    public void setAllocatedPlayer(Player allocatedPlayer) {
        this.allocatedPlayer = allocatedPlayer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllocated() {
        return isAllocated;
    }

    public void setAllocated(boolean allocated) {
        isAllocated = allocated;
    }
}
