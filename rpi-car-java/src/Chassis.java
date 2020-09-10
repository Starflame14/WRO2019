import com.ergotech.brickpi.motion.Motor;

import java.util.concurrent.Callable;

class Chassis {
    public Motor leftMotor, rightMotor;

    public Chassis(Motor leftMotor, Motor rightMotor) {
        this.leftMotor = leftMotor;
        this.rightMotor = rightMotor;
    }

    void followLine(Callable<Boolean> breakFunction, int[] sensorData, int baseSpeed, int k, int minimumRunning) {
        long startTime = System.currentTimeMillis();

        while (true) {
            try {
                if (breakFunction.call() && System.currentTimeMillis() - startTime > minimumRunning) break;
            } catch (Exception ignored) {
            }

            int sum = 0;
            int valueSum = 0;

            for (int i = 0; i < sensorData.length; i++) {
                sum += sensorData[i] * (i + 1);
                valueSum += sensorData[i];
            }

            double index = sum / valueSum - 1;
            double error = ((double) (sensorData.length - 1) / 2) - index;

            int leftSpeed = (int) (baseSpeed - error * k);
            int rightSpeed = (int) (baseSpeed + error * k);

            leftMotor.setCommandedOutput(leftSpeed);
            rightMotor.setCommandedOutput(rightSpeed);
        }

        leftMotor.setCommandedOutput(0);
        rightMotor.setCommandedOutput(0);
        System.out.println("BREAK");
    }

    void goUntilBlack(int minimumRunning, int speed) {
        speed = 40;

        followLine(() -> SimpleRead.isBlack(SimpleRead.sensorData[0]) &&
                        SimpleRead.isBlack(SimpleRead.sensorData[SimpleRead.sensorData.length - 1]),
                SimpleRead.sensorData, speed, speed / 6, minimumRunning);
    }

    void goForward(double rotations, int speed) {

        if (rotations >= 0) speed = Math.abs(speed);
        else speed = -Math.abs(speed);

        int startPos = leftMotor.getCurrentEncoderValue();

        leftMotor.setCommandedOutput(speed);
        rightMotor.setCommandedOutput(speed);

        while (Math.abs(leftMotor.getCurrentEncoderValue() - startPos) < Math.abs(rotations)) {
            MyRobot.sleep(10);
        }

        leftMotor.setCommandedOutput(0);
        rightMotor.setCommandedOutput(0);
    }

    void turn(double turns, int speed) {
        if (turns >= 0) speed = Math.abs(speed);
        else speed = -Math.abs(speed);

        int startPos = leftMotor.getCurrentEncoderValue();

        if (turns <= 0) {
            leftMotor.setCommandedOutput(-speed);
            rightMotor.setCommandedOutput(speed);
        } else {
            leftMotor.setCommandedOutput(speed);
            rightMotor.setCommandedOutput(-speed);
        }

        while (Math.abs(leftMotor.getCurrentEncoderValue() - startPos) < Math.abs(turns)) {
            MyRobot.sleep(10);
        }

        leftMotor.setCommandedOutput(0);
        rightMotor.setCommandedOutput(0);
    }
}

