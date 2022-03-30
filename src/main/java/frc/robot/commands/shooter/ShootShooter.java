package frc.robot.commands.shooter;

import frc.robot.Robot;
import frc.robot.RobotMap.Shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ShootShooter extends CommandBase {
    
    //only really use one
    public static double error;
    public static double rpm;

    public ShootShooter() {


        addRequirements(Robot.shooter);
        //TODO find out how to get rotation speed of flywheel
    }

    @Override
    public void initialize(){
        Robot.shooter.resetEncoders();
    }

    @Override
    public void execute() {

        /*
        setShooterSpeed = pidController.calculate(measurement);
        */

  
        Robot.shooter.runShooterPID();

        if(!Robot.shooter.shooterAtSetpoint()){
            return;
        }

        if(Robot.shooter.indexerAtSetpoint()){
            Robot.shooter.resetIndexerEncoders();
        }
        Robot.shooter.runIndexerPID();

      

    }

}
