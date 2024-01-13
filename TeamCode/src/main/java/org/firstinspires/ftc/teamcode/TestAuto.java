
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@Autonomous(name = "TestAuto", group = "Autonomous")
public class TestAuto extends LinearOpMode {

    private HardwareHandler customHardwareHandler;


    @Override
    public void runOpMode() {
        // Initialize hardware
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);



        waitForStart();

        //customHardwareHandler.strafeFourWheel(1,true);
        customHardwareHandler.rotation(90,this);
        customHardwareHandler.rotation(-90,this);
        customHardwareHandler.rotation(90,this);

        sleep(1000);

        //customHardwareHandler.strafeFourWheel(0,true);

        sleep(1000);

        customHardwareHandler.moveFourWheel(1);

        sleep(1000);  // Sleep for 3000 milliseconds (3 seconds)

        // Stop the motors

        customHardwareHandler.moveFourWheel(0);





        // Stop the OpMode
        stop();
    }
}
