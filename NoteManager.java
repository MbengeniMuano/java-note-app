import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages all note operations including CRUD, search, and persistence
 */
public class NoteManager {
    private List<Note> notes;
    private final String DATA_FILE = "notes.dat";
    
    public NoteManager() {
        this.notes = new ArrayList<>();
        loadNotes();
    }
    
    /**
     * Add a new note
     */
    public void addNote(String title, String content) {
        Note note = new Note(title, content);
        notes.add(note);
        saveNotes();
    }
    
    /**
     * Update an existing note
     */
    public boolean updateNote(String id, String title, String content) {
        Note note = findNoteById(id);
        if (note != null) {
            note.setTitle(title);
            note.setContent(content);
            saveNotes();
            return true;
        }
        return false;
    }
    
    /**
     * Delete a note by ID
     */
    public boolean deleteNote(String id) {
        Note note = findNoteById(id);
        if (note != null) {
            notes.remove(note);
            saveNotes();
            return true;
        }
        return false;
    }
    
    /**
     * Find a note by ID
     */
    public Note findNoteById(String id) {
        return notes.stream()
                .filter(note -> note.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
    
    /**
     * Get all notes
     */
    public List<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }
    
    /**
     * Search notes by title or content
     */
    public List<Note> searchNotes(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllNotes();
        }
        
        String lowerQuery = query.toLowerCase();
        return notes.stream()
                .filter(note -> 
                    note.getTitle().toLowerCase().contains(lowerQuery) ||
                    note.getContent().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
    
    /**
     * Get notes sorted by creation date (newest first)
     */
    public List<Note> getNotesSortedByDate() {
        return notes.stream()
                .sorted((n1, n2) -> n2.getModifiedAt().compareTo(n1.getModifiedAt()))
                .collect(Collectors.toList());
    }
    
    /**
     * Save notes to file
     */
    private void saveNotes() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(notes);
        } catch (IOException e) {
            System.err.println("Error saving notes: " + e.getMessage());
        }
    }
    
    /**
     * Load notes from file
     */
    @SuppressWarnings("unchecked")
    private void loadNotes() {
        File file = new File(DATA_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
                notes = (List<Note>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading notes: " + e.getMessage());
                notes = new ArrayList<>();
            }
        }
    }
    
    /**
     * Get total number of notes
     */
    public int getNotesCount() {
        return notes.size();
    }
    
    /**
     * Clear all notes
     */
    public void clearAllNotes() {
        notes.clear();
        saveNotes();
    }
}