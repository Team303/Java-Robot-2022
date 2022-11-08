// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap.DrivebaseConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DrivebaseSubsystem extends SubsystemBase {

	/* ShuffleBoard */
	public static final ShuffleboardTab DRIVEBASE_TAB = Shuffleboard.getTab("Drivebase");

	public static final NetworkTableEntry LEFT_ENCODER_ENTRY = DRIVEBASE_TAB.add("Left Encoder", 0).getEntry();
	public static final NetworkTableEntry RIGHT_ENCODER_ENTRY = DRIVEBASE_TAB.add("Right Encoder", 0).getEntry();
	public static final NetworkTableEntry ENCODER_DISTANCE_ENTRY = DRIVEBASE_TAB.add("Encoder Distance", 0).getEntry();
	public static final NetworkTableEntry NAVX_ANGLE_ENTRY = DRIVEBASE_TAB.add("NavX Angle", 0).getEntry();
	public static final NetworkTableEntry NAVX_RATE_ENTRY = DRIVEBASE_TAB.add("NavX Rate", 0).getEntry();

	/* Left Motors */
	private final CANSparkMax leftFrontSparkMax;
	private final CANSparkMax leftBackSparkMax;
	private final MotorControllerGroup leftMotors;

	/* Right Motors */
	private final CANSparkMax rightFrontSparkMax;
	private final CANSparkMax rightBackSparkMax;
	private final MotorControllerGroup rightMotors;

	/* Encoders */
	private final RelativeEncoder frontLeftEncoder;
	private final RelativeEncoder backLeftEncoder;
	private final RelativeEncoder frontRightEncoder;
	private final RelativeEncoder backRightEncoder;

	private DifferentialDrive drive;

	private double maxOutput = 1;

	public DrivebaseSubsystem() {
		/* Left Motors */
		leftFrontSparkMax = new CANSparkMax(DrivebaseConstants.LEFT_FRONT_SPARK_ID, MotorType.kBrushless);
		leftBackSparkMax = new CANSparkMax(DrivebaseConstants.LEFT_BACK_SPARK_ID, MotorType.kBrushless);

		leftFrontSparkMax.setIdleMode(IdleMode.kBrake);
		leftBackSparkMax.setIdleMode(IdleMode.kBrake);

		leftFrontSparkMax.setInverted(DrivebaseConstants.LEFT_FRONT_SPARK_INVERTED);
		leftBackSparkMax.setInverted(DrivebaseConstants.LEFT_BACK_SPARK_INVERTED);

		leftMotors = new MotorControllerGroup(leftFrontSparkMax, leftBackSparkMax);

		/* Right Motors */
		rightFrontSparkMax = new CANSparkMax(DrivebaseConstants.RIGHT_FRONT_SPARK_ID, MotorType.kBrushless);
		rightBackSparkMax = new CANSparkMax(DrivebaseConstants.RIGHT_BACK_SPARK_ID, MotorType.kBrushless);

		rightFrontSparkMax.setIdleMode(IdleMode.kBrake);
		rightBackSparkMax.setIdleMode(IdleMode.kBrake);

		rightFrontSparkMax.setInverted(DrivebaseConstants.RIGHT_FRONT_SPARK_INVERTED);
		rightBackSparkMax.setInverted(DrivebaseConstants.RIGHT_BACK_SPARK_INVERTED);

		rightMotors = new MotorControllerGroup(rightFrontSparkMax, rightBackSparkMax);

		/* All Together */
		this.drive = new DifferentialDrive(leftMotors, rightMotors);

		/* Encoders */
		frontLeftEncoder = leftFrontSparkMax.getEncoder();
		backLeftEncoder = leftBackSparkMax.getEncoder();
		frontRightEncoder = rightFrontSparkMax.getEncoder();
		backRightEncoder = rightBackSparkMax.getEncoder();
	}

	public void drive(double leftSpeed, double rightSpeed) {
		this.drive.tankDrive(leftSpeed, rightSpeed);
	}

	public void drive(double leftSpeed, double rightSpeed, boolean squareInputs) {
		this.drive.tankDrive(leftSpeed, rightSpeed, squareInputs);
	}

	public void arcadeDrive(double power, double turningPower, boolean squareInputs) {
		this.drive.arcadeDrive(power, turningPower, squareInputs);
	}

	public void arcadeDrive(double power, double turningPower) {
		this.drive.arcadeDrive(power, turningPower);
	}

	public void resetEncoders() {
		frontLeftEncoder.setPosition(0);
		backLeftEncoder.setPosition(0);
		frontRightEncoder.setPosition(0);
		backRightEncoder.setPosition(0);
	}

	public int getLeftEncoder() {
		// Take average of both encoders
		return (int) ((frontLeftEncoder.getPosition() + backLeftEncoder.getPosition()) / 2);
	}

	public int getRightEncoder() {
		// Take average of both encoders
		return (int) ((frontRightEncoder.getPosition() + backRightEncoder.getPosition()) / 2);
	}

	/**
	 * Converts encoder units to inches
	 * 
	 * @return
	 */
	private static double encoderUnitsToInches(double encoderUnits) {
		return encoderUnits * DrivebaseConstants.DISNATCE_PER_ENCODER_PULSE;
	}

	/**
	 * Measures how far the robot should have traveled (in inches)
	 * based on the left motor's measured rotation
	 */
	public double getLeftEncoderDistance() {
		return encoderUnitsToInches(getLeftEncoder());
	}

	/**
	 * Measures how far the robot should have traveled (in inches)
	 * based on the right motor's measured rotation
	 */
	public double getRightEncoderDistance() {
		return encoderUnitsToInches(getRightEncoder());
	}

	/**
	 * Takes the average of both motor encoders to calculate how far
	 * the robot should have traveled (assuming a straight path)
	 */
	public double getAverageEncoderDistance() {
		return (getLeftEncoderDistance() + getRightEncoderDistance()) / 2.0;
	}

	/**
	 * Sets the max output of the drive. Useful for scaling the drive to drive more
	 * slowly.
	 *
	 * @param maxOutput the maximum output to which the drive will be constrained
	 */
	public void setMaxOutput(double maxOutput) {
		drive.setMaxOutput(maxOutput);
		this.maxOutput = maxOutput;
	}

	public double getMaxOutput() {
		return this.maxOutput;
	}

	@Override
	public void periodic() {
		/* Update ShuffleBoard */
		LEFT_ENCODER_ENTRY.setDouble(getLeftEncoder());
		RIGHT_ENCODER_ENTRY.setDouble(getRightEncoder());
		ENCODER_DISTANCE_ENTRY.setDouble(getAverageEncoderDistance());
		NAVX_ANGLE_ENTRY.setDouble(Robot.navX.getAngle());
		NAVX_RATE_ENTRY.setDouble(Robot.navX.getRate());
	}
}
