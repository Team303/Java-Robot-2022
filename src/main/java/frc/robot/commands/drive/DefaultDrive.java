// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

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
		Robot.drivebase.drive(Robot.leftJoystick.getY(), Robot.rightJoystick.getY());
	}
}
