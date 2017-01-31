package org.usfirst.frc1573.Main2017Robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc1573.Main2017Robot.Robot;
import org.usfirst.frc1573.Main2017Robot.RobotMap;

/**
 *
 */
public class Climb extends Command {
	double m_speed = 0;
    public Climb() {
        requires(Robot.climbing);
        m_speed = 1;
    }
    
    public Climb(double speed) {
    	m_speed = speed;
        requires(Robot.climbing);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.climbing.set(RobotMap.climbingSensor.get() ? m_speed : 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return !RobotMap.climbingSensor.get();
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.climbing.set(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.climbing.set(0);
    }
}
