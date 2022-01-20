// Copyright (c) 2022 Team 303

package frc.robot.commands.led;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetLEDColor extends CommandBase {

	Color color;

	public SetLEDColor(Color color) {
		addRequirements(Robot.ledStrip);
		this.color = color;
	}

	@Override
	public void initialize() {
		for (var i = 0; i < Robot.ledStrip.ledBuffer.getLength(); i++) {
			Robot.ledStrip.ledBuffer.setLED(i, this.color);
		}

		Robot.ledStrip.led.setData(Robot.ledStrip.ledBuffer);
	}
}
