package org.firstinspires.ftc.teamcode.config.lunala;

import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.util.InterpLUT;

public class Constants {
    public static class LED {
        public static double INIT = 1;
        public static double FULL = 0.72;
        public static double ERROR = 0.279;
        public static double REVVING = 0.388;
        public static double READY = 1;
        public static double IDLE = 0.612;
    }

    public static class POLLEN {
        public static PIDFCoefficients pid = new PIDFCoefficients(
                0.0001,
                0,
                0,
                0.0001
        );
        public static double distance_offset = 0;
        public static double shooter_offset = 0;
        public static InterpLUT generateLUT() {
            InterpLUT lut = new InterpLUT();
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.createLUT();
            return lut;
        }
    }

    public static class NECTAR {
        public static PIDFCoefficients pid = new PIDFCoefficients(
                0.0001,
                0,
                0,
                0.0001
        );
        public static double distance_offset = 0;
        public static double shooter_offset = 0;
        public static InterpLUT generateLUT() {
            InterpLUT lut = new InterpLUT();
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.add(0000, 0.0);
            lut.createLUT();
            return lut;
        }
    }

}
