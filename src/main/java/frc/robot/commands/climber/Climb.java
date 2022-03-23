// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Climb extends CommandBase {
  
  
  /** Creates a new ClimbUp. */
  public Climb() {
    addRequirements(Robot.climb);
  }

  @Override
  public void execute() {
    //set speed and direction from play station controllor to climb
    Robot.climb.climb(Robot.operatorController.getRightY());
  }
  
 

}