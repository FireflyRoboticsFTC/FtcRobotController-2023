package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MotorTest extends LinearOpMode {
    private DcMotor motor;

    //private CRServo intakeFront;
    //private CRServo intakeBack;

    @Override
    public void runOpMode() {
        motor = hardwareMap.dcMotor.get("motor");

        //intakeFront = hardwareMap.crservo.get("intakeFront");
        //intakeBack = hardwareMap.crservo.get("intakeBack");

        motor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            double power = -gamepad1.left_stick_y;
            //double j = -gamepad1.right_stick_y;

            motor.setPower(power);
            //intakeFront.setPower(j);
            //intakeBack.setPower(j);
        }
    }
}
