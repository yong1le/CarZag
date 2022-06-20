import java.awt.*;
import javax.swing.*;

public class Bullet extends Rectangle {
  private int direction;
  private Image image;

  Bullet(int x, int y, int BULLET_WIDTH, int BULLET_HEIGHT, int direction, Image image) {
    super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    this.image = image;
    this.direction = direction;
  }

  public void draw(Graphics g) {
    g.setColor(Color.red);
    g.fillRoundRect(x, y, width, height, 5, 5);
  }
  
  public void move() {
    if (direction == 1) {
      y -= 7;
    }
    else {
      x -= 7;
    }
  }
}
