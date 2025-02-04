package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d; // RoadRunner
import com.acmerobotics.roadrunner.geometry.Vector2d; // RoadRunner
import com.acmerobotics.roadrunner.trajectory.Trajectory; // RoadRunner
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive; // RoadRunner

@Autonomous (name="Second Road Runner Auto")
public class secondRoadRunnerAuto extends LinearOpMode {
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

            Pose2d startPose = new Pose2d();

            drive.setPoseEstimate(startPose);

            Trajectory myTrajectory1 = drive.trajectoryBuilder(startPose)
                    .lineToConstantHeading(new Vector2d(9,33))
                    .build();

            Trajectory myTrajectory2 = drive.trajectoryBuilder(myTrajectory1.end().plus(new Pose2d(0,0,Math.toRadians(-45))))

                    .lineToLinearHeading(new Pose2d(15.5,29.5,Math.toRadians(0)))
                    .build();

            Trajectory myTrajectory3 = drive.trajectoryBuilder(myTrajectory2.end())
                    .lineToLinearHeading(new Pose2d(9, 33,Math.toRadians(-45)))
                    .build();

            Trajectory myTrajectory4 = drive.trajectoryBuilder(myTrajectory3.end())
                    .lineToLinearHeading(new Pose2d(15.5,44))
                            .build();

            Trajectory myTrajectory5 = drive.trajectoryBuilder(myTrajectory4.end())
                    .lineToLinearHeading(new Pose2d(9, 33,Math.toRadians(-45)))
                    .build();







            waitForStart();

            if(isStopRequested()) return;

            kevinFrame.resetArm();
            kevinFrame.resetSlides();

            drive.followTrajectory(myTrajectory1);

            drive.turn(Math.toRadians(-45));
            kevinFrame.arm(0.5,0.5); // BEGIN PRELOAD DROP
            sleep(1000);
            kevinFrame.gripRotate(true);
            kevinFrame.slides(0.75,0.75);
            sleep(1500);
            kevinFrame.arm(0.17,0.5);
            sleep(1000);
            kevinFrame.grip(false);
            sleep(400);
            kevinFrame.arm(0.5,0.5);
            sleep(500);
            kevinFrame.slides(0,0.75);
            sleep(1000); // END PRELOAD DROP

            drive.followTrajectory(myTrajectory2);

            kevinFrame.arm(0.95,0.5); // BEGIN FLOOR 1 PICKUP
            sleep(1500);
            kevinFrame.grip(true);
            sleep(800);
            kevinFrame.arm(0.5,0.5); // END FLOOR 1 PICKUP

            drive.followTrajectory(myTrajectory3);

            kevinFrame.slides(0.75,0.75);
            sleep(1500);
            kevinFrame.arm(0.17,0.5);
            sleep(1000);
            kevinFrame.grip(false);
            sleep(400);
            kevinFrame.arm(0.5,0.5);
            sleep(500);
            kevinFrame.slides(0,0.75); // END FLOOR 1 DROP
            sleep(2000);

            drive.followTrajectory(myTrajectory4);

            kevinFrame.arm(0.95,0.5); // BEGIN FLOOR 2 PICKUP
            sleep(1500);
            kevinFrame.grip(true);
            sleep(800);
            kevinFrame.arm(0.5,0.5); // END FLOOR 2 PICKUP

            drive.followTrajectory(myTrajectory5);

            kevinFrame.slides(0.75,0.75);
            sleep(1500);
            kevinFrame.arm(0.17,0.5);
            sleep(1000);
            kevinFrame.grip(false);
            sleep(400);
            kevinFrame.arm(0.5,0.5);
            sleep(500);
            kevinFrame.slides(0,0.75); // END FLOOR 2 DROP
            sleep(2000);

        }

    }