import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.util.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Point;

public class Piece extends Block{
  private ArrayList<Block> blocks;
  private int[][] relative; //the array of relative values 4x4
  private int state = 0;
  private int x;
  private int y;
  private int type;

  //Main ctor
  public Piece(int x, int y, int t){
    type = t;
    this.x = x;
    this.y = y;
    blocks = new ArrayList<Block>(4);
    relative = new int[4][4];
    //Convert Types to color:
    // 0 = Black
    // 1 = Purple / T piece
    // 2 = Cyan / I Piece
    // 3 = orange / L piece
    // 4 = Blue / J piece
    // 5 = Green / S piece
    // 6 = Red / Z piece
    // 7 = Yellow / O piece
    if(t == 1){
      // Test t piece
      blocks.add(new Block(x-1, y, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x, y-1, type));
      blocks.add(new Block(x+1, y, type));
      relative[0][1] = 1;
      relative[1][1] = 1;
      relative[1][0] = 1;
      relative[2][1] = 1;
    }
    if(t==2){
      // The test I piece
      blocks.add(new Block(x-1, y, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x+1, y, type));
      blocks.add(new Block(x+2, y, type));
      relative[0][1] = 1;
      relative[1][1] = 1;
      relative[2][1] = 1;
      relative[3][1] = 1;
    }
    if(t==3){
      // The test L piece
      blocks.add(new Block(x-1, y, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x+1, y, type));
      blocks.add(new Block(x+1, y-1, type));
      relative[0][1] = 1;
      relative[1][1] = 1;
      relative[2][1] = 1;
      relative[2][0] = 1;
    }
    if(t==4){
      // The test L piece
      blocks.add(new Block(x-1, y, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x+1, y, type));
      blocks.add(new Block(x-1, y-1, type));
      relative[0][1] = 1;
      relative[1][1] = 1;
      relative[2][1] = 1;
      relative[0][0] = 1;
    }
    if(t==5){
      // The test S piece
      blocks.add(new Block(x-1, y, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x, y-1, type));
      blocks.add(new Block(x+1, y-1, type));
      relative[0][1] = 1;
      relative[1][1] = 1;
      relative[1][0] = 1;
      relative[2][0] = 1;
    }
    if(t==6){
      // The test Z piece
      blocks.add(new Block(x-1, y-1, type));
      blocks.add(new Block(x, y-1, type));
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x+1, y, type));
      relative[0][0] = 1;
      relative[1][0] = 1;
      relative[1][1] = 1;
      relative[2][1] = 1;
    }
    if(t==7){
      // The test O piece
      blocks.add(new Block(x, y, type));
      blocks.add(new Block(x, y-1, type));
      blocks.add(new Block(x+1, y, type));
      blocks.add(new Block(x+1, y-1, type));
      relative[1][1] = 1;
      relative[1][2] = 1;
      relative[2][1] = 1;
      relative[2][2] = 1;
    }
    if(t==8){
      blocks.add(new Block(x, y, 6));
      blocks.add(new Block(x+1, y, 6));
      blocks.add(new Block(x+2, y, 6));
      relative[0][0] = 1;
      relative[1][0] = 1;
      relative[2][0] = 1;
      
      // blocks.add(new Block(x, y+1, 2));
      // blocks.add(new Block(x+1, y+1, 2));
      blocks.add(new Block(x+2, y+1, 6));
      blocks.add(new Block(x+3, y+1, 6));
      relative[1][2] = 1;
      relative[2][2] = 1;
      relative[3][2] = 1;
      relative[4][2] = 1;
      blocks.add(new Block(x, y+2, 6));
      blocks.add(new Block(x+1, y+2, 6));
      blocks.add(new Block(x+2, y+2, 6));
      blocks.add(new Block(x+3, y+2, 6));
      relative[1][3] = 1;
      relative[2][3] = 1;
      relative[3][3] = 1;
      relative[4][3] = 1;

      blocks.add(new Block(x, y+3, 6));
      blocks.add(new Block(x+2, y+3, 6));
      relative[1][4] = 1;
      relative[3][4] = 1;

    }
    rotateCW();
    rotateCCW();
  }
  public Piece(int x, int y, Point[] coords) {
    this.x = x;
    this.y = y;
    blocks = new ArrayList<Block>(4);
    relative = new int[4][4];
    for (int i = 0; i < coords.length; i++) {
      blocks.add(new Block(coords[i]));
      relative[coords[i].x][coords[i].y] = 1;
    }
  }

  //Getters----------------------------------
  public int getType(){
    return type;
  }
  public ArrayList<Block> getBlockList(){
    return blocks;
  }
  public int getX(){
    return x;
  }
  public int getY(){
    return y;
  }



  // Methods-----------------------------------
  public void updateState(int x) {
    state = x;
  }

  public void rotateMatrix(int in[][]) { //rotates 4x4
    for (int x = 0; x < 2; x++) {
      for (int y = x; y < 3 - x; y++) {
        int temp = in[x][y];
        in[x][y] = in[y][3 - x];
        in[y][3 - x] = in[3 - x][3 - y];
        in[3 - x][3 - y] = in[3 - y][x];
        in[3 - y][x] = temp;
      }
    }
  }
  public void rotateMatrix3(int in[][]) { //rotates 3x3
    int N = 3;
    for (int i = 0; i < 3 / 2; i++)
    {
        for (int j = i; j < N - i - 1; j++)
        {
            int temp = in[i][j];
            in[i][j] = in[N - 1 - j][i];
            in[N - 1 - j][i] = in[N - 1 - i][N - 1 - j];
            in[N - 1 - i][N - 1 - j] = in[j][N - 1 - i];
            in[j][N - 1 - i] = temp;
        }
    }
  }
  public void rotateCCW() {
    blocks = new ArrayList<Block>(4);
    if (type == 1 || type == 3 || type == 4 || type == 5 || type == 6 ) {
      rotateMatrix3(relative);
    } else {
      rotateMatrix(relative);
    }
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (relative[i][j] == 1) {
          blocks.add(new Block(i, j, type));
        }
      }
    }
  }
    public void rotateOneEighty() {
    rotateCCW();
    rotateCCW();
  }
  
  public void rotateCW() {
    blocks = new ArrayList<Block>(4);
    if (type == 1 || type == 3 || type == 4 || type == 5 || type == 6 ) {
      rotateMatrix3(relative);
      rotateMatrix3(relative);
      rotateMatrix3(relative);
    } else {
      rotateMatrix(relative);
      rotateMatrix(relative);
      rotateMatrix(relative);
    }
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (relative[i][j] == 1) {
          blocks.add(new Block(i, j, type));
        }
      }
    }
  }
  
  public void moveLeft(Board board) {
    x--;
    draw(board);
  }
  public void moveRight(Board board) {
    x++;
    draw(board);
  }
  public void moveDown(Board board) {
    y++;
   draw(board);
  }
  public void moveUp(Board board) {
    y--;
    draw(board);
  }
  public int hardDrop(Board board) {
    int count = 0;
    while(isLegal(board)){
      erase(board);
      moveDown(board);
      
      count++;
    }
    moveUp(board);
    return 2*count;
  }

  //Check if current location is within the bounds of board
  public boolean isLegal(Board board){
    boolean isLegal = true;
    for (Block b : blocks) {
      if(
         b.getX() + x > board.getWidth()-1 ||
         b.getX() + x < 0 ||
         b.getY() + y > board.getHeight()-1 ||
         b.getY() + y < 0 ||
         (board.getBlock(b.getX() + x,b.getY() + y).getType() > 0 && !board.getBlock(b.getX() + x,b.getY() + y).isCurBlock())
        ){
          isLegal = false;
        }
    }
    return isLegal;
  }
  public void setCurPiece(Boolean state){
    for (Block b : blocks) {
      b.setCurBlock(state);
    }
  }
  //Draw Board 
  public void draw(Board board) { //Graphics windows
    if(isLegal(board)){
      for (Block b : blocks) {
        board.setBlock(b.getX() + x, b.getY() + y, b);
      }
    }
  }
  //Undraw piece
  public void erase(Board board) { //Graphics windows
      if(isLegal(board)){
        for (Block b : blocks) {
          board.setBlock(b.getX() + x, b.getY() + y, new Block());
        }
      }
  }
  //Debug code
  public void print() {
    for (int[] r : relative) {
      for (int i : r) {
        System.out.print(i);
      }
      System.out.println();
    }
    System.out.println();
    System.out.println(Arrays.deepToString(relative) + "\n");
  }
}