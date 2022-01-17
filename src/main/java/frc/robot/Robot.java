// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.shuffleboard.LayoutType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotMap.IOConstants;
import frc.robot.autonomous.Autonomous;
import frc.robot.autonomous.AutonomousProgram;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.SetDriveSpeed;
import frc.robot.subsystems.DrivebaseSubsystem;

public class Robot extends TimedRobot {
	/* Define Robot Subsystems */
	public static DrivebaseSubsystem drivebase = new DrivebaseSubsystem();

	/* Robot IO Controls */
	public static Joystick leftJoystick = new Joystick(IOConstants.LEFT_JOYSTICK_ID);
	public static Joystick rightJoystick = new Joystick(IOConstants.RIGHT_JOYSTICK_ID);

	// The command configured to run during auto
	private Command autonomousCommand;

	@Override
	public void robotInit() {
		// Configure the button bindings
		configureButtonBindings();

		// This runs if no other commands are scheduled (teleop)
		drivebase.setDefaultCommand(new DefaultDrive());

		/* Shuffleboard Stuff */

		AutonomousProgram.addAutosToShuffleboard();
	}

	private void configureButtonBindings() {
		// Grab the hatch when the 'A' button is pressed.
		new JoystickButton(rightJoystick, ButtonType.kTrigger.value)
				.whenPressed(new DriveDistance(4, 1));

		// While holding the shoulder button, drive at half speed
		new JoystickButton(leftJoystick, ButtonType.kTrigger.value)
				.whenHeld(new SetDriveSpeed(0.5));
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
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running.
		if (autonomousCommand != null) {
			autonomousCommand.cancel();
		}
	}

	@Override
	public void testInit() {
		// Cancels all running commands at the start of test mode.
		CommandScheduler.getInstance().cancelAll();
	}
}
