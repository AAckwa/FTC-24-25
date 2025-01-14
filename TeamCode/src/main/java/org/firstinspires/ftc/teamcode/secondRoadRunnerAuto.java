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

        waitForStart();

        if (isStopRequested()) return;

        Pose2d startPose = new Pose2d();

        drive.setPoseEstimate(startPose);

        Trajectory myTrajectory1 = drive.trajectoryBuilder(startPose)
                .splineTo(new Vector2d(30, -30), Math.toRadians(0))
                .build();

        drive.followTrajectory(myTrajectory1);


    }
}
