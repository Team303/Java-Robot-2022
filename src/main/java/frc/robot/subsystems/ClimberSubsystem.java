// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import javax.swing.text.html.HTMLDocument.RunElement;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxLimitSwitch.Type;
import com.revrobotics.CANSparkMax.SoftLimitDirection;
import com.revrobotics.RelativeEncoder;
import frc.robot.RobotMap.Climber;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import com.revrobotics.SparkMaxLimitSwitch;


public class ClimberSubsystem extends SubsystemBase {
  
  private final CANSparkMax climbMotor;
  private final RelativeEncoder climbEncoder;
  private final SparkMaxLimitSwitch upperLimitSwitch;
  private final SparkMaxLimitSwitch lowerLimitSwitch;
  private static MotorControllerGroup climber;

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    
    climbMotor = new CANSparkMax(Climber.CLIMB_PORT_ID, MotorType.kBrushless);
    
    climbMotor.setInverted(Climber.CLIMB_MOTOR_INVERTED);
   
    //set limits so it doesn't go past (DON'T KNOW IF IT WORKS)
    climbMotor.setSoftLimit(SoftLimitDirection.kForward, Climber.SOFT_LIMIT);
    climbMotor.setSoftLimit(SoftLimitDirection.kReverse, 0);

    climbEncoder = climbMotor.getEncoder();

    upperLimitSwitch = climbMotor.getForwardLimitSwitch(Type.kNormallyClosed);
    lowerLimitSwitch = climbMotor.getReverseLimitSwitch(Type.kNormallyClosed);

  }
  public void climb(double speed) {
    //might not be needed 
    //since limitswiches should do this aready
    if((lowerLimitReached() &&  speed < 0)
     ||(upperLimitReached() && speed > 0))
      return;
    climber.set(speed);
  } 

  private boolean upperLimitReached(){
    if(upperLimitSwitchTringered() || encoderPosition() >= Climber.SOFT_LIMIT)
      return true;
    return false;
  }

  private boolean lowerLimitReached(){
    if(lowerLimitSwitchTringered() || encoderPosition() <= 0)
      return true;
    return false;
  }


  
  public void resetEncoders(){
    climbEncoder.setPosition(0.0);
  }
   
  public double encoderPosition() {
    return climbEncoder.getPosition();
  }

  private boolean upperLimitSwitchTringered(){
    return upperLimitSwitch.isPressed();
  }

  private boolean lowerLimitSwitchTringered(){
    return lowerLimitSwitch.isPressed();
  }
  
}
