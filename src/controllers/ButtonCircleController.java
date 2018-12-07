package controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import javax.swing.Timer;

import components.RoundButton;
import models.ButtonCircleModel;
import models.ButtonModel;
import views.ButtonCircleView;

public class ButtonCircleController {

	private ButtonCircleModel buttonCircleModel;
	private ButtonCircleView buttonCircleView;
	
	public ButtonCircleController() {
		
	}
	
	public ButtonCircleController(ButtonCircleModel model, ButtonCircleView view) {
		buttonCircleModel = model;
		buttonCircleView = view;
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
		}
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
			
			String maxId = "" + (buttonCircleView.getButtons().size()-1);
			if(maxId.equals(button.getText())) {
				Timer timer = buttonCircleView.getTimer();
				timer.stop();
			}
			else if(button.getText().equals("0")) {
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
