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
  private final DoubleSolenoid Solenoid;
  


  /** Creates a new PneumaticsSubsystem. */
  public PneumaticsSubsystem() {

    compressor = new Compressor(Pneumatics.PNEMATIC_HUB_ID,  PneumaticsModuleType.REVPH);

    Solenoid = new DoubleSolenoid(
      Pneumatics.SOLINOID_ID , 
      PneumaticsModuleType.REVPH, 
      Pneumatics.FORWARD_ID, 
      Pneumatics.REVERSE_ID
    );
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void intakeDeploySystem(Value direction){
    Solenoid.set(direction);
  }

  public boolean compressorState(){
    return compressor.enabled();
  }
  public double pneumaticPressure(){
    return compressor.getPressure();
  }
}
