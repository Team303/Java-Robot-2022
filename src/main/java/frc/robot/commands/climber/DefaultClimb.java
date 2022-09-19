// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Default command for the climber subsystem that responds to xbox controller
 * inputs
 */
public class DefaultClimb extends CommandBase {

	public DefaultClimb() {
		addRequirements(Robot.climb);
	}

	@Override
	public void execute() {
		// Deadband the controller so that only intentional movement activates it
		double speed = MathUtil.applyDeadband(-Robot.operatorController.getRightY(), 0.1);

		// Limit the speed in both directions to avoid too much stress on the motor
		speed = MathUtil.clamp(speed, -0.5, 0.75);

		// NOTE: The climber has a soft stop and will not respond to any input if it
		// detects that it is at either of the extrema
		Robot.climb.climb(speed);
	}

}