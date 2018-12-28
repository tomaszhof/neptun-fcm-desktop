package application;

import controllers.DataController;
import data.Question;
import data.QuestionController;
import views.*;

public class App {
    //komentarz tylko po to, żeby wprowadzić zmiany i sprawdzić czy jestem contributor'em
	public static void main(String[] args) {
		QuestionWindowFactory questionWindowFactory = new QuestionWindowFactory();
		questionWindowFactory.createForCode("Q16");



		/*
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
	      */
	   }

}
