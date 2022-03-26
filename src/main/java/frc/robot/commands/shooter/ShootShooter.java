package frc.robot.commands.shooter;

import frc.robot.Robot;
import frc.robot.RobotMap.Shooter;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class ShootShooter extends CommandBase {
    
    private PIDController pidController;
    private double shooterSpeed = 0;
    private final double kP = 0.015;
    private final double kI = 0;
    private final double kD = 0;

    //only really use one
    public static double error;
    public static double rpm;

    public ShootShooter() {

        pidController = new PIDController(kP, kI, kD);

        addRequirements(Robot.shooter);
        //TODO find out how to get rotation speed of flywheel
    }

    @Override
    public void execute() {

        /*
        setShooterSpeed = pidController.calculate(measurement);
        */
        Robot.shooter.runShooter(Robot.shooter.calculateSpeed(Robot.shooter.getRevolutions(), Shooter.SHOOTER_SETPOINT));
        Robot.shooter.runIndexer(pidController.calculate(Robot.shooter.getIndexerEncoders(), Shooter.INDEXER_SETPOINT));
      

    }

    @Override
    public void end(boolean interrupted){

        Robot.shooter.runShooter(0.0);
        Robot.shooter.runIndexer(0.0);
        Robot.shooter.resetIndexerEncoders();

    }
    @Override
    public boolean isFinished(){
        
        return pidController.atSetpoint();

    }
}
