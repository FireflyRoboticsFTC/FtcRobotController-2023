package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.Range;

public class BasicOpMode_Iterative extends OpMode {

    private CustomHardwareHandler customHardwareHandler;

    @Override
    public void init() {
        telemetry.addData("Status","Initialized");
        customHardwareHandler = new CustomHardwareHandler(hardwareMap);
    }

    @Override
    public void loop() {
        double c = 0.65;
        if (gamepad1.x) c = 0.2;
        double f = gamepad1.left_stick_y;
        double r = gamepad1.right_stick_x * 0.5 / 0.65;
        double s = gamepad1.left_stick_x;
        double speed = Math.max(Math.max(f * f, r * r), s * s) * c;
        customHardwareHandler.moveWithPower(f,r,s,speed);

        if (gamepad1.a) {
            customHardwareHandler.railLauncher(1);
        }

        if (gamepad1.b) {
            customHardwareHandler.railLauncher(0);
        }

    }
}
