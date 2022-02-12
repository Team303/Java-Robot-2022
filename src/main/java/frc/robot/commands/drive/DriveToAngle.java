package frc.robot.commands.drive;

import java.util.function.DoubleBinaryOperator;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;

public class DriveToAngle extends CommandBase{
    
	
	private double angle;
    private double kP = 0.015; 
    private double kI = 0;
    private double kD = 0;
    private double speed;
    public static double error; 
    
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

        updatePID();

        speed = kP * error;

        if(speed > 0.8 || speed < -0.8){
            speed = 0.8 * (speed / Math.abs(speed));
            
        }
        Robot.drivebase.drive(speed, -speed);
         //(kP * error, -(kP * error)

    }

    private void updatePID(){
        //NOTE: these values are based off of a google search and might not work
        //kP = 1/ (angle - error + 1);
        //kP = ((error/angle) + 0.02);
        //kP = Math.cos(2 * Math.PI / Math.toRadians(angle*4));
        kP = Math.abs(error /2000);
    }





    @Override
    public void end(boolean interrupted){
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        if(error == 0){
            Robot.drivebase.drive(0, 0);
            return true;
        }
        return false;

    }

}