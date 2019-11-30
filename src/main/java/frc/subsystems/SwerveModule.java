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
import frc.util.Position;

public class SwerveModule {
    public static final double ANGLE_ENCODER_CONV = (9.0*18)/(34*96)*Math.PI*2;
    private double angleKP = 1.5, angleKI = 0.0, angleKD = 1.0, angleKF = 0.0;
    private double angleMinOutput = -0.8, angleMaxOutput = 0.8;
    private double angleTargetValue = 0.0;

    public static final double DRIVE_KP = 0.0, DRIVE_KI = 0.0, DRIVE_KD = 0.0, DRIVE_KF = 0.0;
    private double driveMinOutput = -1.0, driveMaxOutput = 1.0;
    
    private CANSparkMax angleMotor, driveMotor;
    private CANEncoder driveEncoder;
    private CANPIDController driveController, angleController;
    private CANEncoder angleEncoder;

    public final Position modulePosition;

    public SwerveModule(int angleMotorId, int driveMotorId, Position modulePosition) {
        this.angleMotor = new CANSparkMax(angleMotorId, MotorType.kBrushless);
        this.driveMotor = new CANSparkMax(driveMotorId, MotorType.kBrushless);
        this.angleMotor.restoreFactoryDefaults();
        this.driveMotor.restoreFactoryDefaults();
        this.angleMotor.setIdleMode(IdleMode.kBrake);
        this.driveMotor.setIdleMode(IdleMode.kBrake);

        this.angleEncoder = this.angleMotor.getEncoder();
        this.angleEncoder.setPositionConversionFactor(ANGLE_ENCODER_CONV);
        this.angleController = this.angleMotor.getPIDController();
        System.out.println(this.angleController.setP(angleKP));
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

        this.modulePosition = modulePosition;
    }

    public void setVector(Vector2D vector, boolean driving) {
        boolean revirsed = true;
        if(vector.getLength() != 0) {
            revirsed = this.setAngle(vector.getAngle());
        }
        if(driving) {
            if(revirsed) {
                this.setSpeed(vector.getLength()*-1);
            } else {
                this.setSpeed(vector.getLength());
            }
        }
    }

    public void setVector(Vector2D vector) {
        boolean revirsed = true;
        if(vector.getLength() != 0) {
            revirsed = this.setAngle(vector.getAngle());
        }
        if(revirsed) {
            this.setSpeed(vector.getLength()*-1);
        } else {
            this.setSpeed(vector.getLength());
        }
    }

    public boolean setAngle(double angle) { //in radians return false if same direction, true if opposite
        /*this.angleController.setReference(angle, ControlType.kPosition);
        this.angleTargetValue = angle;*/
        angle %= (Math.PI*2);
        double currValue = this.angleEncoder.getPosition();
        double modularCurrValue = currValue%(Math.PI*2);
        double fullRotationAdd = ((int)(currValue/(Math.PI*2)))*Math.PI*2;
        if(Math.abs(angle-modularCurrValue) <= Math.PI/2) {
            this.angleController.setReference(fullRotationAdd+angle, ControlType.kPosition);
            return false;
        } 
        if(Math.abs(angle-modularCurrValue) > Math.PI/2 && Math.abs(angle-modularCurrValue) < Math.PI*1.5) {
            if(angle-modularCurrValue > 0) {
                this.angleController.setReference(fullRotationAdd+(angle-Math.PI), ControlType.kPosition);
            } else {
                this.angleController.setReference(fullRotationAdd+(angle+Math.PI), ControlType.kPosition);
            }
            return true;
        } 
        if(angle-modularCurrValue > 0) {
            this.angleController.setReference(fullRotationAdd+(angle-(Math.PI*2)), ControlType.kPosition);
        } else {
            this.angleController.setReference(fullRotationAdd+(angle+(Math.PI*2)), ControlType.kPosition);
        }
        return false;
    }

    public void setAngleDeg(double angle) { //in degrees
        this.setAngle(angle/180*Math.PI);
    }

    public void setSpeed(double percentOutput) {
        this.driveMotor.set(percentOutput);
    }

    public void updateAnglePIDF(double kP, double kI, double kD, double kF, double min, double max) {
        /*boolean changed = false;
        if(this.angleKP != kP) {
            this.angleController.setP(kP);
            this.angleKP = kP;
            changed = true;
        }
        if(this.angleKI != kI) {
            this.angleController.setI(kI);
            this.angleKI = kI;
            changed = true;
        }
        if(this.angleKD != kD) {
            this.angleController.setD(kD);
            this.angleKD = kD;
            changed = true;
        }
        if(this.angleKF != kF) {
            this.angleController.setFF(kF);
            this.angleKF = kF;
            changed = true;
        }
        if(this.angleMinOutput != min) {
            this.angleController.setOutputRange(min, max);
            changed = true;
        } else if(this.angleMaxOutput != max) {
            this.angleController.setOutputRange(min, max);
            changed = true;
        }
        if(changed) {
            printConstants();
        }*/
        System.out.println("altering constants");
        System.out.println(this.angleController.setP(kP));
        System.out.println(this.angleController.setD(kD));
        System.out.println(this.angleController.setOutputRange(min, max));
    }

    public void printConstants() {
        System.out.println(this.angleController.getP());
        System.out.println(this.angleController.getI());
        System.out.println(this.angleController.getD());
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

    public double getAngleError() {
        return this.angleTargetValue - this.angleEncoder.getPosition();
    }

    public double getTargetRadians() {
        return this.angleTargetValue;
    }

    public double getEncoderValue() {
        return this.angleEncoder.getPosition();
    }
}
