package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetDriveSpeed extends CommandBase {

	//declare varibles
	private final double speed;

	public SetDriveSpeed(double speed) {
		//initialize varibles
		this.speed = speed;
	}

	@Override
	public void initialize() {
		//sets max speed
		Robot.drivebase.setMaxOutput(this.speed);
	}

	@Override
	public void end(boolean interrupted) {
		//should really never be called until robot is disabled
		//resets max speed back to 1
		Robot.drivebase.setMaxOutput(1);
	}
}