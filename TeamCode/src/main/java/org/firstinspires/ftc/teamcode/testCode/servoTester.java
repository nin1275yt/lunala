package org.firstinspires.ftc.teamcode.testCode;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
@TeleOp(name = "servoTester", group = "test_35707")
public class servoTester extends LinearOpMode {
    public static double pos = 0;
    public static String name = "hoodP";
    @Override
    public void runOpMode() {
        Servo servo = hardwareMap.get(Servo.class, name);
        waitForStart();
        if (isStopRequested()) return;
        while (opModeIsActive()) {
            servo.setPosition(pos);
        }
    }
}
