import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Panel extends JPanel implements Runnable {

  static final int GAME_HEIGHT = 750;
  static final int GAME_WIDTH = 900;
  static final int PLAYER_WIDTH = 12;
  static final int PLAYER_HEIGHT = 12;
  static final int ENEMY_WIDTH = 60;
  static final int ENEMY_HEIGHT = 60;
  static final int ROAD_WIDTH = 70;
  static final int ROAD_HEIGHT = 70;
  static final int BULLET_WIDTH = 2;
  static final int BULLET_HEIGHT = 30;
  static final double GAME_SPEED = 4.0;

  static int prob = 300;
  static boolean running = true;
  Image grass;
  Image roadPic;
  Image roadPic1;
  Image roadPic2;
  Image roadPic3;
  Image character;
  Image character1;
  Image enemy;
  Thread gamethread;
  Player player;
  ArrayList<Road> road = new ArrayList<>();
  ArrayList<Enemies> enemies = new ArrayList<>();
  Score score;
  Bullet bullet;
  int scoreNum;
  Random r = new Random();

  Panel() {
    grass = new ImageIcon("images/ground.png").getImage();
    roadPic = new ImageIcon("src/images/grassv.png").getImage();
    roadPic1 = new ImageIcon("src/images/grassh.png").getImage();
    roadPic2 = new ImageIcon("src/images/grassvh.png").getImage();
    roadPic3 = new ImageIcon("src/images/grasshv.png").getImage();
    character = new ImageIcon("src/images/tankv.png").getImage();
    character1 = new ImageIcon("src/images/tankh.png").getImage();
    enemy = new ImageIcon("src/images/skull.png").getImage();

    newRoad();
    newPlane();
    newScore();
    setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
    setFocusable(true);
    addKeyListener(new AL());
    gamethread = new Thread(this);
    gamethread.start();
  }

  public void newRoad() {
    for (int i = ROAD_HEIGHT * 30; i > 0; i -= ROAD_HEIGHT)
      road.add(new Road(GAME_WIDTH / 2 - ROAD_WIDTH / 2, i, ROAD_WIDTH, ROAD_HEIGHT, roadPic));
  }

  public void addRoad() {
    int rand = r.nextInt(2);
    int x = road.get(road.size() - 1).x;
    int y = road.get(road.size() - 1).y;

    int spawn = r.nextInt(prob);

    if (rand == 0) {
      if (road.get(road.size() - 1).getImage() == roadPic1) {
        road.get(road.size() - 1).changeImage(roadPic3);
      }
      road.add(new Road(x, y - ROAD_HEIGHT, ROAD_WIDTH, ROAD_HEIGHT, roadPic));
      if (spawn == 0) {
        enemies.add(new Enemies(x + ROAD_WIDTH / 4, y + ROAD_HEIGHT / 4, ENEMY_WIDTH, ENEMY_HEIGHT, enemy));
        prob = 300;
      } else {
        prob /= 2;
      }
    }

    else if (rand == 1) {
      if (road.get(road.size() - 1).getImage() == roadPic) {
        road.get(road.size() - 1).changeImage(roadPic2);
      }
      road.add(new Road(x - ROAD_HEIGHT, y, ROAD_HEIGHT, ROAD_WIDTH, roadPic1));
      if (spawn == 0) {
        enemies.add(new Enemies(x + ROAD_WIDTH / 4, y + ROAD_HEIGHT / 4, ENEMY_WIDTH, ENEMY_HEIGHT, enemy));
        prob = 300;
      } else {
        prob /= 2;
      }
    }

  }

  public void newPlane() {
    player = new Player(GAME_WIDTH / 2 - PLAYER_WIDTH / 2, GAME_HEIGHT / 2, PLAYER_WIDTH, PLAYER_HEIGHT, character);
  }

  public void newScore() {
    score = new Score(GAME_WIDTH - 200, 100, 150, 150);
  }

  public void shootBullet() {
    int direction = road.get(0).getDirection();
    if (bullet == null) {
      if (direction == 1)
        bullet = new Bullet(player.x + (PLAYER_WIDTH / 3), player.y, BULLET_WIDTH, BULLET_HEIGHT, direction, roadPic);
      else
        bullet = new Bullet(player.x, player.y + (PLAYER_HEIGHT / 3), BULLET_HEIGHT, BULLET_WIDTH, direction, roadPic);
    }
  }

  public void paint(Graphics g) {
    draw(g);
    for (Road r : road)
      r.draw(g);
    for (Enemies e : enemies)
      e.draw(g);
    player.draw(g);
    if (bullet != null)
      bullet.draw(g);
    score.draw(g, scoreNum);
  }

  public void draw(Graphics g) {
    for (int i = 0; i < GAME_WIDTH; i += 50) {
      for (int j = 0; j < GAME_HEIGHT; j += 50) {
        g.drawImage(grass, i, j, 50, 50, null);
      }
    }
  }

  public void move() {
    for (Road r : road)
      r.move();
    for (Enemies e : enemies)
      e.move();
    if (bullet != null)
      bullet.move();
  }

  public void collect() {
    for (int i = 0; i < road.size(); i++) {
      if (road.get(i).x + ROAD_HEIGHT >= GAME_WIDTH + 200 || road.get(i).y + ROAD_HEIGHT >= GAME_HEIGHT + 200) {
        road.remove(i);
        addRoad();
      }
    }
    if (bullet != null && (bullet.x < 0 || bullet.y < 0))
      bullet = null;
  }

  public void bulletHit() {
    for (Enemies e : enemies) {
      if (bullet.intersects(e)) {
        enemies.remove(e);
        scoreNum += 1;
        bullet = null;
        return;
      }
    }
  }

  public boolean collision() {
    for (Enemies e : enemies) {
      if (player.intersects(e)) {
        return true;
      }
    }
    return false;
  }

  public boolean outOfBounds() {
    for (Road r : road) {
      if (r.intersects(player)) {
        return false;
      }
      // if (player.x + 10 > r.x
      //     && player.x + (PLAYER_WIDTH) < r.x + ROAD_WIDTH
      //     && player.y + 10 > r.y
      //     && player.y + (PLAYER_WIDTH) < r.y + ROAD_HEIGHT)
      //   return false;
    }
    return true;
  }


  public void restart() {
    for (Enemies en : enemies)
      en.up();
    for (Road r : road)
      r.up();
    road.clear();
    enemies.clear();
    player.up(character);
    newRoad();
    scoreNum = 0;

  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    double amountOfTicks = 60.0;
    double ns = 1000000000 / amountOfTicks;
    double delta = 0;
    while (running) {
      long now = System.nanoTime();
      delta += (now - lastTime) / ns;
      lastTime = now;
      if (delta >= 1) {
        move();
        // collision();
        if (bullet != null) {
          bulletHit();
        }
        if (outOfBounds() || collision()) {
          restart();
        }
        collect();
        repaint();
        delta--;
      }
    }
  }

  public class AL extends KeyAdapter {
    public void keyPressed(KeyEvent e) {
      if (e.getKeyCode() == KeyEvent.VK_UP) {
        scoreNum += 1;
        for (Enemies en : enemies)
          en.up();
        for (Road r : road)
          r.up();
        player.up(character);
      }
      if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        scoreNum += 1;
        for (Road r : road)
          r.left();
        for (Enemies en : enemies)
          en.left();
        player.left(character1);
      }
      if (e.getKeyCode() == KeyEvent.VK_SPACE) {
        shootBullet();
      }

    }

    public void keyReleased(KeyEvent e) {
    }
  }
}
