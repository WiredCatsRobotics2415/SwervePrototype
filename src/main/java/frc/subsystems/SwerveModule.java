/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

/**
 * Add your docs here.
 */
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDController;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import util.SparkMaxPIDOutput;

public class SwerveModule {
    CANSparkMax angleMotor, driveMotor;
    CANEncoder driveEncoder;
    CANPIDController driveController;
    AnalogInput angleEncoder;
    PIDController angleController;

    public SwerveModule(int angleMotorId, int driveMotorId) {
        this.angleMotor = new CANSparkMax(angleMotorId, MotorType.kBrushless);
        this.driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless);

        this.angleEncoder = new AnalogInput(0);
        this.angleController = new PIDController(Kp, Ki, Kd, this.angleEncoder, output);

        this.driveEncoder = this.driveMotor.getEncoder();
        this.driveController = this.angleMotor.getPIDController();
    }
}
