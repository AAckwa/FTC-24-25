package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@Autonomous(name="Playground_Auto")
public class playgroundAuto extends LinearOpMode {

    private testAutoFrame kevinFrame;

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

        kevinFrame.arm(1,0.25);
        kevinFrame.gripRotate(true);
        sleep(1000);
        kevinFrame.grip(false);
        sleep(1000);
        kevinFrame.gripRotate(false);
        sleep(1000);
        kevinFrame.grip(true);
        sleep(1000);
        kevinFrame.arm(0,0.25);


    }
}