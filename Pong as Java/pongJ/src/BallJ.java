import java.awt.*;
import java.util.Random;

public class BallJ {
    float x, y;
    int w, h;
    int xSpeed, ySpeed;
    int br = 255, bg = 255, bb = 255;

    float dirX = 1, dirY = 0;
    float ballAcc = 1;
    float[] dirXR = {1, 0};
    float[] dirYR = {1, 0};
    Dimension screenSize;

    Random r = new Random();
    BallJ(int x_, int y_, int w_, int h_, int xSpeed_, int ySpeed_, Dimension screenSize_) {
        x = x_;
        y = y_;
        w = w_;
        h = h_;
        xSpeed = xSpeed_;
        ySpeed = ySpeed_;
        screenSize = screenSize_;
    }

    void drawBall(Graphics g) {
        g.setColor(Color.white);
        g.fillOval((int) x,(int) y, w, h); //adds color to circle
        g.setColor(Color.black);
        g.drawOval((int) x,(int) y, w, h);
        g.setColor(Color.black);
    }

    void moveBall(PlayerJ p1, PlayerJ p2) {
        ballAcc += .00001;
        if (isInsideRect(p1.x, p1.y, p1.w, p1.h, x, y)){
            dirX = 1;
        }
        else if (isInsideRect(p2.x-p2.w, p2.y, p2.w*2, p2.h, x, y)) {
            dirX = 0;
        }

        if (y > screenSize.height-(h*2))
            dirY = 1;
        else if (y < 0)
            dirY = 0;


        //move X
        if (dirX == 1)
            x += .1*ballAcc;
        else
            x -= .1*ballAcc;

        //Move Y
        if (dirY == 0)
            y += .1*ballAcc;
        else
            y -= .1*ballAcc;
    }

    public boolean score(PlayerJ p1, PlayerJ p2) {
        boolean isScored = false;
//        System.out.println(x);
        if (x < 0){
            p2.score += 1;
            System.out.println("Player 2: " + p2.score);
            isScored = true;
        }
        else if (x > screenSize.width) {
            p1.score += 1;
            System.out.println("Player 1: " + p1.score);
            isScored = true;
        }

        if (isScored) {
            x = 487.5F;
            y = 125;
            br = r.nextInt(255);
            bg = r.nextInt(255);
            bb = r.nextInt(255);
            int randomX = r.nextInt(dirXR.length);
            int randomY = r.nextInt(dirYR.length);
            dirX = dirXR[randomX];
            dirY = dirYR[randomY];
            ballAcc = 1;
        }

//        rectMode(CORNER);
//        textSize(16);
//        fill(0);
//        text("Time: " + (int)timer, 492.5, 0, 200, 100);
//        text(p1.score + ":" + p2.score, 500, 15, 100, 100);
//        fill(255);
        return isScored;
    }

    boolean isInsideRect(float rx, float ry, float rw, float rh, float px, float py) {
        boolean isInside = false;

        if (isInRange(rx, rx + rw, px) && isInRange(ry, ry + rh, py)) {
            isInside = true;
        }

        return isInside;
    }

    boolean isInRange(float start, float end, float value) {
        boolean isRange = false;
        if (value >= start && value <= end)
            isRange = true;
        return isRange;
    }
}
