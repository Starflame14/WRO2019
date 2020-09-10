import com.ergotech.brickpi.RemoteBrickPi;
import com.ergotech.brickpi.motion.Motor;
import com.ergotech.brickpi.motion.MotorPort;
import com.ergotech.brickpi.sensors.Sensor;
import com.ergotech.brickpi.sensors.SensorType;

class MyRobot {
    static SimpleRead sensorArray;
    static Chassis chassis;
    static Commander commander;
    static Lifter lifter;

    static void onInit() {
        //TODO set the brickpi's address
        RemoteBrickPi brickPi = new RemoteBrickPi("");
        //Get the instance of the motors
        Motor motorA = new Motor();
        Motor motorB = new Motor();
        Motor motorC = new Motor();
        Motor motorD = new Motor();

        //creating motors
        brickPi.setMotor(motorA, MotorPort.MA);
        brickPi.setMotor(motorB, MotorPort.MB);
        brickPi.setMotor(motorC, MotorPort.MC);
        brickPi.setMotor(motorD, MotorPort.MD);

        //enabling motors
        motorA.setEnabled(true);
        motorB.setEnabled(true);
        motorC.setEnabled(true);
        motorD.setEnabled(true);

        //setting brickPi
        motorA.setBrickPi(brickPi);
        motorB.setBrickPi(brickPi);
        motorC.setBrickPi(brickPi);
        motorD.setBrickPi(brickPi);

        //setting speed to 0
        motorA.setCommandedOutput(0);
        motorB.setCommandedOutput(0);
        motorC.setCommandedOutput(0);
        motorD.setCommandedOutput(0);

        //setting direction to clockwise
        motorA.setDirection(Motor.Direction.CLOCKWISE);
        motorB.setDirection(Motor.Direction.CLOCKWISE);
        motorC.setDirection(Motor.Direction.CLOCKWISE);
        motorD.setDirection(Motor.Direction.CLOCKWISE);

        //Reset the motors encoder to 0 //TODO Wait for all to finish in parallel?
        System.out.println("Resetting motor encoder...");
        motorA.resetEncoder();
        motorB.resetEncoder();
        motorC.resetEncoder();
        motorD.resetEncoder();


        chassis = new Chassis(motorC, motorB);
        sensorArray = new SimpleRead();
        commander = new Commander();
        lifter = new Lifter(motorA, motorD);

        System.out.println("Initializing sensor array...");
        sleep(3000);

        commander.executeCommands(Command.GO_FORWARD,
                Command.PICK_UP_FRONT_LEFT,
                Command.PICK_UP_FRONT_RIGHT);


        lifter.resetGearShiftMotor();

        chassis.leftMotor.setCommandedOutput(0);
        chassis.rightMotor.setCommandedOutput(0);
        System.out.println("Megvártuk az előző promise-t és most már fut tovább a kód");

    }

    static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}