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

#define echoPin 2
#define trigPin 3
#define compIn 13

int sIn = 0;
int distance = 0;
int bearing = -1;

void setup() {
	Serial.begin(9600);
	pinMode(echoPin, INPUT);
  	pinMode(trigPin, OUTPUT);
  	pinMode(compIn, INPUT);
}

void loop() {

	if (Serial.available() > 0) {
		// read the incoming byte:
		sIn = Serial.read();
		if (sIn == 100) {
			getCompass();
		}
		if (sIn == 101) {
			getSonar();
		}		
	}
}

void getSonar(){
  digitalWrite(trigPin, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  int distance = pulseIn(echoPin, HIGH);
  distance = distance/74;
  Serial.println(distance);                     
}

void getCompass(){
  int bearing = pulseIn(compIn, HIGH);
  bearing = (bearing-500)/50
  Serial.println(bearing);                     
}

void getHello() {
  delay (100);
  Serial.println(“hello”);
}