package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Robot;
import frc.robot.commands.WaitCommand;

public class DriveWait extends WaitCommand {

	public DriveWait(double seconds) {
		super(seconds, Robot.drivebase);
	}

}
