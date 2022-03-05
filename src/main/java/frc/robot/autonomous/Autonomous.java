package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveWait;
import frc.robot.commands.Intake.StartIntake;

import static frc.robot.autonomous.AutonomousProgram.create;


/*
Quick guide to Comand Groups

SequentialComandGroup:
	Will run all comands in order within it's parentheses
	Note: If a comand does not have a isFinshed statment the code will be stuck on that command forever 

ParallelCommandGroup:
	Will run commands in parallel if they use diffrent SubSystems
	Note: Both commands will have to finish to move on

ParallelRaceGoup:
	Will run commands in parallel if they use diffrent SubSystems
	Note: As soon as one command runs it's isfinished method runs then both commands will end

ParallelDeadlineGroup
	Will run commands in parallel if they use diffrent SubSystems
	Note: Only the first command will finish the group 

*/
public class Autonomous {
	public static void init() {
		create("Drive Straight 1ft", () -> new DriveDistance(12, .5)); // TODO needs to be 93 inches for comp
		create("Driven 1ft Wait 2 seconds Drive 1ft",
			() -> new SequentialCommandGroup(
					new DriveDistance(12, .5),
					new DriveWait(2),
					new DriveDistance(12, .5)));
		create("Drive Forward 100 inches", () -> new DriveDistance(100, .5));
		create("Drive Backwards 100 inches", () -> new DriveDistance(100, 0.5));
	}
}
