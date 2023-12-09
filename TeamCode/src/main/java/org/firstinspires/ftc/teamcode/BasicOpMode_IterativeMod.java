package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name = "TestingWireless",group = "OpModes")

public class BasicOpMode_IterativeMod extends OpMode {

    private HardwareHandler HardwareHandler;

    private boolean toggleSlow;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        HardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);
        toggleSlow = false;
    }

    @Override
    public void loop() {
        double c = 0.65;
        double f = gamepad1.left_stick_y;
        double r = gamepad1.right_stick_x * 0.5 / 0.65;
        double s = gamepad1.left_stick_x;
        double speed = Math.max(Math.max(f * f, r * r), s * s) * c;

        if (gamepad1.x) {
            toggleSlow = HardwareHandler.toggleSlow();
        }

        if (toggleSlow) {
            speed *= 0.25;
        }

        HardwareHandler.moveWithPower(f, r, s, speed);

        boolean a = gamepad2.dpad_up;
        boolean b = gamepad2.dpad_down;
        HardwareHandler.toggleSlide(a,1.0);
        HardwareHandler.toggleSlide(b,-0.75);

//        double p = -gamepad2.left_stick_y;
//        HardwareHandler.moveConveyorBelt(p);

        if (gamepad1.b) {
            HardwareHandler.dispenseOutput();
        }

        if (gamepad2.a) {
            HardwareHandler.intakeAndTransfers(1);
        }

        if (gamepad2.b) {
            HardwareHandler.intakeAndTransfers(-1);
        }

        if(!gamepad2.b && !gamepad2.a) {
            HardwareHandler.intakeAndTransfers(0);
        }

        if (gamepad2.x) {
            HardwareHandler.doorRelease(0.4);
        }

        if(gamepad2.left_bumper) {
            HardwareHandler.doorRelease(0.5);
        }

        if (gamepad2.y) {
            HardwareHandler.railLauncher(0.4);
        }

        if (gamepad2.right_bumper) {
            HardwareHandler.railLauncher(1);
        }
    }
}
