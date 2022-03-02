// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.DrivebaseConstants;

import com.ctre.phoenix.sensors.CANCoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DrivebaseSubsystem extends SubsystemBase {

	/* Left Motors */
	private final CANSparkMax leftFrontSparkMax;
	private final CANSparkMax leftBackSparkMax;
	private final MotorControllerGroup leftMotors;

	/* Right Motors */
	private final CANSparkMax rightFrontSparkMax;
	private final CANSparkMax rightBackSparkMax;
	private final MotorControllerGroup rightMotors;

	/* Encoders */
	private final CANCoder leftCanCoder;
	private final CANCoder rightCanCoder;
	

	DifferentialDrive drive;

	public DrivebaseSubsystem() {

		/* Left Motors */
		leftFrontSparkMax = new CANSparkMax(DrivebaseConstants.LEFT_FRONT_SPARK_ID, MotorType.kBrushed); // CAN ID 2
		leftBackSparkMax = new CANSparkMax(DrivebaseConstants.LEFT_BACK_SPARK_ID, MotorType.kBrushed);   // CAN ID 3

		leftFrontSparkMax.setIdleMode(IdleMode.kBrake);
		leftBackSparkMax.setIdleMode(IdleMode.kBrake);

		leftFrontSparkMax.setInverted(DrivebaseConstants.LEFT_FRONT_SPARK_INVERTED);
		leftBackSparkMax.setInverted(DrivebaseConstants.LEFT_BACK_SPARK_INVERTED);

		leftMotors = new MotorControllerGroup(leftFrontSparkMax, leftBackSparkMax);

		/* Right Motors */
		rightFrontSparkMax = new CANSparkMax(DrivebaseConstants.RIGHT_FRONT_SPARK_ID, MotorType.kBrushed);// CAN ID 5
		rightBackSparkMax = new CANSparkMax(DrivebaseConstants.RIGHT_BACK_SPARK_ID, MotorType.kBrushed);// CAN ID 4

		rightFrontSparkMax.setIdleMode(IdleMode.kBrake);
		rightBackSparkMax.setIdleMode(IdleMode.kBrake);

		rightFrontSparkMax.setInverted(DrivebaseConstants.RIGHT_FRONT_SPARK_INVERTED);
		rightBackSparkMax.setInverted(DrivebaseConstants.RIGHT_BACK_SPARK_INVERTED);

		rightMotors = new MotorControllerGroup(rightFrontSparkMax, rightBackSparkMax);

		/* All Together */

		this.drive = new DifferentialDrive(leftMotors, rightMotors);

		/* Encdoers */
		
		leftCanCoder = new CANCoder(DrivebaseConstants.LEFT_CANCODER_ID);   // CAN ID 1
		rightCanCoder = new CANCoder(DrivebaseConstants.RIGHT_CANCODER_ID); // CAN ID 6
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	public void drive(double leftSpeed, double rightSpeed) {
		this.drive.tankDrive(leftSpeed, rightSpeed);
	}

	public void drive(double leftSpeed, double rightSpeed, boolean squareInputs) {
		this.drive.tankDrive(leftSpeed, rightSpeed, squareInputs);
	}

	public void resetEncoders() {
		leftCanCoder.setPosition(0.0);
		rightCanCoder.setPosition(0.0);
		
	}

	public int getLeftEncoder() {
		return (int) leftCanCoder.getPosition();
	}

	public int getRightEncoder() {
		return (int) -rightCanCoder.getPosition();
	}

	/**
	 * Converts encoder units to inches
	 * 
	 * @return
	 */
	private static double encoderUnitsToInches(double encoderUnits) {
		return encoderUnits / DrivebaseConstants.ENCODER_DISNATCE_PER_PULSE;
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
	}
}
