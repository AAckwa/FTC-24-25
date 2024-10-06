// ChatGPT wrote this
/*
========================================================
========================================================
EVERYTHING IS COMMENTED OUT. THIS DOES NOT WORK
I AM USING IT AS A REFERENCE FOR THE AUTOFRAME
=========================================================
=========================================================

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

// Class for controlling a mecanum drive system
public class MecanumDrive {
    private DcMotor frontLeft;   // Motor for the front-left wheel
    private DcMotor frontRight;  // Motor for the front-right wheel
    private DcMotor backLeft;    // Motor for the back-left wheel
    private DcMotor backRight;   // Motor for the back-right wheel

    // Constants for encoder calculations
    static final double COUNTS_PER_MOTOR_REV = 537.7; // Encoder counts per revolution
    static final double DRIVE_GEAR_REDUCTION = 1.0;  // Gear reduction ratio
    static final double WHEEL_DIAMETER_INCHES = 4.09449; // Diameter of the wheel in inches
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * Math.PI); // Encoder counts per inch traveled
    static final double ROBOT_DIAMETER = 18.0; // Diameter of the robot in inches (for turning calculations)

    // Constructor to initialize motors
    public MecanumDrive(DcMotor fl, DcMotor fr, DcMotor bl, DcMotor br) {
        frontLeft = fl;
        frontRight = fr;
        backLeft = bl;
        backRight = br;

        // Set motor directions
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);

        // Set motors to brake when not moving
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
}

    // Method to move the robot forward a specified distance at a specified speed
    public void forward(double distance, double speed) {
        int targetPosition = (int) (distance * COUNTS_PER_INCH); // Calculate target position in encoder counts

        setTargetPosition(targetPosition, targetPosition, targetPosition, targetPosition); // Set target position for all motors
        setRunToPosition(); // Set motors to run to the target position
        setMotorPowers(speed, speed, speed, speed); // Set motor powers to the specified speed
        waitForMotors(); // Wait until all motors reach their target positions
        stopMotors(); // Stop the motors after reaching the target
        setRunUsingEncoders(); // Return motors to run using encoders
    }

    // Method to turn the robot a specified angle at a specified speed
    public void turn(double degrees, double speed) {
        double turnDistance = (Math.PI * ROBOT_DIAMETER) * (degrees / 360.0); // Calculate the distance each wheel needs to turn
        int targetPosition = (int) (turnDistance * COUNTS_PER_INCH); // Calculate target position in encoder counts

        // Set target positions for each motor to turn the robot
        setTargetPosition(-targetPosition, targetPosition, -targetPosition, targetPosition);
        setRunToPosition(); // Set motors to run to the target position
        setMotorPowers(speed, speed, speed, speed); // Set motor powers to the specified speed
        waitForMotors(); // Wait until all motors reach their target positions
        stopMotors(); // Stop the motors after turning
        setRunUsingEncoders(); // Return motors to run using encoders
    }

    // Method to strafe the robot sideways a specified distance at a specified speed
    public void strafe(double distance, double speed) {
        int targetPosition = (int) (distance * COUNTS_PER_INCH); // Calculate target position in encoder counts

        // Set target positions for strafing
        setTargetPosition(targetPosition, -targetPosition, -targetPosition, targetPosition);
        setRunToPosition(); // Set motors to run to the target position
        setMotorPowers(speed, speed, speed, speed); // Set motor powers to the specified speed
        waitForMotors(); // Wait until all motors reach their target positions
        stopMotors(); // Stop the motors after strafing
        setRunUsingEncoders(); // Return motors to run using encoders
    }

    // Method to reset motor encoders to zero
    public void resetEncoders() {
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setRunUsingEncoders(); // Set motors to run using encoders
    }

    // Set all motors to run using encoder values
    public void setRunUsingEncoders() {
        frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // Set motors to run to their target positions
    public void setRunToPosition() {
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    // Method to set target position for each motor
    public void setTargetPosition(int fl, int fr, int bl, int br) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + fl); // Set target position for front left
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + fr); // Set target position for front right
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + bl); // Set target position for back left
        backRight.setTargetPosition(backRight.getCurrentPosition() + br); // Set target position for back right
    }

    // Set the power for each motor
    public void setMotorPowers(double fl, double fr, double bl, double br) {
        frontLeft.setPower(fl); // Set power for front left motor
        frontRight.setPower(fr); // Set power for front right motor
        backLeft.setPower(bl); // Set power for back left motor
        backRight.setPower(br); // Set power for back right motor
    }

    // Wait until all motors reach their target positions
    public void waitForMotors() {
        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy()) {
            // Loop until all motors are done moving
        }
    }

    public void setMotorsVelocity(double fl, double fr, double bl, double br) {
        frontLeft.setVelocity(fl); // Set velocity for front left motor
        frontRight.setVelocity(fr); // Set velocity for front right motor
        backLeft.setVelocity(bl); // Set velocity for back left motor
        backRight.setVelocity(br);
    }
}
*/