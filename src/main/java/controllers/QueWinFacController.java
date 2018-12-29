package controllers;

import data.AnsweredQuestions;
import data.QuestionController;
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
//        phaseNum = 2;
//        queCode = "Q35";
//        answCode = "A198";
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

    private boolean updateQueCode(){
        if(phaseNum == 1) {
            queCode = qaController.getNextQuestionPhase1(queCode, answCode);
            return true;
        }
        else if (phaseNum == 2){
            queCode = qaController.getNextQuestionPhase2(queCode, answCode);

            if (queCode.equals("_")){
                System.out.println("Finish");
                AnsweredQuestions.printAll();
                return false;
            }
            return true;
        }

        if (queCode.equals("P2")){
            runTest();
            phaseNum = 2;
            queCode = "_";
            updateQueCode();
            return true;
        }
        return true;
    }

    private void nextQuestion(){
        answCode = AnsweredQuestions.getQuestionAnswer(queCode);
        System.out.println("Ans code: " + answCode);
        if (updateQueCode()){
            showWindow();
        }
        else{
            //koniec dzialania progrmau
        }


    }

    private void runTest(){

    }
}
