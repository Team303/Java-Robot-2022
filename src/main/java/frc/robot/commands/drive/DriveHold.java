// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveHold extends CommandBase {

	private final double speed;

	public DriveHold(double speed) {
		this.speed = speed;
		addRequirements(Robot.drivebase);
	}
	
	@Override
	public void initialize() {
		Robot.drivebase.resetEncoders();
	}

	@Override
	public void execute() {
		Robot.drivebase.drive(speed, speed, false);
	}

}
