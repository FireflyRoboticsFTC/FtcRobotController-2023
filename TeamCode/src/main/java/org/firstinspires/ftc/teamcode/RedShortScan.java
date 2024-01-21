
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;

@Autonomous(name = "!RedShortScanCOMP", group = "Autonomous")
public class RedShortScan extends LinearOpMode {

    private HardwareHandler customHardwareHandler;
    private TensorFlowObjectDetection blueScan;
    private colorCam propDetection;
    private VisionPortal visionPortal;
    double x;
    double y;

    int seconds = 1;

    //right: 493 / 120
    // x/y

    //left: 46/125
    // x/y


    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);
        blueScan = new TensorFlowObjectDetection();

        propDetection = new colorCam(telemetry, "red");

        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "webcam"), propDetection);
        visionPortal.setProcessorEnabled((VisionProcessor) propDetection, true);

        //customHardwareHandler.moverFourWheel(power); //forward move + / backward -
        //customHardwareHandler.strafeFourWheel(power, direction) // left is true and right is false //  power is distance
        //customHardwareHandler.rotation(degrees, this) //figure out degress yourself
        //customHardwareHandler.rawRotation(degree) if imu dosen't work

        waitForStart();
        //double i = 3;

        switch (propDetection.getLocation()) {
            case RIGHT:
                customHardwareHandler.passThroughToBox(0.3);
                sleep(3000);
                customHardwareHandler.passThroughToBox(0);
                customHardwareHandler.doorRelease(0);
                customHardwareHandler.moveFourWheel(-0.3);
                sleep(700);
                customHardwareHandler.moveFourWheel(0);

                customHardwareHandler.rawRotation(-0.5);
                sleep(850);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(800);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.5);
                //customHardwareHandler.rawRotation(1);
                sleep(750);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(600);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(0.5);
                sleep(850);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.outScan(0.8);
                sleep(2000);
                customHardwareHandler.outScan(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(600);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.upToBoard(0.25,this,0.2);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.4);
                sleep(1000);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(800);
                customHardwareHandler.moveFourWheel(0);

                break;
            case LEFT:
                customHardwareHandler.passThroughToBox(0.3);
                sleep(3000);
                customHardwareHandler.passThroughToBox(0);
                customHardwareHandler.doorRelease(0);
                customHardwareHandler.moveFourWheel(-0.45);
                sleep(1200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.4);
                sleep(800);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.outScan(0.7);
                sleep(2000);
                customHardwareHandler.outScan(0);
                customHardwareHandler.rawRotation(-0.1);
                sleep(1000);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.4);
                sleep(1000);
                customHardwareHandler.upToBoard(0.35,this,0.2);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.4);
                sleep(1000);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(900);
                customHardwareHandler.moveFourWheel(0);
                break;

            case MIDDLE:
                customHardwareHandler.passThroughToBox(0.3);
                sleep(3000);
                customHardwareHandler.passThroughToBox(0);
                customHardwareHandler.doorRelease(0);
                customHardwareHandler.moveFourWheel(-0.55);
                sleep(900);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.outScan(0.5);
                sleep(2000);
                customHardwareHandler.outScan(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(600);
                customHardwareHandler.rawRotation(-0.495);
                sleep(850);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.43);
                //sleep(1000); //little more than 1 tile
                sleep(1000);
                customHardwareHandler.moveFourWheel(0.0);
                customHardwareHandler.rawRotation(0.45);
                sleep(850);
                customHardwareHandler.moveFourWheel(-0.35);
                sleep(700);
                customHardwareHandler.rawRotation(-0.48);
                sleep(700);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.upToBoard(0.35, this, 0.2);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.4);
                sleep(1000);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(900);
                customHardwareHandler.moveFourWheel(0);

                break;
            case NOT_FOUND:
                customHardwareHandler.passThroughToBox(0.3);
                sleep(3000);
                customHardwareHandler.passThroughToBox(0);
                customHardwareHandler.doorRelease(0);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(1050);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(0.5);
                sleep(850);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                //sleep(1000); //little more than 1 tile
                sleep(3000);
                customHardwareHandler.moveFourWheel(0.0);
                customHardwareHandler.upToBoard(0.5,this,0.1);
                customHardwareHandler.moveFourWheel(-0.5);
                sleep(200);
                customHardwareHandler.moveFourWheel(0);
                customHardwareHandler.rawRotation(-0.4);
                sleep(1000);
                customHardwareHandler.rawRotation(0);
                customHardwareHandler.moveFourWheel(0.5);
                sleep(900);
                customHardwareHandler.moveFourWheel(0);

                break;
        }
        //SCAN
        /**
         //      if (x > 1 && x < 10) { //Middle
         customHardwareHandler.passThroughToBox(0.3);
         sleep(3000);
         customHardwareHandler.passThroughToBox(0);
         customHardwareHandler.doorRelease(0);
         customHardwareHandler.moveFourWheel(-0.5);
         sleep(1050);
         customHardwareHandler.moveFourWheel(0.5);
         sleep(1050);
         customHardwareHandler.rawRotation(0.5);
         sleep(850);
         customHardwareHandler.rawRotation(0);
         customHardwareHandler.moveFourWheel(0.5);
         //sleep(1000); //little more than 1 tile
         sleep(3000);
         customHardwareHandler.moveFourWheel(0.0);
         customHardwareHandler.rawRotation(-0.5);
         sleep(850);
         customHardwareHandler.rawRotation(0);
         customHardwareHandler.moveFourWheel(-0.5);
         sleep(1050);
         customHardwareHandler.moveFourWheel(0);
         customHardwareHandler.rawRotation(0.5);
         sleep(1050);
         customHardwareHandler.rawRotation(0);
         customHardwareHandler.upToBoard(0.5, this, 0.1);

         //        }
         **/

        stop();
    }
}
