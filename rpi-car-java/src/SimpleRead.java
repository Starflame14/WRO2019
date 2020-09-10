import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class SimpleRead {
    static int[] sensorData;

    private final Scanner scanner;
    static final int midPoint = 320;

    public SimpleRead() {
        SerialPort serPort[] = SerialPort.getCommPorts();

        int i = 0;

        for (SerialPort port : serPort) {
            System.out.println(i++ + " " + port.getSystemPortName());
        }

        Scanner s = new Scanner(System.in);

        int selected = s.nextInt();

        SerialPort port = serPort[selected];

        System.out.println(port.getBaudRate());
        port.setBaudRate(115200);
        System.out.println(port.getBaudRate());
        port.setNumDataBits(8);
        port.setNumStopBits(1);
        port.setParity(SerialPort.NO_PARITY);

        try {
            port.openPort();
            System.out.println("Connection is opend");
        } catch (Exception ex) {
            System.out.println("Check connection there is a problem");
        }

        port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

        scanner = new Scanner(port.getInputStream());

        new Thread(() -> {
            sensorData = new int[8];
            int j = 0;
            while (scanner.hasNextLine()) {
                try {
                    String line = scanner.nextLine();
                    if (j < sensorData.length) sensorData[j] = (Integer.parseInt(line));
                    j++;
                    System.out.println(line);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    static boolean isBlack(int number) {
        return number > midPoint;
    }
}


