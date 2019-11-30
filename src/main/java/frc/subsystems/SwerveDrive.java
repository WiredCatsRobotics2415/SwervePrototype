/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.subsystems;

import frc.util.Vector2D;
import frc.robot.RobotMap;
/**
 * Add your docs here.
 */
public class SwerveDrive {
    private SwerveModule frontRightModule, frontLeftModule, backRightModule, backLeftModule;

    public SwerveDrive() {
        this.frontRightModule = new SwerveModule(RobotMap.FRONT_RIGHT_AZIMUTH, RobotMap.FRONT_RIGHT_DRIVE, RobotMap.FRONT_RIGHT_MODULE_POSITION);
        this.frontLeftModule = new SwerveModule(RobotMap.FRONT_LEFT_AZIMUTH, RobotMap.FRONT_LEFT_DRIVE, RobotMap.FRONT_LEFT_MODULE_POSITION);
        this.backRightModule = new SwerveModule(RobotMap.BACK_RIGHT_AZIMUTH, RobotMap.BACK_RIGHT_DRIVE, RobotMap.BACK_RIGHT_MODULE_POSITION);
        this.backLeftModule = new SwerveModule(RobotMap.BACK_LEFT_AZIMUTH, RobotMap.BACK_LEFT_DRIVE, RobotMap.BACK_LEFT_MODULE_POSITION);
    }

    public void drive(double x, double y, double r) {
        Vector2D strafVector = new Vector2D(x,y);
        frontRightModule.setVector(getModuleVector(strafVector, r, frontRightModule));
        frontLeftModule.setVector(getModuleVector(strafVector, r, frontLeftModule));
        backRightModule.setVector(getModuleVector(strafVector, r, backRightModule));
        backLeftModule.setVector(getModuleVector(strafVector, r, backLeftModule));
    }

    private Vector2D getModuleVector(Vector2D strafVector, double r, SwerveModule module) {
        return Vector2D.addVectors(strafVector, getTurnAngleVector(r, module));
    }

    private Vector2D getTurnAngleVector(double r, SwerveModule module) {
        double angle;
        if(module.modulePosition.x >= 0) {
            angle = Math.atan2(module.modulePosition.y, module.modulePosition.x)-Math.PI/2;
        } else {
            angle = Math.atan2(module.modulePosition.y, module.modulePosition.x)+Math.PI/2;
        }
        return Vector2D.fromAngle(r, angle);
    }
}
