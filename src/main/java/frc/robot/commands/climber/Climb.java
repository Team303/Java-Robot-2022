// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.climber;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class Climb extends CommandBase {

  double speed;
  
  /** Creates a new ClimbUp. */
  public Climb() {
    addRequirements(Robot.climb);
  }

  @Override
  public void execute() {
    //set speed and direction from operator controllor to climb
    speed = -Robot.operatorController.getRightY();


    if(speed > -0.1 && speed < 0.1){
      speed = 0;
    }
    // nagative is down,
    speed = MathUtil.clamp(speed, -0.5, 0.75);

    Robot.climb.climb(speed);
  }
  
 

}