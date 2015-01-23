package visualization;

import movie.Camera;
import road.Position;
import traffic.Car;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Visualization extends Canvas implements Camera {

    public final int GREEN_LIGHT = 1;
    public final int YELLOW_LIGHT = 2;
    public final int RED_LIGHT = 3;
    public final int RED_YELLOW_LIGHT = 4;

    private BufferStrategy bufferStrategy;
    private Set<Integer> positions;

    public int light;

    Visualization(){
        JFrame frame = new JFrame();
        frame.setTitle("Traffic agents visualization");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Here we add it to the frame
        frame.getContentPane().add(this);
        frame.setVisible(true);

        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();
        requestFocus();

        light = GREEN_LIGHT;
    }


    private Position calculatePosition(int p){

        int x, y, a;
//        if(p > 3278){
//            p = p%3260;
//        }
        p = p%3260;

        if(p > 0 && p < 670){
            x = p+50;
            y = 50;
            a = 270 + 5*p;
            if(a > 360)
                a = 0;
        }
        else if(p >= 670 && p < 810){
            x = 720;
            y = 50 -670 +p;
            a = 5*(p-670);
            if(a > 90)
                a = 90;
        }
        else if(p >= 810 && p < 1320){
            x = 720 -(p-810);
            y = 190;
            a = 90 + 5*(p-810);
            if(a > 180)
                a = 180;
        }
        else if(p >= 1320 && p < 1490){
            x = 210;
            y = 190 + p-1320;
            a = 180 - 5*(p-1320);
            if(a < 90)
                a = 90;
        }
        else if(p >= 1490 && p <1970){
            x = 210 + p -1490;
            y = 360;
            a = 90 - 5*(p-1490);
            if(a < 0)
                a = 0;
        }
        else if(p>=1970 && p <2140){
            x = 690;
            y = 360 + p-1970;
            a = 0 + 5*(p-1970);
            if(a > 90)
                a = 90;
        }
        else if(p >=2140 && p < 2780){
            x = 690 - (p-2140);
            y = 530;
            a = 90 + 5*(p-2140);
            if(a > 180)
                a = 180;
        }
        else if(p >= 2780 && p <3260){
            x = 50;
            y = 530 - (p-2780);
            a = 180 + 5*(p-2780);
            if(a > 270)
                a = 270;
        }
        else {
            x = 50 + p -3260;
            y = 50;
            a = 270 + 5*(p-3260);
            if(a > 360)
                a = 0;
        }
        Position pos = new Position(x, y, a);
        return pos;
    }
    private void drawCar(Graphics2D g, int position){
        drawCar(g, calculatePosition(position));
//        g.setColor(Color.GREEN);
//        g.drawString("Position: "+position, 10, 10);
    }
    private void drawCar(Graphics2D g, Position position){
        drawCar(g, position.x, position.y, position.a);
    }
    private void drawCar(Graphics2D g, int x, int y){
        drawCar(g, x, y, 0);
    }
    private void drawCar(Graphics2D g, int x, int y, int alfa ) {
        int width = 20;
        int height = 8;

        Rectangle2D rect = new Rectangle2D.Double(x -width/2, y-width/2, width, height);

        g.rotate(Math.toRadians(alfa), x, y);
        g.setColor(Color.green);
        g.fill(rect);
        g.setColor(Color.red);
        g.draw(rect);
        g.rotate(Math.toRadians(-alfa), x, y);
    }

    private void drawLights(Graphics2D g){
        Rectangle2D rect = new Rectangle2D.Double(330, 70, 20, 60);
        g.setColor(Color.gray);
        g.fill(rect);
        g.setColor(Color.black);
        g.draw(rect);
        Ellipse2D.Double circle = new Ellipse2D.Double(330, 70, 20, 20);
        if(light == RED_LIGHT || light == RED_YELLOW_LIGHT){
            g.setColor(Color.red);
            g.fill(circle);
            g.setColor(Color.black);
        }
        g.draw(circle);
        circle = new Ellipse2D.Double(330, 90, 20, 20);
        if(light == YELLOW_LIGHT|| light == RED_YELLOW_LIGHT){
            g.setColor(Color.yellow);
            g.fill(circle);
            g.setColor(Color.black);
        }
        g.draw(circle);
        circle = new Ellipse2D.Double(330, 110, 20, 20);
        if(light == GREEN_LIGHT){
            g.setColor(Color.green);
            g.fill(circle);
            g.setColor(Color.black);
        }
        g.draw(circle);
    }


    public void paint() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        //background
        BufferedImage route;
        try {
            route = ImageIO.read(new File("res/route.png"));
            g.drawImage(route,0,0,this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int p: positions){
            drawCar(g, p);
        }

        drawLights(g);

        bufferStrategy.show();
    }

    @Override
    public void nextTimeFrame(Set<Integer> positions) {
        this.positions = positions;
        paint();
    }
}
