// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveWait extends CommandBase {
	double waitTime;
	Timer timer;

	public DriveWait(double seconds) {
		this.waitTime = seconds;
        timer = new Timer();

		addRequirements(Robot.drivebase);
	}

	// Called when the command is initially scheduled.
	@Override
	public void initialize() {
		timer.start();
	}

	// Called once the command ends or is interrupted.
	@Override
	public void end(boolean interrupted) {
		if (interrupted) throw new RuntimeException("Wait action should never be interupted!");
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		if (timer.get() >= waitTime) {
            timer.stop();
            timer.reset();
            return true;
        }

        return false;
	}
}
