package application;

import views.*;


public class App2 {
    //komentarz tylko po to, żeby wprowadzić zmiany i sprawdzić czy jestem contributor'em
	public static void main(String[] args) {
		String currentEncoding = System.getProperty("file.encoding");
		System.setProperty("file.encoding", "UTF-8");
		LoginView loginView = new LoginView();
		System.setProperty("file.encoding", currentEncoding);
	   }

}
