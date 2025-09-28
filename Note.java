import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/**
 * Represents a single note with title, content, and creation/modification timestamps
 */
public class Note implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private String id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    
    public Note(String title, String content) {
        this.id = generateId();
        this.title = title;
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.modifiedAt = LocalDateTime.now();
    }
    
    public Note(String id, String title, String content, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
    
    private String generateId() {
        return "note_" + System.currentTimeMillis() + "_" + (int)(Math.random() * 1000);
    }
    
    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getModifiedAt() { return modifiedAt; }
    
    // Setters
    public void setTitle(String title) {
        this.title = title;
        this.modifiedAt = LocalDateTime.now();
    }
    
    public void setContent(String content) {
        this.content = content;
        this.modifiedAt = LocalDateTime.now();
    }
    
    public String getFormattedCreatedAt() {
        return createdAt.format(FORMATTER);
    }
    
    public String getFormattedModifiedAt() {
        return modifiedAt.format(FORMATTER);
    }
    
    @Override
    public String toString() {
        return title + " (" + getFormattedModifiedAt() + ")";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Note note = (Note) obj;
        return id.equals(note.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
}