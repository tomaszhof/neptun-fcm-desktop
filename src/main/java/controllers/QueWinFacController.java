package controllers;

import data.AnsweredQuestions;
import models.ButtonCircleModel;
import views.*;

import javax.print.attribute.standard.Finishings;
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
//        phaseNum = 1;
//        queCode = "Q15";
//        answCode = "A56";
        updateQueCode();
        showWindow();
    }

    public QueWinFacController(int phase) {
        qaController = NeptunQAController.get();
        questionWindowFactory = new QuestionWindowFactory();
        if(phase == 1){
            phaseNum = 1;
            queCode = "_";
            answCode = null;
        }
        else if(phase == 2){
            queCode = "P2";
        }
//        phaseNum = 1;
//        queCode = "Q15";
//        answCode = "A56";
        updateQueCode();
        showWindow();

    }

    private void showWindow(){
        questionWindowFactory.createForCode(queCode);

        //Listener dla przycisku next
        questionWindowFactory.getNextBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    nextQuestion();
                }
                catch (Exception exc){
                    System.out.println(exc.getMessage());
                    FinishText finishText = new FinishText(questionWindowFactory);
                }
            }
        });
    }

    private void updateQueCode(){
        if(phaseNum == 1) {
            queCode = qaController.getNextQuestionPhase1(queCode, answCode);
        }
        else if (phaseNum == 2){
            queCode = qaController.getNextQuestionPhase2(queCode, answCode);

            if (queCode.equals("_")){
                System.out.println("Finish");
                AnsweredQuestions.printAll();

                return;
                //tu sie konczy
            }
        }
        if (queCode.equals("P2")){
            preTest();
            phaseNum = 2;
            queCode = "_";
            updateQueCode();
        }
    }

    private void nextQuestion(){
        answCode = AnsweredQuestions.getQuestionAnswer(queCode);
        updateQueCode();
        System.out.println("Ans code: " + answCode);
        showWindow();
    }



    private void preTest(){
        JFrame infFieldPre = new InformationFieldPreTest(questionWindowFactory);

//        ((InformationFieldPreTest) infFieldPre).nextBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(((InformationFieldPreTest) infFieldPre).getIsAvailableToNext())
//                    //runTest();
//            }
//        });
    }
}
