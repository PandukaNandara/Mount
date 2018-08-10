package lk.ijse.mountCalvary.model;

import lk.ijse.mountCalvary.entity.AgeGroup;

public class AgeGroupDTO {
    private int GID;
    private String groupName;
    private int max;
    private int min;

    public AgeGroupDTO() {
    }

    public AgeGroupDTO(String groupName, int max, int min) {
        this.groupName = groupName;
        this.max = max;
        this.min = min;
    }

    public AgeGroupDTO(int GID, String groupName) {
        this.GID = GID;
        this.groupName = groupName;
    }

    public AgeGroupDTO(int GID, String groupName, int max, int min) {
        this.GID = GID;
        this.groupName = groupName;
        this.max = max;
        this.min = min;
    }

    public AgeGroupDTO(AgeGroup ageGroup) {
        GID = ageGroup.getGID();
        groupName = ageGroup.getGroupName();
        max = ageGroup.getMax();
        min = ageGroup.getMin();
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
        return groupName;
    }
}
