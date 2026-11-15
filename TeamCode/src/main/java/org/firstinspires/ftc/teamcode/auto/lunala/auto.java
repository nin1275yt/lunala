package org.firstinspires.ftc.teamcode.auto.lunala;

import com.pedropathing.api.Paths;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.pedropathing.paths.interpolator.Interpolator;

@Autonomous(name = "AutoPath", group = "Autonomous")
public class auto extends LinearOpMode {

    private Follower follower;

    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose startPos = poseFactory.of(54.3, 9.1, 90);
    private final Pose flower1 = poseFactory.of(19, 47, 180);
    private final Pose flower1Control1 = poseFactory.of(34.2038, 49.7, 0);
    private final Pose flower1Segment1Heading = poseFactory.of(19, 47, 90);
    private final Pose flower1Segment2Heading = poseFactory.of(19, 47, 180);
    private final Pose flower2 = poseFactory.of(47, 118, 90);
    private final Pose flower2Control1 = poseFactory.of(36.8, 41.3025, 0);
    private final Pose flower2Control2 = poseFactory.of(7.4014, 116.3, 0);
    private final Pose flower2Control3 = poseFactory.of(50.5, 91, 0);
    private final Pose flower2Segment1Heading = poseFactory.of(47, 118, 180);
    private final Pose flower2Segment2Heading = poseFactory.of(47, 118, 90);
    private final Pose parkStart = poseFactory.of(47, 118, 90);
    private final Pose park = poseFactory.of(13.8, 110, 270);
    private final Pose parkControl1 = poseFactory.of(51.5187, 92.3243, 0);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, flower1()),
                follow(follower, flower2()),
                follow(follower, park())
        );
    }

    @Override
    public void runOpMode() {
        Scheduler.reset();
        follower = org.firstinspires.ftc.teamcode.pedro.Constants.create(hardwareMap);
        follower.setPose(startPos);
        follower.update();

        waitForStart();
        schedule(autoRoutine());

        while (opModeIsActive()) {
            follower.update();
            Scheduler.execute();

            telemetry.addData("x", follower.pose().x());
            telemetry.addData("y", follower.pose().y());
            telemetry.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetry.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetry.addData("Path number", follower.pathIndex());
            }

            telemetry.update();
        }
    }

    public Path flower1() {
        return Paths.curve(startPos, flower1Control1, flower1).heading(Interpolator.piecewise().until(0.1827, Interpolator.constant(flower1Segment1Heading)).until(1, Interpolator.constant(flower1Segment2Heading)));
    }

    public Path flower2() {
        return Paths.curve(flower1, flower2Control1, flower2Control2, flower2Control3, flower2).heading(Interpolator.piecewise().until(0.1429, Interpolator.constant(flower2Segment1Heading)).until(1, Interpolator.constant(flower2Segment2Heading)));
    }

    public Path park() {
        return Paths.curve(parkStart, parkControl1, park).linear(parkStart, park);
    }
}
