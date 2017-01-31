package org.usfirst.frc1573.Main2017Robot;

import org.usfirst.frc1573.Main2017Robot.commands.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.*;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    // Joystick stick = new Joystick(port);
    // Button button = new JoystickButton(stick, buttonNumber);

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());
	
    public Joystick driverJoystick;
    public JoystickButton visionModeButton;
    public Joystick mechJoystick;
    public JoystickButton openGearPegButton;
    public JoystickButton climbButton;
    public JoystickButton ejectRopeButton;

    public OI() {
    	// Mech Joystick
        mechJoystick = new Joystick(1);
        
        ejectRopeButton = new JoystickButton(mechJoystick, 7);
        ejectRopeButton.whileHeld(new EjectRope());
        
        climbButton = new JoystickButton(mechJoystick, 3);
        climbButton.whileHeld(new Climb());
        
        openGearPegButton = new JoystickButton(mechJoystick, 1);
        openGearPegButton.whileHeld(new OpenGearPeg());
        
        // Driver Joystick
        driverJoystick = new Joystick(0);
        
        visionModeButton = new JoystickButton(driverJoystick, 1);
        visionModeButton.whileHeld(new VisionCamera());

        // SmartDashboard Buttons
        SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
        SmartDashboard.putData("Climb", new Climb());
        SmartDashboard.putData("Eject Rope", new EjectRope());
        SmartDashboard.putData("Open Gear Peg", new OpenGearPeg());

    }

    public Joystick getDriverJoystick() {
        return driverJoystick;
    }

    public Joystick getMechJoystick() {
        return mechJoystick;
    }

}

