package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name = "TestingWireless",group = "OpModes")

public class BasicOpMode_IterativeMod extends OpMode {

    private HardwareHandler customHardwareHandler;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);

    }

    @Override
    public void loop() {
        double c = 0.65;

        double f = gamepad1.left_stick_y;
        double r = gamepad1.right_stick_x * 0.5 / 0.65;
        double s = gamepad1.left_stick_x;
        double speed = Math.max(Math.max(f * f, r * r), s * s) * c;
        customHardwareHandler.moveWithPower(f, r, s, speed);
        boolean a = gamepad1.x;
        boolean b = gamepad1.y;
        customHardwareHandler.toggleSlide(a,-1.0);
        customHardwareHandler.toggleSlide(b,0.5);
        boolean d = gamepad2.a;
        boolean e = gamepad2.b;

        if (gamepad2.a)
            customHardwareHandler.intakeAndTransfer(1);

        if (gamepad2.b)
            customHardwareHandler.intakeAndTransfer(-1);


        if (gamepad1.a) {
            customHardwareHandler.railLauncher(1);
        }

        if (gamepad1.b) {
            customHardwareHandler.railLauncher(0);
        }



        if (gamepad2.x) {
            customHardwareHandler.doorRelease(0);
    }

        if(gamepad2.y) {
            customHardwareHandler.doorRelease(1);
    }



    }
}
