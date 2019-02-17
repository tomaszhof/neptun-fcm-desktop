package views;

import controllers.DataController;
import controllers.QueWinFacController;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    private boolean register = false;

    JFrame panel = new JFrame();
    JPanel loginNrPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();


    GridBagConstraints c = new GridBagConstraints();

    JLabel nrTextField = new JLabel("Podaj login i hasło, aby się zalogować");
    JTextField loginField = new JTextField("Login");
    JTextField passwordField = new JTextField("Hasło");
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

        loginField.setPreferredSize(new Dimension(200, 50));
        loginField.setSize(200, 50);
        passwordField.setPreferredSize(new Dimension(200, 50));
        passwordField.setSize(200, 50);


        loginNrPanel.setLayout(new BorderLayout());

        nrTextField.setHorizontalAlignment(SwingConstants.CENTER);
        loginField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);

        loginNrPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_START);
        loginNrPanel.add(new JSeparator(SwingConstants.VERTICAL), BorderLayout.LINE_END);
        loginNrPanel.add(nrTextField, BorderLayout.PAGE_START);

        loginNrPanel.add(loginField, BorderLayout.LINE_START);
        loginNrPanel.add(passwordField, BorderLayout.LINE_END);

        loginNrPanel.setBorder(BorderFactory.createEmptyBorder(50, 0,10,0));

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
        DataController.setIsFirstPhaseStart(false);

        //pociągnięcie z bazy informacji o aktualnym użytkowniku
        String tmp = DataController.postLoginUser(loginField.getText(), passwordField.getText());
        if(tmp == null || tmp.equals("NoTlOgEd")){
            nrTextField.setText("Nieprawidłowy login lub hasło");
        }
        else{
            panel.setVisible(false);
            QueWinFacController queWinFacController = new QueWinFacController(2);
        }
    }

    void onGenBtnClick(){
        if(!register){
            DataController.setIsFirstPhaseStart(true);
            nrTextField.setText("Podaj login i haslo, aby się zarejestrować");
            genLoginNumbBtn.setText("Zarejestruj");
            register = true;
        }
        else {

            String result = DataController.postRegisterUser(loginField.getText(), passwordField.getText());
            if(!result.equals("cannot create user")){
                panel.setVisible(false);
                QueWinFacController queWinFacController = new QueWinFacController();
            }
            else {
                JOptionPane.showMessageDialog(null, "Taki użytkownik już istnieje, podaj inny login");
            }
        }
    }
}
