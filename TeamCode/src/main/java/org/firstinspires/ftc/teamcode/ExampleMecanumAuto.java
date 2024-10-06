// ChatGPT wrote this
/*
========================================================
========================================================
EVERYTHING IS COMMENTED OUT. THIS DOES NOT WORK
========================================================
========================================================

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

// Autonomous OpMode using the MecanumDrive class
@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Example Mecanum Auto", group = "Linear Opmode")
public class ExampleMecanumAuto extends LinearOpMode {

    private MecanumDrive mecanumDrive; // Instance of MecanumDrive to control the robot

    @Override
    public void runOpMode() {
        // Initialize motors from the hardware map
        DcMotor frontLeft = hardwareMap.get(DcMotor.class, "frontLeft"); // Get front-left motor
        DcMotor frontRight = hardwareMap.get(DcMotor.class, "frontRight"); // Get front-right motor
        DcMotor backLeft = hardwareMap.get(DcMotor.class, "backLeft"); // Get back-left motor
        DcMotor backRight =
                frontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);hardwareMap.get(DcMotor.class, "backRight"); // Get back-right motor


        // Create a NEW instance of MecanumDrive with the initialized motors
        mecanumDrive = new MecanumDrive(frontLeft, frontRight, backLeft, backRight);

        // Reset encoders to ensure accurate measurements before starting
        mecanumDrive.resetEncoders();

        // Wait for the game to start (the driver presses PLAY)
        waitForStart();

        // Start your autonomous routine
        if (opModeIsActive()) { // Check if the op mode is active

            // Execute movements with specified speeds
            mecanumDrive.forward(24, 0.75);  // Move forward 24 inches at specified speed
            mecanumDrive.turn(90, 1);         // Turn 90 degrees to the right at specified speed
            mecanumDrive.strafe(12, 0.6);     // Strafe left 12 inches at specified speed
            mecanumDrive.forward(12, 0.5);    // Move forward 12 inches at specified speed
        }
    }
}

*/