package views;

import controllers.DataController;
import data.QuestionController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionWindowFactory extends JFrame {
    QuestionController qc;
    private JLabel questionText;
    private JPanel MainPanel;
    private ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
    Dimension screenSize;

    public QuestionWindowFactory(QuestionController qc){
        super("Test");
        this.qc = qc;
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1,1));
        setVisible(true);
        setSize(screenSize.width/2, screenSize.height/2); //przyjmuje polowe wielkosci
        setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu


        questionText = new JLabel("Pytanie :)");
        questionText.setVerticalAlignment(SwingConstants.TOP);
        questionText.setHorizontalAlignment(SwingConstants.CENTER);

        add(questionText);
    }

    public void createForCode(String questionCode){
        //ustawianie pytania
        questionCode = questionCode.replace("\uFEFF", ""); //usuwa spacje tak na wszelki wypadek
        String question = DataController.getQuestion(questionCode); //pytanie
        questionText.setText(question); //ustawia pole tekstowe na pytanie
        ButtonGroup group = new ButtonGroup(); //grupa Radiobuttons
/*
        JCheckBox rb2 = new JCheckBox("rabbit2");
        JCheckBox rb3 = new JCheckBox("rabbi3");
        JCheckBox rb1 = new JCheckBox("rabbit1");
        group.add(rb1);
        group.add(rb2);
        group.add(rb3);
        add(rb1);
        add(rb2);
        add(rb3);
*/
        AbstractButton checkBox;

        //wydobywanie odpowiedzi:
        Map<String, String>  answers = getAnswers(questionCode);
        setLayout(new GridLayout(answers.size()+1,1));

        for(Map.Entry<String, String> entry : answers.entrySet()) {
            String ansCode = entry.getKey();
            String answer = entry.getValue();
            System.out.println(ansCode+":"+answer);
            checkBox = new JCheckBox(ansCode+":"+answer);
            add(checkBox);
            revalidate();
            repaint();
        }
    }

    private Map<String, String> getAnswers(String questionCode){
        Map<String, String>  answers = new HashMap<String, String>(); //mapa zawierajaca kodOdpowiedz:Odpowiedz
        String answerCodes = DataController.getQueAnsCodes(questionCode);
        System.out.println(answerCodes);

        Pattern pattern; //pattern do regexu wykrywającego odpoweidzi do pytania
        if(questionCode.contains("PQ")){
            System.out.println("PQ");
            pattern = Pattern.compile("PA\\d+");
        }
        else{
            System.out.println("Q");
            pattern = Pattern.compile("A\\d+");
        }
        Matcher m = pattern.matcher(answerCodes);

        //znajduje wszystkie dopasowania
        while (m.find()) {
            String key = m.group();
            String answer = DataController.getAnswer(m.group());
            System.out.println(key + ":" + answer);
            answers.put(key, answer);
        }
        return answers;
    }
}

