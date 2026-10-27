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

    }

}
