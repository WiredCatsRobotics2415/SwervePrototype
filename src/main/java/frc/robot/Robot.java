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
  public static final boolean PID_TUNING = false;

  public SwerveDrive drivetrain;
  private XboxController gamepad;

  public Robot() {
  }

  @Override
  public void robotInit() {
    this.gamepad = new XboxController(0);
    this.drivetrain = new SwerveDrive();
    this.drivetrain.getModule(0).printConstants();
    if(PID_TUNING) {
      SmartDashboard.putNumber("P Gain", this.drivetrain.getModule(0).getAngleKP());
      SmartDashboard.putNumber("I Gain", this.drivetrain.getModule(0).getAngleKI());
      SmartDashboard.putNumber("D Gain", this.drivetrain.getModule(0).getAngleKD());
      SmartDashboard.putNumber("Feed Forward", this.drivetrain.getModule(0).getAngleKF());
      SmartDashboard.putNumber("Max Output", this.drivetrain.getModule(0).getAngleMaxOutput());
      SmartDashboard.putNumber("Min Output", this.drivetrain.getModule(0).getAngleMinOutput());
      SmartDashboard.putNumber("Set Target", 0);
      SmartDashboard.putNumber("Error", 0);
      SmartDashboard.putNumber("TargetRad", this.drivetrain.getModule(0).getTargetRadians());
      SmartDashboard.putNumber("Encoder Position", this.drivetrain.getModule(0).getEncoderValue());
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
  public void teleopInit() {
    this.drivetrain.getModule(0).printConstants();
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
      SmartDashboard.putNumber("Error", this.drivetrain.getModule(0).getAngleError());
      SmartDashboard.putNumber("TargetRad", this.drivetrain.getModule(0).getTargetRadians());
      SmartDashboard.putNumber("Encoder Position", this.drivetrain.getModule(0).getEncoderValue());
    } else {
      double x,y,r;
      x = this.gamepad.getRawAxis(0);
      y = this.gamepad.getRawAxis(1)*-1;
      r = this.gamepad.getRawAxis(4);
      if(Math.abs(x) < 0.1) x = 0;
      if(Math.abs(y) < 0.1) y = 0;
      if(Math.abs(r) < 0.1) r = 0;
      if(gamepad.getBButton()) {
        this.drivetrain.drive(x,y,r,false);
      }
      this.drivetrain.drive(x,y,r);
    }
    if(gamepad.getAButtonPressed()) {
      this.drivetrain.getModule(0).updateAnglePIDF(1.5, 0, 1.0, 0, -0.8, 0.8);
      this.drivetrain.getModule(0).printConstants();
    }
  }

  @Override
  public void testPeriodic() {
  }
}
