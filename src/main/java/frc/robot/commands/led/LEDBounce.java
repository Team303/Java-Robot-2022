// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.led;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class LEDBounce extends CommandBase {

	private static final int LED_COUNT = 5;
	int index = 0;
	boolean backwards;
	Color color;

	public LEDBounce(Color color) {
		addRequirements(Robot.ledStrip);

		this.color = color;
	}

	@Override
	public void initialize() {
		Robot.ledStrip.clear();
	}

	@Override
	public void execute() {
		for (int i = 0; i < LED_COUNT; i++)
			Robot.ledStrip.ledBuffer.setLED(index + i, new Color(0, 0, 0));

		if (backwards) {
			index--;

			if (index <= 0)
				backwards = false;
		} else {
			index++;

			if (index >= Robot.ledStrip.ledBuffer.getLength() - LED_COUNT)
				backwards = true;
		}

		for (int i = 0; i < LED_COUNT; i++)
			Robot.ledStrip.ledBuffer.setLED(index + i, this.color);
		Robot.ledStrip.led.setData(Robot.ledStrip.ledBuffer);
	}

	@Override
	public void end(boolean interrupted) {
		Robot.ledStrip.clear();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
