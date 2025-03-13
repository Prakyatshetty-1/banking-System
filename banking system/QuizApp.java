import javax.swing.*;
import java.awt.*;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarOutputStream;

import com.google.gson.*;

class QuizApp extends JFrame {
    private int i= 0;
    private java.util.List<Questions> questionList;
    private JLabel questionLabel;
    private JLabel questionText;
    private JRadioButton option1, option2, option3, option4;
    private ButtonGroup optionsGroup;
    private JButton nextButton;

    QuizApp() {
        loadJSON();

        Font questionFont = new Font("Futura", Font.BOLD, 32);
        Font optionsFont = new Font("Calibri", Font.PLAIN, 28);

        questionLabel = new JLabel("Question", JLabel.CENTER);
        questionLabel.setFont(questionFont);
        questionLabel.setForeground(new Color(255, 255, 240));

        questionText = new JLabel("", JLabel.CENTER);
        questionText.setFont(optionsFont);
        questionText.setForeground(new Color(255, 255, 240));

        option1 = new JRadioButton();
        option2 = new JRadioButton();
        option3 = new JRadioButton();
        option4 = new JRadioButton();

        option1.setFont(optionsFont);
        option2.setFont(optionsFont);
        option3.setFont(optionsFont);
        option4.setFont(optionsFont);

        option1.setBackground(new Color(100, 180, 250));
        option2.setBackground(new Color(65, 105, 225));
        option3.setBackground(new Color(100, 180, 250));
        option4.setBackground(new Color(65, 105, 225));

        option1.setForeground(Color.WHITE);
        option2.setForeground(Color.WHITE);
        option3.setForeground(Color.WHITE);
        option4.setForeground(Color.WHITE);

        optionsGroup = new ButtonGroup();
        optionsGroup.add(option1);
        optionsGroup.add(option2);
        optionsGroup.add(option3);
        optionsGroup.add(option4);

        nextButton = new JButton("Next");
        nextButton.setFont(optionsFont);
        nextButton.setBackground(new Color(50, 205, 50));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));



        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(25, 40, 112));

        questionLabel.setBounds(110, 30, 600, 50);
        questionText.setBounds(110, 80, 600, 50);
        option1.setBounds(150, 160, 500, 50);
        option2.setBounds(150, 220, 500, 50);
        option3.setBounds(150, 280, 500, 50);
        option4.setBounds(150, 340, 500, 50);
        nextButton.setBounds(250, 420, 300, 50);

        c.add(questionLabel);
        c.add(questionText);
        c.add(option1);
        c.add(option2);
        c.add(option3);
        c.add(option4);
        c.add(nextButton);

        loadNextQuestion();
        nextButton.addActionListener(
                a->{
                    Checkans();
                    loadNextQuestion();
                }
        );

        setVisible(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Quiz Application");


    }

    void loadJSON()
    {
        String filename="quiz.json";
        Path p=Paths.get(filename);
        try{
            if(Files.exists(p))
            {
                String result=new String(Files.readAllBytes(p));
                Gson gson=new Gson();
                Questions[] obj1=gson.fromJson(result,Questions[].class);
                questionList=new ArrayList<>(Arrays.asList(obj1));

            }
            else{
                JOptionPane.showMessageDialog(null,"doesnt exist");
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
    }
    void loadNextQuestion()
    {
        if(i<questionList.size())
        {
            Questions obj= questionList.get(i);
            questionLabel.setText("Questions "+(i+1));
            questionText.setText(obj.question);
            option1.setText(obj.options[0]);
            option2.setText(obj.options[1]);
            option3.setText(obj.options[2]);
            option4.setText(obj.options[3]);
            optionsGroup.clearSelection();
            i++;
        }
        else{
            JOptionPane.showMessageDialog(null,"Congo you completed");
            System.exit(0);
        }
    }
    void Checkans()
    {
        Questions obj= questionList.get(i-1);
        String correct=obj.correctAnswer;
        String current ="";
        if(option1.isSelected())
        {
            current =option1.getText();
        }
        else if(option2.isSelected())
        {
            current =option2.getText();
        }
        else if(option3.isSelected())
        {
            current =option3.getText();
        }
        else if(option4.isSelected())
        {
            current =option4.getText();
        }
        if(current.equals(correct))
        {
            JOptionPane.showMessageDialog(null,"Correct answer");
        }
        else {
            JOptionPane.showMessageDialog(null,"worng ans");
        }
    }

    public static void main(String[] args) {
        new QuizApp();
    }
}

class Questions {
    String question;
    String[] options;
    String correctAnswer;
}
