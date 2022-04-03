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
  public final GroundedDigitalInput bottomLeftLimitSwitch; 
  public final GroundedDigitalInput bottomRightLimitSwitch; 
  public final GroundedDigitalInput topRightLimitSwitch; 
  public final GroundedDigitalInput topLeftLimitSwitch; 

  public final GroundedDigitalInput neutralToggleSwitch; 

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    
    climbMotor = new CANSparkMax(Climber.CLIMB_PORT_ID, MotorType.kBrushless);
    
    climbMotor.setInverted(Climber.CLIMB_MOTOR_INVERTED);

    climbMotor.setIdleMode(IdleMode.kBrake);
    
    
    climbEncoder = climbMotor.getEncoder();

    bottomLeftLimitSwitch =  new GroundedDigitalInput(Climber.BOTTOM_LEFT_LIMIT_SWITCH);
    bottomRightLimitSwitch =  new GroundedDigitalInput(Climber.BOTTOM_RIGHT_LIMIT_SWITCH);
    topRightLimitSwitch =  new GroundedDigitalInput(Climber.TOP_RIGHT_LIMIT_SWITCH);
    topLeftLimitSwitch =  new GroundedDigitalInput(Climber.TOP_LEFT_LIMIT_SWITCH);

    neutralToggleSwitch =  new GroundedDigitalInput(Climber.NEUTRAL_TOGGLE_SWITCH);
  }

  public void setNeutralMode(IdleMode mode) {
      climbMotor.setIdleMode(mode);
  }

  public void climb(double speed) {

    if ((bottomLimitReached() && speed < 0) || (topLimitReached() && speed > 0)) {
      climbMotor.set(0);
      return;
    }
    climbMotor.set(speed);
  } 

  public boolean bottomLimitReached(){
    return bottomLeftLimitSwitch.get() || bottomRightLimitSwitch.get();
  }

  public boolean topLimitReached(){
    return topLeftLimitSwitch.get() || topRightLimitSwitch.get();
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
