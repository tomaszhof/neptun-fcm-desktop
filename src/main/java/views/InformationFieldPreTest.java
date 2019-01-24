package views;

import controllers.QueWinFacController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static data.Texts.nextBtnText2;
import static data.Texts.textToShow;
import static data.Texts.textToShow2;

public class InformationFieldPreTest extends JFrame {
    JFrame panel = new JFrame();
    boolean isAvailableToNext = false;
    JPanel infromPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();
    QuestionWindowFactory QWF;


    GridBagConstraints c = new GridBagConstraints();

    JLabel nrTextField = new JLabel();

    public JButton nextBtn = new JButton("Dalej");
    public JButton skitpBtn = new JButton("Pomiń test");

    Dimension screenSize;


    public InformationFieldPreTest(QuestionWindowFactory QWF){
        this.QWF = QWF;
        QWF.hide();
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
        infromPanel.setBorder(BorderFactory.createEmptyBorder(80, 10,80,10));

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
        if(isAvailableToNext){
            panel.setVisible(false);
            QWF.unhide();
        }
        else{
            nrTextField.setText(convertToMultiline(textToShow2));
            nextBtn.setText(nextBtnText2);
            validate();
            repaint();
            isAvailableToNext = true;
        }
    }

    void onSkipBtnClick(){
        panel.setVisible(false);
        QWF.unhide();
    }

    public static String convertToMultiline(String orig)
    {
        return "<html>" + orig.replaceAll("\n", "<br>");
    }

    public boolean getIsAvailableToNext(){
        return this.isAvailableToNext;
    }
}
