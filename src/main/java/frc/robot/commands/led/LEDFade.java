package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;

import static frc.robot.Robot.ledStrip;

public class LEDFade extends CommandBase {

	int blue = 0;
	boolean backwards = false;

	public LEDFade() {
		addRequirements(ledStrip);
	}

	@Override
	public void initialize() {

		blue = 0;
		backwards = false;

		for (var i = 0; i < ledStrip.ledBuffer.getLength(); i++) {
			ledStrip.ledBuffer.setRGB(i, 0, 0, blue);
		}

		ledStrip.led.setData(ledStrip.ledBuffer);

	}

	@Override
	public void execute() {
		if (!backwards)
			blue += 5;
		else
			blue -= 5;

		if (blue > 255) {
			blue = 255;
			backwards = true;
		} else if (blue < 0) {
			blue = 0;
			backwards = false;
		}

		for (var i = 0; i < ledStrip.ledBuffer.getLength(); i++) {
			ledStrip.ledBuffer.setRGB(i, 0, 0, blue);
		}

		ledStrip.led.setData(ledStrip.ledBuffer);

	}

	@Override
	public void end(boolean interrupted) {
		for (var i = 0; i < ledStrip.ledBuffer.getLength(); i++) {
			ledStrip.ledBuffer.setRGB(i, 0, 0, 0);
		}

		blue = 0;
		backwards = false;

		ledStrip.led.setData(ledStrip.ledBuffer);
	}
}
