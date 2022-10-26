package panel;

import node.Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Panel {

    //screen settings
    final int maxCol = 15;
    final int maxRow = 10;
    final int nodeSize = 70;

    int whileInt = 0;
    Random random = new Random();

    Thread thread;

    boolean goalReached = false;

    //NODE
    Node node [][] = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    public Panel() {

        //place nodes:
        int col = 0;
        int row = 0;
        String back = ".";

        while (col < maxCol && row < maxRow) {

            node[col][row] = new Node(col, row, back);
            //this.add(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }

        }
        //set start and goal node:
        //setStartNode(3, 6);


        //setStartNode(8, 3);
        setRandomStartNode();
        setGoalNode(11, 3);
        //set solid nodes:
        setSolidNode(10, 2);
        setSolidNode(10, 3);
        setSolidNode(10, 4);
        setSolidNode(10, 5);
        setSolidNode(10, 6);
        setSolidNode(10, 7);
        setSolidNode(6, 2);
        setSolidNode(7, 2);
        setSolidNode(8, 2);
        setSolidNode(9, 2);
        setSolidNode(11, 7);
        setSolidNode(12, 7);
        setSolidNode(6, 1);
        //setSolidNode(6, 0);//uj

            for (int i = 0; i < maxRow; i++) {//node.length;
                for (int j = 0; j < maxCol; j++) {//node[i].length;

                    if(node[j][i].getBack().equals(".")){

                        System.out.print(String.valueOf(getFCost(node[j][i])) + "/" + String.valueOf(getGCost(node[j][i])) + " ");
                        //System.out.print(".");
                    }else {
                        //System.out.print("\u001b[1;34m" + node[j][i].getBack() + " " + "\u001b[0m");
                        System.out.print("\u001b[1;34m" + node[j][i].getBack() +  "\u001b[0m");
                    }

                }
                System.out.println();
            }

        System.out.println("node[0][0].getBack(): "+node[0][0].getBack());
    }

    private void setStartNode(int col, int row){
    //private void setStartNode(int row, int col){
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }
    private void setRandomStartNode(){
        int row = random.nextInt(maxRow);
        int col = random.nextInt(maxCol);
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }


    private void setGoalNode(int col, int row){
    //private void setGoalNode(int row, int col){
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    private void setSolidNode(int col, int row){
    //private void setSolidNode(int row, int col){
        node[col][row].setAsSolid();
    }

    private int getFCost (Node node){
        //get gCost (The distance from the start node):
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());

        node.setgCost(xDistance + yDistance);

        //get hCost (The distance from the goal node):
        xDistance = Math.abs(node.getCol() - goalNode.getCol());
        yDistance = Math.abs(node.getRow() - goalNode.getRow());

        node.sethCost(xDistance + yDistance);

        //get F cost : F = G + H:

        node.setfCost(node.getgCost() + node.gethCost());

        return node.getfCost();
    }

    private int getGCost (Node node){
        //get gCost (The distance from the start node):
        int xDistance = Math.abs(node.getCol() - startNode.getCol());
        int yDistance = Math.abs(node.getRow() - startNode.getRow());

        node.setgCost(xDistance + yDistance);

        return node.getgCost();
    }

    public void search() {
        while(goalReached == false){
            whileInt++;
            int col = currentNode.getCol();
            int row = currentNode.getRow();

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            //open the up node:
            if(row - 1 >= 0){
                openNode(node[col][row - 1]);
            }
            //open the left node:
            if(col - 1 >= 0){
                openNode(node[col - 1][row]);
            }
            //open the down node:
            if(row + 1 < maxRow){
                openNode(node[col][row + 1]);
            }
            //open the up node:
            if(col + 1 < maxCol){
                openNode(node[col + 1][row]);
            }

            //find the best node:
            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for(int i = 0; i < openList.size(); i++){
                //Check if this node's F cost is better
                if(openList.get(i).getfCost() < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).getfCost();
                } else if(openList.get(i).getfCost() == bestNodeFCost){
                    //check the G cost
                    if(openList.get(i).getgCost() < openList.get(bestNodeIndex).getgCost()){
                        bestNodeIndex = i;
                    }
                }

            }
            //after the loop, we get the best node which is our next step:
            currentNode = openList.get(bestNodeIndex);


            if(currentNode == goalNode){
                System.out.println("IF");
                goalReached = true;
                trackThePath();
            }


            System.out.println("whileInt: " + whileInt);

        }
        System.out.println("c: " + currentNode);
    }

    private void openNode(Node node){
        if(node.isOpen() == false && node.isChecked() == false && node.isSolid() ==false){
            //if the node not opened yet, add it to the open list
            node.setAsOpen();
            node.setParent(currentNode);
            //currentNode = node.getParent();
            openList.add(node);
        }
    }

    int trackInt = 0;

    private void trackThePath(){
        System.out.println("trackThePath");
        Node current = goalNode;
        while (current != startNode){
            trackInt++;
            current = current.getParent();
            System.out.println("current: " + current);
            if(current != startNode){
                System.out.println("if/trackThePath");
                current.setAsPath();
            }
        }
        System.out.println("trackInt: "+trackInt);

        for (int i = 0; i < maxRow; i++) {//node.length;
            for (int j = 0; j < maxCol; j++) {//node[i].length;
                if(node[j][i].getBack().equals("G")){
                    System.out.print("\u001b[1;35m" + "G " +  "\u001b[0m");
                }else
                if(node[j][i].getBack().equals("S")){
                    System.out.print("\u001b[1;34m" + "S " +  "\u001b[0m");
                }
                else if(node[j][i].getBack().equals(".")){
                    //System.out.print(String.valueOf(getFCost(node[j][i])) + "/" + String.valueOf(getGCost(node[j][i])) + " ");
                    System.out.print("* ");
                }else if (node[j][i].getBack().equals("X")){
                    System.out.print("X ");
                    //System.out.print("\u001b[1;33m" + node[j][i].getBack() + " " + "\u001b[0m"); //33 = sárga, 32 = zöld
                    //System.out.print("\u001b[1;34m" + node[j][i].getBack() +  "\u001b[0m");
                } else {
                    System.out.print("\u001b[1;33m" + node[j][i].getBack() + " " + "\u001b[0m"); //33 = sárga, 32 = zöld
                }

            }
            System.out.println();
        }
    }
}
