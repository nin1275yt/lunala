package org.firstinspires.ftc.teamcode.teleop.lunala;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;
import com.pedropathing.utils.Timer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.Servo;
import com.skeletonarmy.marrow.prompts.OptionPrompt;
import com.skeletonarmy.marrow.prompts.Prompter;
import com.skeletonarmy.marrow.zones.PolygonZone;

import org.firstinspires.ftc.teamcode.config.lunala.Constants;
import org.firstinspires.ftc.teamcode.enums.enums;
import org.firstinspires.ftc.teamcode.subsystems.LEDSS;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSS;
import org.firstinspires.ftc.teamcode.subsystems.TurretSS;
import org.firstinspires.ftc.teamcode.utils.Combined.CombinedServo;
import org.firstinspires.ftc.teamcode.utils.MultipleTelemetry;
import org.firstinspires.ftc.teamcode.utils.TelemetryM;

@Configurable
@TeleOp(name = "Main V1", group = ".ftc35707")
public class MainV1 extends OpMode {
    private DcMotorEx intake, transfer;
    private Servo stopper, flower;
    private Follower follower;
    private DigitalChannel bb, c1, c2;
    TelemetryM telemetryM;
    private final Prompter prompter = new Prompter(this);
    // subsystems
    LEDSS ledss;
    ShooterSS pollenSS;
    ShooterSS nectarSS;
    TurretSS pollenTS;
    TurretSS nectarTS;
    // vars
    private Pose target;
    private enums.ALLIANCE alliance;
    private enums.STARTING_POS startPos;
    private final PolygonZone robotZone = new PolygonZone(18, 18);
    private final Timer loop = new Timer();
    private boolean debug = false;

    @Override
    public void init() {
        if (Constants.AUTO.lastPose == null) prompter.prompt("alliance", new OptionPrompt<>("Select Alliance", enums.ALLIANCE.RED, enums.ALLIANCE.BLUE))
                .prompt("start_pos", new OptionPrompt<>("Starting Position", enums.STARTING_POS.TOP, enums.STARTING_POS.BOTTOM))
                .onComplete(this::onPromptsComplete);
        // motors
        DcMotorEx shooterP = hardwareMap.get(DcMotorEx.class, "shooterP"); // 6000 rpm
        DcMotorEx shooterN = hardwareMap.get(DcMotorEx.class, "shooterN"); // 6000 rpm
        intake = hardwareMap.get(DcMotorEx.class, "intake"); // 2200 rpm
        transfer = hardwareMap.get(DcMotorEx.class, "transfer"); // 2000 rpm
        // servos
        //servos
        Servo hoodP = hardwareMap.get(Servo.class, "hoodP"); // 1x swyft speed
        Servo hoodN = hardwareMap.get(Servo.class, "hoodN"); // 1x swyft speed
        stopper = hardwareMap.get(Servo.class, "stopper"); // 1x swyft speed
        flower = hardwareMap.get(Servo.class, "flower"); // 1x axon max
        Servo leds = hardwareMap.get(Servo.class, "leds"); // 2x gobilda leds
        CombinedServo turretP = new CombinedServo(hardwareMap.get(Servo.class, "turretP1"), hardwareMap.get(Servo.class, "turretP2")); // 2x axon mini
        CombinedServo turretN = new CombinedServo(hardwareMap.get(Servo.class, "turretN1"), hardwareMap.get(Servo.class, "turretN2")); // 2x axon mini
        // sensors
        follower = org.firstinspires.ftc.teamcode.pedro.Constants.create(hardwareMap);
        bb = hardwareMap.get(DigitalChannel.class, "bb");
        c1 = hardwareMap.get(DigitalChannel.class, "c1");
        c2 = hardwareMap.get(DigitalChannel.class, "c2");
        // sensors
        GoBildaPinpointDriver pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        MultipleTelemetry telemetry = new MultipleTelemetry(this.telemetry, PanelsTelemetry.INSTANCE.getFtcTelemetry());
        telemetryM = new TelemetryM(telemetry, debug);
        // reverse
        shooterP.setDirection(DcMotorSimple.Direction.REVERSE);
        // reset mode
        transfer.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        transfer.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        intake.setMode(DcMotorEx.RunMode.RUN_WITHOUT_ENCODER);
        bb.setMode(DigitalChannel.Mode.INPUT);
        pinpoint.recalibrateIMU();
        // limits
        hoodP.scaleRange(Constants.POLLEN.hoodRange[0], Constants.POLLEN.hoodRange[1]);
        hoodN.scaleRange(Constants.NECTAR.hoodRange[0], Constants.NECTAR.hoodRange[1]);
        flower.scaleRange(Constants.FLOWER.range[0], Constants.FLOWER.range[1]);
        // subsystems
        ledss = new LEDSS(leds);
        pollenSS = new ShooterSS(follower, shooterP, Constants.POLLEN.pid, hoodP, -4.58, 0);
        nectarSS = new ShooterSS(follower, shooterN, Constants.NECTAR.pid, hoodN, 3.53, 0);
        pollenTS = new TurretSS(follower, turretP, hardwareMap.get(DcMotor.class, "leftRear"), -4.58, 0);
        nectarTS = new TurretSS(follower, turretN, hardwareMap.get(DcMotor.class, "rightRear"), 3.53, 0);
        // init
        pollenSS.setIdle(Constants.POLLEN.idle);
        nectarSS.setIdle(Constants.NECTAR.idle);
        flower.setPosition(Constants.FLOWER.INIT);
        ledss.setColor(Constants.LED.INIT);
        pollenSS.setLUT(Constants.POLLEN.shooterLUT(), Constants.POLLEN.hoodLUT());
        nectarSS.setLUT(Constants.NECTAR.shooterLUT(), Constants.NECTAR.hoodLUT());
    }
    public void onPromptsComplete() {
        alliance = prompter.get("alliance");
        startPos = prompter.get("start_pos");
        if (startPos == enums.STARTING_POS.TOP) {
            if (alliance == enums.ALLIANCE.RED) follower.setPose(Constants.POSES.startPoses[0]);
            if (alliance == enums.ALLIANCE.BLUE) follower.setPose(Constants.POSES.startPoses[1]);
        }
        if (startPos == enums.STARTING_POS.BOTTOM) {
            if (alliance == enums.ALLIANCE.RED) follower.setPose(Constants.POSES.startPoses[2]);
            if (alliance == enums.ALLIANCE.BLUE) follower.setPose(Constants.POSES.startPoses[3]);
        }
        telemetryM.addLine("LUNALA Team 35707");
        telemetryM.addLine(true, "------DEBUG------");
        telemetryM.addLine(true, "INIT DONE!");
        telemetryM.addData(true, "Alliance", alliance);
        telemetryM.addData(true, "Starting pos", startPos);
        telemetryM.addLine(true, "------DEBUG------");
        telemetryM.update();
    }

    @Override
    public void init_loop() {
        prompter.run();
        if (gamepad1.optionsWasPressed()) telemetryM.setDebug(debug = !debug);
    }

    @Override
    public void start() {
        flower.setPosition(Constants.FLOWER.IDLE);
        loop.reset();
    }

    @Override
    public void loop() {
        // controls
        boolean INTAKE = gamepad1.left_trigger > 0.2;
        boolean OUTTAKE = gamepad1.right_trigger > 0.2;
        boolean FEED = gamepad1.right_bumper;
        boolean ALIGN = gamepad1.left_bumper;
        boolean FLOWER = gamepad1.aWasPressed();
        boolean ready =
                pollenSS.getStatus() == enums.shooter.READY &&
                        nectarSS.getStatus() == enums.shooter.READY &&
                        pollenTS.getStatus() == enums.turret.ALIGNED &&
                        nectarTS.getStatus() == enums.turret.ALIGNED;
        boolean full = false;
        // updates
        follower.update();
        pollenSS.update(follower);
        pollenTS.update(follower);
        nectarSS.update(follower);
        nectarTS.update(follower);
        robotZone.setPosition(follower.pose().x(), follower.pose().y());
        robotZone.setRotation(follower.pose().heading());
        if (robotZone.isInside(Constants.POSES.BOTTOM_ZONE)) target = alliance == enums.ALLIANCE.RED ? Constants.POSES.targets[2] : Constants.POSES.targets[3];
        else target = alliance == enums.ALLIANCE.RED ? Constants.POSES.targets[0] : Constants.POSES.targets[1];
        if (gamepad1.optionsWasPressed()) telemetryM.setDebug(debug = !debug);
        // drive
        ManualDrive.driveOrHold(
                follower,
                -gamepad1.left_stick_y,  // forward
                gamepad1.left_stick_x,  // lateral
                gamepad1.right_stick_x // turn
        );
        // commands
        if (FLOWER) {
            if (flower.getPosition() == Constants.FLOWER.IDLE) flower.setPosition(Constants.FLOWER.DOWN);
            else flower.setPosition(Constants.FLOWER.IDLE);
        }
        // if turret or shooter unaligned then do not shoot
        stopper.setPosition(FEED && ready
                ? Constants.STOPPER.OPEN
                : Constants.STOPPER.CLOSE);
        if ((INTAKE && !full) || (FEED && ready)) {
            intake.setPower(1);
            transfer.setPower(1);
        } else if (OUTTAKE) {
            intake.setPower(-1);
            transfer.setPower(-1);
        } else {
            intake.setPower(0);
            transfer.setPower(0);
        }
        // align
        if (ALIGN) {
            pollenSS.align(target);
            nectarSS.align(target);
            pollenTS.align(target);
            nectarTS.align(target);
        }
        // leds
        if (pollenSS.getStatus().equals(enums.shooter.ERROR) || nectarSS.getStatus().equals(enums.shooter.ERROR)) ledss.setColor(Constants.LED.ERROR);
        else if (pollenSS.getStatus().equals(enums.shooter.REVVING) || nectarSS.getStatus().equals(enums.shooter.REVVING) || pollenTS.getStatus().equals(enums.turret.TURNING) || nectarTS.getStatus().equals(enums.turret.TURNING)) ledss.setColor(Constants.LED.REVVING);
        else if (ready) ledss.setColor(Constants.LED.READY);
        else if (full) ledss.setColor(Constants.LED.FULL);
        else ledss.setColor(Constants.LED.IDLE);
        // telemetry
        telemetryM.addLine("LUNALA Team 35707");
        telemetryM.addLine(true, "------DEBUG------");
        telemetryM.addData(true, "Loop Time", loop.milliseconds());
        telemetryM.addLine(true, pollenSS.telemetry());
        telemetryM.addLine(true, nectarSS.telemetry());
        telemetryM.addLine(true, pollenTS.telemetry());
        telemetryM.addLine(true, nectarTS.telemetry());
        telemetryM.addLine(true, "------DEBUG------");
        telemetryM.update();
        loop.reset();
    }
}
