# Note Taking App

A simple and intuitive note-taking application built with Java Swing that allows you to create, edit, search, and manage your notes with persistent storage.

## Features

- **Create Notes**: Add new notes with title and content
- **Edit Notes**: Modify existing notes with real-time updates
- **Delete Notes**: Remove notes with confirmation dialog
- **Search Functionality**: Search through notes by title or content
- **Persistent Storage**: Notes are automatically saved to disk and loaded on startup
- **Intuitive GUI**: Clean and user-friendly interface with Swing components
- **Timestamps**: Track creation and modification times for each note
- **Sorting**: Notes are displayed sorted by modification date (newest first)

## Project Structure

```
Note-Taking app/
├── Note.java              # Note model class
├── NoteManager.java       # Business logic and data management
├── NoteTakingApp.java     # Main GUI application
├── README.md              # This documentation file
└── notes.dat              # Data file (created automatically)
```

## Requirements

- Java 8 or higher
- No external dependencies required (uses built-in Swing)

## How to Run

1. **Compile the Java files:**
   ```bash
   javac *.java
   ```

2. **Run the application:**
   ```bash
   java NoteTakingApp
   ```

## Usage Guide

### Creating a New Note
1. Click the "New Note" button
2. Enter a title in the title field
3. Add your content in the text area
4. Click "Save Note" to save

### Editing an Existing Note
1. Select a note from the list on the left
2. Modify the title or content as needed
3. Click "Save Note" to update

### Searching Notes
1. Type in the search field at the top left
2. Results will filter automatically as you type
3. Search works on both title and content

### Deleting Notes
1. Select a note from the list
2. Click the "Delete" button
3. Confirm the deletion in the dialog

## Technical Details

### Classes Overview

#### Note.java
- Represents individual notes with title, content, and timestamps
- Implements Serializable for file persistence
- Provides formatted date display methods

#### NoteManager.java
- Handles all CRUD operations (Create, Read, Update, Delete)
- Manages file persistence using Java serialization
- Provides search and sorting functionality
- Maintains data integrity and error handling

#### NoteTakingApp.java
- Main GUI application using Java Swing
- Implements event handling for user interactions
- Provides responsive layout with split-pane design
- Includes status updates and user feedback

### Data Persistence
- Notes are automatically saved to `notes.dat` file
- Uses Java object serialization for data storage
- Data is loaded automatically when the application starts
- No manual save/load operations required

### Key Features Implementation
- **Real-time search**: Filters notes as you type
- **Auto-save**: Notes are saved immediately after creation/modification
- **Confirmation dialogs**: Prevents accidental deletions
- **Status updates**: Provides feedback on user actions
- **Responsive design**: Adapts to window resizing

## Keyboard Shortcuts
- **Enter** in search field: Applies search filter
- **Tab**: Navigate between fields
- **Escape**: Clear current selection (when implemented)

## Future Enhancements
- Export notes to different formats (TXT, PDF)
- Categories and tags for better organization
- Rich text formatting
- Note templates
- Backup and restore functionality
- Dark mode theme
- Keyboard shortcuts for common actions

## Troubleshooting

### Application won't start
- Ensure Java 8+ is installed
- Check that all .java files are compiled successfully
- Verify you're running `java NoteTakingApp` (not `javac`)

### Notes not saving
- Check write permissions in the application directory
- Ensure sufficient disk space
- Look for error messages in the console

### Search not working
- Search is case-insensitive and searches both title and content
- Clear the search field to see all notes
- Try different search terms

## License
MIT License

Copyright (c) 2025 Mbengeni Muano

See the `LICENSE` file for the full license text.

## Contributing
Feel free to fork this project and submit pull requests for improvements or bug fixes.
