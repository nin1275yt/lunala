package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.util.InterpLUT;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.enums.enums;

public class ShooterSS {
    private Follower follower;
    private final DcMotorEx motor;
    private final Servo hood;
    private PIDFCoefficients pid;
    private enums.shooter status;
    private static final double HOOD_K = 0.0001;
    private InterpLUT veloLUT;
    private InterpLUT hoodLUT;
    private double distance;
    private Pose targetPos;
    private double offset = 0;
    private long failTime = 0;
    private boolean motorError = false, encoderError = false;

    public ShooterSS(Follower follower, DcMotorEx motor, PIDFCoefficients pid, Servo hood) {
        this.follower = follower;
        this.motor = motor;
        this.hood = hood;
        this.pid = pid;
        this.status = enums.shooter.FLOATING;
        init();
    }
    private void init() {
        hood.setPosition(0);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setVelocityPIDFCoefficients(pid.p, pid.i, pid.d, pid.f);
        motor.setVelocity(0);
        this.status = enums.shooter.ZERO;
    }
    public void update(Follower follower) {
        this.follower = follower;
        // add status logic here
    }
    public void setLUT(InterpLUT veloLUT, InterpLUT hoodLUT) {
        this.veloLUT = veloLUT;
        this.hoodLUT = hoodLUT;
    }
    public void align() {
        distance = follower.pose().distance(targetPos);
        // compensation calc
        double target = calculateVelo(distance);
        double current = motor.getVelocity();
        double error = target - current;
        double compensation = error * HOOD_K;
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
            motor.setVelocity(target);
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
}
