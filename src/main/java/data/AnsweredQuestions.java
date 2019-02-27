package data;

import java.util.ArrayList;
import java.util.List;

public class AnsweredQuestions {
    private static List<AQ> answeredQuestions = new ArrayList<>();

    public static void addAnswer(String question, ArrayList<String> answers){
        printAll();

        if(!contains(question)){
            answeredQuestions.add(new AQ(question, answers));
        }
    }

    public static List<AQ> getAnsweredQuestions() {
        return answeredQuestions;
    }

    static void setAnsweredQuestions(ArrayList<AQ> answeredQuestions) {
        AnsweredQuestions.answeredQuestions = answeredQuestions;
    }

    private static boolean contains(String questionCode){
        for(AQ data : answeredQuestions){
            if(data.getQuestion().equals(questionCode)){
                return true;
            }
        }
        return false;
    }


    static void displayElement(int i){
        String tmp = answeredQuestions.get(i).getFirstAnswer();
        System.out.println(tmp);
    }

    public static String getQuestionAnswer(String queCode){
        for(AQ data : answeredQuestions){
            if (data.getQuestion().equals(queCode))
                return data.getFirstAnswer();
        }
        System.out.println("Sth went wrong - null");
        return null;
    }

    public static void printAll(){
        System.out.println("\n--------\n");
        for(AQ data : answeredQuestions)
            System.out.println(data.getQuestion() + " " + data.getAnswers().toString());
        System.out.println("\n--------\n");
    }

    public static String getString(){
        String tmp = "";
        for(AQ data : answeredQuestions)
            tmp += "(" + data.getQuestion() + ":" + data.getAnswers().toString() + ")";
        return tmp;
    }

    public static void clearAnswers(){
        System.out.println("Cleared");
        answeredQuestions = new ArrayList<>();
    }

}

class AQ{
    private String question;
    private ArrayList<String> answers;

    AQ(String question, ArrayList<String> answers){
        this.question = question;
        this.answers = answers;
    }

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }

    public String getFirstAnswer(){
        return answers.get(0);
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getAnswers() {
        return answers;
    }
}




