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
        int licznik = 2;
        //ustawianie pytania
        AbstractButton checkBox;
        questionCode = questionCode.replace("\uFEFF", ""); //usuwa spacje tak na wszelki wypadek
        String question = DataController.getQuestion(questionCode); //pytanie
        questionText.setText(question); //ustawia pole tekstowe na pytanie

        String anwerCodes = DataController.getQueAnsCodes(questionCode); //pobiera kody odpowiedzi
        anwerCodes = anwerCodes.replace("\"", "");
        String[] groupedAnswerCodes = anwerCodes.split(";"); //grupuje kody odpowiedzi

        //grupowanie odpowiedzi, tak żeby można było udzielić tylko jednej odpowiedzi dla jednej grupy
        ArrayList<ButtonGroup> Buttgroups = new ArrayList<>(); //tablica grup
        for(String answCode : groupedAnswerCodes){
            ButtonGroup group = new ButtonGroup(); //grupa buttonow
            Map<String, String>  answers = getAnswers2(answCode);

            for(Map.Entry<String, String> entry : answers.entrySet()) {
                licznik++;
                setLayout(new GridLayout(licznik,1));

                String ansCode = entry.getKey();
                String answer = entry.getValue();
                System.out.println(ansCode+":"+answer);
                checkBox = new JCheckBox(ansCode+":"+answer);
                group.add(checkBox);
                add(checkBox);
                revalidate();
                repaint();
            }
            Buttgroups.add(group);
        }

/*
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
        */
    }

    private Map<String, String> getAnswers(String questionCode){
        Map<String, String>  answers = new HashMap<String, String>(); //mapa zawierajaca kodOdpowiedz:Odpowiedz
        String answerCodes = DataController.getQueAnsCodes(questionCode);
        //System.out.println(answerCodes);

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
            answer = answer.replace("\"", ""); //wyrzuca " z odpowiedzi
            System.out.println(key + ":" + answer);
            answers.put(key, answer);
        }
        return answers;
    }

    private Map<String, String> getAnswers2(String anwersCodes){
        Map<String, String>  answers = new HashMap<String, String>(); //mapa zawierajaca kodOdpowiedz:Odpowiedz

        Pattern pattern; //pattern do regexu wykrywającego odpoweidzi do pytania
        pattern = Pattern.compile("(A\\d+)|(PA\\d+)");

        Matcher m = pattern.matcher(anwersCodes);

        //znajduje wszystkie dopasowania
        while (m.find()) {
            String key = m.group();
            String answer = DataController.getAnswer(m.group());
            answer = answer.replace("\"", ""); //wyrzuca " z odpowiedzi
            System.out.println(key + ":" + answer);
            answers.put(key, answer);
        }
        return answers;
    }


}

