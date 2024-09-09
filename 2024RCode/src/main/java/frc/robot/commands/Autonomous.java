// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

public class Autonomous extends Command {
  

 DriveTrain driveTrain; 
 private boolean finish = false;
 Timer driveTimer;
 Timer headTimer;
 Timer climbliftTimer;
 
  /** Creates a new DriveForwardTimed. */
  public Autonomous(DriveTrain dt) {
    driveTrain = dt;
      addRequirements(driveTrain);
      climbliftTimer = new Timer();
      driveTimer = new Timer();
      headTimer = new Timer();
    // Use addRequirements here to declare subsystem dependencies
    }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTimer.reset();
    driveTimer.start();
    while(driveTimer.get() < Constants.DRIVE_FORWARD_TIME){
      driveTrain.driveForward(Constants.AUTONOMOUS_SPEED);
      
    }
    climbliftTimer.reset();
    climbliftTimer.start();
    // I don't remember why we commented out this feature, but I'll leave it to show how I was doing it before
    /*while(climbliftTimer.get() < 0.5){
    driveTrain.armsUpAUTO();
    }
*/
    headTimer.reset();
    headTimer.start(); 
    while(headTimer.get() < Constants.HEAD_DOWN_TIME){
     //This is what calling a method made in another .java file looks like.
      driveTrain.setRetracted();
    }


  finish = true;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finish;
  }
}
