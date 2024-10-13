package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;


@TeleOp(name="Test_TeleOp")
public class testTeleOp extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotorEx leftSlide;
    private DcMotorEx rightSlide;

    // Acceleration control multiplier
    // Higher number = LESS smoothing
    double smoothFactor = 0.8;
    final double smoothIncrement = 0.05; // How much smoothFactor changes with each button press

    // this is for using x/a to adjust the speed of the dpad
    double dpadSpeed = 0.2;
    final double minDpadSpeed = 0.05;

    //used for exponential joystick scaling
    /*
    double sqrtInput(double input) {
        return Math.signum(input) * Math.sqrt(Math.abs(input)); //square root input, preserve sign.
    }
     */

    static final double COUNTS_PER_SLIDES_REV = 537.7; // Encoder counts per revolution
    static final double maxSlidesHeight = (double) 2928/360*COUNTS_PER_SLIDES_REV; // max height in ticks (Currently 4373.29333...)
    static final double minSlidesHeight = 0; // min height in ticks

    int slideOffset = 0; // this is a part of a janky reset in case of the gt2 belt slipping

    // these are used to keep track button releases.
    boolean a1Pressed = false;
    boolean b1Pressed = false;
    boolean x1Pressed = false;
    boolean y1Pressed = false;
    boolean y2Pressed = false;

    @Override
    public void init() {
        // runs once on init

        // drive wheels
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        // slides
        leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");

        // Hard stop
        // drive wheels
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // slides
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // drive wheels
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //slides
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // flip left motors so everything runs forward
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        rightSlide.setDirection(DcMotor.Direction.REVERSE);

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

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      CONTROLLER 1 CONTROLS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

        // Get square root joystick inputs for high-speed control
        /*
        double x = sqrtInput(gamepad1.left_stick_x);
        double y = sqrtInput(-gamepad1.left_stick_y);  // Invert Y-axis as needed
        double rotation = sqrtInput(gamepad1.right_stick_x);
         */

        // does not use exponential joystick scaling
        double y = gamepad1.left_stick_y * -1; // Move forward/backward
        double x = gamepad1.left_stick_x * 1; // Strafe left/right
        double rotation = gamepad1.right_stick_x * 1; // Rotate


        /*
        ================================================
        this is to adjust the speed of the dpad
        ================================================
         */
        if (gamepad1.y && !y1Pressed) { //when x is pressed, increase dpadSpeed by 0.025
            dpadSpeed += 0.025;
            y1Pressed = true;
        }
        if (gamepad1.x && dpadSpeed > minDpadSpeed && !x1Pressed) { // when a is pressed, decrease dpadSpeed by 0.025
            dpadSpeed -= 0.025;
            x1Pressed = true;
        }

        telemetry.addData("Dpad Speed: ", dpadSpeed);


        // this is to drive with the dpad
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

        /*
        ================================================
        Alternative method of preventing >1 power value
        ================================================
        // Scale the values to keep within the -1 to 1 range
        frontLeftPower = Range.clip(frontLeftPower, -1.0, 1.0);
        frontRightPower = Range.clip(frontRightPower, -1.0, 1.0);
        backLeftPower = Range.clip(backLeftPower, -1.0, 1.0);
        backRightPower = Range.clip(backRightPower, -1.0, 1.0);
        */

        // This scaling will keep values proportional.
        // I still want to test which one drivers prefer.
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

        if (gamepad1.b && smoothFactor < maxSmooth && !b1Pressed) {
            smoothFactor += smoothIncrement; // Increase smoothFactor (faster acceleration)
            b1Pressed = true;
        }
        if (gamepad1.a && smoothFactor > minSmooth && !a1Pressed) {
            smoothFactor -= smoothIncrement; // Decrease smoothFactor (slower acceleration)
            a1Pressed = true;
        }

        telemetry.addData("Smooth Factor: ", smoothFactor);





        //=========================
        // Acceleration control
        // EXCLUDES DECELERATION
        //=========================

        // if the current power is greater than the target power, limit acceleration.
        // else, do nothing (do not limit deceleration)
        // Apply smoothing for **acceleration only**, no limit on deceleration
        /*
        if (Math.abs(frontLeftPower) > Math.abs(frontLeft.getPower())) {
            frontLeftPower = (1 - smoothFactor) * frontLeft.getPower() + smoothFactor * frontLeftPower;
        }
        if (Math.abs(backLeftPower) > Math.abs(backLeft.getPower())) {
            backLeftPower = (1 - smoothFactor) * backLeft.getPower() + smoothFactor * backLeftPower;
        }
        if (Math.abs(frontRightPower) > Math.abs(frontRight.getPower())) {
            frontRightPower = (1 - smoothFactor) * frontRight.getPower() + smoothFactor * frontRightPower;
        }
        if (Math.abs(backRightPower) > Math.abs(backRight.getPower())) {
            backRightPower = (1 - smoothFactor) * backRight.getPower() + smoothFactor * backRightPower;
        }
        */

         /*
        ==============================
        Acceleration control
        INCLUDES DECELERATION
        ==============================
        */
        frontLeftPower = (1 - smoothFactor) * frontLeft.getPower() + smoothFactor * frontLeftPower;
        backLeftPower = (1 - smoothFactor) * backLeft.getPower() + smoothFactor * backLeftPower;
        frontRightPower = (1 - smoothFactor) * frontRight.getPower() + smoothFactor * frontRightPower;
        backRightPower = (1 - smoothFactor) * backRight.getPower() + smoothFactor * backRightPower;

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      CONTROLLER 2 CONTROLS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

        double slidePower = -gamepad2.left_stick_y;

        int slideCurrentPosition = leftSlide.getCurrentPosition() + slideOffset;

        /*
        =======================================
        prevents bottoming out slides too fast
        =======================================
         */
        if (slideCurrentPosition > maxSlidesHeight && slidePower > 0) {
            slidePower = 0;
        }
        if (slideCurrentPosition < minSlidesHeight && slidePower < 0) {
            slidePower = 0;
        }
        if (slideCurrentPosition >= maxSlidesHeight * 0.9 && slidePower > 0) {
            slidePower *= 0.1;
        }
        if (slideCurrentPosition <= maxSlidesHeight * 0.1 && slidePower < 0) {
            slidePower *= 0.1;
        }

        telemetry.addData("Slides position: ", slideCurrentPosition);
        telemetry.addData("Max Slides Height: ", maxSlidesHeight);

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      FINAL SETTINGS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

        // sends final value to the motors
        frontLeft.setPower(frontLeftPower);
        frontRight.setPower(frontRightPower);
        backLeft.setPower(backLeftPower);
        backRight.setPower(backRightPower);
        leftSlide.setPower(slidePower);
        rightSlide.setPower(slidePower);

        // these keep track of button releases
        if (!gamepad1.a) {
            a1Pressed = false;
        }
        if (!gamepad1.b) {
            b1Pressed = false;
        }
        if (!gamepad1.x) {
            x1Pressed = false;
        }
        if (!gamepad1.y) {
            y1Pressed = false;
        }
        if (!gamepad2.y) {
            y2Pressed = false;
        }

        telemetry.update();
    }

    @Override
    public void stop() {
        //runs once on stop
    }
}