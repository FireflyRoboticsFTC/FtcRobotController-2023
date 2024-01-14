
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@Autonomous(name = "TestAuto", group = "Autonomous")
public class TestAuto extends LinearOpMode {

    private HardwareHandler customHardwareHandler;

    double x;
    double y;


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

        customHardwareHandler.moveFourWheel(0.5);
        sleep(1000);
        customHardwareHandler.moveFourWheel(0.0);

        //customHardwareHandler.strafeFourWheel(1,true);

        /**
        //IMU ROTATION TESTING
        customHardwareHandler.rotation(90,this);
        customHardwareHandler.rotation(-90,this);
        customHardwareHandler.rotation(90,this);

        //customHardwareHandler.rawRotation(1);

        sleep(1000);

        //customHardwareHandler.strafeFourWheel(0,true);

        sleep(1000);

        customHardwareHandler.moveFourWheel(1);

        sleep(1000);


        customHardwareHandler.moveFourWheel(0);

        customHardwareHandler.strafeLeft(1);

        sleep(1000);

        customHardwareHandler.strafeRight(1);

        sleep(1000);

**/



        // Stop the OpMode
        stop();
    }
}
