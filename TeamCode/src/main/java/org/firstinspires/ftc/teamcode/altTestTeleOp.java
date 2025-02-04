package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Disabled
@TeleOp(name="Alt_Test_TeleOp")
public class altTestTeleOp extends OpMode {

    private DcMotor frontLeft;
    private DcMotor frontRight;
    private DcMotor backLeft;
    private DcMotor backRight;

    private DcMotorEx leftSlide;
    private DcMotorEx rightSlide;

    private DcMotorEx armLeft;
    private DcMotorEx armRight;

    private Servo grip;

    // Acceleration control multiplier
    // Higher number = LESS smoothing
    double smoothFactor = 1;
    final double smoothIncrement = 0.05; // How much smoothFactor changes with each button press
    final double minSmooth = 0; // Minimum smoothFactor
    final double maxSmooth = 1; // Maximum smoothFactor
    double adaptiveSmoothFactor;

    // Calculate the adaptive smooth factor based on the current power
    double calculateSmoothFactor(double power) {
        double absPower = Math.abs(power);
        return (1-absPower) * smoothFactor;
    }

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


    static final double armRear = 0;
    double armThrottle = 0.5;

    int slideOffset = 0; // this is a part of a janky reset in case of the gt2 belt slipping

    // these are used to keep track button releases.
    boolean a1Pressed = false;
    boolean b1Pressed = false;
    boolean x1Pressed = false;
    boolean y1Pressed = false;
    boolean a2Pressed = false;
    boolean b2Pressed = false;
    boolean x2Pressed = false;
    boolean y2Pressed = false;
    boolean lb2Pressed = false;

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
        // arm
        armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
        armRight = hardwareMap.get(DcMotorEx.class, "armRight");
        //servo
        grip = hardwareMap.get(Servo.class, "grip");

        // Hard stop
        // drive wheels
        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // slides
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //arm
        armLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // drive wheels
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //slides
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        //arm
        armLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // flip left motors so everything runs forward
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        rightSlide.setDirection(DcMotor.Direction.REVERSE);
        armLeft.setDirection(DcMotor.Direction.REVERSE);

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



        //smoothFactor buttons
        if (gamepad1.b && smoothFactor < maxSmooth && !b1Pressed) {
            smoothFactor += smoothIncrement; // Increase smoothFactor (faster acceleration)
            b1Pressed = true;
        }
        if (gamepad1.a && smoothFactor > minSmooth && !a1Pressed) {
            smoothFactor -= smoothIncrement; // Decrease smoothFactor (slower acceleration)
            a1Pressed = true;
        }

        telemetry.addData("Smooth Factor: ", smoothFactor);


        // Adjust the baseSmoothFactor using buttons
        if (gamepad1.a) {
            smoothFactor += smoothIncrement;
        }
        if (gamepad1.b) {
            smoothFactor -= smoothIncrement;
        }

        /*
        ==============================
            Acceleration control
        ==============================
         */

        // Front Left Wheel
        adaptiveSmoothFactor = calculateSmoothFactor(frontLeftPower);
        frontLeftPower = (1 - adaptiveSmoothFactor) * frontLeft.getPower() + adaptiveSmoothFactor * frontLeftPower;

        // Front Right Wheel
        adaptiveSmoothFactor = calculateSmoothFactor(frontRightPower);
        frontRightPower = (1 - adaptiveSmoothFactor) * frontRight.getPower() + adaptiveSmoothFactor * frontRightPower;

        // Back Left Wheel
        adaptiveSmoothFactor = calculateSmoothFactor(backLeftPower);
        backLeftPower = (1 - adaptiveSmoothFactor) * backLeft.getPower() + adaptiveSmoothFactor * backLeftPower;

        // Back Right Wheel
        adaptiveSmoothFactor = calculateSmoothFactor(backRightPower);
        backRightPower = (1 - adaptiveSmoothFactor) * backRight.getPower() + adaptiveSmoothFactor * backRightPower;

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      SLIDES CONTROLS
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

        // reset slides to top
        if (gamepad2.y && gamepad2.left_bumper && !y2Pressed && !lb2Pressed) {
            leftSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            rightSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            slideOffset = (int) maxSlidesHeight;
            y2Pressed = true;
            lb2Pressed = true;
        }

        //reset slides to bottom
        if (gamepad2.left_bumper && gamepad2.x && !x2Pressed && !lb2Pressed) {
            slideOffset = (int) minSlidesHeight;
            leftSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            rightSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            lb2Pressed = true;
            x2Pressed = true;
        }

        telemetry.addData("Slides position: ", slideCurrentPosition);
        telemetry.addData("Max Slides Height: ", maxSlidesHeight);

        /*
        ================================================
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                      ARM CONTROLS
                <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        ================================================
         */

        double armPower = gamepad2.right_stick_y;// get right stick value
        double armPosition = armLeft.getCurrentPosition();
        if (armPosition < armRear && armPower < 0) {
            armPower = 0;
        }
        armPower *= armThrottle;

        if (gamepad2.a && !a2Pressed) {
            grip.setPosition(0);
            a2Pressed = true;
        }
        if (gamepad2.b && !b2Pressed) {
            grip.setPosition(1);
            b2Pressed = true;
        }

        telemetry.addData("Arm position: ", armPosition);

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
        armLeft.setPower(armPower);


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
        if (!gamepad2.x) {
            x2Pressed = false;
        }
        if (!gamepad2.y) {
            y2Pressed = false;
        }
        if (!gamepad2.left_bumper) {
            lb2Pressed = false;
        }
        if (!gamepad2.a) {
            a2Pressed = false;
        }
        if (!gamepad2.b) {
            b2Pressed = false;
        }
        telemetry.update();
    }

    @Override
    public void stop() {
        //runs once on stop
    }
}