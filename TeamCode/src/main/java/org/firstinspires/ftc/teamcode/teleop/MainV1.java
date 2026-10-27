package org.firstinspires.ftc.teamcode.teleop;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Pose;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.lunala.Constants;
import org.firstinspires.ftc.teamcode.subsystems.LEDSS;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSS;
import org.firstinspires.ftc.teamcode.utils.Combined.CombinedServo;
import org.firstinspires.ftc.teamcode.utils.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.utils.TelemetryM;

@Configurable
@TeleOp(name = "Main V1", group = ".ftc35707")
public class MainV1 extends OpMode {
    // motors
    private DcMotorEx shooterP, shooterN, intake, transfer;
    //servos
    private Servo hoodP, hoodN, stopper, flower, leds;
    private CombinedServo turretP, turretN; // 4x axon mini
    // sensors
    private GoBildaPinpointDriver pinpoint;
    private Follower follower;
    private DigitalChannel bb, c1, c2;
    TelemetryM telemetryM;
    // subsystems
    LEDSS ledss;
    ShooterSS pollenSS;
    ShooterSS nectarSS;


    @Override
    public void init() {
        // motors
        shooterP = hardwareMap.get(DcMotorEx.class, "shooterP");
        shooterN = hardwareMap.get(DcMotorEx.class, "shooterN");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        transfer = hardwareMap.get(DcMotorEx.class, "transfer");
        // servos
        hoodP = hardwareMap.get(Servo.class, "hoodP");
        hoodN = hardwareMap.get(Servo.class, "hoodN");
        stopper = hardwareMap.get(Servo.class, "stopper");
        flower = hardwareMap.get(Servo.class, "flower");
        leds = hardwareMap.get(Servo.class, "leds");
        turretP = new CombinedServo(hardwareMap.get(Servo.class, "turretP1"), hardwareMap.get(Servo.class, "turretP2"));
        turretN = new CombinedServo(hardwareMap.get(Servo.class, "turretN1"), hardwareMap.get(Servo.class, "turretN2"));
        // sensors
        follower = org.firstinspires.ftc.teamcode.pedro.Constants.create(hardwareMap);
        bb = hardwareMap.get(DigitalChannel.class, "bb");
        c1 = hardwareMap.get(DigitalChannel.class, "c1");
        c2 = hardwareMap.get(DigitalChannel.class, "c2");
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        MultipleTelemetry telemetry = new MultipleTelemetry(this.telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        telemetryM = new TelemetryM(telemetry, true);
        // reverse
        shooterN.setDirection(DcMotorSimple.Direction.REVERSE);
        // reset mode
        transfer.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        transfer.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bb.setMode(DigitalChannel.Mode.INPUT);
        pinpoint.recalibrateIMU();
        // subsystems
        ledss = new LEDSS(leds);
        pollenSS = new ShooterSS(follower, shooterP, Constants.POLLEN.pid, hoodP);
        nectarSS = new ShooterSS(follower, shooterN, Constants.NECTAR.pid, hoodN);
        // start pos
        follower.setPose(new Pose(0,0,0));
    }

    @Override
    public void loop() {
        // hardware
        follower.update();
        follower.manual(
                -gamepad1.left_stick_y,  // forward
                gamepad1.left_stick_x,  // lateral
                gamepad1.right_stick_x // turn
        );

        // telemetry
        telemetryM.update();
    }
}
