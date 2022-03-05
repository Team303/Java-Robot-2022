// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.led;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class LEDBounce extends CommandBase {

  int index = 0;
  boolean backwards;
  Color color;
  Timer timer;

  public LEDBounce(Color color) {
    addRequirements(Robot.ledStrip);

    this.color = color;
    this.timer = new Timer();
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.ledStrip.clear();

    this.timer.reset();
    this.timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // if (this.timer.get() < 20 / 1000) {
    //   return;
    // }

    this.timer.reset();
    this.timer.start();

    Robot.ledStrip.ledBuffer.setLED(index, new Color(0, 0, 0));
    
    if (backwards)
      index--;
    else 
      index ++;
    
    if (backwards) {
      if (index <= 0)
        backwards = false; 
    } else {
      if (index >= Robot.ledStrip.ledBuffer.getLength() - 1)
        backwards = true; 
    }
    
    Robot.ledStrip.ledBuffer.setLED(index, this.color);
    Robot.ledStrip.led.setData(Robot.ledStrip.ledBuffer);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Robot.ledStrip.clear();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
