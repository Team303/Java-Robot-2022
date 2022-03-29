package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.Intake;

public class StartIntake extends CommandBase {
    
    public StartIntake() {
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize(){
        //start intake
        Robot.intake.setIntakeSpeed(Intake.INTAKE_SPARK_SPEED);
    }

    @Override
    public void end(boolean interrupt){
        //stop intake
        Robot.intake.setIntakeSpeed(0);
    }
}