import java.awt.*;
import java.awt.event.*;

public class StudentGradeManagerAWT extends Frame implements ActionListener {
    Label nameLabel, gradesLabel;
    TextField nameField, gradesField;
    Button submitButton, showReportButton;
    TextArea reportArea;
    final int MAX_STUDENTS = 10;
    String[] studentNames = new String[MAX_STUDENTS];
    double[][] studentGrades = new double[MAX_STUDENTS][];
    double[] averages = new double[MAX_STUDENTS];
    double[] highs = new double[MAX_STUDENTS];
    double[] lows = new double[MAX_STUDENTS];
    int studentCount = 0;

    public StudentGradeManagerAWT() {
        // Frame settings
        setTitle("Student Grade Manager (AWT)");
        setSize(500, 400);
        setLayout(new FlowLayout());
        setVisible(true);

        // GUI Components
        nameLabel = new Label("Student Name:");
        nameField = new TextField(20);
        gradesLabel = new Label("Grades (comma-separated):");
        gradesField = new TextField(20);
        submitButton = new Button("Submit");
        showReportButton = new Button("Show Summary");
        reportArea = new TextArea(15, 50);

        // Add components
        add(nameLabel);
        add(nameField);
        add(gradesLabel);
        add(gradesField);
        add(submitButton);
        add(showReportButton);
        add(reportArea);

        // Add listeners
        submitButton.addActionListener(this);
        showReportButton.addActionListener(this);

        // Window close
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            if (studentCount >= MAX_STUDENTS) {
                reportArea.setText("Maximum number of students reached.");
                return;
            }

            String name = nameField.getText().trim();
            String[] gradeStrings = gradesField.getText().split(",");
            double[] grades = new double[gradeStrings.length];

            try {
                for (int i = 0; i < gradeStrings.length; i++) {
                    grades[i] = Double.parseDouble(gradeStrings[i].trim());
                }
            } catch (NumberFormatException ex) {
                reportArea.setText("Invalid grade entered. Use only numbers separated by commas.");
                return;
            }

            // Store data
            studentNames[studentCount] = name;
            studentGrades[studentCount] = grades;

            // Calculate stats
            double sum = 0, max = grades[0], min = grades[0];
            for (double g : grades) {
                sum += g;
                if (g > max) max = g;
                if (g < min) min = g;
            }
            averages[studentCount] = sum / grades.length;
            highs[studentCount] = max;
            lows[studentCount] = min;

            studentCount++;
            nameField.setText("");
            gradesField.setText("");
            reportArea.setText("Student added successfully!");
        } else if (e.getSource() == showReportButton) {
            StringBuilder sb = new StringBuilder();
            sb.append("===== Student Grade Report =====\n\n");

            for (int i = 0; i < studentCount; i++) {
                sb.append("Name: ").append(studentNames[i]).append("\n");
                sb.append("Grades: ");
                for (double g : studentGrades[i]) sb.append(g).append(" ");
                sb.append("\nAverage: ").append(averages[i]);
                sb.append("\nHighest: ").append(highs[i]);
                sb.append("\nLowest: ").append(lows[i]);
                sb.append("\n\n");
            }

            reportArea.setText(sb.toString());
        }
    }

    public static void main(String[] args) {
        new StudentGradeManagerAWT(); // Runs the GUI
    }
}
