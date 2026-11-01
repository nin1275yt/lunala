package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.enums.enums;
import org.firstinspires.ftc.teamcode.utils.Combined;

public class TurretSS {
    private Follower follower;
    private Combined.CombinedServo turret;
    private DcMotor encoder;
    private enums.turret status;
    private static final double GEAR_RATIO =
            (100.0 / 24.0) * // first stage
            (20.0 / 83.0); // second stage
    public TurretSS(Follower follower, Combined.CombinedServo turret, DcMotor encoder) {
        this.follower = follower;
        this.turret = turret;
        this.encoder = encoder;
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
    }
    private void setPos(double angle) {
        double servoAngle = angle / GEAR_RATIO;
        double position = 0.5 + (servoAngle / 360);
        position = Math.max(0.0, Math.min(1.0, position));
        turret.setPosition(position);
    }
    private double getPos() {
        double position = turret.getPosition();
        double servoAngle = (position - 0.5) * 360.0;
        return servoAngle * GEAR_RATIO;
    }
}
