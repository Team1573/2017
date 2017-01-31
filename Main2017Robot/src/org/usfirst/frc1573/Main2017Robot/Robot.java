package org.usfirst.frc1573.Main2017Robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc1573.Main2017Robot.commands.*;
import org.usfirst.frc1573.Main2017Robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Command autonomousCommand;

	public static OI oi;
	public static Gears gears;
	public static Driving driving;
	public static Climbing climbing;
	public static Cameras cameras;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		RobotMap.init();
		gears = new Gears();
		driving = new Driving();
		climbing = new Climbing();
		cameras = new Cameras();

		// OI must be constructed after subsystems. If the OI creates Commands
		// (which it very likely will), subsystems are not guaranteed to be
		// constructed yet. Thus, their requires() statements may grab null
		// pointers. Bad news. Don't move it.
		oi = new OI();

		// instantiate the command used for the autonomous period
		autonomousCommand = new AutonomousCommand();
	}

	/**
	 * This function is called when the disabled button is hit. You can use it
	 * to reset subsystems before shutting down.
	 */
	public void disabledInit() {
		oi.driverJoystick.setRumble(RumbleType.kRightRumble, 0);
		oi.driverJoystick.setRumble(RumbleType.kLeftRumble, 0);
	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	public void autonomousInit() {
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		if (!SmartDashboard.containsKey("fwdTime")) {
			SmartDashboard.putNumber("fwdTime", 3.1);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		cameras.drivingMode();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		driving.drive(oi.driverJoystick.getRawAxis(1) + oi.driverJoystick.getRawAxis(5) / 2,
				oi.driverJoystick.getRawAxis(0) + oi.driverJoystick.getRawAxis(4) * 0.65);
	}

	public void robotPeriodic() {
		double l = SmartDashboard.getNumber("LeftMarkX", 1);
		double r = SmartDashboard.getNumber("RightMarkX", 1);
		double w = SmartDashboard.getNumber("imgWidth", 1);
		boolean found = SmartDashboard.getBoolean("MarksFound", false);

		l /= w;
		r /= w;
		
		if (cameras.isVisionMode) {
			SmartDashboard.putNumber("PegPos", 100 * (1 - Math.abs(1 - (l + r))));
			SmartDashboard.putBoolean("MarksFound2", found);
		} else {
			SmartDashboard.putBoolean("MarksFound2", false);
		}
		

		if (found) {
			SmartDashboard.putNumber("LRumble", Math.max(-l - r + 1, 0));
			SmartDashboard.putNumber("RRumble", Math.max(l + r - 1, 0));
			//oi.driverJoystick.setRumble(RumbleType.kLeftRumble, Math.max(-l - r + 1, 0));
			//oi.driverJoystick.setRumble(RumbleType.kRightRumble, Math.max(l + r - 1, 0));
		} else {
			SmartDashboard.putNumber("LRumble", 0);
			SmartDashboard.putNumber("RRumble", 0);
			oi.driverJoystick.setRumble(RumbleType.kLeftRumble, 0);
			oi.driverJoystick.setRumble(RumbleType.kRightRumble, 0);
		}

		SmartDashboard.putBoolean("IsInside", RobotMap.gearsSensor.getAverageVoltage() > 1.25);
		SmartDashboard.putBoolean("ReachedEnd", RobotMap.climbingSensor.get());
		SmartDashboard.putNumber("Dist", RobotMap.gearsSensor.getAverageVoltage());
	}

	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}
}
