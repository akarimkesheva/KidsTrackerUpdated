package com.tracker;


 //Represents a reward children can earn.

public class Reward {
    private int rewardId;
    private String rewardName;
    private int pointsRequired;
    private boolean isClaimed;

    public Reward(int rewardId, String rewardName, int pointsRequired) {
        this.rewardId = rewardId;
        this.rewardName = rewardName;
        this.pointsRequired = pointsRequired;
        this.isClaimed = false; // Starts as not claimed [cite: 54]
    }

    // Getters
    public int getRewardId() {
        return rewardId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public boolean isClaimed() {
        return isClaimed;
    }


    public void claimReward() {
        this.isClaimed = true;
    }


    public boolean isValid() {
        return rewardName != null && !rewardName.isEmpty() && pointsRequired >= 0;
    }

    @Override
    public String toString() {
        return rewardName + " (Costs: " + pointsRequired + " pts)";
    }
}
