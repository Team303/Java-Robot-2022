// Copyright (c) 2022 Team 303

package frc.robot.commands.drive;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * If no other commands are running, drive based on joystick inputs
 */
public class DefaultDrive extends CommandBase {
	public DefaultDrive() {
		addRequirements(Robot.drivebase);
	}

	@Override
	public void execute() {
		//Just left and right joystick values representing left and right side of robot
		// FLIPPED BC IDEK
		Robot.drivebase.drive(-Robot.leftJoystick.getY(), -Robot.rightJoystick.getY());
	}
}
