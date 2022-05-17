// Copyright (c) 2022 Team 303

package frc.robot.commands.drive;

import frc.robot.Robot;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * If no other commands are running, drive based on joystick inputs
 */
public class DefaultDrive extends CommandBase {
	public DefaultDrive() {
		addRequirements(Robot.drivebase);
	}

	@Override
	public void execute() {
		//Just left and right joystick values representing left and right side of robot
		// FLIPPED BC IDEK

		double right_speed, left_speed;

		if (Robot.operatorController.getRightTriggerAxis() > 0.0 && Robot.operatorController.getLeftTriggerAxis() == 0.0){
			right_speed = Robot.operatorController.getRightTriggerAxis() + Robot.operatorController.getLeftX();
			left_speed = Robot.operatorController.getRightTriggerAxis() - Robot.operatorController.getLeftX();
	
		if (Robot.operatorController.getRightTriggerAxis() == 0.0 && Robot.operatorController.getLeftTriggerAxis() > 0.0){
			right_speed = (Robot.operatorController.getLeftTriggerAxis() + Robot.operatorController.getLeftX()) * -1;
			left_speed = (Robot.operatorController.getLeftTriggerAxis() - Robot.operatorController.getLeftX()) * -1;
		}
		else{
			right_speed = 0.0;
			left_speed = 0.0;
		}


		Robot.drivebase.drive(left_speed, right_speed);
	}
}
