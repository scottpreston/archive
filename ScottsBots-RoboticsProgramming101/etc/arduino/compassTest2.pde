/*
 * (C) Copyright 2000-2011, by Scott Preston and Preston Research LLC
 *
 *  Project Info:  http://www.scottsbots.com
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

#include <Wire.h>

//The SDA line is on analog pin 4 of the Arduino. (Pin 3 of CMPS03)
//The SCL line is on analog pin 5 of the Arduino. (Pin 2 of CMPS03)

#define address 0x60

void setup(){
  Wire.begin();
  Serial.begin(9600);
}

void loop(){
  byte highByte;
  byte lowByte;
  Wire.beginTransmission(address);
  Wire.send(2);                   
  Wire.endTransmission();
  Wire.requestFrom(address, 2);
  while(Wire.available() < 2);
  highByte = Wire.receive();
  lowByte = Wire.receive();
  int bearing = ((highByte<<8)+lowByte)/10; 
  Serial.println(bearing);
  delay(1000);
}