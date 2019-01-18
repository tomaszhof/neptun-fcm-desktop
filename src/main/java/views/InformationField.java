package views;

import controllers.QueWinFacController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationField extends JFrame {
    JFrame panel = new JFrame();
    JPanel infromPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    String textToShow = "W teście 1 na ekranie monitora po okręgu rozmieszczonych jest 15 niebieskich okręgów ponumerowanych od 1 do 15. \n" +
            "Użytkownik do wykonania zadania dysponuje dobranym urządzeniem sterowniczym i wskaźnikiem w kształcie strzałki.\n" +
            "\n" +
            "Zadaniem użytkownika jest dosuwanie wskaźnika (i potwierdzanie tego dosunięcia) do okręgów w kolejności liczbowej od 1 do 15.\n" +
            "Każdą czynność dosunięcia i potwierdzenia dosunięcia użytkownik wykonuje za pomocą dobranego urządzenia i elementów sterowniczych w wybrany przez siebie sposób.\n" +
            "\n" +
            "Każde dosunięcie wskaźnika do kolejnego okręgu należy wykonać dokładnie i możliwie szybko.\n" +
            "\n" +
            "Test 1 rozpoczyna się w chwili wyboru okręgu oznaczonego liczbą 1. Test kończy się po wyborze okręgu o numerze 15.\n" +
            "\n" +
            "Po zakończeniu testu wyświetli się okno z wynikami testu. Wyniki testu należy zapisać lub zapamiętać w celu oceny interakcji.\n";

    GridBagConstraints c = new GridBagConstraints();

    JLabel nrTextField = new JLabel();

    public JButton nextBtn = new JButton("Przejdź do testu");
    public JButton skitpBtn = new JButton("Pomiń test");

    Dimension screenSize;


    public InformationField(){
        nrTextField.setText(convertToMultiline(textToShow));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/2), (int) (screenSize.height/2.5)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu

        infromPanel.setLayout(new BorderLayout());

        nrTextField.setHorizontalAlignment(SwingConstants.CENTER);


        infromPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
        infromPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_END);
        infromPanel.add(nrTextField, BorderLayout.NORTH);
        infromPanel.setBorder(BorderFactory.createEmptyBorder(80, 0,80,0));

        buttonsPanel.add(skitpBtn);
        buttonsPanel.add(nextBtn);

        panel.add(infromPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        skitpBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSkipBtnClick();
            }
        });

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNextBtnClick();
            }
        });
    }

    void onNextBtnClick(){
        //pociągnięcie z bazy informacji o aktualnym użytkowniku
        panel.setVisible(false);

    }

    void onSkipBtnClick(){
        panel.setVisible(false);

    }

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }
}
