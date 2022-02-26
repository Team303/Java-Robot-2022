package frc.robot.autonomous;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveWait;
import frc.robot.commands.drive.DriveToAngle;
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
		create("Drive Straight", () -> new DriveDistance(6, 1));
		create("Drive Wait Drive",
			() -> new SequentialCommandGroup(
					new DriveDistance(12, 1),
					new DriveWait(2),
					new DriveDistance(12, 1)));
		create("Drive Straight and Pick Up", 
			() -> new SequentialCommandGroup(
					new ParallelDeadlineGroup(
						new DriveDistance(12,1), 
						new StartIntake())));
	}
}
