package screens;

import constants.CommonConstants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TitleScreenGui extends JFrame {

    private JComboBox categoriesMenu;
    private JTextField numOfQuestionsTextField;

    public TitleScreenGui() {
        //konstruktor klasy która ma tytuł title screen
        super("Title Screen");

        //ustawienie wielkosci ekranu
        setSize(400, 565);

        //jak jets null to można samemu manipulowac elementy na ekranie
        setLayout(null);

        //wyśrodkowanie
        setLocationRelativeTo(null);

        //zmienianie rozmairu
        setResizable(false);

        //obsługa wychodzenia z aplikacj i kończenia proces
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //ustawienie kolorów i wyglądu
        getContentPane().setBackground(CommonConstants.LIGHT_BLUE);

        addGuiComponents();
    }
    //wszystkie komponenty poczotkowego ekranu
    private void addGuiComponents() {
        JLabel titleLabel = new JLabel("Milionerzy");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setBounds(0,20,390,45);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(titleLabel);

        JLabel chooseCategoryLabel = new JLabel("Choose difficulty");
        chooseCategoryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        chooseCategoryLabel.setBounds(0,90,400,20);
        chooseCategoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
        chooseCategoryLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(chooseCategoryLabel);

        //poziom trudności
        String[] categories = new String[]{"Easy", "Medium", "Hard"};

        categoriesMenu = new JComboBox(categories);
        categoriesMenu.setBounds(20,120,337,45);
        categoriesMenu.setForeground(CommonConstants.DARK_BLUE);
        add(categoriesMenu);

        //ilosc pytan
        JLabel numOfQuestionsLabel = new JLabel("Number of questions");
        numOfQuestionsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsLabel.setBounds(10,190,172,20);
        numOfQuestionsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        numOfQuestionsLabel.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(numOfQuestionsLabel);

        //okienko na wybor ilosci pytan
        numOfQuestionsTextField = new JTextField("10");
        numOfQuestionsTextField.setFont(new Font("Arial", Font.BOLD, 16));
        numOfQuestionsTextField.setBounds(200,190,148,26);
        numOfQuestionsTextField.setHorizontalAlignment(SwingConstants.CENTER);
        numOfQuestionsTextField.setForeground(CommonConstants.DARK_BLUE);
        add(numOfQuestionsTextField);

        // start
        JButton startButton = new JButton("Start");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBounds(65,290,262,45);
        startButton.setBackground(CommonConstants.BRIGHT_YELLOW);
        startButton.setForeground(CommonConstants.LIGHT_BLUE);
        add(startButton);

        // wyjście
        JButton exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.BOLD, 16));
        exitButton.setBounds(65,350,262,45);
        exitButton.setBackground(CommonConstants.RED);
        exitButton.setForeground(CommonConstants.BRIGHT_YELLOW);
        add(exitButton);

        //wprowadzanie pytania do bazy danych (przycisk)
        JButton createAQuestionButton = new JButton("Create a question");
        createAQuestionButton.setFont(new Font("Arial", Font.BOLD, 16));
        createAQuestionButton.setBounds(65,420,262,45);
        createAQuestionButton.setBackground(CommonConstants.LIGHT_BLUE_2);
        createAQuestionButton.setForeground(CommonConstants.LIGHT_GREY);
        add(createAQuestionButton);
    }
}
