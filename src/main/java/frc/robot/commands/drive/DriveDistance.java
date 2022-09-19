package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

/**
 * Attempts to drive the specified distance without ensuring
 * that the robot is driving perfectly straight.
 * 
 * Instead, the average of both encoders is used to determine the
 * traveled distance
 */
public class DriveDistance extends CommandBase {

	private final double distance;
	private final double speed;

	/**
	 * @param inches The number of inches the robot will drive
	 * @param speed  The speed at which the robot will drive (0-1)
	 */
	public DriveDistance(double inches, double speed) {
		this.distance = inches;
		this.speed = speed;

		addRequirements(Robot.drivebase);
	}

	@Override
	public void initialize() {
		Robot.drivebase.resetEncoders();
		Robot.navX.reset();
	}

	@Override
	public void execute() {
		Robot.drivebase.arcadeDrive(speed, 0, false);
	}

	@Override
	public boolean isFinished() {
		// Check if the robot has moved at least the specified distance
		return Math.abs(Robot.drivebase.getAverageEncoderDistance()) >= distance;
	}
}