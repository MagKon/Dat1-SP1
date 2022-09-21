import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;

//main serves as an alternative to the Setup() function in Processing
class main {
    static int WIDTH = 1000;
    static int HEIGHT = 250;

    public static void main(String [] Args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // creating object of JFrame(Window popup)
                JFrame window = new JFrame("Pong");

                // setting closing operation
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // setting size of the pop window
                window.setBounds(30, 30, WIDTH, HEIGHT);

                // set visibility
                window.setVisible(true);

                // setting canvas for draw
                window.getContentPane().add(new MyCanvas());
                //window.addKeyListener(new MyCanvas());
            }
        });
    }
}

//The canvas class is the alternative to the draw() function in Processing
class MyCanvas extends JComponent {
    private static final int IFW = JComponent.WHEN_IN_FOCUSED_WINDOW;
    int WIDTH = 1000, HEIGHT = 250, br = 255, bg = 255, bb = 255;
    Dimension screensize = new Dimension (WIDTH, HEIGHT);
    int p1X = 0, p1Y = 75, p1W = 25, p1H = 50;
    int p2X = 958, p2Y = 75, p2W = 25, p2H = 50;
    //Init players
    PlayerJ p1 = new PlayerJ(p1X, p1Y, p1W, p1H, 1, screensize);
    PlayerJ p2 = new PlayerJ(p2X, p2Y, p2W, p2H, 2, screensize);
    //Init ball
    BallJ ball = new BallJ(487, 100, 25, 25, 5, 5, screensize);
    long sTimer = System.nanoTime()/1000000000;

    public void paint(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(new Color(br, bg, bb));
        g2d.clearRect(0, 0, getParent().getWidth(), getParent().getHeight());

        long cTimer = System.nanoTime()/1000000000;
        p1.drawPlayer(g);
        p2.drawPlayer(g);
        ball.drawBall(g);
        createKeybinds();

        long dTimer = cTimer - sTimer;

        g.drawString("Timer " + dTimer, 485, 15);
        g.drawString(p1.score + " : " + p2.score, 492, 30);

        ball.moveBall(p1, p2);
        boolean isScored = ball.score(p1, p2);

        Random r = new Random();
        if (isScored) {
            br = r.nextInt(255);
            bg = r.nextInt(255);
            bb = r.nextInt(255);
        }
//
//        if (p1.score >= 10 || p2.score >= 10) {
//            noLoop();
//            fill(0);
//            text("Restart?", width/2 - 12.5, height/2, 200, 200);
//            restart = 1;
//        }

        repaint();
    }

    public void createKeybinds() {
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("UP"), "up");
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("DOWN"), "down");
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("W"), "w");
        this.getInputMap(IFW).put(KeyStroke.getKeyStroke("S"), "s");
        this.getActionMap().put("up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2.movePlayer(KeyEvent.VK_UP);
            }
        });
        this.getActionMap().put("down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p2.movePlayer(KeyEvent.VK_DOWN);
            }
        });this.getActionMap().put("w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1.movePlayer(KeyEvent.VK_W);
            }
        });this.getActionMap().put("s", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                p1.movePlayer(KeyEvent.VK_S);
            }
        });
    }
}