package hu.johetajava;

public class Joystick {
    float x;
    float y;
    boolean isPressed;

    public Joystick(float x, float y, boolean isPressed) {
        this.x = x;
        this.y = y;
        this.isPressed = isPressed;
    }

    @Override
    public String toString() {
        return "Joystick{" +
                "x=" + x +
                ", y=" + y +
                ", isPressed=" + isPressed +
                '}';
    }
}
