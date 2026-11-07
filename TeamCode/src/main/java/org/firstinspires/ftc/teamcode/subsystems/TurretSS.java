package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.config.lunala.Constants;
import org.firstinspires.ftc.teamcode.enums.enums;
import org.firstinspires.ftc.teamcode.utils.Combined;

public class TurretSS {
    private Follower follower;
    private final Combined.CombinedServo turret;
    private final DcMotor encoder;
    private enums.turret status;
    private final double offsetX, offsetY;
    private static final double GEAR_RATIO =
            (100.0 / 24.0) * // first stage
            (20.0 / 83.0); // second stage
    private double lastPosition = Double.NaN;
    public TurretSS(Follower follower, Combined.CombinedServo turret, DcMotor encoder, double offsetX, double offsetY) {
        this.follower = follower;
        this.turret = turret;
        this.encoder = encoder;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.init();
    }
    private void init() {
        setPos(0);
        encoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        encoder.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        status = enums.turret.ZERO;
    }
    public void update(Follower follower) {
        this.follower = follower;
        double target = getPos();
        double error = wrapDegrees(target - getAngle());
        // status
        if (Math.abs(error) < 15) {
            status = Math.abs(target) < 1
                    ? enums.turret.ZERO
                    : enums.turret.ALIGNED;
        } else {
            status = enums.turret.TURNING;
        }
    }
    public void align(Pose target) {
        this.setPos(getAngle(target));
    }
    private double getAngle(Pose target) {
        Pose robot = follower.pose();
        double heading = robot.heading();
        double turretX = robot.x() + offsetX * Math.cos(heading) - offsetY * Math.sin(heading);
        double turretY = robot.y() + offsetX * Math.sin(heading) + offsetY * Math.cos(heading);
        double dx = target.x() - turretX;
        double dy = target.y() - turretY;
        double angle = Math.atan2(dy, dx) - heading;
        return Math.toDegrees(Math.atan2(Math.sin(angle), Math.cos(angle)));
    }
    private void setPos(double angle) {
        angle = wrapDegrees(angle);
        double position = 0.5 + angle / (GEAR_RATIO * 360.0);
        position = Math.max(0.0, Math.min(1.0, position));
        double minChange = Constants.TURRETS.MIN_CHANGE_DEG / (GEAR_RATIO * 360.0);
        if (Double.isNaN(lastPosition)
                || Math.abs(position - lastPosition) >= minChange) {
            turret.setPosition(position);
            lastPosition = position;
        }
    }
    private double getPos() {
        double position = turret.getPosition();
        double servoAngle = (position - 0.5) * 360.0;
        return servoAngle * GEAR_RATIO;
    }
    public double getAngle() {
        return ((double) encoder.getCurrentPosition() / 4000) * 360.0 * (20.0 / 83.0);
    }
    private double wrapDegrees(double angle) {
        double min = Constants.TURRETS.WRAP_MIN_DEG;
        double max = Constants.TURRETS.WRAP_MAX_DEG;
        if (max - min != 360.0) return angle; // Limits must span 360 degrees
        while (angle > max) angle -= 360.0;
        while (angle < min) angle += 360.0;
        return angle;
    }
    public enums.turret getStatus() {
        return status;
    }
    public String telemetry() {
        Pose robot = follower.pose();
        double heading = robot.heading();
        int ticks = encoder.getCurrentPosition();
        double actual = (ticks / 4000.0) * 360.0 * (20.0 / 83.0);
        double target = getPos();
        double turretX = robot.x()
                + offsetX * Math.cos(heading)
                - offsetY * Math.sin(heading);
        double turretY = robot.y()
                + offsetX * Math.sin(heading)
                + offsetY * Math.cos(heading);
        double error = wrapDegrees(target - actual);
        return String.format(java.util.Locale.US,
                "===== Turret Telemetry =====\n" +
                        "-- Positions --\n" +
                        "Current angle: %.2f deg\n" +
                        "Commanded angle: %.2f deg\n" +
                        "Error: %.2f deg\n" +
                        "Servo position: %.4f\n" +
                        "Encoder: %d ticks\n" +
                        "-- Values --\n" +
                        "Status: %s\n" +
                        "Gear ratio: %.4f\n" +
                        "Minimum change: %.2f deg\n" +
                        "Alignment tolerance: 15 deg\n" +
                        "Offset X: %.2f\n" +
                        "Offset Y: %.2f\n" +
                        "-- Robot Pose --\n" +
                        "X: %.2f\n" +
                        "Y: %.2f\n" +
                        "Heading: %.2f deg\n" +
                        "-- Turret Field Position --\n" +
                        "X: %.2f\n" +
                        "Y: %.2f\n",
                actual,
                target,
                error,
                turret.getPosition(),
                ticks,
                status,
                GEAR_RATIO,
                Constants.TURRETS.MIN_CHANGE_DEG,
                offsetX,
                offsetY,
                robot.x(),
                robot.y(),
                Math.toDegrees(heading),
                turretX,
                turretY
        );
    }
}
