package hu.johetajava;


import processing.core.PApplet;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.InputStream;

public class Main {


    private static InputStream inputStream;
    static String arduinoPort = "COM12"; //Your port name here
    private static int BAUD_RATE = 115200;
    static Arduino arduino;
    static Drawer drawer;
    private static float velY;
    private static float velX;
    private static float y;
    private static float x;

    public static void main(String[] args) {
        arduino = new Arduino(BAUD_RATE, arduinoPort);
        PApplet.main("hu.johetajava.Drawer", args);
        x = 0.0f;
        y = 0.0f;
    }


    public static void onTick() {
        arduino.communication.eatNextLine();

        velX = arduino.joystick.x;
        velY = arduino.joystick.y;
        x += velX * 10;
        y -= velY * 10;
        drawer.drawWorld();

        moveMouse(new Point((int) x, (int) y));

        if(arduino.joystick.isPressed){
            click((int)x, (int)y);
        }
        System.err.println(new Point((int) x, (int) y));
    }

    public static void moveMouse(Point p) {
        GraphicsEnvironment ge =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();

        // Search the devices for the one that draws the specified point.
        for (GraphicsDevice device : gs) {
            GraphicsConfiguration[] configurations =
                    device.getConfigurations();
            for (GraphicsConfiguration config : configurations) {
                Rectangle bounds = config.getBounds();
                if (p.x> bounds.width) p.x = bounds.width - 1;
                if (p.x<0) p.x = 1;
                if(p.y > bounds.height) p.y = bounds.height -1;
                if(p.y < 0) p.y = 1;

                if (bounds.contains(p)) {
                    // Set point to screen coordinates.
                    Point b = bounds.getLocation();
                    Point s = new Point(p.x - b.x, p.y - b.y);

                    try {
                        Robot r = new Robot(device);
                        r.mouseMove(s.x, s.y);
                    } catch (AWTException e) {
                        e.printStackTrace();
                    }

                    return;
                }

            }
        }
        // Couldn't move to the point, it may be off screen.
        return;
    }

    public static void click(int x, int y){
        try {
            Robot bot = new Robot();
            bot.mouseMove(x, y);
            bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
}

