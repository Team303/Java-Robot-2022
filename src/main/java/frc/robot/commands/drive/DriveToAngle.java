package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;

public class DriveToAngle extends CommandBase{
    
    private double angle;
    private double kP = 0.00000000005; 
    private double error; 
    
    public DriveToAngle(double angleValue) {
        angle = angleValue;
        addRequirements(Robot.drivebase);
        
    }

    @Override
    public void initialize() {
        Robot.drivebase.drive(0,0);
        Robot.gyro.reset();
    }


    @Override
    public void execute() {
        error = angle + Robot.gyro.getAngle();

        Robot.drivebase.drive(kP * error, -(kP * error));
        System.out.println("Error: "+ error);
    }

    @Override
    public void end(boolean interrupted){
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if(error <= 0 ) {
            Robot.drivebase.drive(0, 0);
            return true;
        }
        return false;
        
    }

}