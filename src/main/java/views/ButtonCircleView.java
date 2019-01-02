package views;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import javax.swing.*;

import components.RoundButton;
import controllers.StatisticsController;

public class ButtonCircleView extends JPanel {
   private static final int RADIUS = 200;
   private static final int SLICES = 18;
   private static final int SIDE = 50;
   private static final int GAP = SIDE/2;
   private static final int PREF_W = 2 * RADIUS + 2 * GAP;
   private static final int PREF_H = PREF_W;
   
   private List<RoundButton> buttons;
   private JFrame frame;
   private Timer timer;
   private JLabel label;

   private int lastX = 0;
   private int lastY = 0;

   public ButtonCircleView() {
	   buttons = new ArrayList<RoundButton>();
	   timer = new Timer(100, null);
      JPanel panel = new JPanel(null);

      for (int i = 0; i < SLICES; i++) {
         double phi = (i * Math.PI * 2) / SLICES; 
         int x = (int) (RADIUS * Math.sin(phi) + RADIUS - SIDE / 2) + GAP;
         int y = (int) (RADIUS * Math.cos(phi) + RADIUS - SIDE / 2) + GAP;

         RoundButton roundButton = new RoundButton(""+(i+1));
         roundButton.setBounds(x, y, SIDE, SIDE);

         panel.add(roundButton);
         buttons.add(roundButton);
      }
      
      label = new JLabel("TEST");
      label.setBounds(PREF_W - 3*GAP, PREF_W - 3*GAP, 3*GAP, 3*GAP);
      panel.add(label);
   
      setLayout(new BorderLayout());
      add(panel);
   }
   
   public ButtonCircleView(List<Integer> nodes) {
	   buttons = new ArrayList<RoundButton>();
	   timer = new Timer(100, null);
      JPanel panel = new JPanel(null);
      for (int i = 0; i < nodes.size(); i++) {
         double phi = (i * Math.PI * 2) / nodes.size(); 
         int x = (int) (RADIUS * Math.sin(phi) + RADIUS - SIDE / 2) + GAP;
         int y = (int) (RADIUS * Math.cos(phi) + RADIUS - SIDE / 2) + GAP;

         RoundButton roundButton = new RoundButton(""+nodes.get(i));
         roundButton.setBounds(x, y, SIDE, SIDE);
         roundButton.setxCenter(x+SIDE/2);
         roundButton.setyCenter(y+SIDE/2);
         panel.add(roundButton);
         buttons.add(roundButton);
      }
      
      label = new JLabel("TEST");
      label.setBounds(PREF_W - 3*GAP, PREF_W - 3*GAP, 3*GAP, 3*GAP);
      panel.add(label);
      
      setLayout(new BorderLayout());
      add(panel);
   }

   @Override
   public Dimension getPreferredSize() {
      return new Dimension(PREF_W, PREF_H);
   }

   public void createAndShowGui() {
      //ButtonCircleView mainPanel = new ButtonCircleView();
      frame = new JFrame("PanelsOnCircle");
      //przenies do controllera
      frame.addMouseMotionListener(new MouseMotionAdapter() {
    	  public void mouseMoved(MouseEvent e) {
    	     StatisticsController.makeCalculations(e.getX(), e.getY(), lastX, lastY);

    	     //System.out.println("Mouse moved " + e.getX() + " " + e.getY());

    	     lastX = e.getX();
    	     lastY = e.getY();
    	    }
      });
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(this);
      frame.pack();
      frame.setLocationByPlatform(true);
      frame.setVisible(true);
      frame.setLocationRelativeTo(null); //do wyswiatlanie po srodu ekranu
      
   }

   public void hideGui(){
      frame.setVisible(false);
   }

   public List<RoundButton> getButtons() {
	return buttons;
}

   public void setButtons(List<RoundButton> buttons) {
	this.buttons = buttons;
}

   public Timer getTimer() {
	return timer;
}

   public JLabel getLabel() {
	return label;
}

   public void setLabel(String text) {
	this.label.setText(text);
}
   
}