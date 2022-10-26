package finder_JFrame;



import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class DemoPanel extends JPanel /*implements Runnable*/{

    //screen settings
    final int maxCol = 15;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    Thread thread;

    Random random = new Random();

    boolean goalReached = false;

    //NODE
    Node node [][] = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    public DemoPanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);
        //place nodes:
        int col = 0;
        int row = 0;

        while(col < maxCol && row < maxRow){

            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }

        }
        //set start and goal node:
        //setStartNode(8, 5);
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

        //st costs:
        setCostOnNodes();

    }

    private void setStartNode(int col, int row){
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
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    private void setSolidNode(int col, int row){
        node[col][row].setAsSolid();
    }



    private void setCostOnNodes(){
        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow){
            getCost(node[col][row]);
            col++;
            if(col == maxCol){
                col = 0;
                row++;
            }
        }

    }

    private void getCost (Node node){
        //get gCost (The distance from the start node):
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //get hCost (The distance from the goal node):
         xDistance = Math.abs(node.col - goalNode.col);
         yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        //get F cost : F = G + H:
        node.fCost = node.gCost + node.hCost;

        //displas the costs on every node:
        if(node != startNode && node != goalNode){
            node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost +  "<br>H:" + node.hCost + "<html>");
            //node.setText("<html>F:" + node.fCost + "<br>G:" + node.gCost + "<html>");
        }
    }

    public void search() { //Enter lenyomásával
        if(goalReached == false){
            int col = currentNode.col;
            int row = currentNode.row;
            //System.out.println("elso col: " + col);
            //System.out.println("elso row: " + row);

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
                if(openList.get(i).fCost < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if(openList.get(i).fCost == bestNodeFCost){
                    //check the G cost
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }

            }
            //after the loop, we get the best node which is our next step:
            currentNode = openList.get(bestNodeIndex);



            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }

        }

    }

    public void autoSearch() { //0 lenyomásával

        while(goalReached == false){

            int col = currentNode.col;
            int row = currentNode.row;
            System.out.println("elso col: " + col);
            System.out.println("elso row: " + row);

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);
            //Thread.sleep(1000);

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
                if(openList.get(i).fCost < bestNodeFCost){
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if(openList.get(i).fCost == bestNodeFCost){
                    //check the G cost
                    if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                        bestNodeIndex = i;
                    }
                }

            }
            //after the loop, we get the best node which is our next step:
            currentNode = openList.get(bestNodeIndex);

            if(currentNode == goalNode){
                goalReached = true;
                trackThePath();
            }

        }
    }

    private void openNode(Node node){
        if(node.open == false && node.checked == false && node.solid ==false){
            //if the node not opened yet, add it to the open list
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    int trackInt = 0;

    private void trackThePath(){
        Node current = goalNode;
        while (current != startNode){
            trackInt++;
            current = current.parent;
            if(current != startNode){
                current.setAsPath();
            }
        }
        System.out.println("trackInt: "+trackInt);
    }


    /*
    public void DemoThread(){
        thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run() {
        while (thread != null){
            while(goalReached == false){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int col = currentNode.col;
                int row = currentNode.row;
                System.out.println("elso col: " + col);
                System.out.println("elso row: " + row);

                currentNode.setAsChecked();
                checkedList.add(currentNode);
                openList.remove(currentNode);
                //Thread.sleep(200);

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
                    if(openList.get(i).fCost < bestNodeFCost){
                        bestNodeIndex = i;
                        bestNodeFCost = openList.get(i).fCost;
                    } else if(openList.get(i).fCost == bestNodeFCost){
                        //check the G cost
                        if(openList.get(i).gCost < openList.get(bestNodeIndex).gCost){
                            bestNodeIndex = i;
                        }
                    }

                }
                //after the loop, we get the best node which is our next step:
                currentNode = openList.get(bestNodeIndex);

                if(currentNode == goalNode){
                    goalReached = true;
                    trackThePath();
                }

            }
        }
    }
    */
}
