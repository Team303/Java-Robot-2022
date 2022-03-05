// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LED;;

public class LEDSubsystem extends SubsystemBase {
	public final AddressableLED led;
	public final AddressableLEDBuffer ledBuffer;

	/** Creates a new LEDSubsystem. */
	public LEDSubsystem() {
		led = new AddressableLED(LED.LED_ID); // Set to PWM port 9

		ledBuffer = new AddressableLEDBuffer(106); // Buffer length of 108 LEDs
		led.setLength(ledBuffer.getLength());

		led.setData(ledBuffer);
		led.start();
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}

	public void clear() {
		// for every LED set a color
		for (var i = 0; i < Robot.ledStrip.ledBuffer.getLength(); i++) {
			Robot.ledStrip.ledBuffer.setLED(i, new Color(0, 0, 0));
		}
		// Send color data to LEDSubsytem
		Robot.ledStrip.led.setData(Robot.ledStrip.ledBuffer);
	}
}
