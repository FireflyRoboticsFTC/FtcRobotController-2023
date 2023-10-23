package org.firstinspires.ftc.teamcode;

//import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
//import org.firstinspires.ftc.teamcode.movement.imu.SimpsonIntegrator;
//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.structures.PIDFController;
//import org.firstinspires.ftc.teamcode.structures.SlidePosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CustomHardwareHandler {

    private final HardwareMap hardwareMap;
    // Define your motors here
    private final DcMotor leftFront;
    private final DcMotor leftRear;
    private final DcMotor rightFront;
    private final DcMotor rightRear;
//    private final DcMotor linearSlideRight;
//    private final DcMotor linearSlideLeft;
//    private final DcMotor actuatorLeft;
//    private final DcMotor actuatorRight;

    // Define your servo variables
    private final Servo railLaunch;
//    private final Servo outputDoor;
//    private final Servo conveyorBelt;
//    private final Servo intake;

//    private final Servo intakeRight;
//
//    private final Servo intakeLeft;

    private DcMotor.RunMode currRunMode;

    public static double VLF = 1, VLR = 1, VRF = 1, VRR = 1; //wheel difference variables
    private double axial;

    private double yaw;

    private double lateral;

    private double backLeftPower;

    private double backRightPower;

    private double frontLeftPower;

    private double frontRightPower;

    // Constructor
    public CustomHardwareHandler(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
        // Initialize your motors by retrieving them from the hardware map
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
//        linearSlideRight = hardwareMap.dcMotor.get("linearSlideRight");
//        linearSlideLeft = hardwareMap.dcMotor.get("linearSlideLeft");
//        actuatorLeft = hardwareMap.dcMotor.get("actuatorLeft");
//        actuatorRight = hardwareMap.dcMotor.get("actuatorRight");

//        intakeLeft = hardwareMap.servo.get("intakeLeft");
//        intakeRight = hardwareMap.servo.get("intakeRight");
      railLaunch = hardwareMap.servo.get("railLaunch");
//        outputDoor = hardwareMap.servo.get("outputDoor");
//        conveyorBelt = hardwareMap.servo.get("conveyorBelt");
//        intake = hardwareMap.servo.get("intake");

        // Set the direction for the "left" motors to reverse
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftRear.setDirection(DcMotorSimple.Direction.REVERSE);
//        linearSlideLeft.setDirection(DcMotorSimple.Direction.REVERSE);
//        actuatorLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set motor run modes to RUN_WITHOUT_ENCODER
        leftFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        rightRear.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        linearSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        linearSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        actuatorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        actuatorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior to BRAKE for front and rear motors
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        // Set zero power behavior, max power, etc. as needed
        // ...
    }

    public void moveWithPower(double d, double r, double s, double speed) { // d : linear movement, r : rotational movement, s : speed (0-1); r is signed with CCW as positive
        //assert (speed <= 1 && speed >= 0): "Speed must be between 0 and 1";
        if (currRunMode != DcMotor.RunMode.RUN_WITHOUT_ENCODER)
            currRunMode = DcMotor.RunMode.RUN_WITHOUT_ENCODER;
        speed = Math.abs(speed);
        double total = Math.abs(d) + Math.abs(r) + Math.abs(s);
        if (d == 0 && r == 0 && s == 0) {
            leftFront.setPower(0);
            leftRear.setPower(0);
            rightFront.setPower(0);
            rightRear.setPower(0);
        }
        else {
            leftFront.setPower((-d + r + s) / total * speed * VLF);
            leftRear.setPower((-d + r - s) / total * speed * VLR); // test to change these values
            rightFront.setPower((-d - r + s) / total * speed * VRF);
            rightRear.setPower((-d - r - s) / total * speed * VRR);

            /*
            d + r + s
            d + r - s
            d - r - s
            d - r + s
             */
        }
    }

    // Other methods for controlling the motors, setting power, etc.
    // ...
//    public void moveServo(double speed) {
//        double currentPosition = 0.0;
//        servo.setPosition(currentPosition);
//        if (gamepad1.a) {
//            double targetPosition = 1.0;
//
//            if (currentPosition < targetPosition)
//                currentPosition += speed;
//            else if (currentPosition > targetPosition) {
//                currentPosition -= speed;
//
//
//                servo.setPosition(currentPosition);
//
//                timer.reset();
//
//            }
//            sleep(milliseconds 50);
//        }
//    }
//
    public void railLauncher(double setPosition) {
        railLaunch.setPosition(setPosition);
    }
//
//    public void parallelInput(double setInput){
//        intakeLeft.setPosition(setInput);
//        intakeRight.setPosition(1/setInput);
//    }
//
//    public void dispenseOutput() {
//        outputDoor.setPosition(0.16733333333334);   //dispense angle is 50.2 degrees / 300 degrees total = 0.1673333333333
//    }



//public void moveServo(double speed) {
//
//    double currentPosition = 0.0;
//    railLaunch.setPosition(currentPosition);
//    if (gamepad1.a) {
//        double targetPosition = 1.0;
//
//        if (currentPosition < targetPosition) {
//
//            currentPosition += speed;}
//        else if (currentPosition > targetPosition{
//                currentPosition -= speed;
//
//
//                railLaunch.setPosition(currentPosition);
//
//                timer.reset();
//
//                sleep(milliseconds 50);
//
//        }
//    }
}