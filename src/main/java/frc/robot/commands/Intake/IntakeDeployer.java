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

  private final Value direction;

  public IntakeDeployer(Value direction) {

    this.direction = direction;
    addRequirements(Robot.pneumatics);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Robot.pneumatics.intakeDeploySystem(direction);
  }

  // Called every time the scheduler runs while the command is scheduled.

}
