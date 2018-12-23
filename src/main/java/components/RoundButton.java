package components;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.awt.event.*;
 
public class RoundButton extends JButton {
 private int xCenter;
 private int yCenter;
	
  public RoundButton(String label) {
    super(label);
 
    setBackground(Color.lightGray);
    setFocusable(false);
 
    Dimension size = getPreferredSize();
    size.width = size.height = Math.max(size.width, size.height);
    setPreferredSize(size);
 
    setContentAreaFilled(false);
  }
 
  protected void paintComponent(Graphics g) {
    if (getModel().isArmed()) {
      g.setColor(Color.gray);
    } else {
      g.setColor(getBackground());
    }
    g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
    //g.setColor(Color.BLUE);
    //g.drawLine((getSize().width - 1)/2, (getSize().height - 1)/2, getSize().width - 1, getSize().height - 1);
    super.paintComponent(g);
  }
 
  protected void paintBorder(Graphics g) {
    g.setColor(Color.darkGray);
    g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
  }
 
  // Hit detection.
  Shape shape;
 
  public boolean contains(int x, int y) {
    // If the button has changed size,  make a new shape object.
    if (shape == null || !shape.getBounds().equals(getBounds())) {
      shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
    }
    return shape.contains(x, y);
  }
 
  public static void main(String[] args) {
 
    JFrame.setDefaultLookAndFeelDecorated(true);
    JFrame frame = new JFrame("Rounded Button Example");
    frame.setLayout(new FlowLayout());
 
    JButton b1 = new RoundButton("B1");
    JButton b2 = new RoundButton("B2");
 
    frame.add(b1);
    frame.add(b2);
 
    frame.setSize(300, 150);
    frame.setVisible(true);
  }

public int getxCenter() {
	return xCenter;
}

public void setxCenter(int xCenter) {
	this.xCenter = xCenter;
}

public int getyCenter() {
	return yCenter;
}

public void setyCenter(int yCenter) {
	this.yCenter = yCenter;
}
}
