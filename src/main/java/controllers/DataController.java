package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;


public class DataController {
    //    private static final String importer = "https://neptun-fcm.herokuapp.com/importer";
    private static final String getQuestionsUrl = "https://neptun-fcm.herokuapp.com/api/questions";
    private static final String getQueAnsCodeUrl = "https://neptun-fcm.herokuapp.com/api/question/";
    private static final String getAnswersUrl = "https://neptun-fcm.herokuapp.com/api/answers";
    private static final String getRulesUrl = "https://neptun-fcm.herokuapp.com/api/rules";
    private static final String postRegisterUserUrl = "https://neptun-fcm.herokuapp.com/admin/api/users/register";
    private static final String postTestResultUserUrl = "https://neptun-fcm.herokuapp.com/admin/api/users/UID/results/";

    public static String getQuestion(String questionCode) {
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

    public static String getAnswer(String answerCode) {
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

    public static Set<String> getAllQueId() {
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

    public static String getQueAnsCodes(String questionCode) {
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

    public static String getRulesOfQuestions() {

        try {
            //połączenie z URL'em do pobierania reguł
            URL url = new URL(getRulesUrl);
            URLConnection request = url.openConnection();
            request.connect();
            java.util.Scanner scanner = new java.util.Scanner((InputStream) request.getContent());
            java.util.Scanner s = scanner.useDelimiter("\\A");
            String result = s.hasNext() ? s.next() : "NO RULES FOUND!";
            s.close();
            scanner.close();
            return result;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String postRegisterUser(String username, String password) {

        try {
            RestTemplate restTemplate = new RestTemplate();
            //TODO: implement that method
            return null;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void postTestResultUser(int UID) {
        String query_url = postTestResultUserUrl;
        query_url = query_url.replace("UID",  Integer.toString(UID));
        String json = "{\n" +
                "\t\"beforeAnswers\":\"beforeAnswer4\",\n" +
                "\t\"afterAnswers\":\"afeterAnswer4\",\n" +
                "\t\"shortestPath\":504,\n" +
                "\t\"realPath\":304,\n" +
                "\t\"deviation\":64,\n" +
                "\t\"maxDeviation\":124,\n" +
                "\t\"integralU\":14\n" +
                "}";
        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            String result = IOUtils.toString(in, "UTF-8");
            System.out.println(result);
            System.out.println("result after Reading JSON Response");
            JSONObject myResponse = new JSONObject(result);
//            System.out.println("jsonrpc- "+myResponse.getString("jsonrpc"));
            System.out.println("id- " + myResponse.getInt("id"));
            System.out.println("result- " + myResponse.getString("result"));
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
