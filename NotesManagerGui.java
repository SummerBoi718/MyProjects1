import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotesManagerGui {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private String currentUser;

    public NotesManagerGui() {
        frame = new JFrame("Notes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 400)); // Set preferred size of the frame
        
        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                register();
            }
        });

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Authenticate user
        if (authenticate(username, password)) {
            currentUser = username;
            openMainMenu();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        // Validate username and password
        if (!isValidUsername(username)) {
            JOptionPane.showMessageDialog(frame, "Invalid username. Please enter alphanumeric characters only.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!isValidPassword(password)) {
            JOptionPane.showMessageDialog(frame, "Invalid password. Please enter at least 6 characters.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create new user account
        File accountFile = new File("accounts/" + username + ".txt");
        if (accountFile.exists()) {
            JOptionPane.showMessageDialog(frame, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            FileWriter writer = new FileWriter(accountFile);
            writer.write(username + "\n" + password);
            writer.close();
            JOptionPane.showMessageDialog(frame, "Account created successfully.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error creating account: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean authenticate(String username, String password) {
        // Check if user's account file exists and contains correct credentials
        File accountFile = new File("accounts/" + username + ".txt");
        if (accountFile.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(accountFile));
                String storedUsername = reader.readLine();
                String storedPassword = reader.readLine();
                reader.close();
                return username.equals(storedUsername) && password.equals(storedPassword);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Error reading account file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private void openMainMenu() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        JButton viewNotesButton = new JButton("View Notes");
        viewNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewNotes();
            }
        });

        JButton createNotesButton = new JButton("Create Note");
        createNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNote();
            }
        });

        JButton deleteNotesButton = new JButton("Delete Note");
        deleteNotesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteNote();
            }
        });

        panel.add(viewNotesButton);
        panel.add(createNotesButton);
        panel.add(deleteNotesButton);

        frame.getContentPane().add(panel);
        frame.pack();
    }

    private void viewNotes() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        // Load and display existing notes for the current user
        File userDir = new File("notes/" + currentUser);
        if (userDir.exists()) {
            File[] noteFiles = userDir.listFiles();
            if (noteFiles != null) {
                for (File noteFile : noteFiles) {
                    String title = noteFile.getName().replace(".txt", "");
                    JButton viewButton = new JButton("i view ni bi " + title);
                    viewButton.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            viewNoteContent(noteFile);
                        }
                    });
                    panel.add(viewButton);
                }
            }
        }

        JButton backButton = new JButton("Balik");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMainMenu();
            }
        });
        panel.add(backButton);

        frame.getContentPane().add(panel);
        frame.pack();
    }

    private void viewNoteContent(File noteFile) {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextArea noteContentArea = new JTextArea();
        noteContentArea.setEditable(false); // Make the text area read-only

        JScrollPane scrollPane = new JScrollPane(noteContentArea);

        try {
            BufferedReader reader = new BufferedReader(new FileReader(noteFile));
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            reader.close();
            noteContentArea.setText(content.toString());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error reading note file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        panel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewNotes();
            }
        });
        panel.add(backButton, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.pack();
    }

    private void createNote() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create a panel for the title
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        JTextField titleField = new JTextField();
        titlePanel.add(new JLabel("Title:"), BorderLayout.WEST);
        titlePanel.add(titleField, BorderLayout.CENTER);

        // Create a panel for the content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        JTextArea contentArea = new JTextArea();
        contentPanel.add(new JLabel("Content:"), BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(contentArea), BorderLayout.CENTER);

        // Add the title panel and content panel to the main panel
        panel.add(titlePanel, BorderLayout.NORTH);
        panel.add(contentPanel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save Note");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveNoteAndReturn(titleField.getText(), contentArea.getText());
            }
        });
        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openMainMenu();
            }
        });
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        // Add the button panel to the main panel
        panel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(panel);
        frame.pack();
    }

    private void deleteNote() {
        JFileChooser fileChooser = new JFileChooser(new File("notes/" + currentUser));
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile.delete()) {
                JOptionPane.showMessageDialog(frame, "Note deleted successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Error deleting note.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveNoteAndReturn(String title, String content) {
        // Validate title
        if (title.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Title cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Create directory for user's notes if it doesn't exist
        File userDir = new File("notes/" + currentUser);
        if (!userDir.exists()) {
            userDir.mkdirs();
        }

        // Create the note file
        File noteFile = new File(userDir, title + ".txt");
        try {
            FileWriter writer = new FileWriter(noteFile);
            writer.write(content);
            writer.close();
            JOptionPane.showMessageDialog(frame, "Note created successfully.");
            openMainMenu(); // Return to the main menu after saving the note
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error creating note: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isValidUsername(String username) {
        // Check if username contains only alphanumeric characters
        return username.matches("[a-zA-Z0-9]+");
    }

    private boolean isValidPassword(String password) {
        // Check if password has at least 6 characters
        return password.length() >= 6;
    }

    public static void main(String[] args) {
        // Create directories for accounts and notes if they don't exist
        File accountsDir = new File("accounts");
        if (!accountsDir.exists()) {
            boolean created = accountsDir.mkdir();
            if (!created) {
                System.out.println("Failed to create accounts directory.");
                return;
            }
        }

        File notesDir = new File("notes");
        if (!notesDir.exists()) {
            boolean created = notesDir.mkdir();
            if (!created) {
                System.out.println("Failed to create notes directory.");
                return;
            }
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new NotesManagerGui();
            }
        });
    }
}
