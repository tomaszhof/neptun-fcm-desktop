package data;

import java.util.List;

public class QAs {
    List<Question> questions;
    List<Answer> answers;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestion(String queCode){
        for (Question question : questions){
            if(question.getCode().equals(queCode))
                return question.getText();
        }
        return "";
    }

    public String getAnswer(String ansCode){
        for (Answer answer : answers){
            if (answer.getCode().equals(ansCode))
                return answer.getText();
        }
        return "";
    }

    public String getQueAnsCode(String questionCode){
        for (Question question : questions){
            if (question.getCode().equals(questionCode))
                return question.getAnswersCodes();
        }
        return "";
    }
}
