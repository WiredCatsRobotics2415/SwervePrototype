/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import frc.util.Vector2D;

/**
 * Add your docs here.
 */
public class SwerveDrive {
    private SwerveModule[] modules;
    private double[][] modulePositions = {{1,1},{-1,1},{-1,-1},{1,-1}};

    public SwerveDrive() {
        this.modules = new SwerveModule[1];
        this.modules[0] = new SwerveModule(0, 1);
    }

    public SwerveDrive(double[][] modulePositions) {
        this.modules = new SwerveModule[1];
        this.modules[0] = new SwerveModule(0, 1);
        this.modulePositions = new double[modulePositions.length][];
        for(int i = 0; i < modulePositions.length; i++) {
            this.modulePositions[i] = modulePositions[i].clone();
        }
    }

    public void drive(double x, double y, double r) {
        for(int i = 0; i < modules.length; i++) {
            modules[i].setVector(Vector2D.addVectors(new Vector2D(x,y), getTurnAngleVector(r, modulePositions[i][0], modulePositions[i][1])));
        }
    }

    private Vector2D getTurnAngleVector(double r, double moduleX, double moduleY) {
        double angle;
        if(moduleX >= 0) {
            angle = Math.atan2(moduleY, moduleX)-Math.PI/2;
        } else {
            angle = Math.atan2(moduleY, moduleX)+Math.PI/2;
        }
        return Vector2D.fromAngle(r, angle);
    }

    public SwerveModule getModule(int index) {
        return this.modules[index];
    }
}
