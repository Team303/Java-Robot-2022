package frc.robot;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Joystick.ButtonType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.cscore.VideoListener;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.robot.RobotMap.IOConstants;
import frc.robot.autonomous.Autonomous;
import frc.robot.autonomous.AutonomousProgram;
import frc.robot.commands.drive.DefaultDrive;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveHold;
import frc.robot.commands.drive.DriveToAngle;
import frc.robot.commands.drive.DriveWait;
import frc.robot.commands.drive.ZeroEncoders;
import frc.robot.commands.Intake.IntakeDeployer;
import frc.robot.commands.Intake.StartIntake;
import frc.robot.commands.led.LEDBounce;
import frc.robot.commands.led.LEDRainbowRotate;
import frc.robot.commands.led.SetLEDColor;
import frc.robot.commands.climber.Climb;
import frc.robot.commands.climber.ResetClimbEncoder;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.LEDSubsystem;
import frc.robot.subsystems.PneumaticsSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import frc.robot.subsystems.ClimberSubsystem;

public class Robot extends TimedRobot {

  /* Define Robot Subsystems */
  public static DrivebaseSubsystem drivebase = new DrivebaseSubsystem();
  public static LEDSubsystem ledStrip = new LEDSubsystem();
  public static IntakeSubsystem intake = new IntakeSubsystem();
  public static ShooterSubsystem shooter = new ShooterSubsystem();
  public static ClimberSubsystem climb = new ClimberSubsystem();
  public static PneumaticsSubsystem pneumatics = new PneumaticsSubsystem();

  /* RoboRio Sensors */
  public static AHRS navX = new AHRS(); 

  /* Robot IO Controls */
  public static Joystick leftJoystick = new Joystick(
    IOConstants.LEFT_JOYSTICK_ID
  );
  public static Joystick rightJoystick = new Joystick(
    IOConstants.RIGHT_JOYSTICK_ID
  );
  public static XboxController operatorController = new XboxController(
    IOConstants.OPERATOR_CONTROLLER
  );

  /* Sendable for the color */
  public static SendableChooser<Color> colorChooser = new SendableChooser<>();

  public static SendableChooser<Double> autoDelayChooser = new SendableChooser<>();

  static {
    autoDelayChooser.addOption("0.25", 0.25D);
    autoDelayChooser.addOption("0.5",  0.5D);
    autoDelayChooser.addOption("0.75", 0.75D);
    autoDelayChooser.addOption("1.0",  1.0D);
    autoDelayChooser.addOption("1.25", 1.25D);
    autoDelayChooser.addOption("1.5",  1.5D);
    autoDelayChooser.addOption("1.75", 1.75D);  
    autoDelayChooser.addOption("2.0",  2.0D);
    autoDelayChooser.addOption("2.25", 2.25D);
    autoDelayChooser.addOption("2.5",  2.5D);
    autoDelayChooser.addOption("2.75", 2.75D);
    autoDelayChooser.addOption("3.0",  3.0D);
    autoDelayChooser.setDefaultOption("0.0",  0.0D);

    Shuffleboard
    .getTab("Autonomous")
    .add("Auto Start Delay", autoDelayChooser);
  }

  // The command configured to run during auto
  private Command autonomousCommand;

  @Override
  public void robotInit() {
    // Configure the button bindings
    configureButtonBindings();

    //Reset everything back to default
    navX.calibrate();
    navX.reset();
 
    drivebase.resetEncoders();
    new ResetClimbEncoder();

    // This runs if no other commands are scheduled (teleop)
    drivebase.setDefaultCommand(new DefaultDrive());
    climb.setDefaultCommand(new Climb());

    // Could use DriverStation.getAlliance()
    colorChooser.addOption("Red", RobotMap.LED.RED);
    colorChooser.addOption("Blue", RobotMap.LED.BLUE);
    colorChooser.setDefaultOption("Red", RobotMap.LED.RED);
    Shuffleboard.getTab("Autonomous").add("Team Color Chooser", colorChooser);

    /* Shuffleboard Stuff */
    Autonomous.init();
    AutonomousProgram.addAutosToShuffleboard();

    Shuffleboard
      .getTab("Commands")
      .add("Drive 2ft (1/2 power)", new DriveDistance(24, 0.5));
    Shuffleboard.getTab("Commands").add("Reset Encoders", new ZeroEncoders());
    setSmartDashboard();
  }

  private void setSmartDashboard() {
    SmartDashboard.setDefaultNumber("Left Encoder", 0);
    SmartDashboard.setDefaultNumber("Right Encoder", 0);
    SmartDashboard.setDefaultNumber("Encoder Distance in Inches", 0);

    SmartDashboard.setDefaultNumber("navX Angle", 0);
    SmartDashboard.setDefaultNumber("navX Rate", 0);
    SmartDashboard.setDefaultNumber("Angle Error", 0);

    SmartDashboard.setDefaultNumber("Angle P value", DriveToAngle.kP);
    SmartDashboard.setDefaultNumber("Angle I value", DriveToAngle.kI);
    SmartDashboard.setDefaultNumber("Angle D value", DriveToAngle.kD);

    SmartDashboard.setDefaultNumber("Climb Speed", 0);
    SmartDashboard.setDefaultNumber("Climber Encoder", 0);
    SmartDashboard.setDefaultNumber("Climber RPM", 0);
    SmartDashboard.setDefaultNumber("Climber Voltage Compensation", 0);

    SmartDashboard.setDefaultBoolean("Compressor State", false);
    SmartDashboard.setDefaultNumber("Pneumatic Presure", 0);

    SmartDashboard.setDefaultBoolean("Right Limit Switch", false);
    SmartDashboard.setDefaultBoolean("Left Limit Switch", false);
    SmartDashboard.setDefaultBoolean("Limit Switches", false);
  }

  private void updateSmartDashbaord() {
    SmartDashboard.putNumber("Left Encoder", drivebase.getLeftEncoder());
    SmartDashboard.putNumber("Right Encoder", drivebase.getRightEncoder());
    SmartDashboard.putNumber(
      "Encoder Distance in Inches",
      drivebase.getAverageEncoderDistance()
    );

    SmartDashboard.putNumber("navX Angle", navX.getAngle());
    SmartDashboard.putNumber("navX Rate", navX.getRate());
    SmartDashboard.putNumber("Angle Error", DriveToAngle.error);

    DriveToAngle.kP =
      SmartDashboard.getNumber("Angle P value", DriveToAngle.kP);
    DriveToAngle.kI =
      SmartDashboard.getNumber("Angle I value", DriveToAngle.kI);
    DriveToAngle.kD =
      SmartDashboard.getNumber("Angle D value", DriveToAngle.kD);
    
    SmartDashboard.putNumber("Climb Speed", operatorController.getRightY());
    SmartDashboard.putNumber("Climber Encoder", climb.encoderPosition());
    SmartDashboard.putNumber("Climber RPM", climb.getRPMofClimber());
    SmartDashboard.putNumber("Climber Voltage Compensation", climb.getVoltageSpike());

    SmartDashboard.putBoolean("Compressor State", pneumatics.compressorState());
    SmartDashboard.putNumber("Pneumatic Presure", pneumatics.pneumaticPressure());

    SmartDashboard.putBoolean("Right Limit Switch", climb.bottomRightLimitSwitch.get());
    SmartDashboard.putBoolean("Left Limit Switch", climb.bottomLeftLimitSwitch.get());
    SmartDashboard.putBoolean("Limit Switches", climb.bottomLimitReached());
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

    //run commands with joysticks
    // new JoystickButton(leftJoystick, 4).whenPressed(new DriveToAngle(-45));
    // new JoystickButton(leftJoystick, 6).whenPressed(new DriveToAngle(-90));
    // new JoystickButton(leftJoystick, 2).whenPressed(new DriveToAngle(180));
    // new JoystickButton(leftJoystick, 5).whenPressed(new DriveToAngle(90));
    // new JoystickButton(leftJoystick, 3).whenPressed(new DriveToAngle(45));

    // new JoystickButton(leftJoystick, 5).whenHeld(new ExtendIntake());
    // new JoystickButton(leftJoystick, 6).whenHeld(new RetractIntake());

    new JoystickButton(rightJoystick, 3).whileHeld(new DriveHold(.5));
    new JoystickButton(rightJoystick, 5).whileHeld(new DriveHold(1));
    new JoystickButton(rightJoystick, 6).whileHeld(new DriveHold(-1));
    new JoystickButton(rightJoystick, 4).whileHeld(new DriveHold(-.5));

    new JoystickButton(rightJoystick, 2).whileHeld(new DriveHold(.75));
    
    //A botton
    new JoystickButton(operatorController, 2).whenPressed(new IntakeDeployer(DoubleSolenoid.Value.kForward));
    //Y botton
    new JoystickButton(operatorController, 4).whenPressed(new IntakeDeployer(DoubleSolenoid.Value.kReverse));

    new JoystickButton(leftJoystick, ButtonType.kTrigger.value).whenHeld(new StartIntake());

    //new JoystickButton(leftJoystick, ButtonType.kTrigger.value).whenHeld(new StartIntake());
    //new JoystickButton(rightJoystick, ButtonType.kTrigger.value).whenHeld();
    //wait until PID is finished
    //new JoystickButton(leftJoystick, ButtonType.kTrigger.value).whenPressed(new DriveToAngle(180));

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
    updateSmartDashbaord();
  }

  @Override
  public void autonomousInit() {
    //Chooses which auto we do from SmartDashboard
    autonomousCommand = AutonomousProgram.autoChooser.getSelected().construct();

    // schedule the autonomous command (example)
    if (autonomousCommand != null) {
      new SequentialCommandGroup(
        new DriveWait(autoDelayChooser.getSelected()),
        autonomousCommand
      )
        .schedule();
    }

    ledStrip.setDefaultCommand(new LEDBounce(colorChooser.getSelected()));
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running.
    if (autonomousCommand != null) {
      autonomousCommand.cancel();
    }

    new SetLEDColor(RobotMap.LED.TELEOP_COLOR).initialize();

    // ledStrip.setDefaultCommand(
    //   new LEDLeftRight(
    //     new Color(230 / 255D, 30 / 255D, 0 / 255D),
    //     new Color(70D / 255, 0, 20D / 255)
    //   )
    // );

    ledStrip.setDefaultCommand(new LEDRainbowRotate());
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();

    new SetLEDColor(new Color(255, 255, 0)).initialize();
  }

  @Override
  public void disabledInit() {
    new SetLEDColor(RobotMap.LED.DISABLED_COLOR).initialize();
  }
}
