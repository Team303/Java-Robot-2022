package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.Robot;
import frc.robot.subsystems.DrivebaseSubsystem;

public class TurnToAngle extends PIDCommand {

	private static final PIDController PID_CONTROLLER = new PIDController(0.01, 0, 0);

	static {
		DrivebaseSubsystem.DRIVEBASE_TAB.add("TurnToAngle PID", PID_CONTROLLER);
	}

	public TurnToAngle(double angle) {
		super(
				PID_CONTROLLER,
				() -> -Robot.navX.getAngle(),
				angle,
				(output) -> Robot.drivebase.arcadeDrive(0, output),
				Robot.drivebase);

		// Set the controller to be continuous (because it is an angle controller)
		getController().enableContinuousInput(-180, 180);

		// Set the controller tolerance - the delta tolerance ensures the robot is
		// stationary at the setpoint before it is considered as having reached the
		// reference
		getController().setTolerance(2, 0);
	}

	@Override
	public void initialize() {
		Robot.drivebase.resetEncoders();
		Robot.navX.reset();
	}

	@Override
	public boolean isFinished() {
		return getController().atSetpoint();
	}
}
