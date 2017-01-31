package org.usfirst.frc1573.Main2017Robot.subsystems;

import org.usfirst.frc1573.Main2017Robot.RobotMap;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Climbing extends Subsystem {
    private final SpeedController left = RobotMap.climbingLeft;
    private final SpeedController right = RobotMap.climbingRight;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void set(double speed) {
    	left.set(-speed);
    	right.set(speed);
    }
}


