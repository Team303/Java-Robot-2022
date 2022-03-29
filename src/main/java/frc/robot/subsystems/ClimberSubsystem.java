// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.RelativeEncoder;

import frc.robot.RobotMap.Climber;


public class ClimberSubsystem extends SubsystemBase {
  
  private final CANSparkMax climbMotor;
  private final RelativeEncoder climbEncoder;
 
  // NOTE: Spark max has built in limit swtich capabilities but im stupid and didn't know that
  public final DigitalInput bottomLeftLimitSwitch = new DigitalInput(Climber.BOTTOM_LEFT_LIMIT_SWITCH);
  public final DigitalInput bottomRightLimitSwitch = new DigitalInput(Climber.BOTTOM_RIGHT_LIMIT_SWITCH);

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    
    climbMotor = new CANSparkMax(Climber.CLIMB_PORT_ID, MotorType.kBrushless);
    
    climbMotor.setInverted(Climber.CLIMB_MOTOR_INVERTED);

    climbMotor.setIdleMode(IdleMode.kBrake);
    
    climbEncoder = climbMotor.getEncoder();
  }

  public void climb(double speed) {
    if (bottomLimitReached() && speed < 0) {
      climbMotor.set(0);
      return;
    }
    climbMotor.set(speed);
  } 

  public boolean bottomLimitReached(){
    // Inverted for some reason?
    return !bottomLeftLimitSwitch.get() || !bottomRightLimitSwitch.get();
  }

  public void resetEncoders(){
    climbEncoder.setPosition(0.0);
  }
   
  public double encoderPosition() {
    return climbEncoder.getPosition();
  }

  public double getRPMofClimber(){
    return climbEncoder.getCountsPerRevolution();
  }

  public double getVoltageSpike(){
    return climbMotor.getVoltageCompensationNominalVoltage();
  }
}
