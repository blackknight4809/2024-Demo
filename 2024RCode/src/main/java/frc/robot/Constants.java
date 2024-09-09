// Copyright (c) FIRS%T and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {

    //Joysticks
    public static final int DRIVER_CONTROLLER = 0;// this is the port number the controller is assinged in the DriverStation
   
// Xbox Joystick axis (found in driver station) 
    public static final int XBOX_LEFT_Y_AXIS = 1;
    public static final int XBOX_LEFT_X_AXIS = 0;
    public static final int XBOX_Right_Y_AXIS = 5;

// Xbox joystick assignment (found in driver station)
    public static final int JOYSTICK_Drive = 0;

    //Drivetrain speeds/times
    public static final double SHOOTERSPEED = -.35; 
    
    public static final double DRIVETRAINSPEED = 1;//1 is 100% speed
    
    public static final double HEAD_DOWN_TIME = 3.5;
    public static final double DRIVE_FORWARD_TIME = 1.25; // 3.0 seconds
    public static final double AUTONOMOUS_SPEED = 0.6; // 0.6 is 60% speed
}
    