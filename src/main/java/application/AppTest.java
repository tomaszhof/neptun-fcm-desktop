package application;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;
import controllers.DataController;
import data.TestResult;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;


public class AppTest {
    public static void main(String[] args) {
//
//        TestResult testResult = new TestResult();
//
//        testResult.setBeforeAnswers("dp34pfd:3423");
//        testResult.setDeviation((long) 123);
//
//        Gson gson = new Gson();
//        String json = gson.toJson(testResult);
//        System.out.println(json);

        DataController.postTestResultUserAfter2();


    }

}
