import javax.swing.JFrame;
import java.awt.Component;

public class Runner extends JFrame
{
  private static final int WIDTH = 800;
  private static final int HEIGHT = 600;

  public Runner()
  {
    super("TETRIS");
    setSize(WIDTH,HEIGHT);

    Tetris theGame = new Tetris();
    ((Component)theGame).setFocusable(true);

    getContentPane().add(theGame);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);

    
  }

  public static void main( String args[] )
  {
    Runner run = new Runner();
  }
}