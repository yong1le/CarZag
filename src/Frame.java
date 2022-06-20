import java.awt.*;
import javax.swing.*;

public class Frame extends JFrame {
  Panel panel;

  Frame() {
    panel = new Panel();
    add(panel);
    setTitle("Jason's Exploration");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    pack();
  }
}
