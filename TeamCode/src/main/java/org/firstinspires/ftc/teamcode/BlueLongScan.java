
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@Autonomous(name = "BlueLongScan", group = "Autonomous")
public class BlueLongScan extends LinearOpMode {

    private HardwareHandler customHardwareHandler;
    private TensorFlowObjectDetection blueScan;

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

        //customHardwareHandler.moverFourWheel(power); //forward move + / backward -
        //customHardwareHandler.strafeFourWheel(power, direction) // left is true and right is false //  power is distance
        //customHardwareHandler.rotation(degrees, this) //figure out degress yourself
        //customHardwareHandler.rawRotation(degree) if imu dosen't work

        waitForStart();


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
        if(blueScan.getPositionX() > 490) {//Right Side
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
            customHardwareHandler.upToBoard(0.5, this, 0.1);
        }

/**
        else //Left Side


*/
        // Stop the OpMode
        stop();
    }
}
