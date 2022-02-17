package frc.robot.commands.drive;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.MathUtil;

import frc.robot.Robot;

public class DriveToAngle extends CommandBase{
    
	//Declaring varibles
	private double angle;
    private double kP = 0.015; 
    private double kI = 0;
    private double kD = 0;
    private double speed;
    private PIDController pidcontroller;
    public static double error; 
    
    //Takes a varible of what angle you want to turn to
    public DriveToAngle(double angle) {
        //Initialize varibles
        this.angle = angle;
        pidcontroller = new PIDController(kP, kI, kD);
        pidcontroller.setSetpoint(angle);
        addRequirements(Robot.drivebase);
    }

    @Override
    public void initialize() {
        //reset everything 
        Robot.drivebase.drive(0,0);
        Robot.gyro.reset();
    }

    @Override
    public void execute() {
        

        // Max trun speed of 0.8 number not tested | negative angle beacsue Gyro is reversed 
        speed = MathUtil.clamp(pidcontroller.calculate(-Robot.gyro.getAngle()), -0.8, 0.8);

        //(kP * error), -(kP * error)
        Robot.drivebase.drive(speed, -speed);;
        

    }

    @Override
    public void end(boolean interrupted){
        //At the end set motors to 0 so you don't go over
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        //check if you are at the angle and returns that value
        return pidcontroller.atSetpoint();
    }

}