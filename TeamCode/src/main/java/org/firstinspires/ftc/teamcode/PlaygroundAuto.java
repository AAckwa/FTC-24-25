package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Playground_Auto")
public class PlaygroundAuto extends LinearOpMode {

    private autoFrame kevinFrame;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotorEx frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        DcMotorEx frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        DcMotorEx backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        DcMotorEx backRight = hardwareMap.get(DcMotorEx.class, "backRight");

        kevinFrame = new autoFrame(frontLeft,frontRight,backLeft,backRight);

        waitForStart();

        kevinFrame.resetEncoders();

        kevinFrame.forward(10, 0.5);
        kevinFrame.right(10,0.5);
        kevinFrame.right(-10,0.5);
        kevinFrame.rotate(90,0.5);

    }
}
