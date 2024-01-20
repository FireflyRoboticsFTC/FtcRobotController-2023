package org.firstinspires.ftc.teamcode;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

//this code just makes rectangles around the three critical points than checks if the color concentraion is greatest
// that dictates where the cone is
public class colorCam implements VisionProcessor {
    Telemetry telemetry;
    Mat mat = new Mat();
    Mat right= new Mat();
    Mat middle = new Mat();
    Mat left = new Mat();
    public enum Location {
        LEFT,
        RIGHT,
        MIDDLE,
        NOT_FOUND
    }

    public colorCam(Telemetry t) { telemetry = t; }

    Scalar lowHSV;
    Scalar highHSV;
    int width = 640;
    int height = 480;

    private Location location;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // just makes sure these rectangles corespond with the lines where the beacon will be placed

    /*Rect LEFT_ROI = new Rect(0, 0, 213, 320);

    Rect MIDDLE_ROI = new Rect(213, 0, 214, 320);

    Rect RIGHT_ROI = new Rect(427, 0, 213, 320);*/

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    static double PERCENT_COLOR_THRESHOLD = 0.6;


    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Not useful in this case, but we do need to implement it either way
        width = 640;
        height = 480;
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        Rect LEFT_ROI = new Rect(0, 0, width/4, height/3*2);

        Rect MIDDLE_ROI = new Rect(width/4, 0, width*5/12, height/3*2);

        Rect RIGHT_ROI = new Rect((width/3)*2, 0, width/3, height/3*2);

        //if the whole HSV stuff is too hard then just use RGB colors and comment all lines from 44 and use lowHSV and highHSV as rgb values
        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2HSV); //

        //this is where we input color we can do that tommorow
        //if you want to do it now just search up HSV values for red ranges

        //red
        Scalar lowHSV = new Scalar(120, 120, 120);
        Scalar highHSV = new Scalar(178, 250, 250);

        //blue
        //Scalar lowHSV = new Scalar(100, 30, 110);
        //Scalar highHSV = new Scalar(111, 85, 250);

        //H the number of color
        //S the amoutnt of white
        //V the amount of dark
        //blue HSV: H180.7 S32 V99

        Core.inRange(frame, lowHSV, highHSV, frame);

        left = frame.submat(LEFT_ROI);
        right = frame.submat(RIGHT_ROI);
        middle = frame.submat(MIDDLE_ROI);

        double leftValue = Core.mean(left).val[0];
        double middleValue = Core.mean(middle).val[0];
        double rightValue = Core.mean(right).val[0];


        telemetry.addData("Left raw value", (int) Core.sumElems(left).val[0]);
        telemetry.addData("Middle raw value", (int) Core.sumElems(middle).val[0]);
        telemetry.addData("Right raw value", (int) Core.sumElems(right).val[0]);
        telemetry.addData("Left percentage", Math.round(leftValue * 100) + "%");
        telemetry.addData("Middle percentage", Math.round(middleValue * 100) + "%");
        telemetry.addData("Right percentage", Math.round(rightValue * 100) + "%");

        boolean beaconLeft = leftValue > PERCENT_COLOR_THRESHOLD;
        boolean beaconRight = rightValue > PERCENT_COLOR_THRESHOLD;
        boolean beaconMiddle = middleValue > PERCENT_COLOR_THRESHOLD;

        if (beaconLeft) {
            location = Location.LEFT;
            telemetry.addData("Beacon Location", "Left");
        }
        else if (beaconRight) {
            location = Location.RIGHT;
            telemetry.addData("Beacon Location", "Right");
        }
        else if(beaconMiddle)
        {
            location = Location.MIDDLE;
            telemetry.addData("Beacon Location", "Middle");
        }
        else{
            location = Location.NOT_FOUND;
            telemetry.addData("Beacon Location", "Not Found");
        }
        telemetry.update();

        // Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar color1 = new Scalar(255, 0, 0); //LEFT is red
        Scalar color2 = new Scalar(0, 255, 0); //RIGHT is green
        Scalar color3 = new Scalar(0,0,255); //MIDDLE is blue

        Scalar correct = new Scalar(255,255,0);

        Imgproc.rectangle(frame, LEFT_ROI, location == Location.LEFT? correct:color1);
        Imgproc.rectangle(frame, RIGHT_ROI, location == Location.RIGHT? correct:color2);
        Imgproc.rectangle(frame, MIDDLE_ROI, location == Location.MIDDLE? correct:color3);

        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }


    public Location getLocation() {
        return location;
    }
}