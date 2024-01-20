
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
        double i = 3;

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
        //if(blueScan.getPositionX() > 490) {//Right Side
        if(i == 1) {
            customHardwareHandler.passThroughToBox(0.3);
            sleep(3000);
            customHardwareHandler.passThroughToBox(0);
            customHardwareHandler.doorRelease(0);
            customHardwareHandler.moveFourWheel(-0.45);
            sleep(1200);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.rawRotation(-0.25);
            sleep(1000);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.outScan(0.5);
            sleep(2000);
            customHardwareHandler.outScan(0);
            customHardwareHandler.moveFourWheel(0.3);
            sleep(1200);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.rawRotation(0.23);
            sleep(1000);

            customHardwareHandler.moveFourWheel(0.2);
            sleep(1500);
            customHardwareHandler.rawRotation(0.5);
            sleep(850);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.moveFourWheel(0.43);
            //sleep(1000); //little more than 1 tile
            sleep(3000);
            customHardwareHandler.moveFourWheel(0.0);
            customHardwareHandler.rawRotation(-0.45);
            sleep(850);
            customHardwareHandler.moveFourWheel(-0.35);
            sleep(1050);
            customHardwareHandler.rawRotation(0.5);
            sleep(850);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.moveFourWheel(0.5);
            sleep(1000);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.upToBoard(0.5, this, 0.2);

        }

        //if else (middle)
        else if(i==2){
            customHardwareHandler.passThroughToBox(0.3);
            sleep(3000);
            customHardwareHandler.passThroughToBox(0);
            customHardwareHandler.doorRelease(0);
            customHardwareHandler.moveFourWheel(-0.55);
            sleep(1050);
            customHardwareHandler.moveFourWheel(0);
            sleep(1050);
            customHardwareHandler.moveFourWheel(0.51);
            sleep(850);
            customHardwareHandler.rawRotation(0.495);
            sleep(850);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.moveFourWheel(0.43);
    //sleep(1000); //little more than 1 tile
            sleep(3100);
            customHardwareHandler.moveFourWheel(0.0);
            customHardwareHandler.rawRotation(-0.45);
            sleep(850);
            customHardwareHandler.moveFourWheel(-0.35);
            sleep(1050);
            customHardwareHandler.rawRotation(0.48);
            sleep(850);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.upToBoard(0.5, this, 0.2);
        }

        else {
            customHardwareHandler.passThroughToBox(0.3);
            sleep(3000);
            customHardwareHandler.passThroughToBox(0);
            customHardwareHandler.doorRelease(0);
            customHardwareHandler.moveFourWheel(-0.45);
            sleep(1000);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.rawRotation(0.25);
            sleep(1000);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.outScan(0.5);
            sleep(2000);
            customHardwareHandler.outScan(0);
            customHardwareHandler.moveFourWheel(0.3);
            sleep(100);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.rawRotation(-0.3);
            sleep(1200);

            customHardwareHandler.moveFourWheel(0.3);
            sleep(1480);
            customHardwareHandler.rawRotation(0.4);
            sleep(1300);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.moveFourWheel(0.43);
            //sleep(1000); //little more than 1 tile
            sleep(3000);
            customHardwareHandler.moveFourWheel(0.0);
            customHardwareHandler.rawRotation(-0.45);
            sleep(850);
            customHardwareHandler.moveFourWheel(-0.35);
            sleep(1050);
            customHardwareHandler.rawRotation(0.5);
            sleep(900);
            customHardwareHandler.rawRotation(0);
            customHardwareHandler.moveFourWheel(0.5);
            sleep(800);
            customHardwareHandler.moveFourWheel(0);
            customHardwareHandler.upToBoard(0.5, this, 0.2);
        }

      //  else //Left Side



        // Stop the OpMode
        stop();
    }
}
