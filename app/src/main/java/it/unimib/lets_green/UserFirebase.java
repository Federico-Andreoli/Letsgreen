package it.unimib.lets_green;

public class UserFirebase {
    private String userId;
    private String username;
    private Double score;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserFirebase(String userId, String username, Double score) {
        this.userId = userId;
        this.username = username;
        this.score = score;
    }

    public UserFirebase() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
