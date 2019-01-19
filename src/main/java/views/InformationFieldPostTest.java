package views;

import controllers.QueWinFacController;
import static javax.swing.ScrollPaneConstants.*;
import controllers.StatisticsController;
import data.Texts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static data.Texts.*;
import static javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;

public class InformationFieldPostTest extends JFrame {
    JFrame panel = new JFrame();
    boolean isAvailableToNext = false;
    JPanel infromPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    GridBagConstraints c = new GridBagConstraints();

    JLabel nrTextField = new JLabel();

    public JButton nextBtn = new JButton("Przejdz do testu 2");
    //public JButton skitpBtn = new JButton("Pomiń test");

    Dimension screenSize;
    QuestionWindowFactory QWF;


    public InformationFieldPostTest(QuestionWindowFactory QWF){
        this.QWF = QWF;
        nrTextField.setText(convertToMultiline(textToShow));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/3), (int) (screenSize.height/2)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu


        //sprawdz wrap layout
        infromPanel.setLayout(new BorderLayout());

        nrTextField.setHorizontalAlignment(SwingConstants.CENTER);


        //infromPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
        //infromPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_END);
        infromPanel.add(nrTextField);
        infromPanel.setBorder(BorderFactory.createEmptyBorder(80, 10,80,10));

        //buttonsPanel.add(skitpBtn);
        buttonsPanel.add(nextBtn);

        panel.add(infromPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        nrTextField.setText(convertToMultiline(StatisticsController.getStatistics()));

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNextBtnClick();
            }
        });
    }

    void onNextBtnClick(){
        //pociągnięcie z bazy informacji o aktualnym użytkowniku
        if(!isAvailableToNext){
            nrTextField.setText(convertToMultiline(Texts.textToShow3));
            nextBtn.setText("Przejdź do oceny interakcji");
            isAvailableToNext = true;
        }
        else{
            QWF.unhide();
            panel.setVisible(false);
        }

    }

    public static String convertToMultiline(String orig)
    {
        String toReturn = orig.replaceAll("\n", "<br>");
        return "<html>" + toReturn;
    }

    public boolean getIsAvailableToNext(){
        return this.isAvailableToNext;
    }

}
