package hu.johetajava;


public class Arduino {
    Joystick joystick;

    Communication communication;
    Arduino(int baudRate, String portName){

        communication = new Communication(baudRate, portName);

        joystick = new Joystick(0,0,false);
    }


}
