package screens;

import constants.CommonConstants;
import database.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CreateQuestinsScreenGui extends JFrame {
    private JTextArea questionTextArea;
    private JTextField categoryTextField;
    private JRadioButton[] answerRadioButton;
    private JTextField[] answerTextField;
    private ButtonGroup buttonGroup;
    public CreateQuestinsScreenGui() {
        super("Create Questins");
        setSize(850, 565);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        answerRadioButton = new JRadioButton[4];
        answerTextField = new JTextField[4];
        buttonGroup = new ButtonGroup();

        addGuiComponents();
    }

    private void addGuiComponents() {
        //tyutułowy label
        JLabel titleLabel = new JLabel("Wprowadź swoje pytanie:");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
        titleLabel.setBounds(50,15,340,29);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        //label na pytania
        JLabel questionLabel = new JLabel("Pytanie:");
        questionLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        questionLabel.setBounds(50,60,140,20);
        questionLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(questionLabel);

        //przestrzeń na pyania
        questionTextArea = new JTextArea();
        questionTextArea.setFont(new Font("Tahoma", Font.BOLD, 14));
        questionTextArea.setBounds(50,90,340,120);
        questionTextArea.setForeground(CommonConstants.DARK_BLUE);
        questionTextArea.setLineWrap(true);
        questionTextArea.setWrapStyleWord(true);
        add(questionTextArea);

        //kategoria pytania
        JLabel categoryLabel = new JLabel("Kategoria pytania:");
        categoryLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
        categoryLabel.setBounds(50,250,140,20);
        categoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(categoryLabel);

        //kategoria pytania miejsce na wpisanie
        categoryTextField = new JTextField();
        categoryTextField.setFont(new Font("Tahoma", Font.BOLD, 14));
        categoryTextField.setBounds(50,280,340,30);
        categoryTextField.setForeground(CommonConstants.DARK_BLUE);
        add(categoryTextField);

        addAnswerComponents();

        //wprowadzenie odpowiedzi
        JButton submitButton = new JButton("Zatwierdź odpowiedź");
        submitButton.setFont(new Font("Tahoma", Font.BOLD, 20));
        submitButton.setBounds(300,440,260,40);
        submitButton.setForeground(CommonConstants.DARK_BLUE);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(validateInput()){
                    String question = questionTextArea.getText();
                    String category = categoryTextField.getText();
                    String[] answers = new String[answerTextField.length];
                    int correctIndex = 0;
                    for(int i = 0; i < answerTextField.length; i++){
                        answers[i] = answerTextField[i].getText();
                        if(answerRadioButton[i].isSelected()){
                            correctIndex = i;

                        }
                    }
                    //aktualizacja bazy danych
                    if(JDBC.saveQuestionCategoryAndAnswersToDatabase(question, category, answers, correctIndex)){
                        JOptionPane.showMessageDialog(CreateQuestinsScreenGui.this, "Udało się dodać pytanie");

                        resetFields();
                    }else{
                        JOptionPane.showMessageDialog(CreateQuestinsScreenGui.this, "Nie udało sie dodać pytania");
                    }
                }else{
                    JOptionPane.showMessageDialog(CreateQuestinsScreenGui.this, "ERROR: INVALID INPUT");
                }
            }
        });
        add(submitButton);

        //powrót do startowego ekranu
        JLabel goBackLabel = new JLabel("Powrót do ekranu startowego");
        goBackLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        goBackLabel.setBounds(300,500,260,20);
        goBackLabel.setForeground(CommonConstants.DARK_BLUE);
        goBackLabel.setHorizontalAlignment(SwingConstants.CENTER);
        goBackLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                //wyświetlanie kranu startowego
                TitleScreenGui titleScreenGui = new TitleScreenGui();
                titleScreenGui.setLocationRelativeTo(CreateQuestinsScreenGui.this);

                CreateQuestinsScreenGui.this.dispose();
                titleScreenGui.setVisible(true);
            }
        });
        add(goBackLabel);

    }
    private void addAnswerComponents() {
        //bloki na odpowiedzi
        int verticalSpacing = 100;

        for(int i = 0; i < 4; i++){
            JLabel answerLabel = new JLabel("Odpowiedź: " + (i+1));
            answerLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
            answerLabel.setBounds(470,60 + (i*verticalSpacing),100,20);
            answerLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
            add(answerLabel);

            //przyciski
            answerRadioButton[i] = new JRadioButton();
            answerRadioButton[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            answerRadioButton[i].setBounds(440,95 + (i*verticalSpacing),20,20);
            answerRadioButton[i].setBackground(null);
            buttonGroup.add(answerRadioButton[i]);
            add(answerRadioButton[i]);

            //odpowiedzi
            answerTextField[i] = new JTextField();
            answerTextField[i].setBounds(470,90 + (i*verticalSpacing),320,30);
            answerTextField[i].setFont(new Font("Tahoma", Font.BOLD, 14));
            answerTextField[i].setForeground(CommonConstants.DARK_BLUE);
            answerTextField[i].setBackground(CommonConstants.BRIGHT_YELLOW);
            add(answerTextField[i]);
        }

        answerRadioButton[0].setSelected(true);
    }
    private boolean validateInput(){
        //sprawdzenie czy okno nie jest puste
        if(questionTextArea.getText().replaceAll(" ", "").length() <= 0) return false;

        //sprawdzenie czy kategoria jest wybrana
        if(categoryTextField.getText().replaceAll(" ", "").length() <= 0) return false;

        //sprawdzenie czy pola z odpowiedziami nie są puste
        for(int i = 0; i < answerTextField.length; i++){
            if(answerTextField[i].getText().replaceAll(" ", "").length() <= 0)
                return false;
        }
        return true;
    }

    private void resetFields(){
        questionTextArea.setText("");
        categoryTextField.setText("");
        for(int i = 0; i < answerTextField.length; i++){
            answerTextField[i].setText("");
        }
    }
}
