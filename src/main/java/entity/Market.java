package entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Market {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private String square;
    private Theme theme;
    private int totalDesks;
    private int assignedDesks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getSquare() {
        return square;
    }

    public void setSquare(String square) {
        this.square = square;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public int getTotalDesks() {
        return totalDesks;
    }

    public void setTotalDesks(int totalDesks) {
        this.totalDesks = totalDesks;
    }

    public int getAssignedDesks() {
        return assignedDesks;
    }

    public void setAssignedDesks(int assignedDesks) {
        this.assignedDesks = assignedDesks;
    }
}
