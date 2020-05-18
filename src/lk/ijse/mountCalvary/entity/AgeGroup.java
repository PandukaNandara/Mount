package lk.ijse.mountCalvary.entity;

public class AgeGroup {
    private int GID;
    private String groupName;
    private int max;
    private int min;

    public AgeGroup() {
    }

    public AgeGroup(String groupName, int max, int min) {
        this.groupName = groupName;
        this.max = max;
        this.min = min;
    }

    public AgeGroup(int GID, String groupName, int min, int max) {
        this(groupName, max, min);
        this.GID = GID;
    }

    public int getGID() {
        return GID;
    }

    public void setGID(int GID) {
        this.GID = GID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "AgeGroup{" +
                "GID=" + GID +
                ", groupName='" + groupName + '\'' +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
