package controllers;

import java.util.ArrayList;

public class StatisticsController {
    static ArrayList<Node> nodes = new ArrayList<>(); //przechowuje informacje o położeniu i nazwie przycisku
    static ArrayList<NodePair> nodePairs = new ArrayList<>(); //przechowuje pary przyciskow i liczy najkrotsze odleglosci miedzy nimi oraz realne odległości wykonane przez uzytkownika
    static int shortestPathSum = 0; // sumaryczna dlugosc najkrotszej sciezki
    static String actualNode;

    public static void addNode(String name, int xCenter, int yCenter){
        nodes.add(new Node(name, xCenter, yCenter));
    }

    static void setActualNode(String node){
        actualNode = node;
    }

    static public void calcRealPath(int actX, int actY, int lastX, int lastY){
        int tmp1 = (int) Math.pow((actX - lastX), 2);
        int tmp2 = (int) Math.pow((actY - lastY), 2);
        int length = (int) Math.sqrt(tmp1 + tmp2);

        for(NodePair pair : nodePairs){
            if(pair.n1.name.equals(actualNode)){
                //System.out.println(pair.n1.name);
                pair.realLength += length;
            }
        }
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

    public static int getShortestPathSum(){
        return shortestPathSum;
    }

    public static int getShortestPath(String id){
        for(NodePair pair : nodePairs){
            if(id.equals(pair.n1.name))
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

    public static int getRealPathBeetwen(String id1, String id2){
        for (NodePair pair : nodePairs){
            if (id1.equals(pair.n1.name) && id2.equals(pair.n2.name))
                return pair.realLength;
        }
        return 0;
    }

    public static int getRealPathBeetwen(String FirstNodeId){
        for (NodePair pair : nodePairs) {
            if(FirstNodeId.equals(pair.n1.name))
                return pair.realLength;
        }
        return 0;
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

}



