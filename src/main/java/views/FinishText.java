package views;

import controllers.DataController;
import data.Texts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FinishText extends JFrame {
    JFrame panel = new JFrame();
    JPanel infromPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    GridBagConstraints c = new GridBagConstraints();

    JLabel TextField = new JLabel();

    public JButton nextBtn = new JButton("Zakończ działanie programu");


    Dimension screenSize;
    QuestionWindowFactory QWF;


    public FinishText(QuestionWindowFactory QWF){
        DataController.postTestResultUserAfter();
        this.QWF = QWF;
        QWF.hide();
        TextField.setText(convertToMultiline(Texts.textFinish));
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/3), (int) (screenSize.height/1.5)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu

        infromPanel.setLayout(new BorderLayout());

        TextField.setHorizontalAlignment(SwingConstants.CENTER);

        infromPanel.add(TextField);
        infromPanel.setBorder(BorderFactory.createEmptyBorder(80, 10,80,10));

        buttonsPanel.add(nextBtn);

        panel.add(infromPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        nextBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNextBtnClick();
            }
        });
    }

    void onNextBtnClick(){
//        QWF.dispose();
//        panel.dispose();
//        QWF.load.dispose();
//        dispose();
        System.exit(0);
    }

    public static String convertToMultiline(String orig)
    {
        String toReturn = orig.replaceAll("\n", "<br>");
        return "<html>" + toReturn;
    }

}
