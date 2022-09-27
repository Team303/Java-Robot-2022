package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.RobotMap.IOConstants;
import frc.robot.RobotMap.LED;
import frc.robot.autonomous.Autonomous;
import frc.robot.autonomous.AutonomousProgram;
import frc.robot.commands.climber.DefaultClimb;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveHold;
import frc.robot.commands.drive.DriveWait;
import frc.robot.commands.drive.SetDriveSpeed;
import frc.robot.commands.led.LEDRainbowRotate;
import frc.robot.commands.led.LEDSolidColor;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.LEDSubsystem;

public class Robot extends TimedRobot {

	/* Define Robot Subsystems */
	public static final DrivebaseSubsystem drivebase = new DrivebaseSubsystem();
	public static final LEDSubsystem ledStrip = new LEDSubsystem();
	public static final ClimberSubsystem climb = new ClimberSubsystem();

	/* RoboRio Sensors */
	public static final AHRS navX = new AHRS();

	/* Robot IO Controls */
	public static final Joystick leftJoystick = new Joystick(IOConstants.LEFT_JOYSTICK_ID);
	public static final Joystick rightJoystick = new Joystick(IOConstants.RIGHT_JOYSTICK_ID);
	public static final XboxController operatorController = new XboxController(IOConstants.OPERATOR_CONTROLLER);

	/* Shufflebaord Tabs */
	public static final ShuffleboardTab AUTO_TAB = Shuffleboard.getTab("Autonomous");

	/* Shuffleboard Choosers */
	public static SendableChooser<Double> autoDelayChooser = new SendableChooser<>();

	/* Robot alliance color */
	public static Color allianceColor = DriverStation.getAlliance() == Alliance.Blue ? LED.RED : LED.BLUE;

	/**
	 * Defines all the options for the autonomous delay
	 */
	static {
		for (double i = 0; i < 15; i += 0.25)
			autoDelayChooser.addOption(String.format("%.2f", i), i);

		autoDelayChooser.setDefaultOption("0.0", 0.0D);

		AUTO_TAB.add("Auto Start Delay", autoDelayChooser);
	}

	// The command configured to run during auto
	private Command autonomousCommand;

	@Override
	public void robotInit() {
		// Configure the joystick and controller bindings
		configureButtonBindings();

		// Reset everything back to default
		navX.calibrate();
		navX.reset();

		// Reset motor encoders for all sub systems
		drivebase.resetEncoders();
		climb.resetEncoders();

		// This runs if no other commands are scheduled (teleop)
		drivebase.setDefaultCommand(new DefaultDrive());
		climb.setDefaultCommand(new DefaultClimb());
		ledStrip.setDefaultCommand(new LEDRainbowRotate());

		// Set limited drive speed for normal driving
		drivebase.setMaxOutput(0.75);

		/* Shuffleboard Stuff */
		Autonomous.init();
		AutonomousProgram.addAutosToShuffleboard();

		// Start Camera
		CameraServer.startAutomaticCapture();
	}

	@Override
	public void autonomousInit() {
		// Chooses which auto we do from SmartDashboard
		autonomousCommand = AutonomousProgram.autoChooser.getSelected().construct();

		// Schedule the selected autonomous command group
		if (autonomousCommand != null) {
			CommandScheduler.getInstance().schedule(
					// To achieve the configured delay, use a sequential group that contains a wait
					// command
					new SequentialCommandGroup(
							new DriveWait(autoDelayChooser.getSelected()),
							autonomousCommand));
		}

		// Match LEDs color to team
		CommandScheduler.getInstance().schedule(new LEDSolidColor(allianceColor));
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when teleop starts running.
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		// Override whatever the brake switch is
		climb.setNeutralMode(IdleMode.kBrake);

		// Match LEDs color to team
		CommandScheduler.getInstance().schedule(new LEDSolidColor(allianceColor));
	}

	@Override
	public void disabledInit() {
		// Change LED color to signify disabled state
		CommandScheduler.getInstance().schedule(new LEDSolidColor(LED.DISABLED_COLOR));
	}

	@Override
	public void disabledPeriodic() {
		// Poll the brake switch to correctly set climber nuetral mode
		climb.updateBrakeSwitch();
	}

	private void configureButtonBindings() {
		// While holding turbo button, increase drive speed to full power
		new JoystickButton(rightJoystick, 2).whileHeld(new SetDriveSpeed(1));

		// Drive fwd
		new JoystickButton(rightJoystick, 3).whileHeld(new DriveHold(0.75));
		new JoystickButton(rightJoystick, 5).whileHeld(new DriveHold(1));

		// Drive back
		new JoystickButton(rightJoystick, 6).whileHeld(new DriveHold(-1));
		new JoystickButton(rightJoystick, 4).whileHeld(new DriveHold(-0.75));

		// Rotation counter-clockwise
		new JoystickButton(leftJoystick, 3).whileHeld(new DriveHold(-0.75, 0.75));
		new JoystickButton(leftJoystick, 5).whileHeld(new DriveHold(-1, 1));

		// Rotation clockwise
		new JoystickButton(leftJoystick, 6).whileHeld(new DriveHold(1, -1));
		new JoystickButton(leftJoystick, 4).whileHeld(new DriveHold(0.75, -0.75));
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
}
