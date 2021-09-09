package it.unimib.lets_green;

public class UserFirebase {
    private String userId;
    private String username;
    private Long score;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserFirebase(String userId, String username, Long score) {
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

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
