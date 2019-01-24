package application;

import controllers.DataController;
import controllers.NeptunQAController;
import controllers.QueWinFacController;
import data.Question;
import data.QuestionController;
import org.jscience.JScience;
import views.*;

import java.util.function.Function;

public class App2 {
    //komentarz tylko po to, żeby wprowadzić zmiany i sprawdzić czy jestem contributor'em
	public static void main(String[] args) {
		String currentEncoding = System.getProperty("file.encoding");
		System.setProperty("file.encoding", "UTF-8");
		LoginView loginView = new LoginView();
		System.setProperty("file.encoding", currentEncoding);
	   }

}
