package views;

import controllers.DataController;
import data.AnsweredQuestions;

import javax.swing.*;

import components.AnswerCheckBox;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QuestionWindowFactory extends JFrame {
    JFrame panel = new JFrame();
    JFrame load = new JFrame();

    JPanel questionPanel = new JPanel();
    JPanel answersPanel = new JPanel();
    JPanel nextBtnPanel = new JPanel();


    JButton nextBtn;
    String questionCode;
    private JLabel questionText;
    private ArrayList<Component> componentList = new ArrayList<>();

    Dimension screenSize;
    ArrayList<String> answeredQuestions  = new ArrayList<>();; //przechowuje kody udzielonych odpowiedzi


    int licznik;

    public QuestionWindowFactory(){
        super("Test");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        answersPanel.add(nextBtnPanel);

        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/1.5), (int) (screenSize.height/1.5)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu

        answersPanel.setLayout(new GridLayout(1,1));
        //answersPanel.setSize((int) ((double)screenSize.width/3), (int) ((double)screenSize.height/3)); //przyjmuje polowe wielkosci
        answersPanel.setVisible(true);
        answersPanel.setBorder(BorderFactory.createEmptyBorder(0, 10,10,0));

        questionPanel.setLayout(new GridLayout(1,1));
        questionPanel.setVisible(true);
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 0,10,0));

        questionText = new JLabel("Pytanie :)");
        questionText.setVerticalAlignment(SwingConstants.TOP);
        questionText.setHorizontalAlignment(SwingConstants.CENTER);
        questionText.setVisible(true);
        questionText.setFont(new Font(questionText.getName(), Font.BOLD, 15));
        questionPanel.add(questionText);

        panel.add(questionPanel, BorderLayout.PAGE_START);
        panel.add(answersPanel, BorderLayout.CENTER);
        panel.add(nextBtnPanel, BorderLayout.PAGE_END);
        load = setLoadingFrame();
    }

    public void createForCode(String questionCode){
        load.setVisible(true);

        licznik = 2;

        this.questionCode = questionCode;
        loadingPanelOn();
        //musi sie to okno czyscic przy kazdym wywolaniu tej metody

        //ustawianie pytania
        String question = DataController.getQuestion(questionCode); //pytanie
        questionText.setText(question); //ustawia pole tekstowe na pytanie

        answersPanel.revalidate();
        answersPanel.repaint();

        showAnswers();

        addNextBtn();

        loadingPanelOff();
        load.setVisible(false);
    }

    public JButton getNextBtn(){
        return this.nextBtn;
    }

    private void addNextBtn(){
        nextBtn = new JButton("Dalej");
        nextBtn.setBackground(Color.cyan);

        Dimension dim = new Dimension(panel.getWidth()-50, 50);
        nextBtn.setPreferredSize(dim);
        nextBtn.setMaximumSize(dim);
        nextBtn.setMinimumSize(dim);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	answeredQuestions = new ArrayList<>();
            }
        });


        componentList.add(nextBtn); //dla latwiejszego usuwania

        nextBtnPanel.add(nextBtn);
    }

    private Map<String, String> getAnswers(String anwersCodes){
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

    //zwraca tablice odpoweidzi
    private ArrayList<Answer> getAnswers2(String anwersCodes){
        ArrayList<Answer> answers = new ArrayList<>();

        Pattern pattern; //pattern do regexu wykrywającego odpoweidzi do pytania
        pattern = Pattern.compile("(A\\d+)|(PA\\d+)");

        Matcher m = pattern.matcher(anwersCodes);

        //znajduje wszystkie dopasowania
        while (m.find()) {
            String key = m.group();
            String answer = DataController.getAnswer(m.group());
            answer = answer.replace("\"", ""); //wyrzuca " z odpowiedzi
            System.out.println(key + ":" + answer);
            answers.add(new Answer(key, answer));
        }
        return answers;
    }

    private boolean isSingleChoice(String answerCodes){
        return answerCodes.matches("((A\\d+|PA\\d+)\\|)+(A\\d+|PA\\d+)");
    }

    public void removeAllButtons(){
        for(Component component : componentList){
            answersPanel.remove(component);
            nextBtnPanel.remove(component);
        }

        componentList.clear();
    }

    private void loadingPanelOn() {
        removeAllButtons();
        answersPanel.setLayout(new GridLayout(licznik,1));
        answersPanel.revalidate(); //wyczytalem, że to odswieza zawartosc ekranu - srednio odswieza, ale niech zostanie
        answersPanel.repaint();  //to samo co powyzej
    }

    private void loadingPanelOff() {
        answersPanel.setLayout(new GridLayout(licznik,1));
        answersPanel.revalidate(); //wyczytalem, że to odswieza zawartosc ekranu - srednio odswieza, ale niech zostanie
        answersPanel.repaint();  //to samo co powyzej
    }

    private void showAnswers(){
        AnswerCheckBox checkBox;
        String anwerCodes = DataController.getQueAnsCodes(questionCode); //pobiera kody odpowiedzi
        anwerCodes = anwerCodes.replace("\"", ""); //usuwa ' " '
        

        String[] groupedAnswerCodes = anwerCodes.split(";"); //grupuje kody odpowiedzi

        //grupowanie odpowiedzi, tak żeby można było udzielić tylko jednej odpowiedzi dla jednej grupy
        ArrayList<ButtonGroup> Buttgroups = new ArrayList<>(); //tablica grup
        for(String answCode : groupedAnswerCodes){
            ButtonGroup group = new ButtonGroup(); //grupa buttonow
            //Map<String, String>  answers = getAnswers(answCode);
            ArrayList<Answer> answers = this.getAnswers2(answCode);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            answersPanel.add(separator);
            componentList.add(separator);
            licznik++;

            for(Answer answerTmp : answers) {
                licznik++;
                String ansCode = answerTmp.getAnswerCode();
                String answer = answerTmp.getAnswer();

                //System.out.println(ansCode+":"+answer);
                checkBox = new AnswerCheckBox(ansCode, answer); //dodaje tekst
                checkBox.setBorder(BorderFactory.createEmptyBorder(2, 0,2,0));

                if(isSingleChoice(answCode)) //jezeli pytanie jest jednokrotnego wyboru, to dodaje do grupy
                    group.add(checkBox); //dodaje przycisk do grupy przyciskow, czyli tam gdzie mozna go kliknac tylko raz

                checkBox.setSize(checkBox.getWidth(), 50);
                //dodaje listener do checboxa
                checkBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                    	AnswerCheckBox selectedAnswer = (AnswerCheckBox) e.getSource();
                        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                            System.out.println(questionCode + " ::: " + selectedAnswer.getAnswerCode());
                            answeredQuestions.add(selectedAnswer.getAnswerCode());
                            AnsweredQuestions.addAnswer(questionCode, answeredQuestions);

                        }
                        else
                        {    //checkbox has been deselected
                            answeredQuestions.remove(selectedAnswer.getAnswerCode());
                            AnsweredQuestions.addAnswer(questionCode, answeredQuestions);
                        }
                    }
                });
                componentList.add(checkBox);
                answersPanel.add(checkBox); //dodaje przycisk do ekranu
            }

            Buttgroups.add(group); //dodaje grupę przyciskow do tablicy przyciskow
        }
    }

    private JFrame setLoadingFrame(){
        //ImageIcon loading = new ImageIcon("src/main/resources/ajax-loader.gif"); //loading panel
        //JLabel loadingPanel = new JLabel("Ładowanie... ", loading, JLabel.CENTER); //wlasciwy loading panel

        JFrame secPan = new JFrame("Ładowanie");
        secPan.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        secPan.setLayout(new GridLayout(1,1));
        secPan.setVisible(true);
        secPan.setSize(screenSize.width/4, screenSize.height/4); //przyjmuje polowe wielkosci
        secPan.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu
        //secPan.add(loadingPanel);
        secPan.add(new JLabel("Ładowanie", SwingConstants.CENTER));
        secPan.setVisible(true);

        return secPan;
    }

    public void hide(){
        panel.setVisible(false);
    }

    public void unhide(){
        panel.setVisible(true);
    }
}

class Answer{
    String answerCode;
    String answer;

    public Answer(String answerCode, String answer) {
        this.answerCode = answerCode;
        this.answer = answer;
    }

    public String getAnswerCode() {
        return answerCode;
    }

    public String getAnswer() {
        return answer;
    }
}

