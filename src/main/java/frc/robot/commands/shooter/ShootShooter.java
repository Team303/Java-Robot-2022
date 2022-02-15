package frc.robot.commands.shooter;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;



public class ShootShooter extends CommandBase {
    
    private final double kP = 0.015;
    public static double error;
    public static double rpm;


    public ShootShooter() {
        addRequirements(Robot.shooter);
        //rpm = shooter.checkSpeed();
    }

    public void execute() {
        error = rpm * kP;
    }
    
    public void end(){
        
    }
}
