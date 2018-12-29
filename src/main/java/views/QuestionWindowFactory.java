package views;

import controllers.DataController;
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
    ImageIcon loading = new ImageIcon("src/main/resources/ajax-loader.gif"); //loading panel
    JLabel loadingPanel = new JLabel("Ładowanie... ", loading, JLabel.CENTER); //wlasciwy loading panel

    QuestionController qc;
    private JLabel questionText;
    private JPanel MainPanel;
    private ArrayList<AbstractButton> buttonList = new ArrayList<AbstractButton>();
    Dimension screenSize;

    public QuestionWindowFactory(){
        super("Test");
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
        loadingPanelOn();
        //musi sie to okno czyscic przy kazdym wywolaniu tej metody
        ArrayList<String> answeredQuestions = new ArrayList<>(); //przechowuje kody udzielonych odpowiedzi

        int licznik = 2;
        //ustawianie pytania
        AbstractButton checkBox;
        String question = DataController.getQuestion(questionCode); //pytanie
        questionText.setText(question); //ustawia pole tekstowe na pytanie

        String anwerCodes = DataController.getQueAnsCodes(questionCode); //pobiera kody odpowiedzi
        anwerCodes = anwerCodes.replace("\"", ""); //usuwa ' " '
        String[] groupedAnswerCodes = anwerCodes.split(";"); //grupuje kody odpowiedzi

        //grupowanie odpowiedzi, tak żeby można było udzielić tylko jednej odpowiedzi dla jednej grupy
        ArrayList<ButtonGroup> Buttgroups = new ArrayList<>(); //tablica grup
        for(String answCode : groupedAnswerCodes){
            ButtonGroup group = new ButtonGroup(); //grupa buttonow
            Map<String, String>  answers = getAnswers(answCode);

            for(Map.Entry<String, String> entry : answers.entrySet()) {
                licznik++;
                setLayout(new GridLayout(licznik,1));
                String ansCode = entry.getKey();
                String answer = entry.getValue();
                System.out.println(ansCode+":"+answer);
                checkBox = new JCheckBox(ansCode+":"+answer); //dodaje tekst

                if(isSingleChoice(answCode)) //jezeli pytanie jest jednokrotnego wyboru, to dodaje do grupy
                    group.add(checkBox); //dodaje przycisk do grupy przyciskow, czyli tam gdzie mozna go kliknac tylko raz

                //dodaje listener do przycisku
                checkBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
//                            answeredQuestions.add(ansCode); //dodaje kod odpowiedzi
//                            displayAll(answeredQuestions);
                        } else
                            {    //checkbox has been deselected
//                            answeredQuestions.remove(ansCode); //usuwa kod odpowiedzi
//                            displayAll(answeredQuestions);
                        }
                    }
                });
                buttonList.add(checkBox);
                add(checkBox); //dodaje przycisk do ekranu
            }
            Buttgroups.add(group); //dodaje grupę przyciskow do tablicy przyciskow
        }

        addNextBtn();

        loadingPanelOff();
        revalidate(); //wyczytalem, że to odswieza zawartosc ekranu - srednio odswieza, ale niech zostanie
        repaint();  //to samo co powyzej
    }

    private void addNextBtn(){
        JButton next = new JButton("Dalej");
        next.setBackground(Color.cyan);
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pressed");
            }
        });


        buttonList.add(next); //dla latwiejszego usuwania
        add(next); //dodaje do layoutu
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

    private void displayAll(ArrayList<String> answeredQuestions){
        for(String tmp : answeredQuestions)
            System.out.println(tmp);
        System.out.println("\n\n");
    }

    //to dziala, dodac ususwanie przycisku next ;)
    public void removeAllButtons(){
        for(AbstractButton button : buttonList)
            remove(button);
    }

    private void loadingPanelOn() {
        add(loadingPanel);
    }
    private void loadingPanelOff() {
        remove(loadingPanel);
    }
}

