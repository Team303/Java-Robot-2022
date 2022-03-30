package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.BangBangController;
import edu.wpi.first.math.controller.PIDController;
import frc.robot.RobotMap.Shooter;
import com.ctre.phoenix.sensors.CANCoder;


public class ShooterSubsystem extends SubsystemBase{

    private final CANSparkMax shooterSparkMax;
    private final CANSparkMax indexerSparkMax;
    private final BangBangController controller;
    private final RelativeEncoder shooterEncoders;
    private final RelativeEncoder indexerEncoders;
    private final PIDController pidController;
    private final double kP = 0.015;
    private final double kI = 0;
    private final double kD = 0;
    
    
    public ShooterSubsystem() {


        shooterSparkMax = new CANSparkMax(Shooter.SHOOTER_SPARK_ID, MotorType.kBrushless);
        indexerSparkMax = new CANSparkMax(Shooter.INDEXER_SPARK_ID, MotorType.kBrushless);
        controller = new BangBangController();
        pidController = new PIDController(kP, kI, kD);
        shooterEncoders = shooterSparkMax.getEncoder();
        indexerEncoders = indexerSparkMax.getEncoder();
        
        pidController.setSetpoint(Shooter.INDEXER_SETPOINT);
        pidController.setTolerance(2);
        
        shooterSparkMax.setInverted(Shooter.SHOOTER_SPARK_INVERTED);
        indexerSparkMax.setInverted(Shooter.INDEXER_SPARK_INVERTED);

        shooterSparkMax.setIdleMode(IdleMode.kCoast);
        indexerSparkMax.setIdleMode(IdleMode.kBrake);

        controller.setSetpoint(Shooter.SHOOTER_SETPOINT);
        controller.setTolerance(Shooter.SHOOTER_TOLERANCE);

    }

    public double getRate(){
        return shooterEncoders.getVelocity();
    }

    public double getShooterEncoders(){
        return shooterEncoders.getPosition();
    }

    public double getIndexerEncoders(){
        return indexerEncoders.getPosition();
    }
    
    public void resetIndexerEncoders(){
        indexerEncoders.setPosition(0);
    }

    private void resetShooterEncoders(){
        shooterEncoders.setPosition(0);
    }

    public void resetEncoders(){
        resetIndexerEncoders();
        resetShooterEncoders();
    }

    public void runShooterPID(){
        shooterSparkMax.set(controller.calculate(getRate(), Shooter.SHOOTER_SETPOINT));
    }

    public void runIndexer(double speed){
        indexerSparkMax.set(speed);
    }

    public void runIndexerPID(){
        indexerSparkMax.set(pidController.calculate(getIndexerEncoders()));
    }

    public boolean indexerAtSetpoint(){
        return pidController.atSetpoint();
    }

    public boolean shooterAtSetpoint(){
        return controller.atSetpoint();
    }





/*
    public double checkSpeed(){
        
        return 0;
    }
*/

}