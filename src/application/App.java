package application;

import javax.swing.SwingUtilities;
import models.*;
import views.*;
import controllers.*;

public class App {

	public static void main(String[] args) {
	      SwingUtilities.invokeLater(new Runnable() {
			 public void run() {
				 ButtonCircleModel buttonCircleModel = new ButtonCircleModel();
				 buttonCircleModel.generateRandomNodes(15);
				 ButtonCircleView buttonCircleView = new ButtonCircleView(buttonCircleModel.getNodeLabels());
				 ButtonCircleController buttonCircleController = new ButtonCircleController(buttonCircleModel, buttonCircleView);
				 buttonCircleController.initController();
				 //MainView mainView = new MainView();
			 }
	      });
	   }

}
