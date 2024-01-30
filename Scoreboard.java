import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.*;
import java.io.File;  
import java.io.FileWriter;
import java.io.FileNotFoundException;  
import java.io.IOException;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Canvas;

public class Scoreboard {
  private ArrayList<Score> scoreList;

  public Scoreboard() {
    scoreList = new ArrayList<Score>();
    readFile();
  }
  public void readFile() {
    scoreList = new ArrayList<Score>();
    try {
      File myObj = new File("scores.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        int cutoff = data.indexOf(':') + 1;
        String name = data.substring(0, cutoff-1);
        data = data.substring(cutoff+1);
        int score = Integer.parseInt(data);
        scoreList.add(new Score(name, score));
      }
      myReader.close();
      while (scoreList.size() <= 5) {
        scoreList.add(new Score("", 0));
      }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }//;-;

  public void sortArray(){
    scoreList.sort(Comparator.comparing(Score::getScore));
  }

  public void draw(Graphics window) {
    window.setColor(Color.WHITE);
    window.fillRect(330, 330, 330, 330);
    //graphToBack.fillRect(300,200-150,200,200);  
    sortArray();
    int offset = 0;
    window.setColor(Color.BLACK); 
    window.drawString("High Scores: ", 330, 330);
    for (int i = scoreList.size()-5; i < scoreList.size(); i++) {
      String s = (scoreList.get(i).toString());
      window.drawString((5 - offset) + " - " + s, 330, 430 - 20*offset);
      offset++;
    }
  }
}