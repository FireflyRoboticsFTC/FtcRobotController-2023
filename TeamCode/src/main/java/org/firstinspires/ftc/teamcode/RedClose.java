package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@Autonomous(name = "RedClose", group = "Autonomous")
public class RedClose extends LinearOpMode{

    private HardwareHandler customHardwareHandler;

    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);

        waitForStart();

        customHardwareHandler.doorRelease(0);
        customHardwareHandler.moveFourWheel(-0.5);
        sleep(1100);
        customHardwareHandler.moveFourWheel(0);
        customHardwareHandler.rawRotation(-0.5);
        sleep(830);
        customHardwareHandler.rawRotation(0);
        customHardwareHandler.moveFourWheel(0.5);
        //sleep(1000); //little more than 1 tile
        sleep(1150);
        customHardwareHandler.moveFourWheel(0.0);
        customHardwareHandler.upToBoard(0.5,this,0.1);

        customHardwareHandler.doorRelease(0);

        stop();
    }
}
