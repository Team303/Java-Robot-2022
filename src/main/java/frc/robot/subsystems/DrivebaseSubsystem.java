// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.DrivebaseConstants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class DrivebaseSubsystem extends SubsystemBase {

	public final WPI_TalonSRX leftMotor;
	public final WPI_TalonSRX rightMotor;

	DifferentialDrive drive;

	public DrivebaseSubsystem() {
		leftMotor = new WPI_TalonSRX(DrivebaseConstants.LEFT_MOTOR_ID);
		rightMotor = new WPI_TalonSRX(DrivebaseConstants.RIGHT_MOTOR_ID);

		leftMotor.setNeutralMode(NeutralMode.Brake);
		rightMotor.setNeutralMode(NeutralMode.Brake);

		leftMotor.setInverted(DrivebaseConstants.LEFT_MOTOR_INVERTED);
		rightMotor.setInverted(DrivebaseConstants.RIGHT_MOTOR_INVERTED);

		this.drive = new DifferentialDrive(leftMotor, rightMotor);
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
		leftMotor.setSelectedSensorPosition(0, 0, 1000);
		rightMotor.setSelectedSensorPosition(0, 0, 1000);
	}

	public int getLeftEncoder() {
		return (int) leftMotor.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return (int) -rightMotor.getSelectedSensorPosition(0);
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
		return (int) -rightMotor.getSelectedSensorPosition(0);
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
