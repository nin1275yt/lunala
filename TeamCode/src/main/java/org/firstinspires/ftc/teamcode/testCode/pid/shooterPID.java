package org.firstinspires.ftc.teamcode.testCode.pid;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.controller.PIDController;
import com.seattlesolvers.solverslib.controller.PIDFController;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.config.lunala.Constants;

@Configurable
@TeleOp(name = "shooterPID", group = "test_35707")
public class shooterPID extends LinearOpMode {
    public static double target = 0;
    private final String[] name = {
            "shooterP",
            "shooterN"
    };
    public static boolean pollen = true;
    public static boolean reverse = true;
    @Override
    public void runOpMode() {
        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, pollen ? name[0] : name[1]);
        motor.setDirection(reverse ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // PIDController controller = new PIDController(pollen ? Constants.POLLEN.pid : Constants.NECTAR.pid);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            // controller.setCoefficients(pollen ? Constants.POLLEN.pid : Constants.NECTAR.pid);
            // motor.setVelocity(controller.calculate(target, motor.getVelocity()));
            if (pollen) motor.setVelocityPIDFCoefficients(Constants.POLLEN.pid.p, Constants.POLLEN.pid.i, Constants.POLLEN.pid.d, Constants.POLLEN.pid.f);
            if (!pollen) motor.setVelocityPIDFCoefficients(Constants.NECTAR.pid.p, Constants.NECTAR.pid.i, Constants.NECTAR.pid.d, Constants.NECTAR.pid.f);
            motor.setVelocity(target);
            // telemetry.addData("PID", "P: " + controller.getP() + " I: " + controller.getI() + " D: " + controller.getD());
            telemetry.addData("PIDF", pollen ? Constants.POLLEN.pid : Constants.NECTAR.pid);
            telemetry.addData("target", target);
            telemetry.addData("velo", motor.getVelocity());
            telemetry.addData("error", Math.abs(target-motor.getVelocity()));
            telemetry.addData("current", motor.getCurrent(CurrentUnit.MILLIAMPS));
            telemetry.update();
        }
    }
}
