import java.awt.*;

public class Enemies extends Rectangle {
  public static int direction = 1;
  private Image image;
  Enemies(int x, int y, int ENEMY_WIDTH, int ENEMY_HEIGHT, Image image) {
    super(x, y, ENEMY_WIDTH, ENEMY_HEIGHT);
    this.image = image;
  }

  public void draw(Graphics g) {
    // g.setColor(Color.white);
    // g.fillOval(x, y, width, height);
    g.drawImage(image, x, y, width, height, null);
  }

  public void move() {
    if (direction == 1)
      y += 4;
    else if (direction == 2)
      x += 4;
  }

  public void up() {
    direction = 1;
  }

  public void left() {
    direction = 2;
  }

}
