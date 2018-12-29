package views;

import controllers.DataController;
import data.AnsweredQuestions;
import data.QuestionController;

import javax.swing.*;
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
    JFrame mainPanel = new JFrame();
    JFrame load = new JFrame();

    JButton nextBtn;
    String questionCode;
    private JLabel questionText;
    private ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
    Dimension screenSize;


    int licznik;

    public QuestionWindowFactory(){
        super("Test");
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        mainPanel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridLayout(1,1));
        mainPanel.setVisible(true);
        mainPanel.setSize(screenSize.width/2, screenSize.height/2); //przyjmuje polowe wielkosci
        mainPanel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu


        questionText = new JLabel("Pytanie :)");
        questionText.setVerticalAlignment(SwingConstants.TOP);
        questionText.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(questionText);
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

        mainPanel.revalidate();
        mainPanel.repaint();

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
        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        buttonList.add(nextBtn); //dla latwiejszego usuwania
        mainPanel.add(nextBtn); //dodaje do layoutu
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

    private boolean isSingleChoice(String answerCodes){
        return answerCodes.matches("((A\\d+|PA\\d+)\\|)+(A\\d+|PA\\d+)");
    }

    //to dziala, dodac ususwanie przycisku nextBtn ;)
    public void removeAllButtons(){
        for(AbstractButton button : buttonList)
            mainPanel.remove(button);
        buttonList.clear();
    }

    private void loadingPanelOn() {
        removeAllButtons();
        mainPanel.setLayout(new GridLayout(licznik,1));
        mainPanel.revalidate(); //wyczytalem, że to odswieza zawartosc ekranu - srednio odswieza, ale niech zostanie
        mainPanel.repaint();  //to samo co powyzej
    }

    private void loadingPanelOff() {
        mainPanel.setLayout(new GridLayout(licznik,1));
        mainPanel.revalidate(); //wyczytalem, że to odswieza zawartosc ekranu - srednio odswieza, ale niech zostanie
        mainPanel.repaint();  //to samo co powyzej
    }

    private void showAnswers(){
        AbstractButton checkBox;
        String anwerCodes = DataController.getQueAnsCodes(questionCode); //pobiera kody odpowiedzi
        anwerCodes = anwerCodes.replace("\"", ""); //usuwa ' " '
        ArrayList<String> answeredQuestions  = new ArrayList<>();; //przechowuje kody udzielonych odpowiedzi

        String[] groupedAnswerCodes = anwerCodes.split(";"); //grupuje kody odpowiedzi

        //grupowanie odpowiedzi, tak żeby można było udzielić tylko jednej odpowiedzi dla jednej grupy
        ArrayList<ButtonGroup> Buttgroups = new ArrayList<>(); //tablica grup
        for(String answCode : groupedAnswerCodes){
            ButtonGroup group = new ButtonGroup(); //grupa buttonow
            Map<String, String>  answers = getAnswers(answCode);

            for(Map.Entry<String, String> entry : answers.entrySet()) {
                licznik++;
                String ansCode = entry.getKey();
                String answer = entry.getValue();
                //System.out.println(ansCode+":"+answer);
                checkBox = new JCheckBox(answer); //dodaje tekst

                if(isSingleChoice(answCode)) //jezeli pytanie jest jednokrotnego wyboru, to dodaje do grupy
                    group.add(checkBox); //dodaje przycisk do grupy przyciskow, czyli tam gdzie mozna go kliknac tylko raz

                //dodaje listener do checboxa
                checkBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
                            System.out.println(questionCode + " " + ansCode);
                            answeredQuestions.add(ansCode);
                            AnsweredQuestions.addAnswer(questionCode, answeredQuestions);

                        }
                        else
                        {    //checkbox has been deselected
                            answeredQuestions.remove(ansCode);
                            AnsweredQuestions.addAnswer(questionCode, answeredQuestions);
                        }
                    }
                });
                buttonList.add(checkBox);
                mainPanel.add(checkBox); //dodaje przycisk do ekranu
            }
            Buttgroups.add(group); //dodaje grupę przyciskow do tablicy przyciskow
        }
    }

    private JFrame setLoadingFrame(){
        ImageIcon loading = new ImageIcon("src/main/resources/ajax-loader.gif"); //loading panel
        JLabel loadingPanel = new JLabel("Ładowanie... ", loading, JLabel.CENTER); //wlasciwy loading panel

        JFrame secPan = new JFrame();
        secPan = new JFrame();
        secPan.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        secPan.setLayout(new GridLayout(1,1));
        secPan.setVisible(true);
        secPan.setSize(screenSize.width/4, screenSize.height/4); //przyjmuje polowe wielkosci
        secPan.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu
        secPan.add(loadingPanel);
        secPan.setVisible(true);

        return secPan;
    }
}

