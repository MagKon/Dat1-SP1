import com.sun.javafx.geom.Path2D;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;
import java.lang.reflect.Constructor;
import java.util.Scanner;

class PlayerJ{
    int x;
    int y;
    int w;
    int h;
    int score = 0;
    int pNum;

    Dimension screenSize;

    PlayerJ(int x_, int y_, int w_, int h_, int pNum_, Dimension screenSize_) {
        x = x_;
        y = y_;
        w = w_;
        h = h_;
        pNum = pNum_;
        screenSize = screenSize_;
        //drawPlayer();

    }

    void drawPlayer(Graphics g) {
        g.setColor(Color.white);
        g.fillRect((int) x,(int) y, w, h); //adds color to circle
        g.setColor(Color.black);
        g.drawRect(x,y, w, h);
        g.setColor(Color.black);
    }

    int[] getPlayer() {
        return new int[]{x, y, w, h};
    }

    public void movePlayer(int e) {
//        System.out.println("Key Pressed: " + e);
//        System.out.println(this.y);
        switch( e ) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                // handle up
                if (y > 0)
                    this.y -= 20;
                else
                    this.y = 0;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S :
                // handle down
                System.out.println("Screen Height: " + screenSize.getHeight());
                if (y < screenSize.height - h*2)
                    this.y += 20;
                else
                    this.y = screenSize.height - h*2;
                //this.y += 10;
                break;
            default:
                // handle other keys
                System.out.println("Key not recognized");
                break;
        }
//        System.out.println(this.y);
    }
}