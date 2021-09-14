package it.unimib.lets_green;

public class UserFirebase {
    private String userId;
    private String username;
    private double score;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserFirebase(String userId, String username, double score) {
        this.userId = userId;
        this.username = username;
        this.score = score;
    }

    public UserFirebase(String id, String documentId, String score) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
