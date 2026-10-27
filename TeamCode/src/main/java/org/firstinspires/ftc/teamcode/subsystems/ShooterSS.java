package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.enums.enums;

public class ShooterSS {
    private Follower follower;
    private final DcMotorEx motor;
    private final Servo hood;
    private PIDFCoefficients pid;
    private enums.shooter status;
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
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor.setVelocityPIDFCoefficients(pid.p, pid.i, pid.d, pid.f);
        motor.setVelocity(0);
        this.status = enums.shooter.ZERO;
    }
    public void update(Follower follower) {
        this.follower = follower;
    }
    public void align() {
        hood.setPosition(calculateHood());
        motor.setVelocity(calculateVelo());
    }
    private double calculateHood() {
        return 0.0;
    }
    private double calculateVelo() {
        return 0.0;
    }
}
