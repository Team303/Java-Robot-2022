// Copyright (c) 2022 Team 303

package frc.robot;

public final class RobotMap {
    public static final class DrivebaseConstants {
        /* CAN IDs of the Motors on the Drive Base */
        public static final int LEFT_TALON_ID = 2;
        public static final int LEFT_SPARK_ID = 3;
        public static final int RIGHT_TALON_ID = 1;         
        public static final int RIGHT_SPARK_ID = 4;

        /* Drivebase Motor Inversion */
        public static final boolean LEFT_TALON_INVERTED = false;
        public static final boolean LEFT_SPARK_INVERTED = false;
        public static final boolean RIGHT_TALON_INVERTED = true;
        public static final boolean RIGHT_SPARK_INVERTED = true;

        /* Motor Encoder Calculations */
        public static final double WHEEL_DIAMTER = 6; // inches
        public static final int ENCODER_COUNTS_PER_REV = 256;
        public static final double ENCODER_DISNATCE_PER_PULSE = (WHEEL_DIAMTER * Math.PI)
                / ((double) ENCODER_COUNTS_PER_REV / 512);
    }

    public static final class Intake{
        public static final int INTAKE_SPARK_ID = 5;
        public static final boolean INTAKE_SPARK_INVERTED = false;
        public static final double INTAKE_SPARK_SPEED = 0.75;
    }

    public static final class IOConstants {
        public static final int LEFT_JOYSTICK_ID = 0;
        public static final int RIGHT_JOYSTICK_ID = 1;
    }
}
