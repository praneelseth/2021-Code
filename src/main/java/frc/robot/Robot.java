// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.Constants.IntakeConstants;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  private VictorSPX mIntakeMotor = new VictorSPX(Constants.IntakeConstants.VictorSPXPort);

  private XboxController m_XboxController = new XboxController(Constants.XboxControllerPort);

  private Compressor mCompressor;

  private DoubleSolenoid mSolenoidOne;

  private DoubleSolenoid mSolenoidTwo;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();

    mCompressor = new Compressor();
    mCompressor.setClosedLoopControl(true);

    mSolenoidOne =
        new DoubleSolenoid(IntakeConstants.kForwardChannelOne, IntakeConstants.kReverseChannelOne);
    mSolenoidTwo =
        new DoubleSolenoid(IntakeConstants.kForwardChannelTwo, IntakeConstants.kReverseChannelTwo);

    mSolenoidOne.set(DoubleSolenoid.Value.kOff);
    mSolenoidTwo.set(DoubleSolenoid.Value.kOff);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
    mSolenoidOne.set(DoubleSolenoid.Value.kOff);
    mSolenoidTwo.set(DoubleSolenoid.Value.kOff);
  }

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }
  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    if (m_XboxController.getBumper(GenericHID.Hand.kLeft)) {
      mIntakeMotor.set(ControlMode.PercentOutput, Constants.IntakeConstants.kIntakePower);
    } else if (m_XboxController.getBumper(GenericHID.Hand.kRight)) {
      mIntakeMotor.set(ControlMode.PercentOutput, -Constants.IntakeConstants.kIntakePower);
    } else {
      mIntakeMotor.set(ControlMode.PercentOutput, 0);
    }
    if (m_XboxController.getAButton()) {
      mSolenoidOne.set(DoubleSolenoid.Value.kForward);
      mSolenoidTwo.set(DoubleSolenoid.Value.kForward);
    } else if (m_XboxController.getBButton()) {
      mSolenoidOne.set(DoubleSolenoid.Value.kReverse);
      mSolenoidTwo.set(DoubleSolenoid.Value.kReverse);
    } else {
      mSolenoidOne.set(DoubleSolenoid.Value.kOff);
      mSolenoidTwo.set(DoubleSolenoid.Value.kOff);
    }
  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
