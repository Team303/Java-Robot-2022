package frc.robot.commands.drive;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Robot;

/**
 * Command that will drive the specified distance, and account
 * for turning error automatically using the difference between right and left
 * encoders
 */
public class DriveDistanceStraightByEncoders extends PIDCommand {

	private final double distance;

	/**
	 * @param distance The number of inches the robot will drive
	 * @param speed    The speed at which the robot will drive (0-1). Negative
	 *                 values will go backwards.
	 */
	public DriveDistanceStraightByEncoders(double distance, double speed) {
		super(
				new PIDController(0.01, 0, 0),
				// PID Measurement is based on encoder distance delta
				() -> Robot.drivebase.getLeftEncoderDistance() - Robot.drivebase.getRightEncoderDistance(),
				// Setpoint is 0 degrees to try our best to move perfectly straight
				0,
				(output) -> {
					/*
					 * If the robot has not reached the specified distance, keep moving with
					 * correction. Otherwise, only correct for angle error
					 */
					if (Math.abs(Robot.drivebase.getAverageEncoderDistance()) < distance) {
						Robot.drivebase.arcadeDrive(speed, output, false);
					} else {
						Robot.drivebase.arcadeDrive(0, output, false);
					}
				},
				Robot.drivebase);

		// Set the controller to be continuous (because it is an angle controller)
		getController().enableContinuousInput(-180, 180);
		// Set the tollerance to 1 inch
		getController().setTolerance(1);

		this.distance = distance;
	}

	@Override
	public void initialize() {
		Robot.drivebase.resetEncoders();
		Robot.navX.reset();
	}

	@Override
	public boolean isFinished() {
		/*
		 * Stop if we are facing straight and have gone at least
		 * as far as the specified distance
		 */
		return getController().atSetpoint() && Math.abs(Robot.drivebase.getAverageEncoderDistance()) >= distance;
	}
}