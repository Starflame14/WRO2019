class Commander {
    void executeCommand(Command command){
        switch (command) {
            case TURN_LEFT:
                MyRobot.chassis.turn(-0.5, 20);
                break;

            case TURN_RIGHT:
                MyRobot.chassis.turn(1, 20);
                break;

            case GO_FORWARD:
                MyRobot.chassis.goUntilBlack(400, 40);
                MyRobot.chassis.goForward(0.5, 20);
                break;

            case PICK_UP_FRONT_LEFT:
                MyRobot.lifter.move(Lift.FRONT_LEFT, LiftState.DOWN);
                MyRobot.chassis.goForward(0.5, 20);
                MyRobot.lifter.move(Lift.FRONT_LEFT, LiftState.UP);
                MyRobot.chassis.goForward(-0.5, 20);
                MyRobot.sleep(10);
                break;

            case PICK_UP_FRONT_RIGHT:
                MyRobot.lifter.move(Lift.FRONT_RIGHT, LiftState.DOWN);
                MyRobot.chassis.goForward(0.5, 20);
                MyRobot.lifter.move(Lift.FRONT_RIGHT, LiftState.UP);
                MyRobot.chassis.goForward(-0.5, 20);
                MyRobot.sleep(10);
                break;

            case PICK_UP_REAR_LEFT:
                MyRobot.lifter.move(Lift.REAR_LEFT, LiftState.DOWN);
                MyRobot.chassis.goForward(0.5, 20);
                MyRobot.lifter.move(Lift.REAR_LEFT, LiftState.UP);
                MyRobot.chassis.goForward(-0.5, 20);
                MyRobot.sleep(10);
                break;

            case PICK_UP_REAR_RIGHT:
                MyRobot.lifter.move(Lift.REAR_RIGHT, LiftState.DOWN);
                MyRobot.chassis.goForward(0.5, 20);
                MyRobot.lifter.move(Lift.REAR_RIGHT, LiftState.UP);
                MyRobot.chassis.goForward(-0.5, 20);
                MyRobot.sleep(10);
                break;

            case PUT_DOWN_FRONT_LEFT:
                break;

            case PUT_DOWN_FRONT_RIGHT:
                break;

            case PUT_DOWN_REAR_LEFT:
                break;

            case PUT_DOWN_REAR_RIGHT:
                break;
        }
    }

    void executeCommandList(Command[] commands) {
        for (int i = 0; i < commands.length; i++) {
            executeCommand(commands[i]);
        }

    }

    void executeCommands(Command... commands){
        executeCommandList(commands);
    }
}

enum Command {
    TURN_LEFT,
    TURN_RIGHT,
    GO_FORWARD,
    PICK_UP_FRONT_LEFT,
    PICK_UP_FRONT_RIGHT,
    PICK_UP_REAR_LEFT,
    PICK_UP_REAR_RIGHT,
    PUT_DOWN_FRONT_LEFT,
    PUT_DOWN_FRONT_RIGHT,
    PUT_DOWN_REAR_LEFT,
    PUT_DOWN_REAR_RIGHT
}