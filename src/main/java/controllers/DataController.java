package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

public class DataController {
    private static final String importer = "https://neptun-fcm.herokuapp.com/importer";
    private static final String getQuestionsUrl = "https://neptun-fcm.herokuapp.com/api/questions";
    private static final String getQueAnsCodeUrl = "https://neptun-fcm.herokuapp.com/api/question/";
    private static final String getAnswersUrl = "https://neptun-fcm.herokuapp.com/api/answers";


    public static String getQuestion(String questionCode){
        String question = null;
        try {
            //połączenie z URL'em do pobierania pytań
            URL url = new URL(getQuestionsUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            //wyciaganie pytania
            question = rootobj.get(questionCode.toUpperCase()).getAsString(); //just grab the zipcode
            //Set<String> tmp = rootobj.keySet();
            System.out.println(question);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return question;
    }

    public static String getAnswer(String answerCode){
        String answer = null;

        try {
            //połączenie z URL'em do pobierania pytań
            URL url = new URL(getAnswersUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            //wyciaganie pytania

            answer = rootobj.get(answerCode).toString(); //just grab the answer

        } catch (IOException e) {
            e.printStackTrace();
            return "A0";
        }
        return answer;
    }

    public static Set<String> getAllQueId(){
        Set<String> ids = null;
        try {
            //połączenie z URL'em do pobierania pytań
            URL url = new URL(getQuestionsUrl);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            ids = rootobj.keySet();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ids;
    }

    public static String getQueAnsCodes(String questionCode){
        String queAnsCodes = null;
        System.out.println(questionCode);

        questionCode = questionCode.toUpperCase();
        try {
            //połączenie z URL'em do pobierania pytań
            URL url = new URL(getQueAnsCodeUrl + questionCode);
            URLConnection request = url.openConnection();
            request.connect();

            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.

            //wyciaganie pytania
            queAnsCodes = rootobj.get(questionCode.toUpperCase()).toString(); //just grab the question
            //Set<String> tmp = rootobj.keySet();
            System.out.println(queAnsCodes);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return queAnsCodes;
    }
}
