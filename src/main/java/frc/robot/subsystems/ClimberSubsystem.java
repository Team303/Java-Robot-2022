// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

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
  private final SparkMaxLimitSwitch leftLimitSwitch;
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

    leftLimitSwitch = winchLeft.getForwardLimitSwitch(Type.kNormallyClosed);


  }
  public void climb(double speed) {
    climber.set(speed);
  } 

  public void resetAndEnable(){
    resetEncoders();
    enableLimitSwitch();
  }
  
  private void resetEncoders(){
    climbEncoder.setPosition(0.0);
  }
  
  private void enableLimitSwitch(){
    leftLimitSwitch.enableLimitSwitch(true);
  }
   
  public double encoderPosition() {
    return climbEncoder.getPosition();
  }

  
}
