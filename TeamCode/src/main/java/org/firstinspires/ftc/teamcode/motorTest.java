package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Disabled
@TeleOp(name="Motor Test")
public class motorTest extends OpMode {

    private DcMotor armLeft;
    private DcMotor armRight;

    static final double armThrottle = 0.25;
    static final double ARM_TICKS_PER_REV = 1425.1;
    static final  double armRPM = 117.0;

    @Override
    public void init() {
        armLeft = hardwareMap.get(DcMotor.class, "armLeft");
        armRight = hardwareMap.get(DcMotor.class, "armRight");
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setDirection(DcMotor.Direction.REVERSE);
    }

    @Override
    public void loop() {


        double armInput = gamepad2.right_stick_y;

        armLeft.setPower(armInput);
        armRight.setPower(armInput);




        telemetry.addData("Left Encoder: ", armLeft.getCurrentPosition());
        telemetry.addData("Right Encoder: ", armRight.getCurrentPosition());
        telemetry.update();
    }
}
