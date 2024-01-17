package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.Position;

@TeleOp(name = "TestingWireless",group = "OpModes")

public class BasicOpMode_IterativeMod extends OpMode {

    private HardwareHandler customHardwareHandler;
    private boolean doorIsOpen = false;

    private boolean intakeIn = false;

    private boolean intakeOut = false;

    private boolean slowMode = false;

    private boolean gpd2bPrevState = false;

    private boolean gpd1bPrevState = false;

    private boolean aPrevState = false;

    private boolean xPrevState = false;

    Gamepad gamePad1;
    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
        customHardwareHandler = new HardwareHandler(hardwareMap, new Position(), telemetry);



    }

    @Override
    public void loop() {

        boolean gpd2bCurrState = gamepad2.b;

        boolean gpd1bCurrState = gamepad1.b;

        boolean aCurrState = gamepad2.a;

        boolean xCurrState = gamepad1.x;


        double c = 0.65;

        double f = gamepad1.left_stick_y;
        double r = gamepad1.right_stick_x * 0.5 / 0.65;
        double s = gamepad1.left_stick_x;
        double speed = Math.max(Math.max(f * f, r * r), s * s) * c;
        customHardwareHandler.moveWithPower(f, r, s, speed);
        boolean a = gamepad2.dpad_up;
        boolean b = gamepad2.dpad_down;
        customHardwareHandler.toggleSlide(a,1.0);
        customHardwareHandler.toggleSlide(b,-0.5);
        boolean d = gamepad2.a;
        boolean e = gamepad2.b;

        if(xCurrState && !xPrevState) {
            if(!customHardwareHandler.isSlow()) {
                customHardwareHandler.slowMode(0.35);
                //customHardwareHandler.slowMode(1);
            }
            else{
                customHardwareHandler.normalMode();
            }
        }

        if (aCurrState && !aPrevState) {
            if (!intakeIn)
                customHardwareHandler.intakeAndTransfer(1);
            else
                customHardwareHandler.intakeAndTransfer(0);
            intakeIn = !(intakeIn);
        }

        if (gpd2bCurrState && !gpd2bPrevState) {
            if (!intakeOut)
                customHardwareHandler.intakeAndTransfer(-1);
            else
                customHardwareHandler.intakeAndTransfer(0);
            intakeOut = !(intakeOut);

        }



        if (gamepad2.y) {
            double val = customHardwareHandler.getPositionRale();
            telemetry.addData("this is the thing",val);
            //customHardwareHandler.railLauncher(-0.02);

        }

        if (gamepad2.x) {
            customHardwareHandler.railLauncher(0);
        }



        if (gpd1bCurrState && !gpd1bPrevState) {
            if (doorIsOpen)
                customHardwareHandler.doorRelease(0.2);
            else
                customHardwareHandler.doorRelease(0);
        doorIsOpen = !(doorIsOpen);
        }

    gpd2bPrevState = gpd2bCurrState;
    gpd1bPrevState = gpd1bCurrState;
    aPrevState = aCurrState;
    xPrevState = xCurrState;



    }
}
