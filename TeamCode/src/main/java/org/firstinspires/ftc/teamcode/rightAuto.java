package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Right Auto")
public class rightAuto extends LinearOpMode {

    private  testAutoFrame kevinFrame;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        DcMotorEx leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotorEx rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        DcMotorEx armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
        DcMotorEx armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        Servo grip = hardwareMap.get(Servo.class, "grip");
        Servo gripRotation = hardwareMap.get(Servo.class, "gripRotation");

        kevinFrame = new testAutoFrame(frontLeft,frontRight,backLeft,backRight,leftSlide,rightSlide,armLeft,armRight,grip,gripRotation);

        waitForStart();

        kevinFrame.resetEncoders();
        kevinFrame.resetSlides();
        kevinFrame.resetArm();

        kevinFrame.right(45,0.25);



    }
}
