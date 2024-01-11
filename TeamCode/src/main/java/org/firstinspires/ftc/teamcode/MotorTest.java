package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class MotorTest extends LinearOpMode {
    private DcMotor rightMotor;
    private DcMotor leftMotor;
    //private CRServo intakeFront;
    //private CRServo intakeBack;

    @Override
    public void runOpMode() {
        rightMotor = hardwareMap.dcMotor.get("rightMotor");
        leftMotor = hardwareMap.dcMotor.get("leftMotor");
        //intakeFront = hardwareMap.crservo.get("intakeFront");
        //intakeBack = hardwareMap.crservo.get("intakeBack");

        rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        while (opModeIsActive()) {
            double power = -gamepad1.left_stick_y;
            //double j = -gamepad1.right_stick_y;

            rightMotor.setPower(power);
            leftMotor.setPower(power);
            //intakeFront.setPower(j);
            //intakeBack.setPower(j);
        }
    }
}
