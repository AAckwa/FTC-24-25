package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Production_Auto")
public class productionAuto extends LinearOpMode {

    private autoFrame kevinFrame;

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

        kevinFrame = new autoFrame(frontLeft,frontRight,backLeft,backRight,leftSlide,rightSlide,armLeft,armRight,grip,gripRotation);

        waitForStart();

        kevinFrame.resetEncoders();
        kevinFrame.resetSlides();
        kevinFrame.resetArm();

        kevinFrame.grip(true);
        kevinFrame.forward(10,0.25);
        kevinFrame.right(-33,0.25);
        kevinFrame.rotate(45,0.25);
        kevinFrame.arm(0.5,0.5);
        sleep(1000);
        kevinFrame.gripRotate(true); // Arrive at baskets
        kevinFrame.slides(0.75,0.75);
        sleep(1500);
        kevinFrame.arm(0.17,0.5);
        sleep(1000);
        kevinFrame.grip(false); // Drop preload
        sleep(400);
        kevinFrame.arm(0.5,0.5);
        sleep(500);
        kevinFrame.slides(0,0.75);
        sleep(2000);
        kevinFrame.forward(11,0.25);
        kevinFrame.rotate(135,0.25);
//        kevinFrame.arm(0,0.5);
//        //kevinFrame.forward(-9,0.25);
//        kevinFrame.grip(true);
//        sleep(400);
//        kevinFrame.forward(-26,0.25);
//        kevinFrame.rotate(-90,0.25);
//        kevinFrame.forward(24,0.25);
//        kevinFrame.arm(0.70,0.25);
//        sleep(999999);



//        kevinFrame.arm(0.5,0.5);
//        sleep(1000);
//        kevinFrame.gripRotate(true); // Arrive at baskets
//        kevinFrame.slides(0.75,0.5);
//        sleep(1500);
//        kevinFrame.arm(0.17,0.5);
//        sleep(1000);
//        kevinFrame.grip(false); // Drop floor neutral 3
//        sleep(400);
//        kevinFrame.arm(0.5,0.5);
//        sleep(500);
//        kevinFrame.slides(0,0.5);
//        sleep(500);
//        kevinFrame.arm(0,0.5);
//        sleep(750);




//        kevinFrame.arm(0.167,0.25);
//        kevinFrame.forward(11,0.25);
//        kevinFrame.rotate(-45,0.25);
//        kevinFrame.forward(3,0.25); // arrival at floor neutral 1
//        kevinFrame.arm(1,0.5);
//        kevinFrame.grip(true); // Obtain floor neutral 1
//        sleep(200);
//        kevinFrame.arm(0.167,0.5);
//        kevinFrame.forward(-3,0.25);
//        kevinFrame.rotate(45,0.25);
//        kevinFrame.arm(0.5,0.5);
//        kevinFrame.forward(-11,0.25);// arrival at baskets
//        kevinFrame.slides(0.75,0.5);
//        kevinFrame.arm(0.167,0.5);
//        kevinFrame.grip(false); // drop floor neutral 1
//        kevinFrame.arm(0.5,0.5);
//        kevinFrame.slides(0,0.5);
//        kevinFrame.arm(0.167,0.5);
//        kevinFrame.rotate(-45,0.25);
//        kevinFrame.gripRotate(false);
//        sleep(500);
//        kevinFrame.arm(0,0.5);
//        kevinFrame.forward(-4,0.25);
//        kevinFrame.right(85,0.5);








    }
}