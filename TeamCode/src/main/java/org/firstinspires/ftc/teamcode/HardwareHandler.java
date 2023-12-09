package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.movement.imu.SimpsonIntegrator;

import java.util.HashMap;

//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.structures.PIDFController;
//import org.firstinspires.ftc.teamcode.structures.SlidePosition;


public class HardwareHandler {

    private final HardwareMap hardwareMap;
    // Define your motors here

    private final int msPollInterval = 100;
    //private final BNO055IMU imu;
    private final BHI260IMU imu;
    private final SimpsonIntegrator integrator;
    private final DcMotor leftFront;

    private Position currPos;

    private double initAngle;

    private final DcMotor leftRear;
    private final DcMotor rightFront;
    private final DcMotor rightRear;

    public static double KLF = 1, KLR = 0.95, KRF = 0.9, KRR = 0.855; //KLF = 1, KLR = 0.944, KRF = 0.934825, KRR = 0.8824748;
    private final DcMotor linearSlideRight;
    private final DcMotor linearSlideLeft;
 //   private final DcMotor actuatorLeft;
 //   private final DcMotor actuatorRight;

    // Define your servo variables
    private final Servo railLaunch;
    private final Servo outputDoor;
    private final DcMotor conveyorBelt;


    private final CRServo intakeFront;
//
    private final CRServo intakeBack;

    private DcMotor.RunMode currRunMode;

    private final  Servo drop;

    public static double VLF = 1, VLR = 1, VRF = 1, VRR = 1; //wheel difference variables
    private double axial;

    private double yaw;

    private double lateral;

    private double backLeftPower;

    private double backRightPower;

    private double frontLeftPower;

    private double frontRightPower;


    // Constructor
    public HardwareHandler(HardwareMap hardwareMap, Position currPos, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        // Initialize your motors by retrieving them from the hardware map
        leftFront = hardwareMap.dcMotor.get("leftFront");
        leftRear = hardwareMap.dcMotor.get("leftRear");
        rightFront = hardwareMap.dcMotor.get("rightFront");
        rightRear = hardwareMap.dcMotor.get("rightRear");
        conveyorBelt = hardwareMap.dcMotor.get("conveyorBelt");
        linearSlideRight = hardwareMap.dcMotor.get("linearSlideRight");
        linearSlideLeft = hardwareMap.dcMotor.get("linearSlideLeft");
//        actuatorLeft = hardwareMap.dcMotor.get("actuatorLeft");
//        actuatorRight = hardwareMap.dcMotor.get("actuatorRight");

        intakeFront = hardwareMap.crservo.get("intakeFront");
        intakeBack = hardwareMap.crservo.get("intakeBack");
        railLaunch = hardwareMap.servo.get("railLaunch");
        drop = hardwareMap.servo.get("BAM");
        outputDoor = hardwareMap.servo.get("outputDoor");

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
        linearSlideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        linearSlideLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        actuatorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        actuatorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Set zero power behavior to BRAKE for front and rear motors
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRear.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        linearSlideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        integrator = new SimpsonIntegrator(msPollInterval);



        imu = hardwareMap.get(BHI260IMU.class, "imu");


        //assert(imu.isSystemCalibrated()): "Calibrate the IMU";

        //this.currPos = currPos;
        //this.initAngle = initAngle;
        //this.telemetry = telemetry;*/

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

    public void intakeAndTransfers(double j) {
        double conveyerRatio = 1;
        intakeFront.setPower(-j);
        intakeBack.setPower(-j);
        conveyorBelt.setPower(j*conveyerRatio);
    }

    public void doorRelease(double release) {
        drop.setPosition(release);
    }



    boolean buttonPressed = false;
    ElapsedTime runtime = new ElapsedTime();

    public void toggleSlide(boolean a, double power) {
        // Check if the button is pressed
        boolean isButtonPressed = a; // Change "a" to the desired button
        double rightPowerModifer = 1;
        if (power > 0)
            rightPowerModifer = 0.8;
        // Check if the button state has changed
        if (isButtonPressed && !buttonPressed) {
            // Button is pressed, start the motor
            linearSlideRight.setPower(power*rightPowerModifer); // Set the motor power to your desired value
            linearSlideLeft.setPower(-power);
            runtime.reset(); // Reset the timer

        } else if (!isButtonPressed && buttonPressed) {
            // Button is released, stop the motor
            linearSlideLeft.setPower(0.0); // Stop the motor
            linearSlideRight.setPower(0.0);
        }

        // Update the button state
        buttonPressed = isButtonPressed;

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
    boolean outputButton = false;
    long outputLastPressed = 0;
    public void dispenseOutput() {
        if (System.currentTimeMillis() - outputLastPressed > 500) {
            outputLastPressed = System.currentTimeMillis();
            outputButton = ! outputButton;
            outputDoor.setPosition(outputButton ? 0.16733333333334 : 0);
        }
        //dispense angle is 50.2 degrees / 300 degrees total = 0.1673333333333
    }

    boolean slowButton = false;
    long slowLastPressed = 0;
    public boolean toggleSlow() {
        if (System.currentTimeMillis() - slowLastPressed > 500) {
            slowLastPressed = System.currentTimeMillis();
            slowButton = !slowButton;
        }
        return slowButton;
    }

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



    public HashMap<String, Object> getIMUTelemetry() {
        return integrator.getTelemetry();
    }



}