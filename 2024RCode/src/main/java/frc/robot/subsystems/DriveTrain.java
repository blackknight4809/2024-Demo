// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.math.filter.SlewRateLimiter;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;


public class DriveTrain extends SubsystemBase {
  /** Creates a new DriveTrain. */
  XboxController controller = new XboxController(0);//Controller USB port declaration
  Spark pickupWheels = new Spark(0);
  Spark rightDart = new Spark(1); 
  TalonSRX leftDart = new TalonSRX(); 
  CANSparkMax l_FrontDrive = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax l_BackDrive = new CANSparkMax(1, MotorType.kBrushless);
  CANSparkMax r_FrontDrive = new CANSparkMax(2,MotorType.kBrushless);
  CANSparkMax r_BackDrive = new CANSparkMax(3, MotorType.kBrushless);


//Declaring the Spark Max controllers for the shooter wheels(CANid, MotorType.kBrushless OR MotorType.kBrushed)  ADDED
  //If you get errors declaring, don't forget to try right clicking and importing needed packages
  CANSparkMax r_FrontShoot = new CANSparkMax(8, MotorType.kBrushless);//Shooter motor
  CANSparkMax r_BackShoot = new CANSparkMax(7, MotorType.kBrushless);//Shooter motor
  CANSparkMax l_FrontShoot = new CANSparkMax(10, MotorType.kBrushless);//Shooter motor
  CANSparkMax l_BackShoot = new CANSparkMax(9, MotorType.kBrushless);//Shooter motor

  
  //Pnuematics declaration
  DoubleSolenoid Head = new DoubleSolenoid(PneumaticsModuleType.REVPH, 3, 2);
  DoubleSolenoid Pickup = new DoubleSolenoid(PneumaticsModuleType.REVPH, 4, 5);
  //ADDED FROM DRIVETRAIN
  DoubleSolenoid ShootBars = new DoubleSolenoid(PneumaticsModuleType.REVPH, 0, 1);
  

 // DifferentialDrive tempShooter;
  DifferentialDrive differentialDrive;

  SlewRateLimiter driveLimit = new SlewRateLimiter(0.35);
  SlewRateLimiter dartLimit = new SlewRateLimiter(0.5);

  public DriveTrain() {
    differentialDrive = new DifferentialDrive(l_FrontDrive, r_FrontDrive);

//limelight code
 NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
  NetworkTableEntry tx = table.getEntry("tx");
  NetworkTableEntry ty = table.getEntry("ty");
  NetworkTableEntry ta = table.getEntry("ta");
  double x = tx.getDouble(0.0);
  double y = ty.getDouble(0.0);
  double area = ta.getDouble(0.0);
    
    //setting follower motors "follower motor".follow(leader motor)
    l_BackDrive.follow(l_FrontDrive);
    r_BackDrive.follow(r_FrontDrive);
    
//Inverting motors until everything moves right
    l_FrontDrive.setInverted(true);
    l_BackDrive.setInverted(true);
    r_FrontDrive.setInverted(false); // Not explicitly needed, used as more of a safety
    pickupWheels.setInverted(true);
   

  }
//ADDED FROM SHOOTER
  public void shooter() {
    //setting follower motors "follower motor".follow(leader motor)
    l_BackShoot.follow(l_FrontShoot);
    r_BackShoot.follow(r_FrontShoot);

    //inverting the motors until things move right
    l_FrontShoot.setInverted(true);
    r_FrontShoot.setInverted(false);
}
  //Making booleans, doubles, and ints for later assignment in the periodic function below
  boolean xboxAbutton;
  boolean xboxXbutton;   
  double xboxLT;
  double xboxRT;
  int DartControl;
  //ADDED FROM SHOOTER
  boolean xboxYbutton;
  boolean xboxBbutton;
  boolean xboxRBbutton;
  boolean xboxStartButton;
  int headToggle = 1;


  @Override
  public void periodic() {
    // This method will be called once per scheduler run 
    xboxAbutton = controller.getRawButton(1);
    xboxLT = controller.getLeftTriggerAxis();
    xboxRT = controller.getRightTriggerAxis();
    DartControl = controller.getPOV();


//Pickup and wheels
if(xboxLT >= 0.75){
  //This is what calling a method looks like
  pickup();
}
if(xboxRT >= 0.75){
  reversePickup();
}
else if(xboxLT <= 0.75 && xboxRT<=0.75){
  Pickup.set(Value.kReverse);
  pickupWheels.set(0);
} 

//climbing (Code for one controller climb plan)
// "||" means OR and can be used in place of making multiple if statements that do the same thing
// If statements with OR conditions will execute if any of the conditions are met
if(DartControl==0 || DartControl==45 || DartControl==315){
 leftDart.set(.82);
 rightDart.set(.75);
}
else if(DartControl==180 || DartControl==135 || DartControl ==225){
  leftDart.set(-.82);
  rightDart.set(-.75);
}
else{
  leftDart.set(0);
  rightDart.set(0);
    }

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
//Pneumatics - Brought over from Shooter
if (xboxStartButton==true){
  headToggle = headToggle*-1;
}
if (xboxRBbutton == true && headToggle == 1) {
  headUp();
}
else if (xboxRBbutton == true && headToggle == -1){
  headDown();
}

Head.set(Value.kForward);


}

//Everything below here is methods 

//This is your main driving method (DRIVETRAINSPEED can be found in constants.java)
  public void driveWithJoysticks(XboxController controller, double DRIVETRAINSPEED){
    differentialDrive.arcadeDrive(controller.getLeftY()*DRIVETRAINSPEED, controller.getLeftX()*DRIVETRAINSPEED); 
    }
  
//driveForward and driveBackward are both meant to be autonomous driving methods (AUTONOMOUS_SPEED can be found in contants.java)
  public void driveForward(double AUTONOMOUS_SPEED){
    differentialDrive.tankDrive(-AUTONOMOUS_SPEED, -AUTONOMOUS_SPEED);
  }
  
  public void driveBackward(double AUTONOMOUS_SPEED){
      differentialDrive.tankDrive(AUTONOMOUS_SPEED, AUTONOMOUS_SPEED);
  }
  
  //this method is used in autonomous to stop the driving motors if auto gets interrupted
  public void stop(){
    differentialDrive.stopMotor();
  }

  // setRetracted and setExtended simply controlled whether the head was up or down
  // You could call these to activate on a button press or to be used in another method
  public void setRetracted(){
    Head.set(Value.kForward);
  } 

  public void setExtended(){
    Head.set(Value.kReverse);
  }

// The arms methods were made to be called to control both darts in unison. 
// They use a slewratelimiter called dartLimit to make the darts less aggressive and jittery
  public void armsUp(){
    rightDart.set(dartLimit.calculate(.10));
    leftDart.set(dartLimit.calculate(.22));
  }

  public void armsUpAUTO(){
    rightDart.set(dartLimit.calculate(.10));
    leftDart.set(dartLimit.calculate(0.22));
  }

  public void armsStop(){
    rightDart.set(0);
    leftDart.set(0);
  }

  //pickup and reversePickup were both methods that were called to run multiple tasks at the same time for picking stuff up
public void pickup(){
  Head.set(Value.kReverse);
  Pickup.set(Value.kForward);
  pickupWheels.set(0.7);
}
//reversePickups was used specifically for when a ring got stuck and needed to be pulled out and reloaded
public void reversePickup(){
Head.set(Value.kReverse);
pickupWheels.set(-0.7);
}

// These are both methods
public void headUp(){
  Head.set(Value.kReverse);
}

public void headDown(){
  Head.set(Value.kForward);
}



}
