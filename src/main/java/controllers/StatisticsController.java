package controllers;

import java.util.ArrayList;

public class StatisticsController {
    static ArrayList<Node> nodes = new ArrayList<>(); //przechowuje informacje o położeniu i nazwie przycisku
    static ArrayList<NodePair> nodePairs = new ArrayList<>(); //przechowuje pary przyciskow i liczy najkrotsze odleglosci miedzy nimi
    static int shortestPathSum = 0; // sumaryczna dlugosc najkrotszej sciezki

    public static void addNode(String name, int xCenter, int yCenter){
        nodes.add(new Node(name, xCenter, yCenter));
    }

    static void calcShortestPaths(){
        for (int i = 0; i < nodes.size() - 1; i++){
            NodePair pair = new NodePair(nodes.get(i), nodes.get(i+1));
            nodePairs.add(pair);
            shortestPathSum += pair.length;
        }
        System.out.println("Shortest Paht: " + shortestPathSum);
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
    int length = 0;

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
        System.out.println("x1/x2/y1/y2" + x1 + " " + x2 + " " + y1 + " " + y2);
        System.out.println(tmp1 + " tmp1/tmp2 " + tmp2);


        length = (int) Math.sqrt(tmp1 + tmp2);
        System.out.println("Length: " + n1.name + " " + n2.name + " " + length);
    }


    public Node getN1() {
        return n1;
    }

    public Node getN2() {
        return n2;
    }

    public int getLength() {
        return length;
    }

}




