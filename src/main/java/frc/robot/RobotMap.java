package frc.robot;

import frc.util.Position;

public class RobotMap {
    public final static int FRONT_RIGHT_AZIMUTH = 0, FRONT_RIGHT_DRIVE = 1;
    public final static int FRONT_LEFT_AZIMUTH = 2, FRONT_LEFT_DRIVE = 3;
    public final static int BACK_RIGHT_AZIMUTH = 4, BACK_RIGHT_DRIVE = 5;
    public final static int BACK_LEFT_AZIMUTH = 6, BACK_LEFT_DRIVE = 7;

    public final static Position FRONT_RIGHT_MODULE_POSITION = new Position(1, 1);
    public final static Position FRONT_LEFT_MODULE_POSITION = new Position(-1, 1);
    public final static Position BACK_RIGHT_MODULE_POSITION = new Position(1, -1);
    public final static Position BACK_LEFT_MODULE_POSITION = new Position(-1, -1);
}