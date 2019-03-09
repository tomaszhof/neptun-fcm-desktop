package data;

import controllers.DataController;

import java.util.Set;
import java.util.Vector;

public class QuestionController {
    Vector<Que> questions;

    public QuestionController() {
        questions = new Vector<Que>();
    }

    public void printQuestions(){
        for(Que q : questions){
            System.out.println(q.getName());
            System.out.println(q.getQuestion());
            System.out.println(q.getAnswerCodes());
            System.out.println(q.getAnswers());
            System.out.println("\n");
        }
    }


    public Que getQuestion(String queCode){
        Que tmp = new Que();
        for(Que q : questions){
            if(q.getName().equals(queCode)){
             tmp = q;
            }
        }
        return tmp;
    }
}

