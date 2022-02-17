package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.Intake;

public class StartIntake extends CommandBase {
    
    public StartIntake() {
        addRequirements(Robot.intake);
    }

    public void initialize(){
        //start intake
        Robot.intake.setIntakeSpeed(Intake.INTAKE_SPARK_SPEED);
    }

    public void end(){
        //stop intake
        Robot.intake.setIntakeSpeed(0);
    }
}