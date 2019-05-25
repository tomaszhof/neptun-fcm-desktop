package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	private boolean isTouchScreen = false;
	private boolean isNextButtonRedEnabled;
	StringBuilder resultsToSendViaMail = new StringBuilder();


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

		buttonCircleView.getButtons().forEach(btn -> {if (btn.getText().equals("0")) btn.setBackground(Color.RED);});

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

			if(!isTouchScreen){
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent actionEvent) {
						onButtonClick(actionEvent.getSource());
					}
				});
			}
			// dla ekranow dotykowych wylaczone jest klikanie na przyciski
			else {
				button.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent e) {

					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {

					}

					@Override
					public void mouseEntered(MouseEvent e) {
						onButtonClick(e.getSource());
					}

					@Override
					public void mouseExited(MouseEvent e) {

					}
				});
			}

		}
	}
	
	private void onButtonClick(Object object) {
		String id = "" + buttonCircleModel.getNextButtonId();

		RoundButton button = (RoundButton) object;
		if(id.equals(button.getText())) {

			if(isNextButtonRedEnabled){
				turnNextDotRed(id);
			}

			button.setBackground(Color.CYAN);
			buttonCircleModel.setNextButtonId();
			// pomiar realnej sciezki cz.1 - ustawianie aktualnego przycisku
				StatisticsController.setActualNode(button.getText());

			String maxId = "" + (buttonCircleView.getButtons().size()-1);
			if(maxId.equals(button.getText())) {
				Timer timer = buttonCircleView.getTimer();
				timer.stop();
				StatisticsController.printAllStats();
				StatisticsController.printDevioations();
				StatisticsController.calcAllU();

				//po kliknieciu na maxID ponownie pokazuje okno z pytaniami

				// to testow applikacja moze wysylac maila ze statystykami
//				if(isTouchScreen){
//					resultsToSendViaMail.append(StatisticsController.printDevioations());
//					EmailController.sendMail("Ekran dotykowy", resultsToSendViaMail.toString());
//				}

				InformationFieldPostTest informationFieldPostTest = new InformationFieldPostTest(QWF);
				buttonCircleView.hideGui();
			}
			else if(button.getText().equals("0")) {
				StatisticsController.setIsPEtest(true);
				buttonCircleModel.setStartTime(System.currentTimeMillis());
				Timer timer = buttonCircleView.getTimer();
				timer.setDelay(1); //TH: is it necessary?
				timer.addActionListener(new ActionListener() {
			        public void actionPerformed(ActionEvent e) {
			        	long time = System.currentTimeMillis() - buttonCircleModel.getStartTime();
			        	StatisticsController.setActTimeTmp(time);
			        	StatisticsController.setTime(time);
//						System.out.println(time);
			            buttonCircleView.setLabel(""+time);
			        }
			    });
				timer.start();
			}
		}
	}

	private void turnNextDotRed(String id){
		int nextId = Integer.parseInt(id) + 1;
		System.out.println(nextId);
		buttonCircleView.getButtons().forEach(btn -> {
			if(btn.getText().equals(Integer.toString(nextId)))
				btn.setBackground(Color.RED);
		});
	}

	public void setTouchScreen(boolean touchScreen) {
		isTouchScreen = touchScreen;
	}

	public void setNextButtonRedEnabled(boolean nextButtonRedEnabled) {
		isNextButtonRedEnabled = nextButtonRedEnabled;
	}
}
