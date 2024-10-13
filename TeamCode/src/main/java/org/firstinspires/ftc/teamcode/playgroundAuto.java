package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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

        kevinFrame = new testAutoFrame(frontLeft,frontRight,backLeft,backRight,leftSlide,rightSlide);

        waitForStart();

        kevinFrame.resetEncoders();
        kevinFrame.resetSlides();

        kevinFrame.slides(0.5, 0.25);

    }
}