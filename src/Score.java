import java.awt.*;

public class Score extends Rectangle {
  Score(int x, int y, int width, int height) {
    super(x, y, width, height);
  }

  public void draw(Graphics g, int score) {
    g.setColor(Color.white);
    g.setFont(new Font("Consolas", Font.PLAIN, 60));

    g.drawString(Integer.toString(score), x, y);

  }
}