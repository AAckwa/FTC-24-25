package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d; // RoadRunner
import com.acmerobotics.roadrunner.geometry.Vector2d; // RoadRunner
import com.acmerobotics.roadrunner.trajectory.Trajectory; // RoadRunner
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive; // RoadRunner

@Autonomous (name="Fourth Road Runner Auto")
public class fourthRoadRunnerAuto extends LinearOpMode {
    private autoFrame kevinFrame;

    @Override
    public void runOpMode() {

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

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        kevinFrame = new autoFrame(frontLeft,frontRight,backLeft,backRight,leftSlide,rightSlide,armLeft,armRight,grip,gripRotation);

        Pose2d startPose = new Pose2d(0,0,Math.toRadians(-90));

        drive.setPoseEstimate(startPose);

        Trajectory myTrajectory1 = drive.trajectoryBuilder(startPose) // to preload drop
                .lineToLinearHeading(new Pose2d(10,32,Math.toRadians(-45)))
                .build();

        Trajectory myTrajectory2 = drive.trajectoryBuilder(myTrajectory1.end()) // to floor 1 pickup

                .lineToLinearHeading(new Pose2d(14.5,28,Math.toRadians(0)))
                .build();

        Trajectory myTrajectory3 = drive.trajectoryBuilder(myTrajectory2.end()) // to floor 1 drop
                .lineToLinearHeading(new Pose2d(10, 32,Math.toRadians(-45)))
                .build();

        Trajectory myTrajectory4 = drive.trajectoryBuilder(myTrajectory3.end()) // to floor 2 pickup
                .lineToLinearHeading(new Pose2d(14.5,41,Math.toRadians(0)))
                .build();

        Trajectory myTrajectory5 = drive.trajectoryBuilder(myTrajectory4.end()) // to floor 2 drop
                .lineToLinearHeading(new Pose2d(10, 32,Math.toRadians(-45)))
                .build();
        Trajectory myTrajectory6 = drive.trajectoryBuilder(myTrajectory5.end()) // to floor 3 pickup
                .lineToLinearHeading(new Pose2d(18.75,32.25,Math.toRadians(45)))
                .build();
        Trajectory myTrajectory7 = drive.trajectoryBuilder(myTrajectory6.end()) // to floor 3 drop
                .lineToLinearHeading(new Pose2d(10, 32,Math.toRadians(-45)))
                .build();







        waitForStart();

        if(isStopRequested()) return;

        kevinFrame.resetArm();
        kevinFrame.resetSlides();

        kevinFrame.grip(true);
        kevinFrame.arm(0.5,1);

        drive.followTrajectory(myTrajectory1);


        kevinFrame.gripRotate(true);
        kevinFrame.slides(0.75,1);
        sleep(1000);
        kevinFrame.arm(0.17,1);
        sleep(500);
        kevinFrame.grip(false);
        sleep(400);
        kevinFrame.arm(0.5,1);
        sleep(250);
        kevinFrame.slides(0,1);
        sleep(750); // END PRELOAD DROP

        drive.followTrajectory(myTrajectory2);

        kevinFrame.arm(0.95,1); // BEGIN FLOOR 1 PICKUP
        sleep(1000);
        kevinFrame.grip(true);
        sleep(800);
        kevinFrame.arm(0.5,1); // END FLOOR 1 PICKUP

        drive.followTrajectory(myTrajectory3);

        kevinFrame.slides(0.75,1);
        sleep(1000);
        kevinFrame.arm(0.17,1);
        sleep(500);
        kevinFrame.grip(false);
        sleep(400);
        kevinFrame.arm(0.5,1);
        sleep(250);
        kevinFrame.slides(0,1);
        sleep(750); // END FLOOR 1 DROP

        drive.followTrajectory(myTrajectory4);

        kevinFrame.arm(0.95,1); // BEGIN FLOOR 2 PICKUP
        sleep(1000);
        kevinFrame.grip(true);
        sleep(800);
        kevinFrame.arm(0.5,1); // END FLOOR 2 PICKUP

        drive.followTrajectory(myTrajectory5);

        kevinFrame.slides(0.75,1);
        sleep(1000);
        kevinFrame.arm(0.17,1);
        sleep(500);
        kevinFrame.grip(false);
        sleep(400);
        kevinFrame.arm(0.5,1);
        sleep(250);
        kevinFrame.slides(0,1);
        sleep(750); // END FLOOR 2 DROP

        drive.followTrajectory(myTrajectory6);

        kevinFrame.arm(0.95,1); // BEGIN FLOOR 3 PICKUP
        sleep(1000);
        kevinFrame.grip(true);
        sleep(800);
        kevinFrame.arm(0.5,1); // END FLOOR 3 PICKUP

        drive.followTrajectory(myTrajectory7);

        kevinFrame.slides(0.75,1);
        sleep(1000);
        kevinFrame.arm(0.17,1);
        sleep(500);
        kevinFrame.grip(false);
        sleep(400);
        kevinFrame.arm(0.5,1);
        sleep(250);
        kevinFrame.slides(0,1);
        sleep(750); // END FLOOR 3 DROP

    }
}