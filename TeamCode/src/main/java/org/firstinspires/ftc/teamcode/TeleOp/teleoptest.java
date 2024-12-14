package org.firstinspires.ftc.teamcode.TeleOp;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.RobotParts.Culisante;
import org.firstinspires.ftc.teamcode.RobotParts.Servos;

@TeleOp
public class teleoptest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));
        Culisante culisante = new Culisante(telemetry);
        Servos servo = new Servos();
        culisante.Culisanteinit(hardwareMap);
        servo.initServo(hardwareMap);
        sleep(400);
        servo.initAxoane(hardwareMap);
        boolean colectare = true;
        double speed =0.85;



    {

        }

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            // Drive logic
            drive.setDrivePowers(new PoseVelocity2d(
                    new Vector2d(
                            -gamepad1.left_stick_y * speed,
                            -gamepad1.left_stick_x * speed
                    ),
                    -gamepad1.right_stick_x * speed
            ));

            drive.updatePoseEstimate();


            if (gamepad1.triangle) {
                culisante.goToPosition(1, Culisante.PozCulisante.Jos);
                servo.startExtendo();
                sleep(300);
                speed =0.4;
            }

            if (gamepad1.cross) {
                speed = 0.85;
                servo.startTransf();
            }

            servo.updateExtendo();
            servo.updateTransf();


            if (gamepad1.right_bumper)
                servo.rotireGripper.setPosition(servo.rotireGripper.getPosition() + 0.01);
            if (gamepad1.left_bumper)
                servo.rotireGripper.setPosition(servo.rotireGripper.getPosition() - 0.01);

            if (gamepad1.dpad_right)
                servo.colectare();

            if (gamepad1.dpad_left && servo.AxonStanga.getPosition() != 0)
                servo.decolectare();

            if (gamepad1.dpad_down) {
                servo.bratGripper.setPosition(servo.bratGripper.getPosition() + 0.001);
            }
            if (gamepad1.dpad_up) {
                servo.bratGripper.setPosition(servo.bratGripper.getPosition() - 0.001);
            }

            if(gamepad1.circle) {servo.transfer.setPosition(0.5);}

            if (gamepad2.cross) {
                culisante.goToPosition(0.75, Culisante.PozCulisante.Basket1);
            }
            if (gamepad2.circle) {
                culisante.goToPosition(0.75, Culisante.PozCulisante.Bara2);
            }

            if (gamepad2.triangle) {
                culisante.goToPosition(0.75, Culisante.PozCulisante.Basket2);
            }

            if (gamepad2.right_bumper) {
                culisante.CulisantaDreapta.setTargetPosition(culisante.CulisantaDreapta.getCurrentPosition() + 30);
                culisante.CulisantaStanga.setTargetPosition(culisante.CulisantaStanga.getCurrentPosition() + 30);
                culisante.CulisantaStanga.setPower(0.75);
                culisante.CulisantaDreapta.setPower(0.75);
            }


            if (gamepad2.left_bumper) {
                culisante.CulisantaDreapta.setTargetPosition(culisante.CulisantaDreapta.getCurrentPosition() - 30);
                culisante.CulisantaStanga.setTargetPosition(culisante.CulisantaStanga.getCurrentPosition() - 30);
                culisante.CulisantaStanga.setPower(0.75);
                culisante.CulisantaDreapta.setPower(0.75);
            }


            telemetry.addData("poz culi", culisante.CulisantaDreapta.getCurrentPosition());
            telemetry.update();

        }

    }



    }



