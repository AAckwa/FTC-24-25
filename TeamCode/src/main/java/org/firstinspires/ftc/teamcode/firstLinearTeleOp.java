package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="First_Linear_TeleOp")
public class firstLinearTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //Init goes here
        DcMotor motorOne;
        motorOne = hardwareMap.get(DcMotor.class, "motorOne");

        DcMotorEx motorTwo;
        motorTwo = hardwareMap.get(DcMotorEx.class, "motorTwo");


        //Hard stop
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Gentle stop
        motorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        motorOne.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();
        //after start

        while(opModeIsActive()){
            motorOne.setPower(0.5);//[-1,1]
            motorTwo.setVelocity(150);
        }
    }
}
