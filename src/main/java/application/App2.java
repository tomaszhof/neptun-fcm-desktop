package application;

import controllers.DataController;
import views.*;

public class App2 {
	public static void main(String[] args) {

		String currentEncoding = System.getProperty("file.encoding");
		System.setProperty("file.encoding", "UTF-8");

		LoadingPanel loadingPanel = new
				LoadingPanel();

		DataController.getAllQAs(); // load QAs to local memory

		loadingPanel.hide();

		LoginView loginView = new LoginView();

		System.setProperty("file.encoding", currentEncoding);
	   }
}

