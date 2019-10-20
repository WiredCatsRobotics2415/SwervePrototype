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
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;

import frc.util.Vector2D;

public class SwerveModule {
    private double angleKP = 0.0, angleKI = 0.0, angleKD = 0.0, angleKF = 0.0;
    public static final double ANGLE_ENCODER_CONV = (9.0*18)/(42*34*96);
    private double angleMinOutput = -1.0, angleMaxOutput = 1.0;

    public static final double DRIVE_KP = 0.0, DRIVE_KI = 0.0, DRIVE_KD = 0.0, DRIVE_KF = 0.0;
    private double driveMinOutput = -1.0, driveMaxOutput = 1.0;
    
    CANSparkMax angleMotor, driveMotor;
    CANEncoder driveEncoder;
    CANPIDController driveController, angleController;
    CANEncoder angleEncoder;

    public SwerveModule(int angleMotorId, int driveMotorId) {
        this.angleMotor = new CANSparkMax(angleMotorId, MotorType.kBrushless);
        this.driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless);
        this.angleMotor.restoreFactoryDefaults();
        this.driveMotor.restoreFactoryDefaults();
        this.angleMotor.setIdleMode(IdleMode.kBrake);
        this.driveMotor.setIdleMode(IdleMode.kBrake);

        this.angleEncoder = this.angleMotor.getEncoder();
        this.angleEncoder.setPositionConversionFactor(ANGLE_ENCODER_CONV);
        this.angleController = this.angleMotor.getPIDController();
        this.angleController.setP(angleKP);
        this.angleController.setI(angleKI);
        this.angleController.setD(angleKD);
        this.angleController.setFF(angleKF);
        this.angleController.setOutputRange(this.angleMinOutput, this.angleMaxOutput);

        this.driveEncoder = this.driveMotor.getEncoder();
        this.driveController = this.angleMotor.getPIDController();
        this.driveController.setP(DRIVE_KP);
        this.driveController.setI(DRIVE_KI);
        this.driveController.setD(DRIVE_KD);
        this.driveController.setFF(DRIVE_KF);
        this.driveController.setOutputRange(driveMinOutput,driveMaxOutput);
    }

    public void setVector(Vector2D vector) {
        setAngle(vector.getAngle());
        setSpeed(vector.getLength());
    }

    public void setAngle(double angle) { //in radians (Might have issue with negative currValues)
        angle %= Math.PI*2;
        double currValue = this.angleEncoder.getPosition();
        double minCheck, maxCheck;
        int multiple = (int)(currValue/(Math.PI*2));
        if(angle > currValue%(Math.PI*2)) {
            multiple--;
        }
        minCheck = multiple*Math.PI*2+angle;
        maxCheck = (multiple+1)*Math.PI*2+angle;
        if(Math.abs(currValue-minCheck) < Math.abs(maxCheck-currValue)) {
            this.angleController.setReference(minCheck, ControlType.kPosition);
        } else {
            this.angleController.setReference(maxCheck, ControlType.kPosition);
        }
    }

    public void setAngleDeg(double angle) { //in degrees
        this.setAngle(angle/Math.PI*360);
    }

    public void setSpeed(double percentOutput) {
        this.driveMotor.set(percentOutput);
    }

    public void updateAnglePIDF(double kP, double kI, double kD, double kF, double min, double max) {
        if(this.angleKP != kP) {
            this.angleController.setP(kP);
            this.angleKP = kP;
        }
        if(this.angleKI != kI) {
            this.angleController.setI(kI);
            this.angleKI = kI;
        }
        if(this.angleKD != kD) {
            this.angleController.setD(kD);
            this.angleKD = kD;
        }
        if(this.angleKF != kF) {
            this.angleController.setFF(kF);
            this.angleKF = kF;
        }
        if(this.angleMinOutput != min) {
            this.angleController.setOutputRange(min, max);
        } else if(this.angleMaxOutput != max) {
            this.angleController.setOutputRange(min, max);
        }
    }

    public double getAngleKP() {
        return this.angleKP;
    }

    public double getAngleKI() {
        return this.angleKI;
    }

    public double getAngleKD() {
        return this.angleKD;
    }

    public double getAngleKF() {
        return this.angleKF;
    }

    public double getAngleMinOutput() {
        return this.angleMinOutput;
    }

    public double getAngleMaxOutput() {
        return this.angleMaxOutput;
    }
}
