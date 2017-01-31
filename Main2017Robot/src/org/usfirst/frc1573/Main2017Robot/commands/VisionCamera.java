package org.usfirst.frc1573.Main2017Robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc1573.Main2017Robot.Robot;

/**
 *
 */
public class VisionCamera extends Command {

	public VisionCamera() {
        requires(Robot.cameras);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.cameras.visionModeLite();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.cameras.drivingMode();
    }
}
