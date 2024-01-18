package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@Autonomous(name = "RedLongAuto", group = "Autonomous")
public class RedLongAuto extends LinearOpMode {

    private HardwareHandler customHardwareHandler;

    double x;
    double y;

    int seconds = 1;


    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);

        //customHardwareHandler.moverFourWheel(power); //forward move + / backward -
        //customHardwareHandler.strafeFourWheel(power, direction) // left is true and right is false //  power is distance
        //customHardwareHandler.rotation(degrees, this) //figure out degress yourself
        //customHardwareHandler.rawRotation(degree) if imu dosen't work

        waitForStart();

        sleep(seconds * 1000);

        customHardwareHandler.moveFourWheel(0.2);
        sleep(250);
        customHardwareHandler.moveFourWheel(0.0);

        /**
        customHardwareHandler.rawRotation(0.5);
        sleep(900);
        customHardwareHandler.rawRotation(0.0);
         **/

        customHardwareHandler.rawRotation(0.5);
        sleep(870);
        customHardwareHandler.rawRotation(0.0);

        customHardwareHandler.moveFourWheel(0.5);
        sleep(3800);
        customHardwareHandler.moveFourWheel(0.0);

        // Stop the OpMode
        stop();
    }
}
