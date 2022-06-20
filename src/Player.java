import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player extends Rectangle{
  private Image image;
  private int lives = 3;

  Player(int x, int y, int PLANE_WIDTH, int PLANE_HEIGHT, Image image) {
    super(x+15, y+15, PLANE_WIDTH, PLANE_HEIGHT);
    this.image = image;
  }

  public void draw(Graphics g) {
    // g.setColor(Color.white);
    // g.fillOval(x, y, 0, height);
    g.drawImage(image, x-15, y-15, width*3, height*3, null);
  }

  public void up(Image img) {
    int temp;
    temp = width;
    width = height;
    height = temp;
    this.image = img;
  }

  public void left(Image img) {
    int temp;
    temp = width;
    width = height;
    height = temp;
    this.image = img;
  }
}
