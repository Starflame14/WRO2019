package hu.johetajava;

import processing.core.PApplet;

import static hu.johetajava.Main.arduino;

public class Drawer extends PApplet {

    public void setup(){
        Main.drawer = this;
    }

    public void settings(){
        size(500,500);
    }

    public void draw(){
        Main.onTick();
    }

    public void drawWorld() {
        background(0);
        if(arduino.joystick.isPressed){
            fill(0, 255, 0);
        }
        else{
            fill(255, 0, 0);
        }
        ellipse(250 + 250 * arduino.joystick.x, 250 - 250*arduino.joystick.y, 20, 20);
        System.out.println("Framerate: " + frameRate);
    }
}
