package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

//this code just makes rectangles around the three critical points than checks if the color concentraion is greatest
// that dictates where the cone is
public class colorCam extends OpenCvPipeline {
    Telemetry telemetry;
    Mat mat = new Mat();
    public enum Location {
        LEFT,
        RIGHT,
        MIDDLE,
        NOT_FOUND
    }
    private Location location;

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    // just makes sure these rectangles corespond with the lines where the beacon will be placed
    static final Rect LEFT_ROI = new Rect( //Red
            new Point(290, 70),
            new Point(390, 170));
    static final Rect RIGHT_ROI = new Rect( //Green
            new Point(290, 530),
            new Point(390, 630));
    static final Rect MIDDLE_ROI = new Rect( //Blue
            new Point(330, 300),
            new Point(430,400));

    ///////////////////////////////////////////////////////////////////////////////////////////////////

    static double PERCENT_COLOR_THRESHOLD = 0.6;

    public colorCam(Telemetry t) { telemetry = t; }

    @Override
    public Mat processFrame(Mat input) {

        //if the whole HSV stuff is too hard then just use RGB colors and comment all lines from 44 and use lowHSV and highHSV as rgb values
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV); //

        //this is where we input color we can do that tommorow
        //if you want to do it now just search up HSV values for red ranges
        Scalar lowHSV = new Scalar(100, 30, 110);
        Scalar highHSV = new Scalar(111, 85, 250);
        //H the number of color
        //S the amoutnt of white
        //V the amount of dark
        //blue HSV: H180.7 S32 V99

        Core.inRange(mat, lowHSV, highHSV, mat);

        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);
        Mat middle = mat.submat(MIDDLE_ROI);

        double leftValue = Core.sumElems(left).val[0] / LEFT_ROI.area() / 255;
        double middleValue = Core.sumElems(middle).val[0] / MIDDLE_ROI.area() / 255;
        double rightValue = Core.sumElems(right).val[0] / RIGHT_ROI.area() / 255;

        left.release();
        right.release();
        middle.release();

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


        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Scalar color1 = new Scalar(255, 0, 0); //LEFT is red
        Scalar color2 = new Scalar(0, 255, 0); //RIGHT is green
        Scalar color3 = new Scalar(0,0,255); //MIDDLE is blue

        Scalar correct = new Scalar(255,255,0);

        Imgproc.rectangle(mat, LEFT_ROI, location == Location.LEFT? correct:color1);
        Imgproc.rectangle(mat, RIGHT_ROI, location == Location.LEFT? correct:color2);
        Imgproc.rectangle(mat, MIDDLE_ROI, location == Location.LEFT? correct:color3);

        return mat;
    }

    public Location getLocation() {
        return location;
    }
}