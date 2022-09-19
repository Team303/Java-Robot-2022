// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Forces the climber to come to the down position
 */
public class ClimberDown extends CommandBase {
	public ClimberDown() {
		addRequirements(Robot.climb);
	}

	@Override
	public void execute() {
		Robot.climb.climb(-1.0);
	}

	@Override
	public void end(boolean interrupted) {
		Robot.climb.climb(0.0);
	}

	@Override
	public boolean isFinished() {
		return Robot.climb.bottomLimitReached();
	}
}
