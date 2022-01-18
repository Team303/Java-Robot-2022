package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveWait;

import static frc.robot.autonomous.AutonomousProgram.create;

public class Autonomous {
	public static void init() {
		create("Drive Straight", () -> new DriveDistance(6, 1));
		create("Drive Wait Drive",
				() -> new SequentialCommandGroup(
						new DriveDistance(6, 1),
						new DriveWait(2),
						new DriveDistance(6, 1)));
	}
}
