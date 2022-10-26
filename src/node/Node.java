package node;

import java.awt.*;

public class Node {


    Node parent;
    int row;
    int col;
    String back;

    int gCost;
    int hCost;
    int fCost;

    boolean start;
    boolean goal;
    boolean solid;
    boolean open;
    boolean checked;

    public Node (int col, int row, String back){
        this.row = row;
        this.col = col;
        this.back = back;
    }

    //start és cél pontok lerakása:

    public void setAsStart(){
        this.back = "S";
        start = true;
    }

    public void setAsGoal(){
        this.back = "G";
        goal = true;
    }

    //obstacle/wall:
    public void setAsSolid(){
        this.back = "X";
        solid = true;
    }

    public void setAsOpen(){
        open = true;
    }

    public void setAsChecked(){
        if(start == false && goal == false){
            setBack("*");
        }
        checked = true;
    }

    public void setAsPath(){
        setBack("\u001b[1;32m" + "P" + "\u001b[0m");
    }

    @Override
    public String toString() {
        return "Node{" +
                "row=" + row +
                ", col=" + col +
                ", back='" + back + '\'' +
                '}';
    }

    public String costs(){//nem jatszik
        return String.valueOf(getfCost() + " " +String.valueOf(getgCost()));
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public int getgCost() {
        return gCost;
    }

    public void setgCost(int gCost) {
        this.gCost = gCost;
    }

    public int gethCost() {
        return hCost;
    }

    public void sethCost(int hCost) {
        this.hCost = hCost;
    }

    public int getfCost() {
        return fCost;
    }

    public void setfCost(int fCost) {
        this.fCost = fCost;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    public boolean isGoal() {
        return goal;
    }

    public void setGoal(boolean goal) {
        this.goal = goal;
    }

    public boolean isSolid() {
        return solid;
    }

    public void setSolid(boolean solid) {
        this.solid = solid;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
