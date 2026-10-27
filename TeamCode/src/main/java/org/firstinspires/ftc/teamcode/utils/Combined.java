package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.configuration.typecontainers.MotorConfigurationType;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Combined {
    public static class CombinedCRServo {
        private final List<CRServo> servos = new ArrayList<>();

        public CombinedCRServo(CRServo... servo) {
            this.servos.addAll(Arrays.asList(servo));
        }

        public ServoController getController(int index) {
            return servos.get(index).getController();
        }

        public int getPortNumber(int index) {
            return servos.get(index).getPortNumber();
        }

        public void setDirection(DcMotor.Direction direction, int index) {
            servos.get(index).setDirection(direction);
        }

        public DcMotor.Direction getDirection(int index) {
            return servos.get(index).getDirection();
        }

        public void setPower(double power) {
            for (CRServo servo : servos) {
                servo.setPower(power);
            }
        }

        public double getPower() {
            return servos.get(0).getPower();
        }

        public HardwareDevice.Manufacturer getManufacturer(int index) {
            return servos.get(index).getManufacturer();
        }

        public String getDeviceName(int index) {
            return servos.get(index).getDeviceName();
        }

        public String getConnectionInfo(int index) {
            return servos.get(index).getConnectionInfo();
        }

        public int getVersion(int index) {
            return servos.get(index).getVersion();
        }

        public void close(int index) {
            servos.get(index).close();
        }
    }
    public class CombinedDcMotorEx {
        private final List<DcMotorEx> motors = new ArrayList<>();
        public CombinedDcMotorEx(DcMotorEx... motor) {
            this.motors.addAll(Arrays.asList(motor));
        }
        public int getPortNumber(int index) {
            return motors.get(index).getPortNumber();
        }
        public DcMotorController getController(int index) {
            return motors.get(index).getController();
        }
        public void setDirection(DcMotor.Direction direction, int index) {
            motors.get(index).setDirection(direction);
        }
        public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
            for (DcMotorEx motor : motors) {
                motor.setZeroPowerBehavior(zeroPowerBehavior);
            }
        }
        public DcMotor.Direction getDirection(int index) {
            return motors.get(index).getDirection();
        }
        public void setPower(double power) {
            for (DcMotorEx motor : motors) {
                motor.setPower(power);
            }
        }
        public void setMode(DcMotor.RunMode mode) {
            for (DcMotorEx motor : motors) {
                motor.setMode(mode);
            }
        }
        public void setTargetPositionTolerance(int tolerance) {
            for (DcMotorEx motor : motors) {
                motor.setTargetPositionTolerance(tolerance);
            }
        }
        public void setVelocity(double velocity) {
            for (DcMotorEx motor : motors) {
                motor.setVelocity(velocity);
            }
        }
        public void setMotorType(MotorConfigurationType motorType) {
            for (DcMotorEx motor : motors) {
                motor.setMotorType(motorType);
            }
        }
        public void setCurrentAlert(double current, CurrentUnit unit) {
            for (DcMotorEx motor : motors) {
                motor.setCurrentAlert(current, unit);
            }
        }
        public void setVelocity(double velocity, AngleUnit unit) {
            for (DcMotorEx motor : motors) {
                motor.setVelocity(velocity, unit);
            }
        }
        public void setTargetPosition(int position) {
            for (DcMotorEx motor : motors) {
                motor.setTargetPosition(position);
            }
        }
        public void setPIDFCoefficients(DcMotorEx.RunMode mode, PIDFCoefficients pidfCoefficients) throws UnsupportedOperationException {
            for (DcMotorEx motor : motors) {
                motor.setPIDFCoefficients(mode, pidfCoefficients);
            }
        }
        public double getPower() {
            return motors.get(0).getPower();
        }
        public DcMotor.RunMode getMode() {
            return motors.get(0).getMode();
        }
        public int getTargetPosition() {
            return motors.get(0).getTargetPosition();
        }
        public boolean getPowerFloat() {
            return motors.get(0).getPowerFloat();
        }
        public DcMotor.ZeroPowerBehavior getZeroPowerBehavior() {
            return motors.get(0).getZeroPowerBehavior();
        }
        public boolean isOverCurrent() {
            return motors.get(0).isOverCurrent();
        }
        public boolean isOverCurrent(int index) {
            return motors.get(index).isOverCurrent();
        }
        public PIDFCoefficients getPIDFCoefficients(DcMotor.RunMode mode) {
            return motors.get(0).getPIDFCoefficients(mode);
        }
        public double getVelocity() {
            return motors.get(0).getVelocity();
        }
        public double getPower(int index) {
            return motors.get(index).getPower();
        }
        public double getVelocity(int index) {
            return motors.get(index).getVelocity();
        }
        public double getCurrent(CurrentUnit unit) {
            return motors.get(0).getCurrent(unit);
        }
        public double getCurrent(int index, CurrentUnit unit) {
            return motors.get(index).getCurrent(unit);
        }
        public double getCurrentAlert(CurrentUnit unit) {
            return motors.get(0).getCurrentAlert(unit);
        }
        public double getCurrentAlert(int index, CurrentUnit unit) {
            return motors.get(index).getCurrentAlert(unit);
        }
        public int getTargetPositionTolerance() {
            return motors.get(0).getTargetPositionTolerance();
        }
        public MotorConfigurationType getMotorType() {
            return motors.get(0).getMotorType();
        }
        public HardwareDevice.Manufacturer getManufacturer(int index) {
            return motors.get(index).getManufacturer();
        }
        public String getDeviceName(int index) {
            return motors.get(index).getDeviceName();
        }
        public String getConnectionInfo(int index) {
            return motors.get(index).getConnectionInfo();
        }
        public int getVersion(int index) {
            return motors.get(index).getVersion();
        }
        public boolean isBusy() {
            return motors.get(0).isBusy();
        }
        public void close(int index) {
            motors.get(index).close();
        }
        public int getCurrentPosition(int index) {
            return motors.get(index).getCurrentPosition();
        }
        public void setMotorEnable(int index) {
            motors.get(index).setMotorEnable();
        }
        public void setMotorDisable(int index) {
            motors.get(index).setMotorDisable();
        }
        public boolean isMotorEnabled(int index) {
            return motors.get(index).isMotorEnabled();
        }
        public int getMotorsSize() {
            return motors.size();
        }
    }
    public static class CombinedServo {
        private final List<Servo> servos = new ArrayList<>();

        public CombinedServo(Servo... servo) {
            this.servos.addAll(Arrays.asList(servo));
        }

        public ServoController getController(int index) {
            return servos.get(index).getController();
        }

        public int getPortNumber(int index) {
            return servos.get(index).getPortNumber();
        }

        public void setDirection(Servo.Direction direction, int index) {
            servos.get(index).setDirection(direction);
        }

        public Servo.Direction getDirection(int index) {
            return servos.get(index).getDirection();
        }

        public void setPosition(double position) {
            for (Servo servo : servos) {
                servo.setPosition(position);
            }
        }

        public void scaleRange(double min, double max) {
            for (Servo servo : servos) {
                servo.scaleRange(min, max);
            }
        }

        public double getPosition() {
            return servos.get(0).getPosition();
        }

        public HardwareDevice.Manufacturer getManufacturer(int index) {
            return servos.get(index).getManufacturer();
        }

        public String getDeviceName(int index) {
            return servos.get(index).getDeviceName();
        }

        public String getConnectionInfo(int index) {
            return servos.get(index).getConnectionInfo();
        }

        public int getVersion(int index) {
            return servos.get(index).getVersion();
        }

        public void close(int index) {
            servos.get(index).close();
        }
    }
    public static class CombinedGamepad {

        public enum MergeMode {
            PRIORITY_FIRST,   // gamepad[0] overrides others
            AVERAGE_STICKS    // sticks averaged, buttons OR'd
        }

        private final List<Gamepad> gamepads = new ArrayList<>();
        private final MergeMode mode;

        public CombinedGamepad(MergeMode mode, Gamepad... gamepads) {
            this.mode = mode;
            this.gamepads.addAll(Arrays.asList(gamepads));
        }

        public CombinedGamepad(Gamepad... gamepads) {
            this(MergeMode.PRIORITY_FIRST, gamepads);
        }

        /* -------------------------
         * STICKS
         * ------------------------- */

        public double leftStickX() {
            if (mode == MergeMode.AVERAGE_STICKS) {
                return avg(g -> g.left_stick_x);
            }
            return firstNonZero(g -> g.left_stick_x);
        }

        public double leftStickY() {
            if (mode == MergeMode.AVERAGE_STICKS) {
                return avg(g -> g.left_stick_y);
            }
            return firstNonZero(g -> g.left_stick_y);
        }

        public double rightStickX() {
            if (mode == MergeMode.AVERAGE_STICKS) {
                return avg(g -> g.right_stick_x);
            }
            return firstNonZero(g -> g.right_stick_x);
        }

        public double rightStickY() {
            if (mode == MergeMode.AVERAGE_STICKS) {
                return avg(g -> g.right_stick_y);
            }
            return firstNonZero(g -> g.right_stick_y);
        }

        /* -------------------------
         * TRIGGERS
         * ------------------------- */

        public double leftTrigger() {
            return max(g -> g.left_trigger);
        }

        public double rightTrigger() {
            return max(g -> g.right_trigger);
        }

        /* -------------------------
         * BUTTONS (OR logic)
         * ------------------------- */

        public boolean a() {
            return any(g -> g.a);
        }

        public boolean b() {
            return any(g -> g.b);
        }

        public boolean x() {
            return any(g -> g.x);
        }

        public boolean y() {
            return any(g -> g.y);
        }

        public boolean leftBumper() {
            return any(g -> g.left_bumper);
        }

        public boolean rightBumper() {
            return any(g -> g.right_bumper);
        }

        public boolean dpadUp() {
            return any(g -> g.dpad_up);
        }

        public boolean dpadDown() {
            return any(g -> g.dpad_down);
        }

        public boolean dpadLeft() {
            return any(g -> g.dpad_left);
        }

        public boolean dpadRight() {
            return any(g -> g.dpad_right);
        }

        public boolean start() {
            return any(g -> g.start);
        }

        public boolean back() {
            return any(g -> g.back);
        }

        public boolean leftStickButton() {
            return any(g -> g.left_stick_button);
        }

        public boolean rightStickButton() {
            return any(g -> g.right_stick_button);
        }

        /* -------------------------
         * HELPERS
         * ------------------------- */

        private boolean any(Condition c) {
            for (Gamepad g : gamepads) {
                if (c.test(g)) return true;
            }
            return false;
        }

        private double avg(Value v) {
            double sum = 0;
            int count = 0;

            for (Gamepad g : gamepads) {
                sum += v.get(g);
                count++;
            }

            return count == 0 ? 0 : sum / count;
        }

        private double max(Value v) {
            double max = 0;
            for (Gamepad g : gamepads) {
                max = Math.max(max, v.get(g));
            }
            return max;
        }

        private double firstNonZero(Value v) {
            for (Gamepad g : gamepads) {
                double val = v.get(g);
                if (Math.abs(val) > 0.05) return val;
            }
            return 0;
        }

        /* -------------------------
         * FUNCTIONAL INTERFACES
         * ------------------------- */

        private interface Condition {
            boolean test(Gamepad g);
        }

        private interface Value {
            double get(Gamepad g);
        }
    }
}
