package hu.johetajava;

import com.fazecast.jSerialComm.SerialPort;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Scanner;

public class Communication {

    private final Scanner in;

    Communication(int baudRate, String portName) {
        arduino.Arduino arduino = new arduino.Arduino(portName, baudRate);
        arduino.openConnection();

        SerialPort comPort = arduino.getSerialPort();
        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        StringBuilder out = new StringBuilder();
        in = new Scanner(comPort.getInputStream());

    }

    public String readLine() {
        try {
            if (in.hasNext()) {
                return in.nextLine();
            } else {
                System.err.println("Nem kapunk adatot!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void closeConnection() {
        in.close();
    }

    public void eatNextLine() {
        eatJSON(readLine());
    }

    public void eatJSON(String JSONString) {
        try {
            JSONObject jsonObject = new JSONObject(JSONString);


            Main.arduino.joystick.isPressed = jsonObject.getJSONObject("joystick").getBoolean("isPressed");
            Main.arduino.joystick.x = (float) jsonObject.getJSONObject("joystick").getDouble("x");
            Main.arduino.joystick.y = (float) jsonObject.getJSONObject("joystick").getDouble("y");

            System.out.println(Main.arduino.joystick);
        } catch (JSONException e) {
            System.err.println("Nem valid JSON-t kaptunk. :(");
        }
    }
}
