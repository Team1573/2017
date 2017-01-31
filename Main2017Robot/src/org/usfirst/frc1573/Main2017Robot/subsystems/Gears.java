package org.usfirst.frc1573.Main2017Robot.subsystems;

import org.usfirst.frc1573.Main2017Robot.RobotMap;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;


/**
 *
 */
public class Gears extends Subsystem {
    private final DoubleSolenoid solenoid = RobotMap.gearsSolenoid;

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public void initDefaultCommand() {

        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
    }
    
    public void openPeg() {
    	solenoid.set(Value.kForward);
    }
    
    public void closePeg() {
    	solenoid.set(Value.kReverse);
    }
}

