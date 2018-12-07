package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainView extends JPanel {

	public MainView() {
		setLayout(new FlowLayout());
		JButton button = new JButton("Test");
		add(button);
		JButton button2 = new JButton("Badanie");
		add(button2);
		
		JFrame frame = new JFrame("PanelsOnCircle");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
	}
}
