package views;

import controllers.QueWinFacController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    JFrame panel = new JFrame();
    JPanel loginNrPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    GridBagConstraints c = new GridBagConstraints();

    JLabel nrTextField = new JLabel("Podaj numer użytkownika");
    JTextField loginField = new JTextField("");
    JButton nextBtn = new JButton("Dalej");
    JButton genLoginNumbBtn = new JButton("Nie posiadam numeru do logowania");

    Dimension screenSize;


    public LoginView(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/3), (int) (screenSize.height/2.5)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu

        loginNrPanel.setLayout(new BorderLayout());

        nrTextField.setHorizontalAlignment(SwingConstants.CENTER);
        loginField.setHorizontalAlignment(SwingConstants.CENTER);

        loginNrPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
        loginNrPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_END);
        loginNrPanel.add(nrTextField, BorderLayout.NORTH);
        loginNrPanel.add(loginField, BorderLayout.CENTER);
        loginNrPanel.setBorder(BorderFactory.createEmptyBorder(80, 0,80,0));

        buttonsPanel.add(genLoginNumbBtn);
        buttonsPanel.add(nextBtn);

        panel.add(loginNrPanel, BorderLayout.CENTER);
        panel.add(buttonsPanel, BorderLayout.SOUTH);

        genLoginNumbBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onGenBtnClick();
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
        QueWinFacController queWinFacController = new QueWinFacController(2);
        //int userNumber = Integer.parseInt(loginField.getText());

    }

    void onGenBtnClick(){
        panel.setVisible(false);
        QueWinFacController queWinFacController = new QueWinFacController();
    }
}
