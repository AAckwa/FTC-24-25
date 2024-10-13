package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;
import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class testAutoFrame {
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;
    public DcMotorEx leftSlide;
    public DcMotorEx rightSlide;

    // GLOBAL MAXIMUM SPEED
    static final double maxThrottle = 1.0; // limits to a % of theo. max speed; ex: 0.9 makes 90% of theo. the functional max speed.

    /*
         Constants for encoder calculations
        ADJUST FOR HARDWARE
     */
    static final double COUNTS_PER_MOTOR_REV = 537.7; // Encoder counts per revolution
    static final double DRIVE_GEAR_REDUCTION = 1.0;  // Gear reduction ratio
    static final double WHEEL_DIAMETER_INCHES = 4.09449; // Diameter of the wheel in inches
    static final double ROBOT_DIAMETER = 21.25; // Diameter of the robot in inches ***CORNERS*** (for turning calculations)
    static final double driveRPM = 312.0; // RPM of the drive motors

    // Math from above constants
    static final double COUNTS_PER_INCH = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI); // Encoder counts per inch traveled
    static final double maxVelocity = (driveRPM * COUNTS_PER_MOTOR_REV * maxThrottle) / 60; // Max possible ticks per second

    // Adjustment factor for turning.
    // I am aware that this fix is not good. I don't have a better idea.
    double adjustmentFactor = 360.0 / 255; // quick fix for bot rotating less than expected.

    // slides stuff
    static final double slidesRPM = 312.0; // RPM of the slide motors
    static final double COUNTS_PER_SLIDES_REV = 537.7; // Encoder counts per revolution
    static final double maxSlidesHeight = (double) 2928/360*COUNTS_PER_SLIDES_REV; // max height in ticks (Currently 4373.29333...)
    static final double minSlidesHeight = 0; // min height in ticks
    static final double maxSlidesVelocity = (slidesRPM * COUNTS_PER_SLIDES_REV * maxThrottle) / 60; // Max possible ticks per second


    // Constructor
    public testAutoFrame(DcMotorEx fLeft, DcMotorEx fRight, DcMotorEx bLeft, DcMotorEx bRight, DcMotorEx lSlide, DcMotorEx rSlide) {
        frontLeft = fLeft;
        frontRight = fRight;
        backLeft = bLeft;
        backRight = bRight;
        leftSlide = lSlide;
        rightSlide = rSlide;


        // Set motors to brake when not moving
        frontLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        frontLeft.setDirection(DcMotorEx.Direction.REVERSE);
        backLeft.setDirection(DcMotorEx.Direction.REVERSE);
        rightSlide.setDirection(DcMotorEx.Direction.REVERSE);
    }

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      GENERAL PURPOSE METHODS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

    // Wait for motors to reach their target position
    public void waitForMotors() {
        while (frontLeft.isBusy() || frontRight.isBusy() || backLeft.isBusy() || backRight.isBusy() || leftSlide.isBusy() || rightSlide.isBusy()){
            // Wait for motors to get to position
        }
    }

    // Stop all motors
    public void stopMotors() {
        setMotorVelo(0, 0, 0, 0); // Set all motor velocities to 0
        setSlidesVelo(0); // Set slide velocity to 0)
    }

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      MECANUM METHODS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

    // Method to reset and restart motor encoders
    public void resetEncoders() {
        frontLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        try {
            Thread.sleep(50); // Sleep for 50 ms (adjust as necessary)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            telemetry.addData("Error", "Thread was interrupted during resetEncoders");
            telemetry.update();
        }
    }
    // Method to set target position for each motor
    public void setTargPos(int fl, int fr, int bl, int br) {
        frontLeft.setTargetPosition(frontLeft.getCurrentPosition() + fl); // Set target position for front left
        frontRight.setTargetPosition(frontRight.getCurrentPosition() + fr); // Set target position for front right
        backLeft.setTargetPosition(backLeft.getCurrentPosition() + bl); // Set target position for back left
        backRight.setTargetPosition(backRight.getCurrentPosition() + br); // Set target position for back right
    }


    // Method to set motor velocities
    public void setMotorVelo(double fl, double fr, double bl, double br) { // velocities are in tps
        frontLeft.setVelocity(fl); // Set velocity for front left motor
        frontRight.setVelocity(fr); // Set velocity for front right motor
        backLeft.setVelocity(bl); // Set velocity for back left motor
        backRight.setVelocity(br); // Set velocity for back right motor
    }

    public void runToPosition(){
        frontLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        backRight.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                           SLIDES METHODS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

    // Method to reset and restart motor encoders
    public void resetSlides() {
        leftSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        try {
            Thread.sleep(50); // Sleep for 50 ms (adjust as necessary)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            telemetry.addData("Error", "Thread was interrupted during resetSlides");
            telemetry.update();
        }
    }

    // Method to set target position for each motor
    public void setSlidesPos(int position) {
        leftSlide.setTargetPosition(position);
        rightSlide.setTargetPosition(position);
    }

    // Method to set motor velocities
    public void setSlidesVelo(double velo) { // velocities are in tps
        leftSlide.setVelocity(velo);
        rightSlide.setVelocity(velo);
    }

    public void runSlidesToPosition(){
        leftSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }


        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                          MOVEMENT METHODS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
        */

    // Method to move the robot forward
    public void forward(double distance, double speed) { // distance is in inches; speed is [1,-1]
        stopMotors();
        resetEncoders();

        double targetPos = distance * COUNTS_PER_INCH;
        double targetVelo = speed * maxVelocity;
        targetVelo = Math.round(targetVelo); // give setMotorVelo an integer value

        setTargPos((int) targetPos, (int) targetPos, (int) targetPos, (int) targetPos); // int for setTargetPos

        runToPosition(); // go to position at set velocity

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

        setTargPos((int) targetPos, (int) -targetPos, (int) -targetPos, (int) targetPos); // int for setTargetPos

        runToPosition(); // go to position at set velocity

        setMotorVelo(targetVelo, -targetVelo, -targetVelo, targetVelo); // Set motor velocities

        waitForMotors(); // Wait for motors to finish
        stopMotors(); // Stop all motors once movement is complete
    }

    // Method to turn the robot by a certain angle (degrees) at a given speed
    public void rotate(double degrees, double speed) {

        double realAngle = degrees * adjustmentFactor;
        // Calculate the arc length each wheel must travel
        double turnCircumference = ROBOT_DIAMETER * Math.PI; // Full circle circumference of the robot
        double arcLength = (turnCircumference * realAngle) / 360; // Arc length for the given degrees of turn


        // Convert arc length to encoder counts
        double targetPos = arcLength * COUNTS_PER_INCH;
        double targetVelo = speed * maxVelocity;
        targetVelo = Math.round(targetVelo); // Round velocity to an integer
        setTargPos((int) targetPos, (int) -targetPos, (int) targetPos, (int) -targetPos);

        runToPosition(); // Run to the target positions

        // For turning, left motors go forward, right motors go backward
        setMotorVelo(targetVelo, -targetVelo, targetVelo, -targetVelo);

        waitForMotors(); // Wait for the motors to reach their target
        stopMotors(); // Stop all motors once the turn is complete
    }

    public void slides(double height, double speed) { //height is [0,1] 0 is down, 1 is up

        double slideTargetPos = height * maxSlidesHeight; // convert target to ticks
        double targetVelo = speed * maxSlidesVelocity;

        if (slideTargetPos > maxSlidesHeight) {
            slideTargetPos = maxSlidesHeight;
        } else if (slideTargetPos < minSlidesHeight) {
            slideTargetPos = minSlidesHeight;
        }
        setSlidesPos((int) slideTargetPos);

        runSlidesToPosition();

        setSlidesVelo(targetVelo);

        waitForMotors(); // Wait for motors to finish
    }
}