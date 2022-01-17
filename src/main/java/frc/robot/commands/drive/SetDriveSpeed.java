package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetDriveSpeed extends CommandBase {
	private final double speed;

	public SetDriveSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public void initialize() {
		Robot.drivebase.setMaxOutput(this.speed);
	}

	@Override
	public void end(boolean interrupted) {
		Robot.drivebase.setMaxOutput(1);
	}
}