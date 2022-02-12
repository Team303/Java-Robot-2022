

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap.Intake;

//imports makes a class IntakeSubsystem
public class IntakeSubsystem extends SubsystemBase {

    //initializes a new Object for the intake motor
    private final CANSparkMax intakeSparkMax;
    
    public IntakeSubsystem() {
        
        // Defines CANSparkMax motor using id and motor type
        intakeSparkMax = new CANSparkMax(Intake.INTAKE_SPARK_ID, MotorType.kBrushed);

        // Inverts SparkMax motor if ports are inverted
        intakeSparkMax.setInverted(Intake.INTAKE_SPARK_INVERTED);
        
        // Sets IdleMode to brake; stops immediately by sending reverse power
        intakeSparkMax.setIdleMode(IdleMode.kBrake);
    }
    
    public void setIntakeSpeed(double speed) {
        intakeSparkMax.set(speed);
    }



    

    
    
}