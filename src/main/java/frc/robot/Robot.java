// Copyright (c) 2022 Team 303

package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotMap.IOConstants;
import frc.robot.autonomous.Autonomous;
import frc.robot.autonomous.AutonomousProgram;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.SetDriveSpeed;
import frc.robot.commands.led.LEDFade;
import frc.robot.commands.led.SetLEDColor;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class Robot extends TimedRobot {
	/* Define Robot Subsystems */
	public static DrivebaseSubsystem drivebase = new DrivebaseSubsystem();
	public static LEDSubsystem ledStrip = new LEDSubsystem();
	
	/* RoboRio Sensors */
	public static ADXRS450_Gyro gyro = new ADXRS450_Gyro();

	/* Robot IO Controls */
	public static Joystick leftJoystick = new Joystick(IOConstants.LEFT_JOYSTICK_ID);
	public static Joystick rightJoystick = new Joystick(IOConstants.RIGHT_JOYSTICK_ID);

	// The command configured to run during auto
	private Command autonomousCommand;

	@Override
	public void robotInit() {
		// Configure the button bindings
		configureButtonBindings();

		gyro.calibrate();

		// This runs if no other commands are scheduled (teleop)
		drivebase.setDefaultCommand(new DefaultDrive());

		/* Shuffleboard Stuff */
		Autonomous.init();
		AutonomousProgram.addAutosToShuffleboard();
		Shuffleboard.getTab("Debug").add(gyro);

		Shuffleboard.getTab("Commands").add("Drive", new DriveDistance(12, 1));
	}

	private void configureButtonBindings() {
		// // Drive 4 inches when right trigger pressed
		// new JoystickButton(rightJoystick, ButtonType.kTrigger.value)
		// .whenPressed(new DriveDistance(24, 1));

		/* Change LED strip colors for buttons 1-3 */
		// new JoystickButton(rightJoystick, 5)
		// 		.whenPressed(new SetLEDColor(new Color(255, 0, 0)));

		// new JoystickButton(rightJoystick, 2)
		// 		.whenPressed(new SetLEDColor(new Color(0, 255, 0)));

		// new JoystickButton(rightJoystick, 6)
		// 		.whenPressed(new SetLEDColor(new Color(0, 0, 255)));

		// // While holding the left trigger button, drive at half speed
		// new JoystickButton(leftJoystick, ButtonType.kTrigger.value)
		// 		.whenHeld(new LEDFade());

	}

	/*
	 * This Robot is configured to run with the WPILib CommandScheduler.
	 * ⛔ Nothing should be handled in the below methods ⛔
	 */

	@Override
	public void robotPeriodic() {

		/*
		 * Runs the Scheduler. This is responsible for polling buttons, adding
		 * newly-scheduled
		 * commands, running already-scheduled commands, removing finished or
		 * interrupted commands,
		 * and running subsystem periodic() methods. This must be called from the
		 * robot's periodic
		 * block in order for anything in the Command-based framework to work.
		 */
		CommandScheduler.getInstance().run();
	}

	@Override
	public void autonomousInit() {
		autonomousCommand = AutonomousProgram.autoChooser.getSelected().construct();

		// schedule the autonomous command (example)
		if (autonomousCommand != null) {
			autonomousCommand.schedule();
		}

		new SetLEDColor(new Color(255, 0, 0)).schedule();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}

		new SetLEDColor(new Color(0, 0, 255)).schedule();
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();

		new SetLEDColor(new Color(255, 255, 0)).initialize();
	}

	@Override
	public void disabledInit() {
		new SetLEDColor(new Color(255, 0, 255)).initialize();
	}
}
