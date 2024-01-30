import java.util.Arrays;
import java.io.File;
import java.net.URL;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.Point;
import java.util.ArrayList;

public class previewBox extends Board {
  private Piece[] pieces;
  private int x;
  private int y;
  
  public previewBox() {
    super(4, 17);
    x = 210;
    y = 100;
  }
  public void setPieces(ArrayList<Integer> in) {
    fillArray();
    for (int i = 1; i < 6; i++) {
      setPiece(in.get(i), i);
    }
  }
  public void setPiece(int t, int i){
    int o = 3*(i-1)+1;
    if(t == 1){
      setBlock(0,1+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(1,0+o, new Block(t));
      setBlock(2,1+o, new Block(t));
    }
    if(t==2){
      setBlock(0,1+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(2,1+o, new Block(t));
      setBlock(3,1+o, new Block(t));
    }
    if(t==3){
      setBlock(0,1+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(2,1+o, new Block(t));
      setBlock(2,0+o, new Block(t));
    }
    if(t==4){
      setBlock(0,1+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(2,1+o, new Block(t));
      setBlock(0,0+o, new Block(t));
    }
    if(t==5){
      setBlock(0,1+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(1,0+o, new Block(t));
      setBlock(2,0+o, new Block(t));
    }
    if(t==6){
      setBlock(0,0+o, new Block(t));
      setBlock(1,0+o, new Block(t));
      setBlock(1,1+o, new Block(t));
      setBlock(2,1+o, new Block(t));
    }
    if(t==7){
      setBlock(0,0+o, new Block(t));
      setBlock(0,1+o, new Block(t));
      setBlock(1,0+o, new Block(t));
      setBlock(1,1+o, new Block(t));
    }
  }
  public void draw(Graphics window) {
    for (int r = 0; r < 17; r++) {
      for (int c = 0; c < 4; c++) {
        window.setColor(new Color(41,41,41));
        window.fillRect(x+c*20, y+ r*20, 20, 20);
        Color col = Color.BLACK;
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
    }
  }
}