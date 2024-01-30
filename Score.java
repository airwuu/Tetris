import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

public class Score {
  private String name;
  private int score; 
  private int level;
  public Score() {
    score = 0;
    level = 1;
  } 
  public Score(String n, int s) {
    name = n;
    score = s;
    level = 1;
  }
  public void incLevel() {
    level++;
  }
  public int getLevel() {
    return level;
  }
  public void rowScore(int n) {
    if (n == 1)
      score += 100 * level;
    if (n == 2)
      score += 300 * level;
    if (n == 3)
      score += 500 * level;
    if (n == 4)
      score += 800 * level;
  }
  public void incScore(int n) {
    score+=n;
  }
  public int getScore() {
    return score;
  }
  public void erase(Graphics window){
    window.setColor(Color.WHITE);
    window.drawString("Score: " + (score), 320, 20);
  }
  public void draw(Graphics window, Color c) {
    window.setColor(c);
    window.drawString("Score: " + score, 320, 20);
  }
    public void draw(Graphics window, Color c, int x, int y) {
    window.setColor(c);
    window.drawString("Your Score: " + score, x, y);
  }
  public String toString() {
    return name + ": " + score;
  }
}