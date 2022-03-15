// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;
import frc.robot.RobotMap.Climber;

public class Climb extends CommandBase {
  
  
public static enum ClimberDirection {kUP, kDOWN}
private final ClimberDirection direction;
private final double speed;
private final double endPosition;

  /** Creates a new ClimbUp. */
  public Climb(ClimberDirection direction, double speed, double endPosition) {
    this.direction = direction;
    this.speed = speed;
    this.endPosition = endPosition;
    addRequirements(Robot.climb);
  }

  @Override
  public void execute() {
    if (direction == ClimberDirection.kUP)
      Robot.climb.extend(speed);
    else
      Robot.climb.retract(speed);
  }
  
  @Override
  public void end(boolean interrupted) {
    Robot.climb.stop();
  }

  @Override
  public boolean isFinished() {
    if (Robot.climb.encoderPosition() > Climber.SOFT_LIMIT)
      return true;
    return false;
   }

}