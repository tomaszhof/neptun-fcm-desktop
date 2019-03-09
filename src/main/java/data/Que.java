package data;

import controllers.DataController;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Que {
    String name;
    String question;
    String answerCodes;
    Map<String, String> answers;

    public Que() {
        this.question = "Jakies pytanie";
        this.name = "Jakies ID";
        this.answerCodes = "Jakies odpowiedzi";
        answers = new HashMap<String, String>();
    }

    public Que(String name, String question, String answerCodes) {
        this.name = name;
        this.question = question;
        this.answerCodes = answerCodes;
        answers = new HashMap<String, String>();
        this.setAnswers();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerCodes() {
        return answerCodes;
    }

    public void setAnswerCodes(String answerCodes) {
        this.answerCodes = answerCodes;
        //this.setAnswers();
    }

    public Map<String, String> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, String> answers) {
        this.answers = answers;
    }

    public void setAnswers(){
        System.out.println("Set answers");
        //answers.clear(); //czysci, żeby nie zrobic balaganu
        System.out.println(this.answerCodes);

        Pattern pattern = Pattern.compile("A\\d+");

        Matcher m = pattern.matcher(answerCodes);
        while (m.find()) {
            String ans = DataController.getAnswer(m.group());
            System.out.println(m.group() + ":" + ans);
            answers.put(m.group(), ans);
        }
    }

    public int getArrLength(){
        return this.answers.size();
    }
}
