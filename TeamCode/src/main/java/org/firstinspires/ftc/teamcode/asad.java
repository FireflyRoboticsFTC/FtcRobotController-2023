package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name = "servoTest",group = "OpModes")

public class asad extends OpMode {

    private HardwareHandler HardwareHandler;

    @Override
    public void init() {
        telemetry.addData("Status","Initialized");
        HardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);
    }

    @Override
    public void loop() {

        if (gamepad1.a) {
            HardwareHandler.railLauncher(1);
        }
        if (gamepad1.b) {
            HardwareHandler.railLauncher(0);
        }
    }
}
