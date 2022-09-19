// Copyright (c) 2022 Team 303

package frc.robot;

import edu.wpi.first.wpilibj.util.Color;

/*
TODO Change around all the CAN IDs to fit
*/

public final class RobotMap {

	public static final class DrivebaseConstants {

		/* CAN IDs of the Motors on the Drive Base */
		public static final int LEFT_FRONT_SPARK_ID = 2;
		public static final int LEFT_BACK_SPARK_ID = 3;
		public static final int RIGHT_FRONT_SPARK_ID = 5;
		public static final int RIGHT_BACK_SPARK_ID = 4;

		/* Encoder CAN IDs */
		public static final int LEFT_CANCODER_ID = 1;
		public static final int RIGHT_CANCODER_ID = 6;

		/* Drivebase Motor Inversion */
		public static final boolean LEFT_FRONT_SPARK_INVERTED = true;
		public static final boolean LEFT_BACK_SPARK_INVERTED = true;
		public static final boolean RIGHT_FRONT_SPARK_INVERTED = false;
		public static final boolean RIGHT_BACK_SPARK_INVERTED = false;

		/* Motor Encoder Calculations */
		public static final double WHEEL_DIAMTER = 6; // Diameter in inches
		public static final int ENCODER_COUNTS_PER_REV = 42; // Neo Hall Effect Sensor
		public static final double DRIVE_GEAR_RATIO = 12.75; // Toughbox mini 12.75:1
		public static final double DISNATCE_PER_ENCODER_PULSE; // Inches traveled for each encoder unit

		static {
			double wheelCircumference = WHEEL_DIAMTER * Math.PI;
			double motorRotationsPerEncoderPulse = 1 / ENCODER_COUNTS_PER_REV;
			double axelRotationsPerMotorRotation = 1 / DRIVE_GEAR_RATIO;

			DISNATCE_PER_ENCODER_PULSE = motorRotationsPerEncoderPulse
					* axelRotationsPerMotorRotation
					* wheelCircumference;
		}
	}

	public static final class Climber {

		public static final int CLIMB_PORT_ID = 7;
		public static final int BOTTOM_LEFT_LIMIT_SWITCH = 0;
		public static final int BOTTOM_RIGHT_LIMIT_SWITCH = 1;
		public static final int TOP_RIGHT_LIMIT_SWITCH = 8;
		public static final int TOP_LEFT_LIMIT_SWITCH = 9;
		public static final int NEUTRAL_TOGGLE_SWITCH = 4;

		public static final float SOFT_LIMIT = 1000;

		public static final boolean CLIMB_MOTOR_INVERTED = true;
	}

	public static final class Intake {

		public static final int INTAKE_SPARK_ID = 9;
		public static final boolean INTAKE_SPARK_INVERTED = false;
		public static final double INTAKE_SPARK_SPEED = 0.75;

		public static final int EXTEND_SPARK_ID = 13;
		public static final boolean EXTEND_SPARK_INVERTED = false;
		public static final double EXTEND_SPARK_SPEED = 0.25;
	}

	public static final class Shooter {

		public static final int SHOOTER_SPARK_ID = 11;
		public static final int INDEXER_SPARK_ID = 12;

		public static final boolean SHOOTER_SPARK_INVERTED = false;
		public static final boolean INDEXER_SPARK_INVERTED = false;

		public static final double SHOOTER_SETPOINT = 5600;
		public static final double SHOOTER_TOLERANCE = 100;

		public static final double INDEXER_SETPOINT = 17;

		public static final int SHOOTER_CANCODER_ID = 0;
	}

	public static final class Pneumatics {
		// left and right solinoids must be the same id of pnematics hub

		public static final int PNEMATIC_HUB_ID = 8;

		public static final int FORWARD_LEFT_ID = 2;
		public static final int REVERSE_LEFT_ID = 3;

		public static final int FORWARD_RIGHT_ID = 0;
		public static final int REVERSE_RIGHT_ID = 1;
	}

	public static final class IOConstants {

		public static final int LEFT_JOYSTICK_ID = 1;
		public static final int RIGHT_JOYSTICK_ID = 2;
		public static final int OPERATOR_CONTROLLER = 0;
	}

	public static final class LED {

		public static final int LED_ID = 1;
		public static final int BUFFER_LENGTH = 200;

		public static final Color RED = new Color(255 / 255D, 0, 0);
		public static final Color BLUE = new Color(0, 0, 255 / 255D);

		public static final Color FLASH_PRIMARY = new Color(
				255 / 255D,
				0 / 255D,
				0 / 255D);
		public static final Color FLASH_SECONDARY = new Color(
				230 / 255D,
				50 / 255D,
				0 / 255D);

		public static final Color TELEOP_COLOR = new Color(
				0 / 255D,
				255 / 255D,
				0 / 255D);
		public static final Color AUTONOMOUS_COLOR = new Color(
				230 / 255D,
				30 / 255D,
				0 / 255D);
		public static final Color DISABLED_COLOR = new Color(
				0 / 255D,
				90 / 255D,
				20 / 255D);
	}
}
