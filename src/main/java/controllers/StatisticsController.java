package controllers;

import com.google.gson.Gson;
import data.AnsweredQuestions;
import data.TestResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

public class StatisticsController {
    private static boolean isPEtest = false;
    static ArrayList<Node> nodes = new ArrayList<>(); //przechowuje informacje o położeniu i nazwie przycisku
    static ArrayList<NodePair> nodePairs = new ArrayList<>(); //przechowuje pary przyciskow i liczy najkrotsze odleglosci miedzy nimi oraz realne odległości wykonane przez uzytkownika
    static int shortestPathSum = 0; // sumaryczna dlugosc najkrotszej sciezki
    static String actualNode;
    static long time; //ms
    static long actTimeTmp;

    public static void reset(){
        nodes = new ArrayList<>();
        nodePairs = new ArrayList<>();
        shortestPathSum = 0;
    }

    public static void addNode(String name, int xCenter, int yCenter){
        nodes.add(new Node(name, xCenter, yCenter));
    }

    static void setActualNode(String node){
        actualNode = node;
    }

    static public void makeCalculations(int actX, int actY, int lastX, int lastY){
        calcRealPath(actX, actY, lastX, lastY);
        calcDeviation(actX, actY);
        manageU(actX, actY);
    }

    static void manageU(int actX, int actY){
        for(NodePair pair : nodePairs){
            if(pair.n1.name.equals(actualNode)){
                int a = (pair.n1.y - pair.n2.y) / (pair.n1.x - pair.n2.x);
                int b = pair.n1.y - a * pair.n1.x;

                int A = a;
                int B = -1;
                int C = b;

                double deviation = ( Math.abs( A * actX + B * actY + C ) ) / ( Math.sqrt( Math.pow(A, 2) + Math.pow(B, 2) ) );
                pair.integrals.add(new ForIntegral(actTimeTmp, (int)deviation));

                //tutaj sprawdzam jaki jest aktualny czas i odległość od wyimaginowanej linii do punktu X,Y.
                //liczę pole. Zapisuję poprzedni czas, żeby mieć punkt odniesienia do
            }
        }
    }

    static void calcRealPath(int actX, int actY, int lastX, int lastY){
        //liczenie realnej ścieżki
        int tmp1 = (int) Math.pow((actX - lastX), 2);
        int tmp2 = (int) Math.pow((actY - lastY), 2);
        int length = (int) Math.sqrt(tmp1 + tmp2);

        for(NodePair pair : nodePairs){
            if(pair.n1.name.equals(actualNode)){
                //System.out.println(pair.n1.name);
                pair.realLength += length;
            }
        }
        //liczenie realnej ścieżki koniec
    }

    static void calcDeviation(int actX, int actY){
        //liczenie max odchylenia
        for(NodePair pair : nodePairs){
            if(pair.n1.name.equals(actualNode)){
                int a = (pair.n1.y - pair.n2.y) / (pair.n1.x - pair.n2.x);
                int b = pair.n1.y - a * pair.n1.x;

                int A = a;
                int B = -1;
                int C = b;

                double deviation = ( Math.abs( A * actX + B * actY + C ) ) / ( Math.sqrt( Math.pow(A, 2) + Math.pow(B, 2) ) );
                if(deviation > pair.maxDeviation)
                    pair.maxDeviation = deviation;

                pair.averageDeviation = (pair.averageDeviation * pair.devCounter + deviation) / ( ++pair.devCounter );


                //tutaj sprawdzam jaki jest aktualny czas i odległość od wyimaginowanej linii do punktu X,Y.
                //liczę pole. Zapisuję poprzedni czas, żeby mieć punkt odniesienia do
            }
        }
        //l max odchylenia koniec
    }

    public static String printDevioations(){
        StringBuilder toReturn = new StringBuilder();
        for(NodePair pair : nodePairs){
            System.out.println(pair.n1.name + ":" + pair.n2.name + "\n" + pair.devToString() + "\n\n");
            toReturn.append(pair.n1.name + ":" + pair.n2.name + "\n" + pair.devToString() + "\n\n");
        }
        return toReturn.toString();
    }

    static void displayRealPaths(){
        for (NodePair pair : nodePairs){
            System.out.println(pair.n1.name + ":" + pair.n2.name +  " l:" + pair.realLength);
        }
    }

    static void calcShortestPaths(){
        System.out.println("Calc shortest Path");

        for (int i = 0; i < nodes.size() - 1; i++){
            Node n1 = getNode(i);
            Node n2 = getNode(i+1);
            NodePair pair = new NodePair(n1, n2);

            nodePairs.add(pair);
            shortestPathSum += pair.shortestLength;
        }
        System.out.println("Shortest Path summary: " + shortestPathSum);
    }

    private static Node getNode(int id){
        //szukanie przycisku o nastepnym ID
        for(Node node : nodes){
            String nodeId = Integer.toString(id);
            if(node.name.equals(nodeId)){
                return node;
            }
        }
        return null;
    }

    public static void setActTimeTmp(long time){
        actTimeTmp = time;
    }

    public static boolean isIsPEtest() {
        return isPEtest;
    }

    public static void setIsPEtest(boolean isPEtest) {
        StatisticsController.isPEtest = isPEtest;
    }

    public static int getShortestPathSum(){
        return shortestPathSum;
    }

    public static int getShortestPath(String FirstNodeId){
        for(NodePair pair : nodePairs){
            if(FirstNodeId.equals(pair.n1.name))
                return pair.shortestLength;
        }
        return 0;
    }

    public static int getShortestPath(String id1, String id2){
        for(NodePair pair : nodePairs){
            if(id1.equals(pair.n1.name) && id2.equals(pair.n2.name))
                return pair.shortestLength;
        }
        return 0;
    }

    public static int getRealPathSum(){
        int realLength = 0;
        for(NodePair pair : nodePairs){
            realLength += pair.realLength;
        }
        return realLength;
    }

    public static int getRealPathBetween(String id1, String id2){
        for (NodePair pair : nodePairs){
            if (id1.equals(pair.n1.name) && id2.equals(pair.n2.name))
                return pair.realLength;
        }
        return 0;
    }

    public static int getRealPathBetween(String FirstNodeId){
        for (NodePair pair : nodePairs) {
            if(FirstNodeId.equals(pair.n1.name))
                return pair.realLength;
        }
        return 0;
    }

    public static double getAverageDeviation(){
        double averageDeviation = 0;
        int counter = 0;
        for (NodePair pair : nodePairs){
            counter++;
            averageDeviation += pair.averageDeviation;
        }
        averageDeviation /= counter;
        return averageDeviation;
    }

    public static double getAverageMaxDeviation(){
        double averageDeviationMax = 0;
        int counter = 0;
        for (NodePair pair : nodePairs){
            counter++;
            averageDeviationMax += pair.maxDeviation;
        }
        averageDeviationMax /= counter;
        return averageDeviationMax;
    }

    public static double getMaxDeviation(String FirstNodeId){
        for (NodePair pair : nodePairs){
            if(FirstNodeId.equals(pair.n1.name))
                return pair.maxDeviation;
        }
        return 0;
    }

    public static double getAverageDeviation(String FirstNodeId){
        for (NodePair pair : nodePairs){
            if(FirstNodeId.equals(pair.n1.name))
                return pair.averageDeviation;
        }
        return 0;
    }

    public static void printAllStats(){
        System.out.println(
                "Shortest Path Summary: " + getShortestPathSum() + "\n"
                + "Shortest Path 2:3 : " + getShortestPath("2") + "\n"
                + "Real Path Summary: " + getRealPathSum() + "\n"
                + "Real Path Between 4:5 :" + getRealPathBetween("4") + "\n"
                + "Average Deviation: " + getAverageDeviation() + "\n"
                + "Average Max Deviation: " + getAverageMaxDeviation() + "\n"
                + "Max Deviation 5:6 " + getMaxDeviation("5") + "\n"
                + "Average Deviation 5:6 " + getAverageDeviation("5") + "\n");

        displayRealPaths();
    }

    public static String getStatistics(){
        return "Czas: " + getTime() + " s\n"
                + "Sumaryczna długość najkrótszych ścieżek: " + getShortestPathSum() + " px\n"
                + "Sumaryczna długość realnych ścieżek: " + getRealPathSum() + " px\n"
                + "Średnie ochylenie: " + (int)getAverageDeviation() + " px\n"
                + "Średnie maksymalne odchylenie: " + (int)getAverageMaxDeviation() + " px\n\n"
                + "Średni współczynnik U: " + (int)getAverageU() + "\n\n"
                + "Poszczególne współczynniki: \n" + getAllU();
    }

    public static String getStatisticsJsonBefore() {
        TestResult testResult = new TestResult();

        testResult.setBeforeAnswers(AnsweredQuestions.getString());

        Gson gson = new Gson();
        String json = gson.toJson(testResult);
        System.out.println(json);

        return json;
    }

    public static String getStatisticsJsonAfter(){
        TestResult testResult = new TestResult();

        testResult.setAfterAnswers(AnsweredQuestions.getString());

        Gson gson = new Gson();
        String json = gson.toJson(testResult);
        System.out.println(json);

        return json;
    }

    public static String getStatisticsJsonAfter2(){
        TestResult testResult = new TestResult();
        String tmp = "(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])" +
                "(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])" +
                "(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])(Q18:[A93, A210, A92, A90, A89, A91])(Q19:[A211, A96, A95, A94])(Q20:[A212, A99])(Q21:[A213, A104, A103, A102])(Q22:[A108])";
        System.out.println(tmp.length());

        testResult.setAfterAnswers(tmp);

        Gson gson = new Gson();
        String json = gson.toJson(testResult);
        System.out.println(json);

        return json;
    }


    public static String getStatisticsTestJson(){
        TestResult testResult = new TestResult();

        testResult.setShortestPath((long) getShortestPathSum());
        testResult.setRealPath((long) getRealPathSum());
        testResult.setDeviation((long) getAverageDeviation());
        testResult.setMaxDeviation((long) getAverageMaxDeviation());
        testResult.setIntegralU((long) getAverageU());

        Gson gson = new Gson();
        String json = gson.toJson(testResult);
        System.out.println(json);

        return json;
    }

    private static String getAllU(){
        String toReturn = "";
        for(NodePair pair : nodePairs){
            toReturn += pair.UtoString() + "\n";
        }
        return toReturn;
    }

    public static double getAverageU(){
        int counter = 0;
        double Usum = 0;
        for(NodePair pair : nodePairs){
            counter++;
            Usum += pair.getU();
        }
        return Usum/counter;
    }

    private static String getAllInfoPaths(){
        String toReturn = "WYNIKI:\n";
        for(NodePair node : nodePairs){
            toReturn += "Ścieżka " + node.n1.name + ":" + node.n2.name
                    + " Najkrótszy dystans:" + node.shortestLength
                    + "\tRealny dystans: " + node.realLength
                    + "\n\tOdchylenie średnie: " + (int)node.averageDeviation
                    + "\t Odchylenie maksymalne: " + (int)node.maxDeviation
                    + "\n\n";
        }
        return toReturn;
    }

    static void setTime(long ti){
        System.out.println("Setting time: " + ti);
        time = ti;
    }

    static String getTime(){
        double ti = time/1000;
        ti = BigDecimal.valueOf(ti)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
        return String.valueOf(ti);
    }

    static void calcAllU(){
        for(NodePair pair : nodePairs) {
            pair.calcU();
            System.out.println("U: " + pair.getU());
        }
    }
}

class Node{
    String name;
    int x, y;

    Node(String name, int xCenter, int yCenter){
        this.name = name;
        this.x = xCenter;
        this.y = yCenter;
    }
}

class NodePair{
    Node n1, n2;
    int shortestLength = 0;
    int realLength = 0;
    //double ID = 0; //index of difficulty
    double maxDeviation; //maksymalne odchylenie
    double averageDeviation; //srednie odchylenie
    int devCounter = 0;
    ArrayList<ForIntegral> integrals = new ArrayList<>();
    double U = 0;

    NodePair(Node n1, Node n2){
        this.n1 = n1;
        this.n2 = n2;
        calcLength();
    }

    void calcLength(){
        int x2 = n2.x;
        int x1 = n1.x;
        int y2 = n2.y;
        int y1 = n1.y;
        int tmp1 = (int) Math.pow((x2 - x1), 2);
        int tmp2 = (int) Math.pow((y2 - y1), 2);
        //System.out.println("x1/x2/y1/y2" + x1 + " " + x2 + " " + y1 + " " + y2);
        //System.out.println(tmp1 + " tmp1/tmp2 " + tmp2);
        shortestLength = (int) Math.sqrt(tmp1 + tmp2);
        System.out.println("Length: " + n1.name + " " + n2.name + " " + shortestLength);
    }


    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public int getShortestLength() {
        return shortestLength;
    }

    public String UtoString(){
        return n1.name + "-" + n2.name + ": U = " + (int)U;
    }

    public String devToString(){
        String toReturn = "";
        for(ForIntegral integral : integrals){
            toReturn+= "[" + integral.getTime() + " , " + integral.getDeviation() + "]\n";
        }
        return toReturn;
    }

    public void calcU(){
        for(int i = 1; i < integrals.size(); i++){
            double height = integrals.get(i).getDeviation();
            double width = integrals.get(i).getTime() - integrals.get(i-1).getTime();
            U += (width * height) / 1000;
        }
    }

    public double getU() {
        return U;
    }
}

class ForIntegral{
    private long time;
    private int deviation;

    ForIntegral(long time, int deviation){
        this.time = time;
        this.deviation = deviation;
    }

    void printAll(){
        System.out.println("deviation:" + deviation + " time: " + time);
    }


    public long getTime() {
        return time;
    }

    public int getDeviation() {
        return deviation;
    }
}




