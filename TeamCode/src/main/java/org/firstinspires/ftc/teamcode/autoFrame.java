package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorEx;

public class autoFrame {
    private DcMotorEx frontLeft;
    private DcMotorEx frontRight;
    private DcMotorEx backLeft;
    private DcMotorEx backRight;

    /*
        Constants for encoder calculations
        ADJUST FOR HARDWARE
     */
    static final double COUNTS_PER_MOTOR_REV = 537.7; // Encoder counts per revolution
    static final double DRIVE_GEAR_REDUCTION = 1.0;  // Gear reduction ratio
    static final double WHEEL_DIAMETER_INCHES = 4.09449; // Diameter of the wheel in inches
    static final double ROBOT_DIAMETER = 18.0; // Diameter of the robot in inches (for turning calculations)
    static final double driveRPM = 312.0; // RPM of the drive motors
    static final double maxThrottle = 1.0; // limits to a % of theo. max speed; ex: 0.9 makes 90% of theo. the functional max speed.

    // Math from above constants
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI); // Encoder counts per inch traveled
    static final double maxVelocity = (driveRPM * COUNTS_PER_MOTOR_REV * maxThrottle) / 60; // Max possible ticks per second

    // Constructor
    public autoFrame(DcMotorEx fLeft, DcMotorEx fRight, DcMotorEx bLeft, DcMotorEx bRight) {
        frontLeft = fLeft;
        frontRight = fRight;
        backLeft = bLeft;
        backRight = bRight;

        // Set motors to brake when not moving
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }

    // Method to reset and restart motor encoders
    public void resetEncoders() {
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
    }

    // Method to set target position for each motor
    public void setTargPos(int fl, int fr, int bl, int br) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + fl); // Set target position for front left
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + fr); // Set target position for front right
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + bl); // Set target position for back left
        backRight.setTargetPosition(backRight.getCurrentPosition() + br); // Set target position for back right
    }

    // Wait for motors to reach their target position
    public void waitForMotors() {
        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            // Wait for motors to get to position
        }
    }

    // Method to set motor velocities
    public void setMotorVelo(double fl, double fr, double bl, double br) { // velocities are in tps
        frontLeft.setVelocity(fl); // Set velocity for front left motor
        frontRight.setVelocity(fr); // Set velocity for front right motor
        backLeft.setVelocity(bl); // Set velocity for back left motor
        backRight.setVelocity(br); // Set velocity for back right motor
    }

    // Stop all motors
    public void stopMotors() {
        setMotorVelo(0, 0, 0, 0); // Set all motor velocities to 0
    }

    public void runToPosition(){
        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    /*
    ============================================
        Methods for movement are here below
    ============================================
     */

    // Method to move the robot forward
    public void forward(double distance, double speed) { // distance is in inches; speed is (0-1]
        stopMotors();
        resetEncoders();

        double targetPos = distance * COUNTS_PER_INCH;
        double targetVelo = speed * maxVelocity;
        targetVelo = Math.round(targetVelo); // give setMotorVelo an integer value

        runToPosition(); // go to position at set velocity

        setTargPos((int) targetPos, (int) targetPos, (int) targetPos, (int) targetPos); // int for setTargetPos
        setMotorVelo(targetVelo, targetVelo, targetVelo, targetVelo); // Set motor velocities

        waitForMotors(); // Wait for motors to finish
        stopMotors(); // Stop all motors once movement is complete
    }

    public void right(double distance, double speed) {// distance is in inches; speed is (0-1]
        stopMotors();
        resetEncoders();

        double targetPos = distance * COUNTS_PER_INCH;
        double targetVelo = speed * maxVelocity;
        targetVelo = Math.round(targetVelo); // give setMotorVelo an integer value

        runToPosition(); // go to position at set velocity

        setTargPos((int) targetPos, (int) -targetPos, (int) -targetPos, (int) targetPos); // int for setTargetPos
        setMotorVelo(targetVelo, -targetVelo, -targetVelo, targetVelo); // Set motor velocities

        waitForMotors(); // Wait for motors to finish
        stopMotors(); // Stop all motors once movement is complete
    }

    // Method to turn the robot by a certain angle (degrees) at a given speed
    public void rotate(double degrees, double speed) {
        // Calculate the arc length each wheel must travel
        double turnCircumference = ROBOT_DIAMETER * Math.PI; // Full circle circumference of the robot
        double arcLength = (turnCircumference * degrees) / 360; // Arc length for the given degrees of turn

        // Convert arc length to encoder counts
        double targetPos = arcLength * COUNTS_PER_INCH;
        double targetVelo = speed * maxVelocity;
        targetVelo = Math.round(targetVelo); // Round velocity to an integer

        runToPosition(); // Run to the target positions

        // For turning, left motors go forward, right motors go backward
        setTargPos((int) targetPos, (int) -targetPos, (int) targetPos, (int) -targetPos);
        setMotorVelo(targetVelo, -targetVelo, targetVelo, -targetVelo);

        waitForMotors(); // Wait for the motors to reach their target
        stopMotors(); // Stop all motors once the turn is complete
    }


}
