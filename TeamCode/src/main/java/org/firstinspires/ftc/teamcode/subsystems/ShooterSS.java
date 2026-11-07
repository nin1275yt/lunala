package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.lunala.Constants;
import org.firstinspires.ftc.teamcode.enums.enums;

public class ShooterSS {
    private Follower follower;
    private final DcMotorEx motor;
    private final Servo hood;
    private final PIDFCoefficients pid;
    private enums.shooter status;
    private InterpLUT veloLUT, hoodLUT;
    private double distance;
    private final double offsetX, offsetY;
    private long failTime = 0;
    private boolean motorError = false, encoderError = false;
    private double idle = 0;

    public ShooterSS(Follower follower, DcMotorEx motor, PIDFCoefficients pid, Servo hood, double offsetX, double offsetY) {
        this.follower = follower;
        this.motor = motor;
        this.hood = hood;
        this.pid = pid;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        init();
    }
    private void init() {
        this.status = enums.shooter.FLOATING;
        hood.setPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setVelocityPIDFCoefficients(pid.p, pid.i, pid.d, pid.f);
        motor.setVelocity(0);
        this.status = enums.shooter.ZERO;
    }
    public void update(Follower follower) {
        this.follower = follower;
        if (status == enums.shooter.ERROR || veloLUT == null || hoodLUT == null) return;
        // status
        if (Math.abs(motor.getVelocity() - calculateVelo(distance)) < 60)
            status = enums.shooter.READY;
        else if (Math.abs(motor.getVelocity() - 0) < 60)
            status = enums.shooter.ZERO;
        else if (Math.abs(motor.getVelocity() - idle) < 60)
            status = enums.shooter.IDLE;
        else if (status != enums.shooter.REVVING)
            status = motor.getZeroPowerBehavior().equals(DcMotor.ZeroPowerBehavior.BRAKE) ? enums.shooter.BREAK : enums.shooter.FLOATING;
        // idle
        if (status != enums.shooter.REVVING && status != enums.shooter.READY && idle != 0)
            motor.setVelocity(idle);
    }
    public void setLUT(InterpLUT veloLUT, InterpLUT hoodLUT) {
        this.veloLUT = veloLUT;
        this.hoodLUT = hoodLUT;
    }
    public void align(Pose target) {
        distance = getDistance(target);
        // compensation calc
        double velo = calculateVelo(distance);
        double current = motor.getVelocity();
        double error = velo - current;
        double compensation = error * Constants.SHOOTERS.hoodK;
        // hood calc
        double hoodPosition = calculateHood(distance) + compensation;
        hoodPosition = Math.max(0.0, Math.min(1.0, hoodPosition));
        // set pos
        if (isMotorDead()) {
            motor.setVelocity(0);
            hood.setPosition(0);
            status = enums.shooter.ERROR;
        } else {
            hood.setPosition(hoodPosition);
            motor.setVelocity(velo);
            if (status != enums.shooter.READY) status = enums.shooter.REVVING;
        }
    }
    private boolean isMotorDead() {
        double current = motor.getCurrent(CurrentUnit.AMPS);
        boolean stopped = Math.abs(motor.getVelocity()) < 10 && Math.abs(calculateVelo(distance)) > 200;
        if (stopped) {
            if (failTime == 0) failTime = System.currentTimeMillis();
            if (System.currentTimeMillis() - failTime > 500) {
                motorError = current < 0.1;
                encoderError = !motorError;
            }
        } else {
            failTime = 0;
            motorError = encoderError = false;
        }
        return (motorError || encoderError);
    }
    private double calculateHood(double distance) {
        return hoodLUT.get(distance);
    }
    private double calculateVelo(double distance) {
        return veloLUT.get(distance);
    }
    private double getDistance(Pose target) {
        Pose robot = follower.pose();
        double heading = robot.heading();
        double x = robot.x() + offsetX * Math.cos(heading) - offsetY * Math.sin(heading);
        double y = robot.y() + offsetX * Math.sin(heading) + offsetY * Math.cos(heading);
        return Math.hypot(target.x() - x, target.y() - y);
    }
    public void setIdle(double idle) {
        this.idle = idle;
    }
    public enums.shooter getStatus() {
        return status;
    }
    public String telemetry() {
        double currentVelocity = motor.getVelocity();
        double currentAmps = motor.getCurrent(CurrentUnit.AMPS);
        double targetVelocity = Double.NaN;
        double targetHood = Double.NaN;

        if (veloLUT != null && hoodLUT != null) {
            targetVelocity = calculateVelo(distance);
            targetHood = calculateHood(distance);
        }

        return String.format(java.util.Locale.US,
                "===== Shooter Telemetry =====\n" +
                        "-- Status --\n" +
                        "Status: %s\n" +
                        "Motor error: %s\n" +
                        "Encoder error: %s\n" +
                        "-- Flywheel --\n" +
                        "Current velocity: %.1f ticks/sec\n" +
                        "Target velocity: %.1f ticks/sec\n" +
                        "Velocity error: %.1f ticks/sec\n" +
                        "Idle velocity: %.1f ticks/sec\n" +
                        "Motor current: %.2f A\n" +
                        "-- Hood --\n" +
                        "Current servo position: %.4f\n" +
                        "LUT hood position: %.4f\n" +
                        "-- Shot --\n" +
                        "Distance: %.2f\n" +
                        "-- PIDF --\n" +
                        "P: %.4f\n" +
                        "I: %.4f\n" +
                        "D: %.4f\n" +
                        "F: %.4f\n",
                status,
                motorError,
                encoderError,
                currentVelocity,
                targetVelocity,
                targetVelocity - currentVelocity,
                idle,
                currentAmps,
                hood.getPosition(),
                targetHood,
                distance,
                pid.p,
                pid.i,
                pid.d,
                pid.f
        );
    }
}
