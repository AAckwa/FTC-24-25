package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d; // RoadRunner
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory; // RoadRunner
import com.pedropathing.localization.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive; // RoadRunner

@Autonomous (name="AAZ_Test Chamber Auto")
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

        Pose2d startPose = new Pose2d(0,0,Math.toRadians(180));

        drive.setPoseEstimate(startPose);

        Trajectory myTrajectory1 = drive.trajectoryBuilder(startPose) // to preload
                .lineToConstantHeading(new Vector2d(32,0))
                .build();

        Trajectory myTrajectory2 = drive.trajectoryBuilder(myTrajectory1.end()) // score preload
                .lineToConstantHeading(new Vector2d(21.5,0))
                .build();

        Trajectory myTrajectory3 = drive.trajectoryBuilder(myTrajectory2.end()) // to HP 1 Pickup
                .lineToLinearHeading(new Pose2d(-3,-40.5,Math.toRadians(90)))
                .build();

        Trajectory myTrajectory4 = drive.trajectoryBuilder(myTrajectory3.end()) // to HP 1 score Part 1
                .lineToLinearHeading(new Pose2d(10,-3,Math.toRadians(180)))
                .build();

        Trajectory myTrajectory5 = drive.trajectoryBuilder(myTrajectory4.end()) // to HP 1 score Part 2
                .lineToConstantHeading(new Vector2d(32 ,-3))
                .build();


        Trajectory myTrajectory6 = drive.trajectoryBuilder(myTrajectory5.end()) // score HP 1
                .lineToConstantHeading(new Vector2d(19,-3))
                .build();

        Trajectory myTrajectory7 = drive.trajectoryBuilder(myTrajectory6.end()) // to Park
                .lineToLinearHeading(new Pose2d(-4.5,-50.5,Math.toRadians(90)))
                .build();

//        Trajectory myTrajectory8 = drive.trajectoryBuilder(myTrajectory7.end()) // to HP 2 score Part 1
//                .lineToLinearHeading(new Pose2d(10,3,Math.toRadians(180)))
//                .build();
//
//        Trajectory myTrajectory9 = drive.trajectoryBuilder(myTrajectory8.end()) // to HP 2 score Part 2
//                .lineToConstantHeading(new Vector2d(32 ,3))
//                .build();
//
//        Trajectory myTrajectory10 = drive.trajectoryBuilder(myTrajectory9.end()) // score HP 2
//                .lineToConstantHeading(new Vector2d(17.5,3))
//                .build();


        waitForStart();

        if(isStopRequested()) return;

        kevinFrame.resetArm();
        kevinFrame.resetSlides();

        kevinFrame.grip(true);
        kevinFrame.arm(0.2,1);
        kevinFrame.gripRotate(true);

        drive.followTrajectory(myTrajectory1);

        kevinFrame.arm(0.28,1);
//        sleep(200);
        drive.followTrajectory(myTrajectory2);
        kevinFrame.grip(false);


        drive.followTrajectory(myTrajectory3);

        kevinFrame.arm(0,1);
        sleep(400);
        kevinFrame.grip(true);
        sleep(600);
        kevinFrame.arm(0.2,1); // END HP 1 PICKUP

        drive.followTrajectory(myTrajectory4);
        drive.followTrajectory(myTrajectory5);

        kevinFrame.arm(0.28,1);
//        sleep(200);
        drive.followTrajectory(myTrajectory6);
        kevinFrame.grip(false); // END HP 1 SCORE


        kevinFrame.arm(0.5,0.5);
        drive.followTrajectory(myTrajectory7);


//        kevinFrame.arm(0,1); //  HP 2 PICKUP
//        sleep(400);
//        kevinFrame.grip(true);
//        sleep(600);
//        kevinFrame.arm(0.2,1);
//
//        drive.followTrajectory(myTrajectory8);
//        drive.followTrajectory(myTrajectory9);
//
//        kevinFrame.arm(0.26,1);
////        sleep(200);
//        drive.followTrajectory(myTrajectory10);
//        kevinFrame.grip(false); // END HP 2 SCORE
//
//        sleep(5000);


    }
}