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
  public static final boolean PID_TUNING = true;

  public SwerveDrive drivetrain;
  private XboxController gamepad;

  public Robot() {
    this.gamepad = new XboxController(0);
    this.drivetrain = new SwerveDrive();
  }

  @Override
  public void robotInit() {
    if(PID_TUNING) {
      SmartDashboard.putNumber("P Gain", this.drivetrain.getModule(0).getAngleKP());
      SmartDashboard.putNumber("I Gain", this.drivetrain.getModule(0).getAngleKI());
      SmartDashboard.putNumber("D Gain", this.drivetrain.getModule(0).getAngleKD());
      SmartDashboard.putNumber("Feed Forward", this.drivetrain.getModule(0).getAngleKF());
      SmartDashboard.putNumber("Max Output", this.drivetrain.getModule(0).getAngleMinOutput());
      SmartDashboard.putNumber("Min Output", this.drivetrain.getModule(0).getAngleMaxOutput());
      SmartDashboard.putNumber("Set Target", 0);
    }
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
  public void teleopPeriodic() {
    if(PID_TUNING) {
      double p = SmartDashboard.getNumber("P Gain", this.drivetrain.getModule(0).getAngleKP());
      double i = SmartDashboard.getNumber("I Gain", this.drivetrain.getModule(0).getAngleKI());
      double d = SmartDashboard.getNumber("D Gain", this.drivetrain.getModule(0).getAngleKD());
      double ff = SmartDashboard.getNumber("Feed Forward", this.drivetrain.getModule(0).getAngleKF());
      double max = SmartDashboard.getNumber("Max Output", this.drivetrain.getModule(0).getAngleMaxOutput());
      double min = SmartDashboard.getNumber("Min Output", this.drivetrain.getModule(0).getAngleMinOutput());
      this.drivetrain.getModule(0).updateAnglePIDF(p, i, d, ff, min, max);
      this.drivetrain.getModule(0).setAngleDeg(SmartDashboard.getNumber("Set Target", 0));
    } else {
      double x,y,r;
      x = this.gamepad.getRawAxis(1);
      y = this.gamepad.getRawAxis(2);
      r = this.gamepad.getRawAxis(4);
      this.drivetrain.drive(x,y,r);
    }
  }

  @Override
  public void testPeriodic() {
  }
}
