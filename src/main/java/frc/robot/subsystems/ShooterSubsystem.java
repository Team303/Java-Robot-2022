package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.SparkMaxRelativeEncoder;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.controller.BangBangController;
import frc.robot.RobotMap.Shooter;
import com.ctre.phoenix.sensors.CANCoder;


public class ShooterSubsystem extends SubsystemBase{

    private final CANSparkMax shooterSparkMax;
    private final CANSparkMax indexerSparkMax;
    private final BangBangController controller;
    
    
    public ShooterSubsystem() {
        shooterSparkMax = new CANSparkMax(Shooter.SHOOTER_SPARK_ID, MotorType.kBrushless);
        indexerSparkMax = new CANSparkMax(Shooter.INDEXER_SPARK_ID, MotorType.kBrushless);
        controller = new BangBangController();
        
        shooterSparkMax.setInverted(Shooter.SHOOTER_SPARK_INVERTED);
        indexerSparkMax.setInverted(Shooter.INDEXER_SPARK_INVERTED);

        shooterSparkMax.setIdleMode(IdleMode.kBrake);
        indexerSparkMax.setIdleMode(IdleMode.kBrake);

        controller.setSetpoint(Shooter.SHOOTER_SETPOINT);
        controller.setTolerance(Shooter.SHOOTER_TOLERANCE);

    }

    public double getRevolutions(){
        return shooterSparkMax.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 360).getVelocity();
    }

    public double getEncoders(){
        return shooterSparkMax.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 360).getPosition();
    }

    public double getIndexerEncoders(){
        return indexerSparkMax.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 360).getPosition();
    }

    public double calculateSpeed(double measurement, double setpoint){
        return controller.calculate(measurement, setpoint);
    }
    
    public void resetIndexerEncoders(){
        shooterSparkMax.getEncoder(SparkMaxRelativeEncoder.Type.kHallSensor, 360).setPosition(0);
    }

    public void runShooter(double speed) {
        shooterSparkMax.set(speed);
    }

    public void runIndexer(double speed){
        indexerSparkMax.set(speed);
    }

/*
    public double checkSpeed(){
        
        return 0;
    }
*/

}