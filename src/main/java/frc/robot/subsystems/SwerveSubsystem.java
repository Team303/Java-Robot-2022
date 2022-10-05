package frc.robot.subsystems;

import java.lang.Math;

import frc.robot.RobotMap.Swerve;
import frc.robot.Robot;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;


public class SwerveSubsystem extends SubsystemBase {

    ChassisSpeeds speeds;

    Translation2d m_frontLeftLocation;
    Translation2d m_backLeftLocation;
    Translation2d m_frontRightLocation;
    Translation2d m_backRightLocation;

    SwerveDriveKinematics m_kinematics;

    SwerveModuleState[] moduleStates;

    private final SwerveSubsystem.SwerveDriveWheel LEFT_FRONT_DRIVE_WHEEL;
    private final SwerveSubsystem.SwerveDriveWheel LEFT_BACK_DRIVE_WHEEL;
    private final SwerveSubsystem.SwerveDriveWheel RIGHT_FRONT_DRIVE_WHEEL;
    private final SwerveSubsystem.SwerveDriveWheel RIGHT_BACK_DRIVE_WHEEL;

    private final CANSparkMax LEFT_FRONT_DRIVE_SPEED_MOTOR;
    private final CANSparkMax LEFT_BACK_DRIVE_SPEED_MOTOR;
    private final CANSparkMax RIGHT_FRONT_DRIVE_SPEED_MOTOR;
    private final CANSparkMax RIGHT_BACK_DRIVE_SPEED_MOTOR;

    private final CANSparkMax LEFT_FRONT_DRIVE_DIRECTION_MOTOR;
    private final CANSparkMax LEFT_BACK_DRIVE_DIRECTION_MOTOR;
    private final CANSparkMax RIGHT_FRONT_DRIVE_DIRECTION_MOTOR;
    private final CANSparkMax RIGHT_BACK_DRIVE_DIRECTION_MOTOR;

    private final RelativeEncoder LEFT_FRONT_DRIVE_SPEED_ENCODER;
    private final RelativeEncoder LEFT_BACK_DRIVE_SPEED_ENCODER;
    private final RelativeEncoder RIGHT_FRONT_DRIVE_SPEED_ENCODER;
    private final RelativeEncoder RIGHT_BACK_DRIVE_SPEED_ENCODER;

    public class SwerveDriveWheel{

        private final PIDController directionController;
        private final CANSparkMax directionMotor;
        private final CANSparkMax speedMotor;
        private final RelativeEncoder directionEncoder;
        private SwerveModuleState mState;

        private double kp, ki, kd;

        public SwerveDriveWheel(double kp, double ki, double kd, CANSparkMax directionMotor, CANSparkMax speedMotor, RelativeEncoder directionEncoder, SwerveModuleState mState){


            this.kp = kp;
            this.ki = ki;
            this.kd = kd;

            directionController = new PIDController(this.kp, this.ki, this.kd);

            this.mState = mState;
            this.directionMotor = directionMotor;
            this.speedMotor = speedMotor;
            this.directionEncoder = directionEncoder;


        }

        public void setSwerveState(SwerveModuleState mState){
            this.mState = mState;
            this.speedMotor.set(this.mState.speedMetersPerSecond / 10);
            this.directionMotor.set(directionController.calculate(directionEncoder.getPosition(), this.mState.angle.getRadians()));
        }

        public double getSpeed(){
            return mState.speedMetersPerSecond;
        }

        public Rotation2d getAngle(){
            return mState.angle;
        }
    }

    public SwerveSubsystem(){

        m_frontLeftLocation = new Translation2d(Swerve.X_FROM_CENTER, Swerve.Y_FROM_CENTER);
        m_frontRightLocation = new Translation2d(Swerve.X_FROM_CENTER, -Swerve.Y_FROM_CENTER);
        m_backLeftLocation = new Translation2d(-Swerve.X_FROM_CENTER, Swerve.Y_FROM_CENTER);
        m_backRightLocation = new Translation2d(-Swerve.X_FROM_CENTER, -Swerve.Y_FROM_CENTER);

        // Creating my kinematics object using the module locations
        m_kinematics = new SwerveDriveKinematics(
            m_frontLeftLocation, m_frontRightLocation, m_backLeftLocation, m_backRightLocation
        );

        speeds = new ChassisSpeeds();

        moduleStates = m_kinematics.toSwerveModuleStates(speeds);
        
        LEFT_FRONT_DRIVE_DIRECTION_MOTOR = new CANSparkMax(0, MotorType.kBrushed);
        LEFT_BACK_DRIVE_DIRECTION_MOTOR = new CANSparkMax(1, MotorType.kBrushed);
        RIGHT_FRONT_DRIVE_DIRECTION_MOTOR = new CANSparkMax(2, MotorType.kBrushed);
        RIGHT_BACK_DRIVE_DIRECTION_MOTOR = new CANSparkMax(3, MotorType.kBrushed);

        LEFT_FRONT_DRIVE_SPEED_MOTOR = new CANSparkMax(4, MotorType.kBrushed);
        LEFT_BACK_DRIVE_SPEED_MOTOR = new CANSparkMax(5, MotorType.kBrushed);
        RIGHT_FRONT_DRIVE_SPEED_MOTOR = new CANSparkMax(6, MotorType.kBrushed);
        RIGHT_BACK_DRIVE_SPEED_MOTOR = new CANSparkMax(7, MotorType.kBrushed);

        LEFT_FRONT_DRIVE_SPEED_ENCODER = LEFT_FRONT_DRIVE_SPEED_MOTOR.getEncoder();
        LEFT_BACK_DRIVE_SPEED_ENCODER = LEFT_BACK_DRIVE_SPEED_MOTOR.getEncoder();
        RIGHT_FRONT_DRIVE_SPEED_ENCODER = RIGHT_FRONT_DRIVE_SPEED_MOTOR.getEncoder();
        RIGHT_BACK_DRIVE_SPEED_ENCODER = RIGHT_BACK_DRIVE_SPEED_MOTOR.getEncoder();

        LEFT_FRONT_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            LEFT_FRONT_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            LEFT_FRONT_DRIVE_DIRECTION_MOTOR.getEncoder(), 
            moduleStates[0]);

        LEFT_BACK_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            LEFT_BACK_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            LEFT_BACK_DRIVE_DIRECTION_MOTOR.getEncoder(), 
            moduleStates[1]);

        RIGHT_FRONT_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            RIGHT_FRONT_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            RIGHT_FRONT_DRIVE_DIRECTION_MOTOR.getEncoder(), 
            moduleStates[2]);

        RIGHT_BACK_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            RIGHT_BACK_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            RIGHT_BACK_DRIVE_DIRECTION_MOTOR.getEncoder(), 
            moduleStates[3]);
        
    }

    public void setSwerveState(double vxMetersPerSecond, double vyMetersPerSecond, double omegaRadiansPerSecond, Rotation2d robotAngle){
        
        moduleStates = m_kinematics.toSwerveModuleStates(ChassisSpeeds.fromFieldRelativeSpeeds(
            vxMetersPerSecond,
            vyMetersPerSecond, 
            omegaRadiansPerSecond, 
            robotAngle));
        
    }
}
