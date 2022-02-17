// Copyright (c) 2022 Team 303

package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveWait extends CommandBase {
	//declare varibles
	double waitTime;
	Timer timer;

	public DriveWait(double seconds) {
		//initialize varibles
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
		// if ever interrupted throw a message explaning why this is bad
		if (interrupted) throw new RuntimeException("Wait action should never be interupted!");
	}

	// Returns true when the command should end.
	@Override
	public boolean isFinished() {
		// will only finish is the timer is greater then your wait time
	
		if (timer.get() >= waitTime) {
			// if it stop everything
            timer.stop();
            timer.reset();
            return true;
        }

        return false;
	}
}
