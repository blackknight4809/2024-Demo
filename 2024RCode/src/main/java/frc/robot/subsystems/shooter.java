// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
//import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class shooter extends SubsystemBase {
  /** Creates a new shooter. */
  XboxController controller = new XboxController(0);//Controller USB port declaration


  //Declaring the Spark Max controllers for the shooter wheels(CANid, MotorType.kBrushless OR MotorType.kBrushed)
  //If you get errors declaring, don't forget to try right clicking and importing needed packages
  CANSparkMax r_FrontShoot = new CANSparkMax(8, MotorType.kBrushless);//Shooter motor
  CANSparkMax r_BackShoot = new CANSparkMax(7, MotorType.kBrushless);//Shooter motor
  CANSparkMax l_FrontShoot = new CANSparkMax(10, MotorType.kBrushless);//Shooter motor
  CANSparkMax l_BackShoot = new CANSparkMax(9, MotorType.kBrushless);//Shooter motor

  //Declaring solenoids for the shooter
  DoubleSolenoid Head = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 2);
  DoubleSolenoid ShootBars = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
 
  public shooter() {
      //setting follower motors "follower motor".follow(leader motor)
      l_BackShoot.follow(l_FrontShoot);
      r_BackShoot.follow(r_FrontShoot);

      //inverting the motors until things move right
      l_FrontShoot.setInverted(true);
      r_FrontShoot.setInverted(false);
  }

  

    //Making booleans, doubles, and ints for later assignment in the periodic function below
  boolean xboxYbutton;
  boolean xboxBbutton;
  boolean xboxRBbutton;
  boolean xboxStartButton;
  int headToggle = 1;

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    xboxYbutton = controller.getRawButton(4);
    xboxBbutton = controller.getRawButton(2);
    xboxStartButton = controller.getRawButton(8);
    xboxRBbutton = controller.getRawButton(6);

     if(xboxBbutton == true){
    l_FrontShoot.set(-.43);
    r_FrontShoot.set(-.43);
  }
  else if (xboxBbutton== false){
    l_FrontShoot.set(0);
    r_FrontShoot.set(0);
  }
//Shooter bars
if (xboxYbutton == true){
  ShootBars.set(Value.kForward);
}
else if(xboxYbutton == false){
  ShootBars.set(Value.kReverse);
}


//Do you remember exactly what you changed? I don't know where the issue lies right now
//Double check that the hardware on the robot looks in order.
//The code looks very similar to how I left it.

//I put the code that I think I substituted in below your code for the pneumatics


//Pneumatics
//This was my method of trying to create a toggle. It was not ideal, but it did the job
if (xboxStartButton==true){
  headToggle = headToggle*-1;
}
if (xboxRBbutton == true && headToggle == 1) {
  headUp();
}
else if (xboxRBbutton == true && headToggle == -1){
  headDown();
}
//I'm pretty sure this is what I had put down because I just modified what you already had
/*
if (xboxRBbutton==true){
  headToggle = headToggle*-1;
}
if (xboxRBbutton == false && headToggle == 1) {
  headUp();
}
else if (xboxRBbutton == false && headToggle == -1){
  headDown();
}
*/

  Head.set(Value.kForward);
}



// These are both methods
  public void headUp(){
    Head.set(Value.kReverse);
  }
  
  public void headDown(){
    Head.set(Value.kForward);
  }

}
