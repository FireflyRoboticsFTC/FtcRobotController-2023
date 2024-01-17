package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class RedLong extends LinearOpMode {

    private HardwareHandler customHardwareHandler;

    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);

        waitForStart();

        customHardwareHandler.passThroughToBox(0.3);
        sleep(3000);
        customHardwareHandler.passThroughToBox(0);
        customHardwareHandler.doorRelease(0);
        customHardwareHandler.moveFourWheel(-0.5);
        sleep(1050);
        customHardwareHandler.moveFourWheel(0);
        customHardwareHandler.rawRotation(-0.5);
        sleep(850);
        customHardwareHandler.rawRotation(0);
        customHardwareHandler.moveFourWheel(0.5);
        //sleep(1000); //little more than 1 tile
        sleep(3000);
        customHardwareHandler.moveFourWheel(0.0);
        customHardwareHandler.upToBoard(0.5, this, 0.1);

        // Stop the OpMode
        stop();
    }
}
