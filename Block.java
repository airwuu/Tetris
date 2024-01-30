import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.Point;

public class Block {
  private int type;
  private Point coords;
  private Color color;
  private Boolean curBlock;

  //CTORS--------------------------------------
  public Block() {
    type = 0;
    color = Color.BLACK;
    curBlock = true;
  }
  public Block(int t) {
    type = t;
    curBlock = true;
  }
  public Block(Point p) {
    coords = p;
    curBlock = true;
  }
  public Block(int x,int y) {
    coords = new Point(x, y);
    type = 0;
    color = Color.BLACK;
    curBlock = true;
  }  
  public Block(int x,int y, int t) {
    coords = new Point(x, y);
    type = t;
    curBlock = true;
  }  
  //METHODS-----------------------------------

  //Getters
  public int getType(){
    return type;
  }
  public int getX(){
    return (int) coords.getX();
  }
  public int getY()
  {
    return (int) coords.getY();
  }
  public boolean isCurBlock(){
    return curBlock;
  }
  //Setters
  public void setX(int x){
    coords.x = x;
  }
  public void setY(int y){
    coords.y = y;
  }
  public void setTypeZero() {
    type=0;
  }
  public void setType(int t){
    type = t;
  }
  public void setCurBlock(Boolean state){
    curBlock = state;
  }
}