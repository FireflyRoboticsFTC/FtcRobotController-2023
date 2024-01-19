package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;


@Autonomous(name="detector", group="Auto")
public class detect extends LinearOpMode {

    private colorCam propDetection;

    private VisionPortal visionPortal;

    @Override
    public void runOpMode() throws InterruptedException {

        propDetection = new colorCam(telemetry);

        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "webcam"), propDetection);
        visionPortal.setProcessorEnabled((VisionProcessor) propDetection, true);

        waitForStart();

        switch (propDetection.getLocation()) {
            case LEFT:
                // ...
                break;
            case RIGHT:
                // ...
                break;
            case MIDDLE:
                break;
            case NOT_FOUND:
                // ...
                break;
        }
    }
}