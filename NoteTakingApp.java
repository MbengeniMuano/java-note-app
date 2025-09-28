import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * Main GUI application for the Note Taking App with Dark Mode Theme
 */
public class NoteTakingApp extends JFrame {
    // Dark Mode Color Scheme
    private static final Color DARK_BACKGROUND = new Color(30, 30, 30);
    private static final Color DARKER_BACKGROUND = new Color(20, 20, 20);
    private static final Color ACCENT_COLOR = new Color(64, 128, 255);
    private static final Color ACCENT_HOVER = new Color(80, 144, 255);
    private static final Color TEXT_COLOR = new Color(240, 240, 240);
    private static final Color SECONDARY_TEXT = new Color(180, 180, 180);
    private static final Color BORDER_COLOR = new Color(60, 60, 60);
    private static final Color SELECTION_COLOR = new Color(50, 100, 200);
    private static final Color BUTTON_BACKGROUND = Color.BLACK;
    private static final Color BUTTON_HOVER = new Color(40, 40, 40);
    
    private NoteManager noteManager;
    private DefaultListModel<Note> listModel;
    private JList<Note> notesList;
    private JTextField titleField;
    private JTextArea contentArea;
    private JTextField searchField;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton newButton;
    private JLabel statusLabel;
    private Note currentNote;
    
    public NoteTakingApp() {
        noteManager = new NoteManager();
        setupDarkTheme();
        initializeGUI();
        loadNotesList();
        updateStatus();
    }
    
    private void setupDarkTheme() {
        // Set dark theme for the application
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Use default look and feel
        }
        
        // Customize UI defaults for dark theme
        UIManager.put("Panel.background", DARK_BACKGROUND);
        UIManager.put("OptionPane.background", DARK_BACKGROUND);
        UIManager.put("OptionPane.messageForeground", TEXT_COLOR);
    }
    
    private void initializeGUI() {
        setTitle("📝 Note Taking App - Dark Mode");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(DARK_BACKGROUND);
        
        // Create main panels
        createLeftPanel();
        createRightPanel();
        createBottomPanel();
        
        // Set window properties
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
    }
    
    private void createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(DARKER_BACKGROUND);
        leftPanel.setBorder(new EmptyBorder(15, 15, 15, 10));
        leftPanel.setPreferredSize(new Dimension(350, 0));
        
        // Search panel with modern styling
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(DARKER_BACKGROUND);
        
        JLabel searchLabel = new JLabel("🔍 Search Notes");
        searchLabel.setForeground(TEXT_COLOR);
        searchLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        searchLabel.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        searchField = new JTextField();
        styleTextField(searchField);
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                searchNotes();
            }
        });
        
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        // Notes list with custom styling
        listModel = new DefaultListModel<>();
        notesList = new JList<>(listModel);
        styleNotesList();
        
        JScrollPane listScrollPane = new JScrollPane(notesList);
        styleScrollPane(listScrollPane);
        listScrollPane.setPreferredSize(new Dimension(320, 0));
        
        // Buttons panel with modern styling
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        buttonsPanel.setBackground(DARKER_BACKGROUND);
        
        newButton = createStyledButton("➕ New Note", Color.BLACK);
        newButton.addActionListener(e -> createNewNote());
        
        deleteButton = createStyledButton("🗑️ Delete", Color.BLACK);
        deleteButton.addActionListener(e -> deleteCurrentNote());
        deleteButton.setEnabled(false);
        
        buttonsPanel.add(newButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(deleteButton);
        
        leftPanel.add(searchPanel, BorderLayout.NORTH);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);
        leftPanel.add(buttonsPanel, BorderLayout.SOUTH);
        
        add(leftPanel, BorderLayout.WEST);
    }
    
    private void createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(DARK_BACKGROUND);
        rightPanel.setBorder(new EmptyBorder(15, 10, 15, 15));
        
        // Title panel with modern styling
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(DARK_BACKGROUND);
        
        JLabel titleLabel = new JLabel("📄 Title");
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        titleLabel.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        titleField = new JTextField();
        styleTextField(titleField);
        titleField.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        titleField.setPreferredSize(new Dimension(0, 35));
        
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(titleField, BorderLayout.CENTER);
        titlePanel.setBorder(new EmptyBorder(0, 0, 15, 0));
        
        // Content panel with modern styling
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(DARK_BACKGROUND);
        
        JLabel contentLabel = new JLabel("📝 Content");
        contentLabel.setForeground(TEXT_COLOR);
        contentLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        contentLabel.setBorder(new EmptyBorder(0, 0, 8, 0));
        
        contentArea = new JTextArea();
        styleTextArea(contentArea);
        
        JScrollPane contentScrollPane = new JScrollPane(contentArea);
        styleScrollPane(contentScrollPane);
        contentScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        contentPanel.add(contentLabel, BorderLayout.NORTH);
        contentPanel.add(contentScrollPane, BorderLayout.CENTER);
        
        // Save button with modern styling
        saveButton = createStyledButton("💾 Save Note", Color.BLACK);
        saveButton.addActionListener(e -> saveCurrentNote());
        saveButton.setEnabled(false);
        saveButton.setPreferredSize(new Dimension(120, 35));
        
        JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        savePanel.setBackground(DARK_BACKGROUND);
        savePanel.add(saveButton);
        
        rightPanel.add(titlePanel, BorderLayout.NORTH);
        rightPanel.add(contentPanel, BorderLayout.CENTER);
        rightPanel.add(savePanel, BorderLayout.SOUTH);
        
        add(rightPanel, BorderLayout.CENTER);
    }
    
    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(DARKER_BACKGROUND);
        bottomPanel.setBorder(new EmptyBorder(10, 15, 15, 15));
        
        statusLabel = new JLabel("Ready");
        statusLabel.setForeground(SECONDARY_TEXT);
        statusLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        bottomPanel.add(statusLabel, BorderLayout.WEST);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void styleTextField(JTextField textField) {
        textField.setBackground(BUTTON_BACKGROUND);
        textField.setForeground(TEXT_COLOR);
        textField.setCaretColor(TEXT_COLOR);
        textField.setBorder(new LineBorder(BORDER_COLOR, 1));
        textField.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        
        // Add padding
        textField.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(BORDER_COLOR, 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
    }
    
    private void styleTextArea(JTextArea textArea) {
        textArea.setBackground(BUTTON_BACKGROUND);
        textArea.setForeground(TEXT_COLOR);
        textArea.setCaretColor(TEXT_COLOR);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(new EmptyBorder(12, 12, 12, 12));
        textArea.setSelectionColor(SELECTION_COLOR);
    }
    
    private void styleNotesList() {
        notesList.setBackground(BUTTON_BACKGROUND);
        notesList.setForeground(TEXT_COLOR);
        notesList.setSelectionBackground(SELECTION_COLOR);
        notesList.setSelectionForeground(Color.WHITE);
        notesList.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        notesList.setBorder(new EmptyBorder(8, 12, 8, 12));
        notesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        notesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedNote();
            }
        });
        
        // Custom cell renderer for better spacing
        notesList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setBorder(new EmptyBorder(8, 12, 8, 12));
                if (isSelected) {
                    setBackground(SELECTION_COLOR);
                    setForeground(Color.WHITE);
                } else {
                    setBackground(BUTTON_BACKGROUND);
                    setForeground(TEXT_COLOR);
                }
                return this;
            }
        });
    }
    
    private void styleScrollPane(JScrollPane scrollPane) {
        scrollPane.setBackground(BUTTON_BACKGROUND);
        scrollPane.setBorder(new LineBorder(BORDER_COLOR, 1));
        scrollPane.getViewport().setBackground(BUTTON_BACKGROUND);
        
        // Style scrollbars
        scrollPane.getVerticalScrollBar().setBackground(DARKER_BACKGROUND);
        scrollPane.getHorizontalScrollBar().setBackground(DARKER_BACKGROUND);
    }
    
    private JButton createStyledButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        button.setBorder(new EmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (backgroundColor.equals(Color.BLACK)) {
                    button.setBackground(BUTTON_HOVER);
                } else {
                    button.setBackground(backgroundColor.brighter());
                }
                button.setForeground(Color.WHITE); // Ensure text stays white
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(backgroundColor);
                button.setForeground(Color.WHITE); // Ensure text stays white
            }
        });
        
        return button;
    }
    
    private void loadNotesList() {
        listModel.clear();
        List<Note> notes = noteManager.getNotesSortedByDate();
        for (Note note : notes) {
            listModel.addElement(note);
        }
    }
    
    private void searchNotes() {
        String query = searchField.getText();
        listModel.clear();
        List<Note> searchResults = noteManager.searchNotes(query);
        for (Note note : searchResults) {
            listModel.addElement(note);
        }
    }
    
    private void loadSelectedNote() {
        Note selectedNote = notesList.getSelectedValue();
        if (selectedNote != null) {
            currentNote = selectedNote;
            titleField.setText(selectedNote.getTitle());
            contentArea.setText(selectedNote.getContent());
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
            statusLabel.setText("📖 Loaded: " + selectedNote.getTitle() + " (Modified: " + selectedNote.getFormattedModifiedAt() + ")");
        }
    }
    
    private void createNewNote() {
        currentNote = null;
        titleField.setText("");
        contentArea.setText("");
        saveButton.setEnabled(true);
        deleteButton.setEnabled(false);
        titleField.requestFocus();
        statusLabel.setText("✨ Creating new note...");
    }
    
    private void saveCurrentNote() {
        String title = titleField.getText().trim();
        String content = contentArea.getText();
        
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a title for the note.", "Title Required", JOptionPane.WARNING_MESSAGE);
            titleField.requestFocus();
            return;
        }
        
        if (currentNote == null) {
            // Create new note
            noteManager.addNote(title, content);
            statusLabel.setText("✅ New note created: " + title);
        } else {
            // Update existing note
            noteManager.updateNote(currentNote.getId(), title, content);
            statusLabel.setText("✅ Note updated: " + title);
        }
        
        loadNotesList();
        updateStatus();
        
        // Select the saved note in the list
        for (int i = 0; i < listModel.size(); i++) {
            Note note = listModel.getElementAt(i);
            if (note.getTitle().equals(title)) {
                notesList.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void deleteCurrentNote() {
        if (currentNote != null) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete the note '" + currentNote.getTitle() + "'?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                noteManager.deleteNote(currentNote.getId());
                statusLabel.setText("🗑️ Note deleted: " + currentNote.getTitle());
                
                currentNote = null;
                titleField.setText("");
                contentArea.setText("");
                saveButton.setEnabled(false);
                deleteButton.setEnabled(false);
                
                loadNotesList();
                updateStatus();
            }
        }
    }
    
    private void updateStatus() {
        int count = noteManager.getNotesCount();
        if (currentNote == null && count > 0) {
            statusLabel.setText("📊 Total notes: " + count + " | Select a note to edit or create a new one");
        } else if (count == 0) {
            statusLabel.setText("📝 No notes found. Click 'New Note' to create your first note.");
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new NoteTakingApp().setVisible(true);
        });
    }
}