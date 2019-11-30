/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;

import frc.subsystems.SwerveDrive;;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public double DEADBAND = 0.1;

  public SwerveDrive drivetrain;
  private XboxController gamepad;

  public Robot() {
  }

  @Override
  public void robotInit() {
    this.gamepad = new XboxController(0);
    this.drivetrain = new SwerveDrive();
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    double x,y,r;
    x = this.gamepad.getRawAxis(0);
    y = this.gamepad.getRawAxis(1)*-1;
    r = this.gamepad.getRawAxis(4);
    if(Math.abs(x) < DEADBAND) x = 0;
    if(Math.abs(y) < DEADBAND) y = 0;
    if(Math.abs(r) < DEADBAND) r = 0;
    this.drivetrain.drive(x,y,r);
  }

  @Override
  public void testPeriodic() {
  }
}
