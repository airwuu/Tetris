import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import static java.lang.Character.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.ArrayList;
import java.io.File;  
import java.io.FileWriter;
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.Scanner;

public class Tetris extends Canvas implements KeyListener, Runnable {
  private boolean[] keys;
  private BufferedImage back;
  private Board board;
  private Holdbox hold;
  private Piece curPiece;
  private Ghost ghost;
  private ArrayList<Integer> nextPieces = new ArrayList<Integer>();
  private Score score;
  private Scoreboard scoreboard;
  private previewBox preview;
  private int timeInterval;
  private int countDownAttempts = 0;
  private int holdPieceType = 0;  
  private int linesCleared;
  private boolean swapped;
  private boolean run;

  public Tetris() {
    run = true;
    keys = new boolean[10];
    board = new Board();
    hold = new Holdbox();
    swapped = false;
    score = new Score();
    preview = new previewBox();
    scoreboard = new Scoreboard();
    timeInterval = 500;
    linesCleared = 0;
    setBackground(Color.WHITE);
    newPiece();
    ghost = new Ghost(curPiece);
    setVisible(true);
    new Thread(this).start();
    addKeyListener(this);
    
  }

  public void update(Graphics window) {
    paint(window);
  }

  public void paint(Graphics window) {
    Graphics2D twoDGraph = (Graphics2D) window;
    if (back == null)
      back = (BufferedImage) (createImage(getWidth(), getHeight()));
    Graphics graphToBack = back.createGraphics();
    // Controls
    checkKeys(graphToBack);
    //Level Text
    graphToBack.setColor(Color.BLACK);
    String lev = "Level: " + score.getLevel();
    graphToBack.drawString(lev, 320, 40);
    //Check for cleared rows
    score.erase(graphToBack);
    int rows = board.clearFilledLines();
    linesCleared += rows;
    if (board.isEmpty() && score.getScore() != 0) {
      score.incScore(2000 * score.getLevel());
    } else {
      score.rowScore(rows);
    }
    if(linesCleared >= 10) {
      graphToBack.setColor(Color.WHITE);
      graphToBack.drawString("Level: " + score.getLevel(), 320, 40);
      score.incLevel();
      linesCleared = 0;
      graphToBack.setColor(Color.BLACK);
      String newLev = "Level: " + score.getLevel();
      graphToBack.drawString(newLev, 320, 40);
    }
    //Check topout --------------------------------------
    if(board.topOut()) {
      nextPieces = new ArrayList<Integer>();
      preview.fillArray();
      graphToBack.setColor(Color.RED);
      String s = "GAME OVER -- YOU LOST";
      graphToBack.drawString(s, 400-graphToBack.getFontMetrics().stringWidth(s) / 2, 250-150);
      graphToBack.setColor(Color.BLACK);
      String s2 = "PRESS R KEY TO RESTART";
      graphToBack.drawString(s2, 400-graphToBack.getFontMetrics().stringWidth(s) / 2, 270-150);
      score.draw(graphToBack, Color.BLACK, 400-graphToBack.getFontMetrics().stringWidth(s) / 2, 290-150); 
      twoDGraph.drawImage(back, null, 0, 0);
      if (run == true) {
        try {
          File myObj = new File("scores.txt");
          if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          } else {
          }
          try {
            FileWriter myWriter = new FileWriter("scores.txt", true);
            Scanner myReader = new Scanner(System.in);
            System.out.print("Enter your name: ");
            String name = myReader.nextLine();
            myWriter.write(name + ": " + score.getScore() + "\n");
            myWriter.close();
            //System.out.println("Successfully wrote to the file.");
            scoreboard.readFile();
            scoreboard.draw(graphToBack);
            //Integer.parseInt(string.replaceAll("[\\D]", ""))
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
        run = false;        
      }
   
    }
    // Draw Things
    if (run) {
    ghost = new Ghost(curPiece);
    ghost.draw(board);
    curPiece.draw(board);
    board.draw(graphToBack);
    hold.draw(graphToBack);
    score.draw(graphToBack, Color.BLACK);
    preview.draw(graphToBack);
    scoreboard.draw(graphToBack);
    }

    
    // Keep this at the end
    twoDGraph.drawImage(back, null, 0, 0);
  }
  
  public void newPiece() {
    if (nextPieces.size() < 7 && run) {
      ArrayList<Integer> temp = new ArrayList<Integer>();
      Collections.addAll(temp, 1, 2, 3, 4, 5, 6, 7);
      Collections.shuffle(temp);
      nextPieces.addAll(temp);
    }
    if (run) {
      preview.setPieces(nextPieces);
      curPiece = new Piece(3, 0, nextPieces.get(0));
      nextPieces.remove(0); 
    }
  }
//;-;

  // Key Presses--------------------------------------------
  public void checkKeys(Graphics graphToBack){
    if (keys[0]) {// Left Arrow
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.moveLeft(board);
      if (!curPiece.isLegal(board)) {
        curPiece.moveRight(board);
      }
      keys[0] = false;
    }
    if (keys[1]) {// Right Arrow
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.moveRight(board);
      if (!curPiece.isLegal(board)) {
        curPiece.moveLeft(board);
      }
      keys[1] = false;
    }
    if (keys[2]||keys[6]) {// Up Arrow and X
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.rotateCW();
      if (!curPiece.isLegal(board)) {
        curPiece.rotateCCW();
      }
      keys[2] = false;
      keys[6] = false;
    }
    if (keys[3] && run) {// Down Arrow
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.moveDown(board);
      score.erase(graphToBack);
      score.incScore(1);
      if (!curPiece.isLegal(board)) {
        curPiece.moveUp(board);
      }
      keys[3] = false;
    }
    if (keys[4] && run) {// Space
      swapped = false;
      hold.setGray(false);
      ghost.erase(board);
      score.erase(graphToBack);
      score.incScore(curPiece.hardDrop(board));
      curPiece.setCurPiece(false);
      newPiece();
      countDownAttempts = 0;
      keys[4] = false;
    }
    if (keys[5]) {// Z
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.rotateCCW();
      if (!curPiece.isLegal(board)) {
        curPiece.rotateCW();
      }
      keys[5] = false;
    }
    if (keys[7]) {// Z
      ghost.erase(board);
      curPiece.erase(board);
      curPiece.rotateOneEighty();
      if (!curPiece.isLegal(board)) {
        curPiece.rotateOneEighty();
      }
      keys[7] = false;
    }
    if (keys[8]) { //R to restart
      graphToBack.setColor(Color.WHITE);
      graphToBack.drawString("Level: " + score.getLevel(), 320, 40);
      run = true;
      ghost.erase(board);
      board = new Board();
      score.erase(graphToBack);
      score = new Score();
      hold.resetHold();
      holdPieceType = 0;
      setBackground(Color.WHITE);
      nextPieces = new ArrayList<Integer>();
      newPiece();
      curPiece.draw(board);
      graphToBack.setColor(Color.WHITE);
      graphToBack.fillRect(300,200-150,200,200);  
      keys[8] = false;
    }
    
    if (keys[9]) { //C to hold
      ghost.erase(board);
      int tempType = curPiece.getType();
      if((holdPieceType == 0)){
        swapped = true;
        hold.setGray(true);
        holdPieceType = tempType;
        hold.setPiece(tempType);
        curPiece.erase(board);
        newPiece();
      }
      else{
        if(!swapped){
          swapped = true;
          hold.setGray(true);
          curPiece.erase(board);
          curPiece = new Piece(3,0,holdPieceType);
          hold.setPiece(tempType);
          holdPieceType = tempType;
        }
      }
      curPiece.draw(board);
      keys[9] = false;
    }
  }
  
  public void keyPressed(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      keys[7] = true;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
      keys[8] = true;
    }
    switch(toUpperCase(e.getKeyChar()))
    {  
    case 'A' : keys[5]=true; break;
    case 'D' : keys[6]=true; break;
    case 'Z' : keys[5]=true; break;
    case 'X' : keys[6]=true; break;
    case 'W' : keys[3]=true; break;
    case 'C' : keys[9]=true; break;
    
    }
    repaint();
  }

  public void keyReleased(KeyEvent e) {
    if (e.getKeyCode() == KeyEvent.VK_LEFT) {
      keys[0] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
      keys[1] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_UP) {
      keys[2] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_DOWN) {
      keys[3] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
      keys[4] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_S) {
      keys[7] = false;
    }
    if (e.getKeyCode() == KeyEvent.VK_R) {
      keys[8] = false;
    }
    switch(toUpperCase(e.getKeyChar()))
    {
    case 'A' : keys[5]=false; break;
    case 'D' : keys[6]=false; break;
    case 'Z' : keys[5]=false; break;
    case 'X' : keys[6]=false; break;
    case 'W' : keys[3]=false; break;
    case 'C' : keys[9]=false; break;
    }
    repaint();
  }

  public void keyTyped(KeyEvent e) {
  }

  public void run() {
    try {
      while (true) {
        //Gravity and auto place
          if(run == true){
            Thread.sleep(timeInterval/score.getLevel());
            curPiece.erase(board);
            curPiece.moveDown(board);
            if (!curPiece.isLegal(board)) {
              countDownAttempts++;
              curPiece.moveUp(board);
            }
            if(countDownAttempts >= 3*score.getLevel()){
              countDownAttempts = 0;
              swapped = false;
              hold.setGray(false);
              curPiece.hardDrop(board);
              curPiece.setCurPiece(false);
              newPiece();
            }
          }
        repaint();
      }
    } catch (Exception e) {
    }
  }
}
