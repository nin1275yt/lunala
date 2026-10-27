package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.config.lunala.Constants;

public class LEDSS {
    private Servo led;
    public LEDSS(Servo led) {
        this.led = led;
        this.init();
    }
    private void init() {
        led.setPosition(Constants.LEDC.INIT);
    }
    public void setColor(double color) {
        led.setPosition(color);
    }
}
