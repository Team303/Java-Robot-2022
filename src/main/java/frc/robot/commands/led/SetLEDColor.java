// Copyright (c) 2022 Team 303

package frc.robot.commands.led;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Robot;

public class SetLEDColor extends CommandBase {
	//define varible
	Color color;

	public SetLEDColor(Color color) {
		addRequirements(Robot.ledStrip);
		//initialize varible
		this.color = color;
	}

	@Override
	public void initialize() {
		// for every LED set a color
		for (var i = 0; i < Robot.ledStrip.ledBuffer.getLength(); i++) {
			Robot.ledStrip.ledBuffer.setLED(i, this.color);
		}
		// Send color data to LEDSubsytem
		Robot.ledStrip.writeData();
	}
}
