package frc.util;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.PIDOutput;

public class SparkMaxPIDOutput implements PIDOutput{
    CANSparkMax motor;

    public SparkMaxPIDOutput(CANSparkMax motor) {
        this.motor = motor;
    }

    @Override
    public void pidWrite(double output) {
        this.motor.set(output);
    }
}