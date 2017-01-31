package org.usfirst.frc1573.Main2017Robot;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
    public static DoubleSolenoid gearsSolenoid;
    public static AnalogInput gearsSensor;
    public static CANTalon drivingFrontLeft;
    public static CANTalon drivingRearLeft;
    public static CANTalon drivingFrontRight;
    public static CANTalon drivingRearRight;
    public static RobotDrive drivingchassi;
    public static SpeedController climbingLeft;
    public static SpeedController climbingRight;
    public static DigitalInput climbingSensor;

    public static void init() {
        gearsSolenoid = new DoubleSolenoid(5, 0, 1);
        LiveWindow.addActuator("Gears", "Solenoid", gearsSolenoid);
        
        gearsSensor = new AnalogInput(0);
        LiveWindow.addSensor("Gears", "Sensor", gearsSensor);
        
        drivingFrontLeft = new CANTalon(1);
        LiveWindow.addActuator("Driving", "Front Left", drivingFrontLeft);
        
        drivingRearLeft = new CANTalon(2);
        LiveWindow.addActuator("Driving", "Rear Left", drivingRearLeft);
        
        drivingFrontRight = new CANTalon(3);
        LiveWindow.addActuator("Driving", "Front Right", drivingFrontRight);
        
        drivingRearRight = new CANTalon(4);
        LiveWindow.addActuator("Driving", "Rear Right", drivingRearRight);
        
        drivingchassi = new RobotDrive(drivingFrontLeft, drivingRearLeft,
              drivingFrontRight, drivingRearRight);
        
        drivingchassi.setSafetyEnabled(true);
        drivingchassi.setExpiration(0.1);
        drivingchassi.setSensitivity(0.5);
        drivingchassi.setMaxOutput(1.0);

        climbingLeft = new Spark(1);
        LiveWindow.addActuator("Climbing", "Left", (Spark) climbingLeft);
        
        climbingRight = new Spark(2);
        LiveWindow.addActuator("Climbing", "Right", (Spark) climbingRight);
        
        climbingSensor = new DigitalInput(0);
        LiveWindow.addSensor("Climbing", "Sensor", climbingSensor);
        
    }
}
