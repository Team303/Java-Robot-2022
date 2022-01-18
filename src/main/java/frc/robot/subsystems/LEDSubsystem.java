// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
	public final AddressableLED led;
	public final AddressableLEDBuffer ledBuffer;

	/** Creates a new LEDSubsystem. */
	public LEDSubsystem() {
		led = new AddressableLED(9); // Set to PWM port 9

		ledBuffer = new AddressableLEDBuffer(60); // Buffer length of 60 LEDs
		led.setLength(ledBuffer.getLength());

		led.setData(ledBuffer);
		led.start();
	}

	@Override
	public void periodic() {
		// This method will be called once per scheduler run
	}
}
