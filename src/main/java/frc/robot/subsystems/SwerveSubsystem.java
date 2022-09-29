package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.PIDController;



public class SwerveSubsystem {

    public class SwerveDriveWheel {

        private final PIDController directionController;
        private final CANSparkMax directionMotor;
        private final CANSparkMax speedMotor;
        private final RelativeEncoder directionEncoder;

        private static final double encoderToAngle = 360/42;
        private double kp, ki, kd;

        public SwerveDriveWheel(double kp, double ki, double kd, CANSparkMax directionM, CANSparkMax speedM, RelativeEncoder directionE){
            this.kp = kp;
            this.ki = ki;
            this.kd = kd;

            directionController = new PIDController(this.kp, this.ki, this.kd);
            directionMotor = directionM;
            directionEncoder = directionE;
            speedMotor = speedM;
        }

        public void setDirection(double setpoint){
            directionMotor.set(directionController.calculate(directionEncoder.getPosition() * encoderToAngle % 360, 
                closestAngle(setpoint, directionEncoder.getPosition() * encoderToAngle)));
        }

        public void setSpeed(double speed){
            speedMotor.set(speed);
        }

        public double getAngle(){
            return directionEncoder.getPosition() * encoderToAngle;
        }

        public void setAngle(double position){
            directionEncoder.setPosition(position);
        }

        public void resetAngle(){
            setAngle(0);
        }

        private double closestAngle(double a, double b){
            double dir = (b % 360.0) - (a % 360.0);

            if (Math.abs(dir) > 180.0)
            {
                    dir = -(Math.signum(dir) * 360.0) + dir;
            }
            return dir;
        }
    }

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

    public SwerveSubsystem(){

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
            LEFT_FRONT_DRIVE_DIRECTION_MOTOR.getEncoder());

        LEFT_BACK_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            LEFT_BACK_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            LEFT_BACK_DRIVE_DIRECTION_MOTOR.getEncoder());

        RIGHT_FRONT_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            RIGHT_FRONT_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            RIGHT_FRONT_DRIVE_DIRECTION_MOTOR.getEncoder());

        RIGHT_BACK_DRIVE_WHEEL = new SwerveSubsystem.SwerveDriveWheel(0, 0, 0, 
            RIGHT_BACK_DRIVE_DIRECTION_MOTOR, 
            LEFT_FRONT_DRIVE_SPEED_MOTOR, 
            RIGHT_BACK_DRIVE_DIRECTION_MOTOR.getEncoder());
    }




}
