import java.awt.*;

public class Road extends Rectangle {
  public static int direction = 1;
  private Image image;

  Road(int x, int y, int ROAD_WIDTH, int ROAD_HEIGHT, Image image) {
    super(x, y, ROAD_WIDTH, ROAD_HEIGHT);
    this.image = image;
  }

  public void draw(Graphics g) {
    g.drawImage(image, x, y, width, height, null);
    // g.setColor(new Color(112, 84, 62));
    // g.fillRect(x, y, width, height);
    // g.fill3DRect(x, y, width, height,true);
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

  public int getDirection() {
    return direction;
  }

  public Image getImage() {
    return this.image;
  }

  public void changeImage(Image pic) {
    this.image = pic;
  }

}
