package org.usfirst.frc1573.Main2017Robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc1573.Main2017Robot.Robot;
import org.usfirst.frc1573.Main2017Robot.RobotMap;

/**
 *
 */
public class AutonomousCommand extends Command {
    int state = 0;
    long startTime = 0;
    Timer rotationTimer = new Timer();
    
    public AutonomousCommand() {

    }
    
    protected void initialize() {
    	state = 0;
		startTime = System.currentTimeMillis();
		Robot.cameras.visionMode();
		Robot.gears.closePeg();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	double l = SmartDashboard.getNumber("LeftMarkX", 1) + SmartDashboard.getNumber("LeftMarkWidth", 1) / 2;
		double r = SmartDashboard.getNumber("RightMarkX", 1) + SmartDashboard.getNumber("RightMarkWidth", 1) / 2;
		double w = SmartDashboard.getNumber("imgWidth", 1);
		l /= w;
		r /= w;
		
		SmartDashboard.putNumber("Dist", RobotMap.gearsSensor.getAverageVoltage());
		
		if (state == 0) { // Drive to perpendicular line
			Robot.driving.drive(-0.5, 0);
			if (System.currentTimeMillis() - startTime > SmartDashboard.getNumber("fwdTime", 2) * 1000) {
				state = 1;
				rotationTimer.reset();
				rotationTimer.start();
			}
		} else if (state == 1) { // Rotate until about centered
			Robot.driving.drive(0, -0.45);
			if (l + r > 1 && SmartDashboard.getBoolean("MarksFound", true)) {
				state = 2;
				rotationTimer.stop();
			}
		} else if (state == 2) { // Actually center
			Robot.gears.openPeg();
			if (SmartDashboard.getBoolean("MarksFound", true)) {
				double rot = l + r - 1;
				rot = Math.sqrt(Math.abs(rot)) * Math.signum(rot);
				Robot.driving.drive(-0.5, 1 * rot);
			} else {
				Robot.driving.drive(-0.5, 0);
			}
			if (r - l > 0.2) {
				state = 3;
			}
		} else if (state == 3) { // Insert gear
			Robot.driving.drive(-0.4, 0);
			if (RobotMap.gearsSensor.getAverageVoltage() > 1.25) {
				state = 4;
				startTime = System.currentTimeMillis();
			}
		} else if (state == 4) { // Get out to beginning line
			Robot.driving.drive(0.6, 0);
			if (System.currentTimeMillis() - startTime > 1000) {
				state = 5;
				startTime = System.currentTimeMillis();
			}
		} else if (state == 5) { // Rotate same rotation, backwards
			Robot.driving.drive(0, 0.45);
			Robot.gears.closePeg();

			if (System.currentTimeMillis() - startTime > rotationTimer.get() * 1000) {
				state = 6;
				startTime = System.currentTimeMillis();
			}
		} else if (state == 6) { // Drive to base line
			Robot.driving.drive(-0.5, 0);
			if (System.currentTimeMillis() - startTime > 1500) {
				state = 7; // Stop
			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return state == 7;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driving.drive(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.driving.drive(0, 0);
    }
}
