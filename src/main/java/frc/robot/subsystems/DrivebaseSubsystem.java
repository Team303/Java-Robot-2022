// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.DrivebaseConstants;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class DrivebaseSubsystem extends SubsystemBase {

	/* Left Motors */
	private final WPI_TalonSRX leftTalon;
	private final CANSparkMax leftSparkMax;
	private final MotorControllerGroup leftMotors;

	/* Right Motors */
	private final WPI_TalonSRX rightTalon;
	private final CANSparkMax rightSparkMax;
	private final MotorControllerGroup rightMotors;

	DifferentialDrive drive;

	public DrivebaseSubsystem() {

		/* Left Motors */
		leftTalon = new WPI_TalonSRX(DrivebaseConstants.LEFT_TALON_ID);
		leftSparkMax = new CANSparkMax(DrivebaseConstants.LEFT_SPARK_ID, MotorType.kBrushed);

		leftTalon.setNeutralMode(NeutralMode.Brake);
		leftSparkMax.setIdleMode(IdleMode.kBrake);

		leftTalon.setInverted(DrivebaseConstants.LEFT_TALON_INVERTED);
		leftSparkMax.setInverted(DrivebaseConstants.LEFT_SPARK_INVERTED);

		leftMotors = new MotorControllerGroup(leftTalon, leftSparkMax);

		/* Right Motors */
		rightTalon = new WPI_TalonSRX(DrivebaseConstants.RIGHT_TALON_ID);
		rightSparkMax = new CANSparkMax(DrivebaseConstants.RIGHT_SPARK_ID, MotorType.kBrushed);

		rightTalon.setNeutralMode(NeutralMode.Brake);
		rightSparkMax.setIdleMode(IdleMode.kBrake);

		rightTalon.setInverted(DrivebaseConstants.RIGHT_TALON_INVERTED);
		rightSparkMax.setInverted(DrivebaseConstants.RIGHT_SPARK_INVERTED);

		rightMotors = new MotorControllerGroup(rightTalon, rightSparkMax);

		/* All Together */

		this.drive = new DifferentialDrive(leftMotors, rightMotors);
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
		leftTalon.setSelectedSensorPosition(0, 0, 1000);
		rightTalon.setSelectedSensorPosition(0, 0, 1000);
	}

	public int getLeftEncoder() {
		return (int) leftTalon.getSelectedSensorPosition(0);
	}

	public int getRightEncoder() {
		return (int) -rightTalon.getSelectedSensorPosition(0);
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
