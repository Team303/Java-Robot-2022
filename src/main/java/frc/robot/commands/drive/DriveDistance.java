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
        //initialize varibles
        this.distance = inches;
        this.speed = speed;
        addRequirements(Robot.drivebase);
    }

    @Override
    public void initialize() {
        //resets everything for accurate readings 
        Robot.drivebase.resetEncoders();
        Robot.gyro.reset();
    }

    @Override
    public void execute() {
        //error is how much you are turning (aka: what you don't want)
        //this is used to correct yourself later
		double error = -Robot.gyro.getRate();// / 360;

        // System.out.println("Rate: " + Robot.gyro.getRate());
        // System.out.println("Error: " + error);

        // double error = 1;

        //uses speed varible multiplied by error to fix any change and keep you going straight
        Robot.drivebase.drive(speed * error, speed *  error, false);
    }

    @Override
    public boolean isFinished() {
        //check if you have went the distance based on encoders
        return Math.abs(Robot.drivebase.getAverageEncoderDistance()) >= distance;
    }
}