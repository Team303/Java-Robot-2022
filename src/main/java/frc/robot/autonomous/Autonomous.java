package frc.robot.autonomous;

import static frc.robot.autonomous.AutonomousProgram.create;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.drive.DriveDistance;
import frc.robot.commands.drive.DriveWait;

/**
 * Quick guide to Comand Groups:
 *
 * SequentialComandGroup:
 * Will run all comands in order within it's parentheses
 * Note: If a comand does not have a isFinshed statment the code will be stuck
 * on that command forever
 *
 * ParallelCommandGroup:
 * Will run commands in parallel if they use diffrent SubSystems
 * Note: Both commands will have to finish to move on
 *
 * ParallelRaceGoup:
 * Will run commands in parallel if they use diffrent SubSystems
 * Note: As soon as one command runs it's isfinished method runs then both
 * commands will end
 *
 * ParallelDeadlineGroup
 * Will run commands in parallel if they use diffrent SubSystems
 * Note: Only the first command will finish the group
 */
public class Autonomous {

  public static void init() {
    /* Start with back against hub */
    create(
      "Drive 3ft Forwards, Ram 3ft Back, Drive 8ft Forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(3 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(3 * 12, -1),
          new DriveWait(1),
          new DriveDistance(8 * 12, 0.5)
        )
    );
    create(
      "Drive 3ft Forwards, Ram 3ft Back, Drive 7ft Forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(3 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(3 * 12, -1),
          new DriveWait(1),
          new DriveDistance(7 * 12, 0.5)
        )
    );
    create(
      "Drive 3ft Forwards, Ram 3ft Back, Drive 6ft Forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(3 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(3 * 12, -1),
          new DriveWait(1),
          new DriveDistance(6 * 12, 0.5)
        )
    );

	/* Just Taxi */
    create("Drive Forward 8ft", () -> new DriveDistance(8 * 12, 0.5));
    create("Drive Forward 7ft", () -> new DriveDistance(7 * 12, 0.5));
    create("Drive Forward 6ft", () -> new DriveDistance(6 * 12, 0.5));
    create("Drive Backwards 8ft", () -> new DriveDistance(8 * 12, -0.5));
    create("Drive Backwards 7ft", () -> new DriveDistance(7 * 12, -0.5));
    create("Drive Backwards 6ft", () -> new DriveDistance(6 * 12, -0.5));

	/* Start on line with back facing hub */
    create(
      "Start at line, Ram 4ft Back,, Drive 6ft forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(4 * 12, -1),
          new DriveWait(.5),
          new DriveDistance(6 * 12, 0.5)
        )
    );
    create(
      "Start at line, Ram 4ft Back,, Drive 7ft forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(4 * 12, -1),
          new DriveWait(.5),
          new DriveDistance(7 * 12, 0.5)
        )
    );
    create(
      "Start at line, Ram 4ft Back,, Drive 8ft forwards",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(4 * 12, -1),
          new DriveWait(.5),
          new DriveDistance(8 * 12, 0.5)
        )
    );

	/* Taxi then score */
    create(
      "Taxi 6ft then score",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(6 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(6 * 12, -1)
        )
    );
    create(
      "Taxi 7ft then score",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(7 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(7 * 12, -1)
        )
    );
    create(
      "Taxi 8ft then score",
      () ->
        new SequentialCommandGroup(
          new DriveDistance(8 * 12, .25),
          new DriveWait(0.5),
          new DriveDistance(8 * 12, -1)
        )
    );
  }
}
