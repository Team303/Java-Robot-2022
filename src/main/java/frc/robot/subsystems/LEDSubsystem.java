// Copyright (c) 2022 Team 303

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.SerialPort.WriteBufferMode;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap.LED;

public class LEDSubsystem extends SubsystemBase {

  public static SerialPort arduino = new SerialPort(9600, Port.kUSB2);
  public final AddressableLEDBuffer ledBuffer;

  /** Creates a new LEDSubsystem. */
  public LEDSubsystem() {
    ledBuffer = new AddressableLEDBuffer(LED.BUFFER_LENGTH); // Buffer length of 108 LEDs
    arduino.setWriteBufferMode(WriteBufferMode.kFlushWhenFull);
    arduino.setWriteBufferSize(LED.BUFFER_LENGTH * 3);
    writeData();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void clear() {
    // for every LED set a color
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      Robot.ledStrip.ledBuffer.setLED(i, new Color(0, 0, 0));
    }
    // Send color data to LEDSubsytem
    Robot.ledStrip.writeData();
  }

  public void writeData() {
    byte[] buff = new byte[LED.BUFFER_LENGTH * 3];
    for (var i = 0; i < ledBuffer.getLength(); i++) {
      buff[i * 3 + 0] = (byte) ledBuffer.getLED(i).red;
      buff[i * 3 + 0] = (byte) ledBuffer.getLED(i).green;
      buff[i * 3 + 0] = (byte) ledBuffer.getLED(i).blue;
    }
	arduino.write(buff, buff.length);
	arduino.flush();
  }
}
