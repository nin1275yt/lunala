package org.firstinspires.ftc.teamcode.config.lunala;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.math.Pose;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.seattlesolvers.solverslib.util.InterpLUT;
import com.skeletonarmy.marrow.zones.Point;
import com.skeletonarmy.marrow.zones.PolygonZone;

public class Constants {
    @Configurable
    public static class LED {
        public static double INIT = 1;
        public static double FULL = 0.72;
        public static double ERROR = 0.279;
        public static double REVVING = 0.388;
        public static double READY = 1;
        public static double IDLE = 0.612;
    }

    @Configurable
    public static class POLLEN {
        public static PIDFCoefficients pid = new PIDFCoefficients(
                50,
                0,
                0,
                30
        );
        public static double turret_offset = 0;
        public static double shooter_offset = 0;
        public static InterpLUT shooterLUT() {
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
        public static InterpLUT hoodLUT() {
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
        public static double[] hoodRange = {
                0,
                0.35
        };
        public static double idle = 900;
    }

    @Configurable
    public static class NECTAR {
        public static PIDFCoefficients pid = new PIDFCoefficients(
                50,
                0,
                0,
                30
        );
        public static double turret_offset = 0;
        public static double shooter_offset = 0;
        public static InterpLUT shooterLUT() {
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
        public static InterpLUT hoodLUT() {
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
        public static double[] hoodRange = {
                0,
                0.395
        };
        public static double idle = 900;
    }
    @Configurable
    public static class SHOOTERS {
        public static double tolerence = 60;
        public static double hoodK = 0.0001;
    }
    @Configurable
    public static class FLOWER {
        public static double[] range = {
                0,
                1
        };
        public static double INIT = 0;
        public static double IDLE = 0.5;
        public static double DOWN = 1;
    }
    @Configurable
    public static class STOPPER {
        public static double[] range = {
                0,
                1
        };
        public static double OPEN = 0;
        public static double CLOSE = 1;
    }
    @Configurable
    public static class TELEOP {

    }
    @Configurable
    public static class POSES {
        public static final PolygonZone BOTTOM_ZONE = new PolygonZone(new Point(70.5, 35), 141, 70);
        public static final Pose[] startPoses = {
                new Pose(0,0,Math.toRadians(0)), // top red
                new Pose(0,0,Math.toRadians(0)), // top blue
                new Pose(0,0,Math.toRadians(0)), // bottom red
                new Pose(0,0,Math.toRadians(0)), // bottom blue
        };
        public static final Pose[] targets = {
                new Pose(0,0,Math.toRadians(0)), // top red
                new Pose(0,0,Math.toRadians(0)), // top blue
                new Pose(0,0,Math.toRadians(0)), // bottom red
                new Pose(0,0,Math.toRadians(0)), // bottom blue
        };
    }
    @Configurable
    public static class AUTO {
        public static Pose lastPose = null;
    }
    @Configurable
    public static class TURRETS {
        public static double MIN_CHANGE_DEG = 0.5;
        public static double WRAP_MIN_DEG = -180;
        public static double WRAP_MAX_DEG = 180;
    }
}
