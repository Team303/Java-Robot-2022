package frc.robot.commands.drive;
import java.util.function.DoubleBinaryOperator;
import edu.wpi.first.math.controller.PIDController;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.Robot;

public class DriveToAngle extends CommandBase{
    
	
	private double angle;
    private double kP = 0.015; 
    private double kI = 0;
    private double kD = 0;
    private double speed;
    private PIDController pidcontroller;
    public static double error; 
    
    public DriveToAngle(double angle) {
        this.angle = angle;
        pidcontroller = new PIDController(kP, kI, kD);
        pidcontroller.setSetpoint(angle);
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

        speed = pidcontroller.calculate(error);

        if(speed > 0.8 || speed < -0.8){
            speed = 0.8 * (speed / Math.abs(speed));
            
        }
        Robot.drivebase.drive(speed, -speed);
         //(kP * error, -(kP * error)

    }

    @Override
    public void end(boolean interrupted){
        Robot.drivebase.drive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return pidcontroller.atSetpoint();
    }

}