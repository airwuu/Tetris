import java.util.Arrays;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Point;


public class Board{
  private Block[][] board;
  private int width;
  private int height;
  private int spawn = 3;

  //CTORS -------------------------------------------
  public Board(){
    width = 10;
    height = 20 + spawn;
    board = new Block[height][width];
    fillArray();
  }
  public Board(int w, int h) {
    width = w;
    height = h+spawn;
    board = new Block[h][w];
    fillArray();
  }

  //METHODS -----------------------------------------
  public int getWidth(){
    return width;
  }
  public int getHeight(){
    return height;
  }
  //Pre populate the board with 0
  public void fillArray(){
    for (Block[] row: board) {
      Arrays.fill(row, new Block());
    }
  }
  public boolean isEmpty(){
    for (Block[] row : board) {
      for (Block b : row) {
        if (b.getType() != 0)
          return false;
      }
    }
    return true;
  }
  //Set individual block
  public void setBlock(int c,int r, Block i) {
    board[r][c] = i;
  }
  public Block getBlock(int c, int r){
    return board[r][c];
  }
  public Block[][] getBoard() {
    return board;
  }
  
//Check board
  public int clearFilledLines() {
    int count = 0;
    for(int row = 0; row< board.length; row++) {
      boolean rowFilled = true;
      for (int col = 0; col < board[0].length; col++) {
        if (board[row][col].getType() == 0 || board[row][col].isCurBlock()) rowFilled = false;
      }
      if(rowFilled){
        
        //Do stuff if row is filled
        //score += ;
        moveRowsDown(row);
        count++;
      }
    }
    return count;
  }
  public void moveRowsDown(int r){
    for(int row = r; row > 1; row--){
      for (int col = 0; col < board[0].length; col++) {
        board[row][col] = board[row-1][col];
      }
    }
  }
  //Draw board
  public void draw(Graphics window) {
    for (int r = 0; r < board.length; r++) {
      for (int c = 0; c < board[0].length; c++) {
        window.setColor(new Color(41,41,41));
        window.fillRect(c*20, r*20, 20, 20);
        Color col = Color.BLACK;
        if (board[r][c].getType() == 1)
          col = new Color(255,5,247); 
        if (board[r][c].getType() == 2)
          col = Color.CYAN;
        if (board[r][c].getType() == 3)
          col = Color.ORANGE;
        if (board[r][c].getType() == 4)
          col = Color.BLUE;
        if (board[r][c].getType() == 5)
          col = Color.GREEN;
        if (board[r][c].getType() == 6)
          col = Color.RED;
        if (board[r][c].getType() == 7)
          col = Color.YELLOW;
        if (board[r][c].getType() == -1)
          col = new Color(50,50,50);
        window.setColor(col);
        window.fillRect(c*20+1, r*20+1, 20, 20);
      }
    }
  }

  public boolean topOut() {
    for (int i = 0; i < board[0].length; i++) {
      if (!board[1][i].isCurBlock() && board[1][i].getType() > 0) {
      return true;
    }
  }
      return false;           
  }

}




