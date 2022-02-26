package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.Intake;

public class ExtendIntake extends CommandBase {

    public ExtendIntake(){
        addRequirements(Robot.intake);
    }

    @Override
    public void initialize(){
        Robot.intake.IntakeExtend(Intake.EXTEND_SPARK_SPEED);
    }
    
    @Override
    public void end(boolean interrupt){
        //stop intake
        Robot.intake.IntakeExtend(0);
    }
}
