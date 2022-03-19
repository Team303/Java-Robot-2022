// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.Climber;

public class Climb extends CommandBase {
  
  
  private final double speed;

  /** Creates a new ClimbUp. */
  public Climb(double speed) {
    this.speed = speed;
    addRequirements(Robot.climb);
  }

  @Override
  public void execute() {

    if (Robot.climb.encoderPosition() >= Climber.SOFT_LIMIT) 
      return;

    Robot.climb.climb(speed);
  }
  
 

}