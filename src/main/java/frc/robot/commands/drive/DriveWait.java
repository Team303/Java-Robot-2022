package frc.robot.commands.drive;

import frc.robot.Robot;
import frc.robot.commands.WaitCommand;

public class DriveWait extends WaitCommand {

	public DriveWait(double seconds) {
		super(seconds, Robot.drivebase);
	}

}
