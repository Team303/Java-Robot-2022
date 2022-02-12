/*package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.Shooter;


public class ShooterSubsystem extends SubsystemBase{

    //private final CANSparkMax shooterSparkMax;
    //private final CANSparkMax indexerSparkMax;

    
    
    public ShooterSubsystem() {
        shooterSparkMax = new CANSparkMax(Shooter.SHOOTER_SPARK_ID, MotorType.kBrushed);
        indexerSparkMax = new CANSparkMax(Shooter.INDEXER_SPARK_ID, MotorType.kBrushed);

        shooterSparkMax.setInverted(Shooter.SHOOTER_SPARK_INVERTED);
        indexerSparkMax.setInverted(Shooter.INDEXER_SPARK_INVERTED);

        shooterSparkMax.setIdleMode(IdleMode.kBrake);
        indexerSparkMax.setIdleMode(IdleMode.kBrake);
    }

    public void run(double speed) {
        shooterSparkMax.set(speed);
    }

    public double checkSpeed(){
        
        return 0;
    }

    public void calcPID(double speed) {
        //speed = kP * error;
    }
}*/