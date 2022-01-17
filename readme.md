# Java Command Based Robot

This is a new version of our robot code for 2022 written with the WPILib Command API

The overall structure of the code is much nicer as it uses a declarative programming paradigm

This means that rather than focusing on HOW to do things, you can focus on the import parts like WHAT to do

In a command based robot everything is either a Subsystem or a Command

## Subsystems

A subsystem is just a code representation of a physicial subsystem on the Robot. This could be the drivebase, the shooter, or the elevator for example. Each subsystem defines methods about how it functions and certain things it can do. This, naturally, lends itself to a very nice object oriented programming pattern that is easy to understand.

## Commands

A command is just a specific action that can be dispatched to the robot to actually perform a task. This could be to drive straight, lift the elevator, or spin the shooter flywheel. Something important to understand is that commands have what is called a dependency. A dependency is a subsystem that the command relies on and interacts with during its life span. Knowing what subsystems a command interacts with actually allows us to schedule multiple commands at the same time without interferig with one another. For instance, we could fireup the shooter WHILE continuing to perform an infinite number of driving tasks simoultaneously. This was quite hard to do with our previous action implementation, and is a large advantage that commands have.

### Autonomous

These command based actions make programming autonomous modes feel relatively the same, but the new API is a bit more versatile in its nature. You still build the program by stringing a series of commands together which can execute sequentially or in parallel. This is called a command group. Command groups are actually themselves also a command which means you can have recursive command definitions with a large command tree of sequential and parallel commands.

### Everything is a Command

The other major advantage of the fully command based system, is that everything even during tele-op is controlled by a command. This allows us to without any extra code, use any of our predefined commands during tele-op and just assign them to our IO inputs.

### Tele-op

You might be asking, how do we get continuous input in a system that requires commands to be programmed together?! And that is a good question. During teleop, you can define a "default" command which gets run every tele-op period just like you normally would have in `teleopPeriodic()`