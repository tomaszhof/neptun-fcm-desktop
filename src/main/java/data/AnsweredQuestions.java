package data;

import java.util.ArrayList;

public class AnsweredQuestions {
    static ArrayList<AQ> answeredQuestions = new ArrayList();

    public static void addAnswer(String question, ArrayList<String> answers){
        boolean contains = false;

        //jezeli zawiera, to w petli się skonczy
        for (AQ data : answeredQuestions){
            if (data.getQuestion().equals(question)){
                data.setAnswers(answers);
                return;
            }
        }

        //jezeli nie zawiera, to wykona sie to
        answeredQuestions.add(new AQ(question, answers));

        System.out.println(question + " " + answeredQuestions.get(0).getFirstAnswer());
    }

    public static ArrayList<AQ> getAnsweredQuestions() {
        return answeredQuestions;
    }

    static void setAnsweredQuestions(ArrayList<AQ> answeredQuestions) {
        AnsweredQuestions.answeredQuestions = answeredQuestions;
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
        for(AQ data : answeredQuestions)
            System.out.println(data.getQuestion() + " " + data.answers.toString());
    }
}

class AQ{
    String question;
    public ArrayList<String> answers = new ArrayList<>();

    public void setAnswers(ArrayList<String> answers) {
        this.answers = answers;
    }


    AQ(String question, ArrayList<String> answers){
        this.question = question;
        this.answers = answers;
    }

    public String getFirstAnswer(){
        return answers.get(0);
    }

    public String getQuestion() {
        return question;
    }


}




