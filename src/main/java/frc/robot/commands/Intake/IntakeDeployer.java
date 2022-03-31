// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

public class IntakeDeployer extends CommandBase {
  /** Creates a new IntakeDeployer. */

  //private final Value direction;
  private Value state;

  public IntakeDeployer() {

    //this.direction = direction;
    addRequirements(Robot.pneumatics);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public IntakeDeployer(Value state){

    this.state = state;
    addRequirements(Robot.pneumatics);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (state != null){
      Robot.pneumatics.intakeStateSystem(state);
    }
    else{
      Robot.pneumatics.intakeDeploySystem();
    }
  }

  // Called every time the scheduler runs while the command is scheduled.

}
