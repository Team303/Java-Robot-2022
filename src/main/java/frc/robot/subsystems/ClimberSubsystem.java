// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.RelativeEncoder;

import frc.robot.RobotMap.Climber;
import frc.robot.util.GroundedDigitalInput;

public class ClimberSubsystem extends SubsystemBase {

	/* ShuffleBoard */
	public static final ShuffleboardTab CLIMBER_TAB = Shuffleboard.getTab("Climber");

	private static final NetworkTableEntry CLIMBER_ENCODER = CLIMBER_TAB.add("Climber Encoder", 0).getEntry();
	private static final NetworkTableEntry CLIMBER_RPM = CLIMBER_TAB.add("Climber RPM", 0).getEntry();
	private static final NetworkTableEntry TOP_LIMIT_SWITCH = CLIMBER_TAB.add("Top Limit Switch", false).getEntry();
	private static final NetworkTableEntry BOTTOM_LIMIT_SWITCH = CLIMBER_TAB.add("Bottom Limit Switch", false)
			.getEntry();

	/* Motors */
	private final CANSparkMax climbMotor;
	private final RelativeEncoder climbEncoder;

	/* Limit Switches */
	public final GroundedDigitalInput bottomLeftLimitSwitch;
	public final GroundedDigitalInput bottomRightLimitSwitch;
	public final GroundedDigitalInput topRightLimitSwitch;
	public final GroundedDigitalInput topLeftLimitSwitch;

	/* Brake toggle */
	public final GroundedDigitalInput neutralToggleSwitch;

	/** Creates a new ClimberSubsystem. */
	public ClimberSubsystem() {

		climbMotor = new CANSparkMax(Climber.CLIMB_PORT_ID, MotorType.kBrushless);

		climbMotor.setInverted(Climber.CLIMB_MOTOR_INVERTED);

		climbMotor.setIdleMode(IdleMode.kBrake);

		climbEncoder = climbMotor.getEncoder();

		bottomLeftLimitSwitch = new GroundedDigitalInput(Climber.BOTTOM_LEFT_LIMIT_SWITCH);
		bottomRightLimitSwitch = new GroundedDigitalInput(Climber.BOTTOM_RIGHT_LIMIT_SWITCH);
		topRightLimitSwitch = new GroundedDigitalInput(Climber.TOP_RIGHT_LIMIT_SWITCH);
		topLeftLimitSwitch = new GroundedDigitalInput(Climber.TOP_LEFT_LIMIT_SWITCH);

		neutralToggleSwitch = new GroundedDigitalInput(Climber.NEUTRAL_TOGGLE_SWITCH);
	}

	public void setNeutralMode(IdleMode mode) {
		climbMotor.setIdleMode(mode);
	}

	public void climb(double speed) {
		if ((bottomLimitReached() && speed < 0) || (topLimitReached() && speed > 0)) {
			climbMotor.set(0);
			return;
		}
		climbMotor.set(speed);
	}

	public boolean bottomLimitReached() {
		return bottomLeftLimitSwitch.get() || bottomRightLimitSwitch.get();
	}

	public boolean topLimitReached() {
		return topLeftLimitSwitch.get() || topRightLimitSwitch.get();
	}

	public void resetEncoders() {
		climbEncoder.setPosition(0.0);
	}

	public double encoderPosition() {
		return climbEncoder.getPosition();
	}

	public double getRPMofClimber() {
		return climbEncoder.getCountsPerRevolution();
	}

	/**
	 * Called periodically during disabled to handle the break switch
	 */
	public void updateBrakeSwitch() {
		setNeutralMode(neutralToggleSwitch.get() ? IdleMode.kBrake : IdleMode.kCoast);
	}

	@Override
	public void periodic() {
		CLIMBER_ENCODER.setNumber(encoderPosition());
		CLIMBER_RPM.setNumber(getRPMofClimber());
		TOP_LIMIT_SWITCH.setBoolean(topLimitReached());
		BOTTOM_LIMIT_SWITCH.setBoolean(bottomLimitReached());
	}
}
