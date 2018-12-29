package controllers;

import java.util.HashMap;
import java.util.Scanner;

public class NeptunQAController {

	private static NeptunQAController neptunQAController = null;
	
	private HashMap<String, String> rulesPhase1 = new HashMap<String, String>();
	private HashMap<String, String> rulesPhase2 = new HashMap<String, String>();
	
	public static NeptunQAController get() {
		if (neptunQAController == null)
			initialize();
		
		return neptunQAController;
	}
	
	private void loadRulesFromServer() {
		System.out.println("Loading rules from server...");
		String rulesContent = DataController.getRulesOfQuestions();
		System.out.println("done.");
		boolean secondPhase = false;
		Scanner scanner = new Scanner(rulesContent);
		while (scanner.hasNextLine()) {
		  String rule = scanner.nextLine();
		  if ((rule != null)&&(rule.contains("="))){
			  String qaMark = rule.split("=")[0];
			  String questionCode = rule.split("=")[1];
			  if ((qaMark.equals("_"))&& (!rulesPhase1.isEmpty())) {
				  secondPhase = true;
			  }
			  if (secondPhase) {
				  rulesPhase2.put(qaMark, questionCode);
				  System.out.println("Phase 2: " + rule);
			  } else {
				  rulesPhase1.put(qaMark, questionCode);
				  System.out.println("Phase 1: " + rule);
			  }
		  }
		}
		scanner.close();
	}
	private static void initialize() {
		neptunQAController = new NeptunQAController();
		neptunQAController.loadRulesFromServer();
	}
	
	public String getNextQuestionPhase1(String currQCode, String currAnswersCodes) {
		if ((currQCode.equals("_"))&&(rulesPhase1.containsKey(currQCode)))
			return rulesPhase1.get(currQCode);
		
		String[] answersCodes = currAnswersCodes.split(";");
		
		for (String answerCode : answersCodes) {
			String qaMark = currQCode + ":" + answerCode;
			if (rulesPhase1.containsKey(qaMark))
				return rulesPhase1.get(qaMark);
		}
		return null;
		
	}
	
	public String getNextQuestionPhase2(String currQCode, String currAnswersCodes) {
		if ((currQCode.equals("_"))&&(rulesPhase2.containsKey(currQCode)))
			return rulesPhase2.get(currQCode);
		
		String[] answersCodes = currAnswersCodes.split(";");
		
		for (String answerCode : answersCodes) {
			String qaMark = currQCode + ":" + answerCode;
			if (rulesPhase2.containsKey(qaMark))
				return rulesPhase2.get(qaMark);
		}
		return null;
		
	}
}
