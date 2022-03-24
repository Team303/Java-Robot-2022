// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import frc.robot.RobotMap.Pneumatics;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PneumaticsSubsystem extends SubsystemBase {

  private final Compressor compressor;
  private final DoubleSolenoid leftSolenoid;
  private final DoubleSolenoid rightSolenoid;
  


  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {

    compressor = new Compressor(Pneumatics.PNEMATIC_HUB_ID,  PneumaticsModuleType.REVPH);

    leftSolenoid = new DoubleSolenoid(
      Pneumatics.LEFT_SOLINOID_ID , 
      PneumaticsModuleType.REVPH, 
      Pneumatics.LEFT_FORWARD_ID, 
      Pneumatics.LEFT_REVERSE_ID
    );

    rightSolenoid = new DoubleSolenoid(
      Pneumatics.RIGHT_SOLINOID_ID,
      PneumaticsModuleType.REVPH, 
      Pneumatics.RIGHT_FORWARD_ID,
      Pneumatics.RIGHT_REVERSE_ID
    );
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intakeDeploySystem(Value direction){
    leftSolenoid.set(direction);
    rightSolenoid.set(direction);
  }

  public boolean compressorState(){
    return compressor.enabled();
  }
  public double pneumaticPressure(){
    return compressor.getPressure();
  }
}
