import java.awt.*;

public class Bullet extends Rectangle {
  private int direction;
  Bullet(int x, int y, int BULLET_WIDTH, int BULLET_HEIGHT, int direction, Image image) {
    super(x, y, BULLET_WIDTH, BULLET_HEIGHT);
    this.direction = direction;
  }

  public void draw(Graphics g) {
    //lol
    g.setColor(Color.red);
    g.fillRoundRect(x, y, width, height, 5, 5);
  }
  
  public void move() {
    //moving it
    if (direction == 1) {
      y -= 7;
    }
    else {
      x -= 7;
    }
  }
}
