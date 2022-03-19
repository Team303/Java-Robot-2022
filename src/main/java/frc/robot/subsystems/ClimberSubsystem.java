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
  
  private final CANSparkMax winchRight;
  private final CANSparkMax winchLeft;
  private final RelativeEncoder climbEncoder;
  private final SparkMaxLimitSwitch upperLimitSwitch;
  private final SparkMaxLimitSwitch lowerLimitSwitch;
  private static MotorControllerGroup climber;

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    
    winchRight = new CANSparkMax(Climber.WINCH_PORT_RIGHT, MotorType.kBrushless);
    winchLeft = new CANSparkMax(Climber.WINCH_PORT_LEFT, MotorType.kBrushless);
    
    winchLeft.setInverted(Climber.WINCH_LEFT_INVERTED);
    winchRight.setInverted(Climber.WINCH_RIGHT_INVERTED);
   
    //set limits so it doesn't go past (DON'T KNOW IF IT WORKS)
    winchLeft.setSoftLimit(SoftLimitDirection.kForward, Climber.SOFT_LIMIT);
    winchLeft.setSoftLimit(SoftLimitDirection.kReverse, 0);


    climber = new MotorControllerGroup(winchLeft, winchRight);


    climbEncoder = winchLeft.getEncoder();


    //Upperlimit on left
    upperLimitSwitch = winchLeft.getForwardLimitSwitch(Type.kNormallyClosed);

    //Lower limit on right
    lowerLimitSwitch = winchRight.getForwardLimitSwitch(Type.kNormallyClosed);

  }
  public void climb(double speed) {
    if((lowerLimitReached() &&  speed < 0)
     ||(upperLimitReached() && speed > 0))
      return;
    climber.set(speed);
  } 

  private boolean upperLimitReached(){
    if(upperLimitSwitchTringered() && encoderPosition() >= Climber.SOFT_LIMIT)
      return true;
    return false;
  }

  private boolean lowerLimitReached(){
    if(lowerLimitSwitchTringered() && encoderPosition() <= 0)
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
