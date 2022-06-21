import javax.swing.*;

public class Frame extends JFrame {
  Panel panel;

  Frame() {
    panel = new Panel();
    add(panel);
    setTitle("CarZag");
    setResizable(false);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setVisible(true);
    pack();
  }
}
