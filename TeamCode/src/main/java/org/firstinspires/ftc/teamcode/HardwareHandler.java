package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.teamcode.movement.imu.SimpsonIntegrator;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;


import java.util.HashMap;

//import org.firstinspires.ftc.teamcode.roadrunner.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.structures.PIDFController;
//import org.firstinspires.ftc.teamcode.structures.SlidePosition;


public class HardwareHandler {

    private final HardwareMap hardwareMap;
    // Define your motors here

    private final int msPollInterval = 100;
    //private final BNO055IMU imu;

    private Orientation angles;
    private final SimpsonIntegrator integrator;

    private final double kP = 0.01;
    private final DcMotor leftFront;

    private Position currPos;

    private double initAngle;

    private final DcMotor leftRear;
    private final DcMotor rightFront;
    private final DcMotor rightRear;

    boolean intakeOn = true;

    boolean openDoor = true;

    boolean slowOn = false;

    boolean slowMode = false;

    public static double KLF = 1, KLR = 0.95, KRF = 0.9, KRR = 0.855; //KLF = 1, KLR = 0.944, KRF = 0.934825, KRR = 0.8824748;
    private final DcMotor linearSlideRight;
    private final DcMotor linearSlideLeft;
 //   private final DcMotor actuatorLeft;
 //   private final DcMotor actuatorRight;

    // Define your servo variables
    private final Servo railLaunch;
    private final Servo outputDoor;
    private final DcMotor conveyorBelt;


    private final DcMotor intakeFront;
//
    //private final DcMotor intakeBack;

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

        intakeFront = hardwareMap.dcMotor.get("intakeFront");
        //intakeBack = hardwareMap.crservo.get("intakeBack");
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



        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        // Initialize IMU hardware
        //imu = hardwareMap.get(BNO055IMU.class, "imu");
        //imu.initialize(parameters);


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
            rightFront.setPower((-d - r - s) / total * speed * VRF);
            rightRear.setPower((-d - r + s) / total * speed * VRR);

            /*
            d + r + s
            d + r - s
            d - r - s
            d - r + s
             */
        }
    }

    public void slowMode(double slowPercent) {
        VLF = 1*slowPercent;
        VLR = 1*slowPercent;
        VRF = 1*slowPercent;
        VRR = 1*slowPercent;
    }

    public void normalMode()
    {
        VLF = 1;
        VLR = 1;
        VRF = 1;
        VRR = 1;
    }

    public boolean isSlow()
    {
        return (VLF < 1);
    }


/*    public void intakeAndTransfer(double a) {
        // Check if the button is pressed

            intakeFront.setPower(a);
            conveyorBelt.setPower(-a);
    }

    public void doorRelease(double release) {
        if (openDoor = true) {
            drop.setPosition(release);
            openDoor = false;
        }
        if (openDoor = false) {
            drop.setPosition(-release);
            openDoor = true;
        }
    }*/




    boolean buttonPressed = false;
    ElapsedTime runtime = new ElapsedTime();

    public void toggleSlide(boolean a, double power) {
        // Check if the button is pressed
        boolean isButtonPressed = a; // Change "a" to the desired button
        double rightPowerModifer = 1;
        if (power > 0)
            rightPowerModifer = 1;
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

    public double getPositionRale(){
        return railLaunch.getPosition();
    }
//
//    public void parallelInput(double setInput){
//        intakeLeft.setPosition(setInput);
//        intakeRight.setPosition(1/setInput);
//    }
/*//
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
    }*/

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


    public void intakeAndTransfer(int i) {
        intakeFront.setPower(i);
        conveyorBelt.setPower(-i);
    }

    public void doorRelease(double release) {
        drop.setPosition(release);
    }


    public void moveFourWheel(double power) {
        leftFront.setPower(power);
        leftRear.setPower(power);
        rightFront.setPower(power);
        rightRear.setPower(power);
    }

    public void strafeFourWheel(double power, boolean direction) {
        if (direction) {
            leftFront.setPower(-power);
            leftRear.setPower(power*0.5);
            rightFront.setPower(power);
            rightRear.setPower(-power*0.5);
        }
        else {
            leftFront.setPower(power);
            leftRear.setPower(-power*0.5);
            rightFront.setPower(power);
            rightRear.setPower(-power*0.5 );
        }

    }


    public void slideTest(double slideTestPower) {
        linearSlideRight.setPower(slideTestPower);
        linearSlideLeft.setPower(-slideTestPower);

    }
    public void passThroughToBox(double conveyorPower) {
        conveyorBelt.setPower(-conveyorPower);
    }

    public void outScan(double power) {
        intakeFront.setPower(-power);
    }
    public void upToBoard(double slidePower, LinearOpMode opMode, double moveBoard) {
        linearSlideLeft.setPower(-slidePower);
        linearSlideRight.setPower(slidePower);
        opMode.sleep(1500);

        leftFront.setPower(moveBoard);
        leftRear.setPower(moveBoard);
        rightFront.setPower(moveBoard);
        rightRear.setPower(moveBoard);


        opMode.sleep(2900);
        drop.setPosition(0.25);
        opMode.sleep(1500);
        drop.setPosition(0);
        linearSlideLeft.setPower(slidePower*0.5);
        linearSlideRight.setPower(-slidePower*0.5);
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
        drop.setPosition(0);



    }


    /**
    private double getHeading() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        return angles.firstAngle;
    }
     **/


    //non-IMU rotation
    public void rawRotation(double power) {
        leftFront.setPower(power);
        leftRear.setPower(power);
        rightFront.setPower(-power);
        rightRear.setPower(-power);
    }
/**
    //IMU ROTATION (MAYBE WORKS?)
    public void rotation(double degrees, LinearOpMode opMode) {
        double currentHeading;
        double initialHeading = 0;


        while (opMode.opModeIsActive()) {

            currentHeading = getHeading();

            double error = degrees - (currentHeading - initialHeading);

            double rotationPower = error * 0.01; //error constant


            leftFront.setPower(rotationPower);
            leftRear.setPower(rotationPower);
            rightFront.setPower(-rotationPower);
            rightRear.setPower(-rotationPower);


            if (Math.abs(error) < 1.0) {
                break;
            }

            opMode.telemetry.addData("Current Heading", currentHeading);
            opMode.telemetry.addData("Error", error);
            opMode.telemetry.update();

            opMode.idle();

        }

    }
    public void strafeLeft(double power) {
        double heading = getHeading();
        double correction = heading * kP;

        // Adjust motor powers for strafing left
        leftFront.setPower(-power + correction);
        leftRear.setPower(power + correction);
        rightFront.setPower(power - correction);
        rightRear.setPower(-power - correction);
    }

    public void strafeRight(double power) {
        double heading = getHeading();
        double correction = heading * kP;

        // Adjust motor powers for strafing left
        leftFront.setPower(power + correction);
        leftRear.setPower(-power + correction);
        rightFront.setPower(-power - correction);
        rightRear.setPower(power - correction);
    }
 **/

}





