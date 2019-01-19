package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import components.RoundButton;
import models.ButtonCircleModel;
import models.ButtonModel;
import views.ButtonCircleView;
import views.InformationFieldPostTest;
import views.QuestionWindowFactory;

public class ButtonCircleController {

	private ButtonCircleModel buttonCircleModel;
	private ButtonCircleView buttonCircleView;
	private QuestionWindowFactory QWF;

	public ButtonCircleController() {
		
	}
	
	public ButtonCircleController(ButtonCircleModel model, ButtonCircleView view) {
		buttonCircleModel = model;
		buttonCircleView = view;
	}

	public ButtonCircleController(ButtonCircleModel model, ButtonCircleView view, QuestionWindowFactory QWF) {
		buttonCircleModel = model;
		buttonCircleView = view;
		this.QWF = QWF;
		QWF.hide();
	}

	public void initController() {
		buttonCircleView.createAndShowGui();
		addListeners();
		createButtonsModel();
	}
	
	public void createButtonsModel() {
		List<ButtonModel> buttonModelList = new ArrayList<ButtonModel>();
		List<RoundButton> buttons = buttonCircleView.getButtons();
		for(int i = 0; i < buttons.size(); i++) {
			RoundButton button = buttons.get(i);
			ButtonModel buttonModel = new ButtonModel(button.getText(), button.getxCenter(), button.getyCenter());
			buttonModelList.add(buttonModel);

			//tutaj pomiar najkrotszej sciezki
			StatisticsController.addNode(button.getText(), button.getxCenter(), button.getyCenter());
			//pomiar koniec
		}
		StatisticsController.calcShortestPaths(); // liczy dlugosci pomiedzy wszystkimi parami

		buttonCircleModel.setNodes(buttonModelList);
	}
	
	public void addListeners() {
		List<RoundButton> buttons = buttonCircleView.getButtons();
		for(int i = 0; i < buttons.size(); i++) {
			RoundButton button = buttons.get(i);
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent actionEvent) {
					onButtonClick(actionEvent.getSource());
				}
	        });
		}
	}
	
	private void onButtonClick(Object object) {
		String id = "" + buttonCircleModel.getNextButtonId();
		RoundButton button = (RoundButton) object;
		if(id.equals(button.getText())) {
			button.setBackground(Color.CYAN);
			buttonCircleModel.setNextButtonId();
			// pomiar realnej sciezki cz.1 - ustawianie aktualnego przycisku
				StatisticsController.setActualNode(button.getText());
			//
			
			String maxId = "" + (buttonCircleView.getButtons().size()-1);
			if(maxId.equals(button.getText())) {
				Timer timer = buttonCircleView.getTimer();
				timer.stop();
				StatisticsController.printAllStats();

				//po kliknieciu na maxID ponownie pokazuje okno z pytaniami
				InformationFieldPostTest informationFieldPostTest = new InformationFieldPostTest(QWF);
				buttonCircleView.hideGui();
			}
			else if(button.getText().equals("0")) {
				StatisticsController.setIsPEtest(true);
				buttonCircleModel.setStartTime(System.currentTimeMillis());
				Timer timer = buttonCircleView.getTimer();
				timer.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			        	long time = System.currentTimeMillis() - buttonCircleModel.getStartTime();
			            buttonCircleView.setLabel(""+time);
			        }
			    });
				timer.start();
			}
		}
	}
}
