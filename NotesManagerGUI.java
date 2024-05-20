import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class NotesManagerGUI {
    private JFrame frame;
    private JPanel panel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    private String currentUser;

    public NotesManagerGUI() {
        frame = new JFrame("Notes Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
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
            openNotesManager();
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

    private void openNotesManager() {
        frame.getContentPane().removeAll();
        frame.repaint();

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JTextField titleField = new JTextField();
        JTextArea contentArea = new JTextArea();
        JButton createButton = new JButton("Create Note");
        createButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createNote(titleField.getText(), contentArea.getText());
            }
        });

        JButton deleteButton = new JButton("Delete Note");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteNote();
            }
        });

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Content:"));
        panel.add(new JScrollPane(contentArea));
        panel.add(createButton);
        panel.add(deleteButton);

        frame.getContentPane().add(panel);
        frame.pack();
    }

    private void createNote(String title, String content) {
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
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error creating note: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                new NotesManagerGUI();
            }
        });
    }
}
