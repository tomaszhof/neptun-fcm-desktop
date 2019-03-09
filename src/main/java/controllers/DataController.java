package controllers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Set;

import data.QAs;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


public class DataController {
    private static String userNum;
    private static boolean isFirstPhaseStart;
    private static final String getQuestionsUrl = "https://neptun-fcm.herokuapp.com/api/questions";
    private static final String getQueAnsCodeUrl = "https://neptun-fcm.herokuapp.com/api/question/";
    private static final String getAnswersUrl = "https://neptun-fcm.herokuapp.com/api/answers";
    private static final String getAnswerUrl = "https://neptun-fcm.herokuapp.com/api/answer/";
    private static final String getRulesUrl = "https://neptun-fcm.herokuapp.com/api/rules";
    private static final String postRegisterUserUrl = "https://neptun-fcm.herokuapp.com/api/users/register";
    private static final String postTestResultUserUrl = "https://neptun-fcm.herokuapp.com/admin/api/users/UID/results/";
    private static final String postLoginUrl = "https://neptun-fcm.herokuapp.com/api/users/login";
    private static final String getAllQAa = "https://neptun-fcm.herokuapp.com/api/code";

    public static QAs qAs;

    public static String getQuestion(String questionCode) {
        return qAs.getQuestion(questionCode);
    }

    public static String getAnswer2(String answerCode){
        try {
            RestTemplate restTemplate = new RestTemplate();
            String URL = getAnswerUrl + answerCode;
            System.out.println("GET ANS: " + answerCode);

            Object response = restTemplate.getForObject(URL, String.class);
            String[] tmp = response.toString().split("\":\"");
            String resp = tmp[1].replace("\"", "");
            resp = resp.replace("}", "");

            //System.out.println(resp);

            return resp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getAnswer(String answerCode){
        return qAs.getAnswer(answerCode);
    }


    public static String getQueAnsCodes(String questionCode) {
        return qAs.getQueAnsCode(questionCode);
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
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("username", username);
            parts.add("password", password);

            Object response = restTemplate.postForObject(postRegisterUserUrl, parts, String.class);
            System.out.println("Response: " + response.toString());
            userNum = response.toString();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String postLoginUser(String username, String password) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();
            parts.add("username", username);
            parts.add("password", password);

            Object response = restTemplate.postForObject(postLoginUrl, parts, String.class);
            System.out.println(response.toString());
            userNum = response.toString();
            return response.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void postTestResultUserBefore() {
        String query_url = postTestResultUserUrl;

        query_url = query_url.replace("UID",  userNum);
        String json = StatisticsController.getStatisticsJsonBefore();

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

    public static QAs getAllQAs(){
        try {
            RestTemplate restTemplate = new RestTemplate();

            QAs response = restTemplate.getForObject(getAllQAa, QAs.class);

            // setting local static questions and answers
            qAs = response;

            return response;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void postTestResultUserAfter() {
        String query_url = postTestResultUserUrl;

        query_url = query_url.replace("UID",  userNum);
        String json = StatisticsController.getStatisticsJsonAfter();
//        String json = getJSONAfter();

        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000);
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
            System.out.println("RESPONSE:");
            System.out.println("res: " + result);


//            System.out.println("result after Reading JSON Response");
//            JSONObject myResponse = new JSONObject(result);
//            System.out.println("jsonrpc- "+myResponse.getString("jsonrpc"));
//            System.out.println("id- " + myResponse.getInt("id"));
//            System.out.println("result- " + myResponse.getString("result"));
            in.close();
            conn.disconnect();
        } catch (Exception e) {
            System.out.println("Exception dupaaa...");
            System.out.println(e.getMessage());
//            postTestResultUserAfter();
        }
    }

    public static void postTestResultUserAfter2() {
        String query_url = postTestResultUserUrl;

        query_url = query_url.replace("UID",  "1");
        String json = StatisticsController.getStatisticsJsonAfter2();
//        String json = getJSONAfter();

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(json, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(query_url, entity, String.class);
            System.out.println(response);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void postTestResultUserTest() {
        String query_url = postTestResultUserUrl;

        query_url = query_url.replace("UID",  userNum);
        String json = StatisticsController.getStatisticsTestJson();

        try {
            URL url = new URL(query_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(50000);
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
            System.out.println("RESPONSE:");
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

    public static void postTestResult(int UID) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            MultiValueMap<String, Object> parts = new LinkedMultiValueMap<String, Object>();

            Object response = restTemplate.postForObject(postLoginUrl, parts, String.class);
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isIsFirstPhaseStart() {
        return isFirstPhaseStart;
    }

    public static void setIsFirstPhaseStart(boolean isFirstPhaseStart) {
        DataController.isFirstPhaseStart = isFirstPhaseStart;
    }
}
