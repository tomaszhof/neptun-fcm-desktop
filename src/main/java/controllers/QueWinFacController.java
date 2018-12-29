package controllers;

import data.AnsweredQuestions;
import models.ButtonCircleModel;
import views.QuestionWindowFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueWinFacController {
    NeptunQAController qaController; //kontroler przechodzenia miedzy pytaniami
    QuestionWindowFactory questionWindowFactory; //do wyswietlania okna z pytaniami
    String queCode; //kod pytania;
    String answCode; //kod odpowiedz
    byte phaseNum; //numer fazy (1 lub 2)


    public QueWinFacController() {
        qaController = NeptunQAController.get();
        questionWindowFactory = new QuestionWindowFactory();
        phaseNum = 1;
        queCode = "_";
        answCode = null;
        updateQueCode();
        showWindow();

    }

    private void showWindow(){
        questionWindowFactory.createForCode(queCode);

        //Listener dla przycisku next
        questionWindowFactory.getNextBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                nextQuestion();
            }
        });
    }

    private void updateQueCode(){
        if(phaseNum == 1) {
            queCode = qaController.getNextQuestionPhase1(queCode, answCode);
        }
        if (phaseNum == 2){
            queCode = qaController.getNextQuestionPhase2(queCode, answCode);
        }
    }

    private void nextQuestion(){
        answCode = AnsweredQuestions.getQuestionAnswer(queCode);
        System.out.println("Ans code: " + answCode);
        updateQueCode();
        System.out.println("Next que code: " + queCode);
        //queCode = "Q16";
        showWindow();
    }
}
