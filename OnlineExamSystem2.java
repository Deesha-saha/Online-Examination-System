import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineExamSystem2 extends JFrame {
    // GUI Components
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton updateProfileButton;
    private JButton submitButton;
    private JButton logoutButton;
    private JTextArea questionTextArea;
    private JRadioButton[] options;
    private Timer timer;

    // Other variables
    private int timeRemaining = 1800; // 30 minutes
    private int currentQuestion = 0;
    private int score = 0;

    // Science Questions
    private String[] questions = {
            "What is the largest planet in our solar system?",
            "Which animal is a mammal: snake, dolphin, or eagle?",
            "What is the symbol for the element oxygen?",
            "What is the freezing point of water in Celsius?",
            "Which is the hardest natural substance on Earth?",
            "Which scientist discovered the theory of general relativity?",
            "What is the unit of electrical resistance?",
            "What is the chemical formula for table salt?",
            "What is the SI unit of force?",
            "What causes the seasons on Earth?",
            "Which gas is most abundant in the Earth's atmosphere?",
            "What is the main component of natural gas?",
            "What is the study of fossils called?",
            "What is the process by which plants convert sunlight into energy?",
            "What is the largest organ in the human body?"
    };

    private String[][] optionsArray = {
            {"Mars", "Jupiter", "Saturn", "Neptune"},
            {"Snake", "Dolphin", "Eagle"},
            {"O", "O2", "H2O", "C"},
            {"0 degrees", "100 degrees", "32 degrees", "212 degrees"},
            {"Diamond", "Graphite", "Quartz", "Topaz"},
            {"Albert Einstein", "Isaac Newton", "Galileo Galilei", "Niels Bohr"},
            {"Ohm", "Ampere", "Watt", "Volt"},
            {"NaCl", "H2O", "CO2", "C6H12O6"},
            {"Newton", "Pascal", "Joule", "Watt"},
            {"Tilt of the Earth's axis", "Moon phases", "Sunspots", "Tectonic plates"},
            {"Nitrogen", "Oxygen", "Carbon dioxide", "Argon"},
            {"Methane", "Ethane", "Propane", "Butane"},
            {"Paleontology", "Anthropology", "Geology", "Archeology"},
            {"Photosynthesis", "Respiration", "Fermentation", "Digestion"},
            {"Skin", "Heart", "Liver", "Brain"}
    };

    private int[] correctAnswers = {1, 2, 0, 2, 0, 0, 0, 0, 3, 0, 1, 0, 0, 0, 2};

    OnlineExamSystem2() {
        // Set up the main frame
        setTitle("Online Examination System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create and add GUI components
        JLabel usernameLabel = new JLabel("Username:");
        usernameTextField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        updateProfileButton = new JButton("Update Profile");
        submitButton = new JButton("Submit");
        logoutButton = new JButton("Logout");
        questionTextArea = new JTextArea(10, 50);
        options = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
        }

        add(usernameLabel);
        add(usernameTextField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);

        // Event listeners
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login logic here
                // Validate the credentials and display the main interface
                // Hide or disable login components and show other components
                showMainInterface();
            }
        });

        updateProfileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle profile update logic here
                // Display a form to update profile information
                // You can add relevant text fields and buttons for updating profile/password
                // Implement the necessary logic to save the updated information
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle answer submission logic here
                int selectedAnswer = -1;
                for (int i = 0; i < options.length; i++) {
                    if (options[i].isSelected()) {
                        selectedAnswer = i;
                        break;
                    }
                }

                if (selectedAnswer == correctAnswers[currentQuestion]) {
                    score++;
                }

                currentQuestion++;

                if (currentQuestion < questions.length) {
                    displayQuestion(currentQuestion);
                } else {
                    displayResult();
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle logout logic here
                // Clear any stored data, reset the timer, and return to the login screen
                resetExam();
                showLoginScreen();
            }
        });

        setVisible(true);
    }

    private void showLoginScreen() {
        usernameTextField.setText("");
        passwordField.setText("");
        questionTextArea.setText("");

        usernameTextField.setEnabled(true);
        passwordField.setEnabled(true);
        loginButton.setEnabled(true);

        updateProfileButton.setEnabled(false);
        submitButton.setEnabled(false);
        logoutButton.setEnabled(false);

        remove(updateProfileButton);
        remove(submitButton);
        remove(logoutButton);

        add(usernameTextField);
        add(passwordField);
        add(loginButton);

        revalidate();
        repaint();
    }

    private void showMainInterface() {
        usernameTextField.setEnabled(false);
        passwordField.setEnabled(false);
        loginButton.setEnabled(false);

        updateProfileButton.setEnabled(true);
        submitButton.setEnabled(true);
        logoutButton.setEnabled(true);

        remove(usernameTextField);
        remove(passwordField);
        remove(loginButton);

        add(updateProfileButton);
        add(submitButton);
        add(logoutButton);

        displayQuestion(currentQuestion);

        revalidate();
        repaint();
    }

    private void displayQuestion(int questionIndex) {
        questionTextArea.setText(questions[questionIndex]);

        ButtonGroup buttonGroup = new ButtonGroup();

        for (int i = 0; i < options.length; i++) {
            options[i].setText(optionsArray[questionIndex][i]);
            buttonGroup.add(options[i]);
        }

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));

        for (int i = 0; i < options.length; i++) {
            optionsPanel.add(options[i]);
        }

        remove(questionTextArea);
        remove(optionsPanel);

        add(questionTextArea);
        add(optionsPanel);

        revalidate();
        repaint();
    }

    private void displayResult() {
        questionTextArea.setText("Exam Completed!\n\nYour Score: " + score + "/" + questions.length);

        for (int i = 0; i < options.length; i++) {
            options[i].setEnabled(false);
        }

        remove(submitButton);

        revalidate();
        repaint();
    }

    private void resetExam() {
        currentQuestion = 0;
        score = 0;
        timeRemaining = 1800;
        timer.stop();
    }

    public static void main(String[] args) {
        new OnlineExamSystem2();
    }
}
