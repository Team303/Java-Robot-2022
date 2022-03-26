// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.RelativeEncoder;
import frc.robot.RobotMap.Climber;


public class ClimberSubsystem extends SubsystemBase {
  
  private final CANSparkMax climbMotor;
  private final RelativeEncoder climbEncoder;
  // private final SparkMaxLimitSwitch upperLimitSwitch;
  // private final SparkMaxLimitSwitch lowerLimitSwitch;

  /** Creates a new ClimberSubsystem. */
  public ClimberSubsystem() {
    
    climbMotor = new CANSparkMax(Climber.CLIMB_PORT_ID, MotorType.kBrushless);
    
    climbMotor.setInverted(Climber.CLIMB_MOTOR_INVERTED);

    climbMotor.setIdleMode(IdleMode.kBrake);
   
    /* not using limit switches right now

    //set limits so it doesn't go past (DON'T KNOW IF IT WORKS)
    climbMotor.setSoftLimit(SoftLimitDirection.kForward, Climber.SOFT_LIMIT);
    climbMotor.setSoftLimit(SoftLimitDirection.kReverse, 0);

    upperLimitSwitch = climbMotor.getForwardLimitSwitch(Type.kNormallyClosed);
    lowerLimitSwitch = climbMotor.getReverseLimitSwitch(Type.kNormallyClosed);

    */
    climbEncoder = climbMotor.getEncoder();

  }

  public void climb(double speed) {
    //might not be needed 
    //since limitswiches should do this aready

    /* DO NOT USE RIGHT NOW
    if((lowerLimitReached() &&  speed < 0)
     ||(upperLimitReached() && speed > 0))
      return;
    */  
    climbMotor.set(speed);
  } 

  /*methods to check climb limits (NOT USING RIGHT NOW)
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
  
  private boolean upperLimitSwitchTringered(){
    return upperLimitSwitch.isPressed();
  }

  private boolean lowerLimitSwitchTringered(){
    return lowerLimitSwitch.isPressed();
  }
  */ 

  /* helper methods*/

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
