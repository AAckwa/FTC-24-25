package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="Production_TeleOp")
public class productionTeleOp extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    // Acceleration control multiplier
    // Higher number = LESS smoothing
    double smoothFactor = 0.8;
    final double smoothIncrement = 0.05; // How much smoothFactor changes with each button press

    // this is for using x/a to adjust the speed of the dpad
    double dpadSpeed = 0.2;
    final double minDpadSpeed = 0.05;

    // these are used to keep track button releases.
    boolean aPressed = false;
    boolean bPressed = false;
    boolean xPressed = false;
    boolean yPressed = false;

    @Override
    public void init() {
        // runs once on init

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        //Hard stop
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

    }

    @Override
    public void init_loop() {
        // loops on init until start
        // use for vision
    }

    @Override
    public void start() {
        //happens once on start

    }

    @Override
    public void loop() {
        // runs on start (repeats)

        // does not use exponential joystick scaling
        double y = gamepad1.left_stick_y * -1; // Move forward/backward
        double x = gamepad1.left_stick_x * 1; // Strafe left/right
        double rotation = gamepad1.right_stick_x * 1; // Rotate


        if (gamepad1.y && !yPressed) { //when x is pressed, increase dpadSpeed by 0.025
            dpadSpeed += 0.025;
            yPressed = true;
        }
        if (gamepad1.x && dpadSpeed > minDpadSpeed && !xPressed) { // when a is pressed, decrease dpadSpeed by 0.025
            dpadSpeed -= 0.025;
            xPressed = true;
        }

        telemetry.addData("Dpad Speed: ", dpadSpeed);



        if (gamepad1.dpad_up) {
            y = dpadSpeed; // Move forward
        } else if (gamepad1.dpad_down) {
            y = -dpadSpeed; // Move backward
        }
        if (gamepad1.dpad_left) {
            x = -dpadSpeed; // Strafe left
        } else if (gamepad1.dpad_right) {
            x = dpadSpeed; // Strafe right
        }

        double frontLeftPower = y + x + rotation;
        double backLeftPower = y - x + rotation;
        double frontRightPower = y - x - rotation;
        double backRightPower = y + x - rotation;

        // This scaling will keep values proportional.
        double maxPower = Math.max(1.0, Math.abs(frontLeftPower));
        maxPower = Math.max(maxPower, Math.abs(backLeftPower));
        maxPower = Math.max(maxPower, Math.abs(frontRightPower));
        maxPower = Math.max(maxPower, Math.abs(backRightPower));

        if (maxPower > 1.0) {
            frontLeftPower /= maxPower;
            backLeftPower /= maxPower;
            frontRightPower /= maxPower;
            backRightPower /= maxPower;
        }


        final double minSmooth = 0.05; // Minimum smoothFactor
        final double maxSmooth = 0.95; // Maximum smoothFactor

        if (gamepad1.b && smoothFactor < maxSmooth && !bPressed) {
            smoothFactor += smoothIncrement; // Increase smoothFactor (faster acceleration)
            bPressed = true;
        }
        if (gamepad1.a && smoothFactor > minSmooth && !aPressed) {
            smoothFactor -= smoothIncrement; // Decrease smoothFactor (slower acceleration)
            aPressed = true;
        }

        telemetry.addData("Smooth Factor: ", smoothFactor);

        // Acceleration control (with deceleration)
        frontLeftPower = (1 - smoothFactor) * frontLeft.getPower() + smoothFactor * frontLeftPower;
        backLeftPower = (1 - smoothFactor) * backLeft.getPower() + smoothFactor * backLeftPower;
        frontRightPower = (1 - smoothFactor) * frontRight.getPower() + smoothFactor * frontRightPower;
        backRightPower = (1 - smoothFactor) * backRight.getPower() + smoothFactor * backRightPower;

        // sends final value to the drive motors
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);

        // these keep track of button releases
        if (!gamepad1.a) {
            aPressed = false;
        }
        if (!gamepad1.b) {
            bPressed = false;
        }
        if (!gamepad1.x) {
            xPressed = false;
        }
        if (!gamepad1.y) {
            yPressed = false;
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        //runs once on stop
    }
}