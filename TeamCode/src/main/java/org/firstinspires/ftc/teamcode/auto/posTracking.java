package org.firstinspires.ftc.teamcode.auto;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedro.Constants;
import org.firstinspires.ftc.teamcode.utils.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.utils.TelemetryM;


@Configurable
@Autonomous(name = "Position tracking odometry", group = "tools_ftc35707")
public class posTracking extends OpMode {
    public static double startPosX = 18;
    public static double startPosY = 119;
    public static double startPosRotation = 144;
    private final Pose startPos = new Pose(startPosX, startPosY, Math.toRadians(startPosRotation)); // start Pos
    private Follower follower;
    static TelemetryM telemetryM;

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);
        follower.setPose(startPos);
        MultipleTelemetry telemetry = new MultipleTelemetry(this.telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        telemetryM = new TelemetryM(telemetry, false);
    }

    @Override
    public void loop() {
        // telemetry for debugging
        telemetryM.addData("x", follower.pose().x());
        telemetryM.addData("y", follower.pose().y());
        telemetryM.addData("heading", Math.toDegrees(follower.pose().heading()));
        telemetryM.update();
        follower.update();
    }
}