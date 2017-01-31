package org.usfirst.frc1573.Main2017Robot.subsystems;

import org.usfirst.frc1573.Main2017Robot.RobotMap;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;

import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Driving extends Subsystem {
    private final RobotDrive chassi = RobotMap.drivingchassi;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void drive(Joystick stick) {
    	chassi.arcadeDrive(stick);
    }
    
    public void drive(double movement, double rotation) {
    	chassi.arcadeDrive(movement, rotation);
    }
}

