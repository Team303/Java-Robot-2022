package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import com.revrobotics.CANSparkMax;

import frc.robot.RobotMap.Swerve;

public class SwerveWheel extends PIDSubsystem{

	public String name;

	private TalonFX steerMotor;
	private AnalogInput absEnc;
	private CANSparkMax drive;

	private int countsWhenFrwd;

	public SwerveWheel(CANSparkMax drive, int m_steer, int analogEnc, int zeroOffset,
					   String name) {
		super(new PIDController(Swerve.kP, Swerve.kI, Swerve.kD));

		this.name = name;

		this.drive = drive;

		countsWhenFrwd = zeroOffset;

		steerMotor = new TalonFX(m_steer);
		absEnc = new AnalogInput(analogEnc);

		// Reset all of the settings on startup
		steerMotor.configFactoryDefault();

		// Set the feedback device for the steering (turning) Talon SRX
		steerMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);

		// Set the current quadrature position relative to the analog position to make sure motor
		// has 0 position on startup
		steerMotor.setSelectedSensorPosition(getAbsAngleDeg() * Swerve.QUAD_COUNTS_PER_ROT / 180);

		// Set the input range of the PIDF so that it will only accept angles between -180 to 180
		// and set it to continuous
		getController().enableContinuousInput(-180, 180);

		// Sets name for viewing in SmartDashboard
		this.setName(name);
	}

	// Get the current angle of the analog encoder
	private int getAbsAngleDeg() {
		return (int)(180 * (absEnc.getValue() - countsWhenFrwd) / 4096);
	}

	// Get current ticks
	public int getTicks() {
		return (int)steerMotor.getSelectedSensorPosition();
	}

	public void setSpeed(double speed) {
		drive.set(speed);
	}

	// Convert ticks to angle bound from -180 to 180
	public double ticksToAngle(int ticks) {
		double angleTicks = ticks % Swerve.QUAD_COUNTS_PER_ROT;

		double result = (angleTicks / (Swerve.QUAD_COUNTS_PER_ROT / 2)) * 180;

		if (result > 180) {
			result -= 360;
		}

		return result;
	}

	@Override
	protected double getMeasurement() {
		return ticksToAngle(getTicks());
	}

	@Override
	protected void useOutput(double output, double setpoint) {
		steerMotor.set(ControlMode.PercentOutput, output);
	}
}