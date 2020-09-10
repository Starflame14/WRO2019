import com.ergotech.brickpi.motion.Motor;

class Lifter {
    private Motor movingMotor, gearShiftMotor;
    private int gearShiftMotorAngle = 0;
    private int movingMotorAngle = 0;

    public Lifter(Motor movingMotor, Motor gearShiftMotor){
        this.movingMotor = movingMotor;
        this.gearShiftMotor = gearShiftMotor;
        this.gearShiftMotor.resetEncoder();
    }


    void resetGearShiftMotor() {
        if (gearShiftMotorAngle != 0) {
            setPosition(gearShiftMotor, 0, 50);
        }
    }

    void move(Lift lift, LiftState lifterState) {
        MyRobot.sleep(100);

        double rate = ((lifterState == LiftState.UP || lifterState == LiftState.DOWN) ? 2.5 : 1.2);

        System.out.println("Rate: " + rate);
        double targetPos = ((lifterState == LiftState.DOWN || lifterState == LiftState.MIDDLE_DOWN) ? -rate * 360 : rate * 360);

        //int speed = lifterState == LiftState.UP || lifterState == LiftState.MIDDLE_UP ? 100 : 50;

        System.out.println("target pos: " + targetPos);


        switch (lift) {
            case REAR_LEFT:
                setPosition(gearShiftMotor, 90, 50);
                gearShiftMotorAngle = 90;
                break;

            case FRONT_RIGHT:
                setPosition(gearShiftMotor, 180, 50);
                gearShiftMotorAngle = 180;
                break;

            case FRONT_LEFT:
                setPosition(gearShiftMotor, 270, 50);
                gearShiftMotorAngle = 270;
                break;

            case REAR_RIGHT:
                setPosition(gearShiftMotor, 0, 50);
                gearShiftMotorAngle = 0;
                break;

        }
        MyRobot.sleep(10);

        //movingMotorAngle += targetPos;

        //await this.movingMotor.setPosition(this.movingMotorAngle, speed);
        this.movingMotor.rotate(targetPos, 80);

        MyRobot.sleep(200);

    }

    static void setPosition(Motor motor, int position, int speed){
        motor.rotate((position - motor.getCurrentEncoderValue()), speed);
    }
}

enum Lift {
    FRONT_LEFT,
    FRONT_RIGHT,
    REAR_LEFT,
    REAR_RIGHT
}

enum LiftState {
    UP,
    DOWN,
    MIDDLE_UP,
    MIDDLE_DOWN
}