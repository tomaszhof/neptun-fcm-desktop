package data;

import controllers.DataController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class QuestionController {
    Vector<Question> questions;

    public QuestionController() {
        questions = new Vector<Question>();
    }

    public void printQuestions(){
        for(Question q : questions){
            System.out.println(q.getName());
            System.out.println(q.getQuestion());
            System.out.println(q.getAnswerCodes());
            System.out.println(q.getAnswers());
            System.out.println("\n");
        }
    }

    //zaciaga baze z serwera
    public void importQA(){
        Set<String> ids = DataController.getAllQueId(); //pobiera wszystkie
        
        for(String tmp : ids){
            System.out.println("ImpoerQA " + tmp);
            tmp = tmp.replace("\uFEFF", ""); //wyrzuca bialy znak, bo zglaszalo wyjatek

            String name = tmp;
            if(!name.equals("") && !name.equals("PQ0")){ //sprawdza czy nazwa odpowiedzi nie rowna sie null lub Q0
                String question = DataController.getQuestion(tmp);
                String QueAns = DataController.getQueAnsCodes(tmp);
                System.out.println(name + " " + question + " " + QueAns);
                this.questions.add(new Question(name, question, QueAns));
            }
        }
    }

    public Question getQuestion(String queCode){
        Question tmp = new Question();
        for(Question q : questions){
            if(q.getName().equals(queCode)){
             tmp = q;
            }
        }
        return tmp;
    }
}

