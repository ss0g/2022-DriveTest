package com.spartronics4915.frc.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static com.spartronics4915.frc.Constants.Drive.*;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Drive extends SubsystemBase
{
    private class SimulatedMotorController implements MotorController
    {
        private double mSpeed;
        private boolean mInverted;
        
        public SimulatedMotorController()
        {
            super();
            mSpeed = 0;
        }

        @Override
        public void disable() {}

        @Override
        public double get() { return mSpeed; }

        @Override
        public boolean getInverted() { return this.mInverted; }

        @Override
        public void set(double speed) { mSpeed = speed; }

        @Override
        public void setInverted(boolean inverted) { mInverted = inverted; }

        @Override
        public void setVoltage(double outputVolts)
        {
            MotorController.super.setVoltage(outputVolts);
        }

        @Override
        public void stopMotor()
        {
            setVoltage(0);
            set(0);
        }
    }
    
    private MotorController mLeftMotor, mRightMotor;

    public Drive()
    {
        boolean success = true;
        try
        {
            mLeftMotor = new CANSparkMax(kLeftMotorID, MotorType.kBrushless);
            mRightMotor = new CANSparkMax(kRightMotorID, MotorType.kBrushless);

            mLeftMotor.setInverted(kLeftMotorIsInverted);
            mRightMotor.setInverted(kRightMotorIsInverted);
        }
        catch (Exception e) 
        {
            DriverStation.reportError(e.getMessage(), false);
            DriverStation.reportWarning("Drive failed to construct hardware", false);
            success = false;
            mLeftMotor = new SimulatedMotorController();
            mRightMotor = new SimulatedMotorController();
        }
    }

    public void tankDrive(double left, double right)
    {
        mLeftMotor.set(left);
        mRightMotor.set(right);
    }

    public void arcadeDrive(double linearPercent, double rotationPercent)
    {
        double max = Math.max(Math.abs(linearPercent + rotationPercent), Math.abs(linearPercent - rotationPercent));
        tankDrive((linearPercent + rotationPercent) / max, (linearPercent - rotationPercent) / max);
    }

    public MotorController getLeftMotor()
    {
        return mLeftMotor;
    }

    public MotorController getRightMotor()
    {
        return mRightMotor;
    }
}
