#/*
# * (C) Copyright 2000-2011, by Scott Preston and Preston Research LLC
# *
# *  Project Info:  http://www.scottsbots.com
# *
# *  This program is free software: you can redistribute it and/or modify
# *  it under the terms of the GNU General Public License as published by
# *  the Free Software Foundation, either version 3 of the License, or
# *  (at your option) any later version.
# *
# *  This program is distributed in the hope that it will be useful,
# *  but WITHOUT ANY WARRANTY; without even the implied warranty of
# *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# *  GNU General Public License for more details.
# *
# *  You should have received a copy of the GNU General Public License
# *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
# */

require 'java'
java_import  com.scottsbots.core.comm.SingleSerialPort
java_import  com.scottsbots.core.controller.Ssc
java_import  com.scottsbots.core.utils.Utils
java_import  com.scottsbots.core.motion.DriveConfiguration
java_import  com.scottsbots.books.robotics101.samples.section4.BasicDiffDriveSample

ssc = Ssc.new(SingleSerialPort::getInstance(1))
leftMotor = DriveConfiguration.new(0,110,100,140,150))
rightMotor = DriveConfiguration.new(1,110,100,140,150))
sampleDrive = BasicDiffDriveSample.new(ssc, leftMotor,rightMotor)
sampleDrive.forward(1000)
Utils::pause(1000)
sampleDrive.pivotLeft(1000)
Utils::pause(1000)
sampleDrive.pivotRight(1000)
Utils::pause(1000)
sampleDrive.reverse(1000)
exit