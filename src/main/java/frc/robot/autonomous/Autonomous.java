package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveWait;
import frc.robot.commands.Intake.StartIntake;

import static frc.robot.autonomous.AutonomousProgram.create;

public class Autonomous {
	public static void init() {
		create("Drive Straight", () -> new DriveDistance(6, 1));
		create("Drive Wait Drive",
			() -> new SequentialCommandGroup(
					new DriveDistance(12, 1),
					new DriveWait(2),
					new DriveDistance(12, 1)));
		create("Drive Straight and Pick Up", 
			() -> new SequentialCommandGroup(
					new ParallelCommandGroup(
						new DriveDistance(12,1), 
						new StartIntake())));
	}
}
