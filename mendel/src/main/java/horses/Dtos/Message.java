package horses.Dtos;

import java.io.Serializable;

public class Message implements Serializable {
    private String type; // e.g., "greet", "time"
    private String content;
    private int value; // Message content
    private String authToken;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    // Default constructor
    public Message() {}

    // Parameterized constructor
    public Message(String type, String content) {
        this.type = type;
        this.content = content;
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}