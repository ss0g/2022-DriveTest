package com.spartronics4915.frc.commands;

import com.spartronics4915.frc.subsystems.Drive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveCommands
{
    private final Drive mDrive;
    private final Joystick mJoystick;
    private final Joystick mArcadeController;

    public DriveCommands(Drive drive, Joystick joystick, Joystick arcadeController)
    {
        mDrive = drive;
        mJoystick = joystick;
        mArcadeController = arcadeController;
    }

    public class TeleOpCommand extends CommandBase
    {
        public TeleOpCommand()
        {
            addRequirements(mDrive);
        }

        @Override
        public void initialize()
        {

        }

        public void execute()
        {
            double x = mJoystick.getX();
            double y = mJoystick.getY();

            mDrive.arcadeDrive(y, x);
        }
    }
}
