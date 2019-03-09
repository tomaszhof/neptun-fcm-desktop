package views;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class LoadingPanel extends JFrame{
    private boolean register = false;

    JFrame panel = new JFrame("Ładowanie");
    JPanel loginNrPanel = new JPanel();

    JLabel loadingInfo = new JLabel("Trwa ładowanie pytań i odpowiedzi. Proszę czekać.");

    Dimension screenSize;


    public LoadingPanel(){
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        panel.setLayout(new BorderLayout());
        panel.setVisible(true);
        panel.setSize((int) (screenSize.width/3), (int) (screenSize.height/4)); //przyjmuje polowe wielkosci
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu

        loginNrPanel.setLayout(new BorderLayout());

        loadingInfo.setHorizontalAlignment(SwingConstants.CENTER);

        loginNrPanel.add(loadingInfo, BorderLayout.CENTER);
        
        loginNrPanel.setBorder(BorderFactory.createEmptyBorder(50, 0,10,0));
        
        panel.add(loginNrPanel, BorderLayout.CENTER);
    }

    public void hide(){
        panel.setVisible(false);
    }


}
