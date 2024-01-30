import java.util.*;
import java.awt.Point;
import java.util.ArrayList;
public class Ghost extends Piece {
  private ArrayList<Block> blocks;
  private int[][] relative; //the array of relative values 4x4
  private int x;
  private int y;
  private int type;
  public Ghost(Piece p){
    super(p.getX(), p.getY(), p.getType());
    blocks = p.getBlockList();
    x = p.getX();
    y = p.getY();
  }
  public void draw(Board board) {
    hardDrop(board);
      for (Block b : blocks) {
        if (isLegal(board)) {
          board.setBlock(b.getX() + x, b.getY() + y, new Block(-1));
        }
      }
  }
  public void erase(Board board) {
        for (Block b : blocks) {
          if (isLegal(board)) {
            board.setBlock(b.getX() + x, b.getY() + y, new Block());
           }
        }
  }
  public int hardDrop(Board board) {
    while(isLegal(board)){
      y++;
    }
    y--;
    return 0;
  }
  public boolean isLegal(Board board){
    boolean isLegal = true;
    for (Block b : blocks) {
      if(
         b.getX() + x > board.getWidth()-1 ||
         b.getX() + x < 0 ||
         b.getY() + y > board.getHeight()-1 ||
         b.getY() + y < 0 ||
         (board.getBlock(b.getX() + x, b.getY() + y).getType() > 0 && !board.getBlock(b.getX() + x, b.getY() + y).isCurBlock())
        ){
          isLegal = false;
        }
    }
    return isLegal;
  }
}