package frc.robot.commands.led;

import edu.wpi.first.wpilibj2.command.CommandBase;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class LEDFlash extends CommandBase {

    Timer timer;
    boolean isPrimary = true;
    double period = 0.5; // 500 milliseconds

    public LEDFlash(double period) {
        addRequirements(Robot.ledStrip);

        timer = new Timer();
        this.period = period;
    }

    public void initialize() {
        timer.start();
    }

    public void execute() {
        double time = timer.get();
        if (time <= period)
            return;
        
        timer.reset();
        timer.start();

        isPrimary = !isPrimary;

        for (var i = 0; i < Robot.ledStrip.ledBuffer.getLength(); i++) {
            Color ledColor = isPrimary ? RobotMap.LED.FLASH_PRIMARY : RobotMap.LED.FLASH_SECONDARY;
            Robot.ledStrip.ledBuffer.setLED(i, ledColor);
            // System.out.printf("Color(%d, %d, %d)\n", (int)(ledColor.red*255),(int)(ledColor.green*255),(int)(ledColor.blue*255));
        }

        Robot.ledStrip.led.setData(Robot.ledStrip.ledBuffer);
    }

    public void end(boolean interupted) {
        timer.stop();
    }

}