import java.util.Arrays;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Point;

public class Holdbox extends Board {
  private int x;
  private int y;
  private Boolean gray = false;

  //CTORS -------------------------------------------
  public Holdbox(){
    super(4, 4);
    x = 210;
    y = 8;
  }
  public Holdbox(int x, int y) {
    this();
    this.x = x;
    this.y = y;
  }
  
  //METHODS -----------------------------------------
  public void setGray(Boolean w){
    gray = w;
  }
  public void setPiece(int t){
    if(t == 1){
      // Test t piece
      fillArray();
      setBlock(0,1+1, new Block(t));
      setBlock(1,1+1, new Block(t));
      setBlock(1,0+1, new Block(t));
      setBlock(2,1+1, new Block(t));
    }
    if(t==2){
      // The test I piece
      fillArray();
      setBlock(0,1, new Block(t));
      setBlock(1,1, new Block(t));
      setBlock(2,1, new Block(t));
      setBlock(3,1, new Block(t));
    }
    if(t==3){
      // The test L piece
      fillArray();
      setBlock(0,1+1, new Block(t));
      setBlock(1,1+1, new Block(t));
      setBlock(2,1+1, new Block(t));
      setBlock(2,0+1, new Block(t));
    }
    if(t==4){
      // The test L piece
      fillArray();
      setBlock(0,1+1, new Block(t));
      setBlock(1,1+1, new Block(t));
      setBlock(2,1+1, new Block(t));
      setBlock(0,0+1, new Block(t));
    }
    if(t==5){
      // The test S piece
      fillArray();
      setBlock(0,1+1, new Block(t));
      setBlock(1,1+1, new Block(t));
      setBlock(1,0+1, new Block(t));
      setBlock(2,0+1, new Block(t));
    }
    if(t==6){
      // The test Z piece
      fillArray();
      setBlock(0,0+1, new Block(t));
      setBlock(1,0+1, new Block(t));
      setBlock(1,1+1, new Block(t));
      setBlock(2,1+1, new Block(t));
    }
    if(t==7){
      // The test O piece
      fillArray();
      setBlock(1,1, new Block(t));
      setBlock(1,2, new Block(t));
      setBlock(2,1, new Block(t));
      setBlock(2,2, new Block(t));
    }
  }
  public void resetHold() {
    fillArray();
  }    
  //Draw board
  public void draw(Graphics window) {
    for (int r = 0; r < 4; r++) {
      for (int c = 0; c < 4; c++) {
        window.setColor(new Color(41,41,41));
        window.fillRect(x+c*20, y+ r*20, 20, 20);
        if(!gray){
          Color col = new Color(0,0,0);
          if (getBlock(c,r).getType() == 1)
            col = new Color(255,5,247); 
          if (getBlock(c,r).getType() == 2)
            col = Color.CYAN;
          if (getBlock(c,r).getType() == 3)
            col = Color.ORANGE;
          if (getBlock(c,r).getType() == 4)
            col = Color.BLUE;
          if (getBlock(c,r).getType() == 5)
            col = Color.GREEN;
          if (getBlock(c,r).getType() == 6)
            col = Color.RED;
          if (getBlock(c,r).getType() == 7)
            col = Color.YELLOW;
          window.setColor(col);
          window.fillRect(x+c*20, y+ r*20, 20, 20);
        }
        else{
          if(getBlock(c,r).getType() > 0)
          window.setColor(new Color(69,69,69));
          window.fillRect(x+c*20, y+ r*20, 20, 20);
        }
      }
    }
  }
}


