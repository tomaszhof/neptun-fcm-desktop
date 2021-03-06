package application;

import controllers.NeptunQAController;
import controllers.QueWinFacController;

public class App {
    //komentarz tylko po to, żeby wprowadzić zmiany i sprawdzić czy jestem contributor'em
	public static void main(String[] args) {
		String currentEncoding = System.getProperty("file.encoding");
		System.setProperty("file.encoding", "UTF-8");
		NeptunQAController qaController = NeptunQAController.get();
		String startQuestionCodePhase1 = qaController.getNextQuestionPhase1("_", null);
		String sampleNextQuestionCodePhase1 = qaController.getNextQuestionPhase1("PQ1", "PA7");
		String sampleLastQuestionCodePhase1 = qaController.getNextQuestionPhase1("Q17", "A87");
		System.out.println(startQuestionCodePhase1);
		System.out.println(sampleNextQuestionCodePhase1);
		System.out.println(sampleLastQuestionCodePhase1);


		String startQuestionCodePhase2 = qaController.getNextQuestionPhase2("_", null);
		String sampleNextQuestionCodePhase2 = qaController.getNextQuestionPhase2("Q25", "A149");
		String sampleLastQuestionCodePhase2 = qaController.getNextQuestionPhase2("Q37", "A204");

		QueWinFacController queWinFacController = new QueWinFacController();

		//restore default encoding for current operating system and JVM
		System.setProperty("file.encoding", currentEncoding);
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				ButtonCircleModel buttonCircleModel = new ButtonCircleModel();
//				buttonCircleModel.generateRandomNodes(15);
//				ButtonCircleView buttonCircleView = new ButtonCircleView(buttonCircleModel.getNodeLabels());
//				ButtonCircleController buttonCircleController = new ButtonCircleController(buttonCircleModel, buttonCircleView);
//				buttonCircleController.initController();
//				//MainView mainView = new MainView();
//			}
//		});


	   }

}
