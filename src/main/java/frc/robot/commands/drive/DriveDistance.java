package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class DriveDistance extends CommandBase {
    private final double distance;
    private final double speed;

    /**
     * Creates a new DriveDistance.
     *
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
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public void execute() {
        Robot.drivebase.drive(-speed, -speed);
    }

    @Override
    public void end(boolean interrupted) {
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(Robot.drivebase.getAverageEncoderDistance()) >= distance;
    }
}