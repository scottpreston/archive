#!/bin/bash
JAVAHOME=/usr/lib/jvm/java-6-sun
export JAVAHOME
gcc -I$JAVAHOME/include -I$JAVAHOME/include/linux -I/usr/include linuxcamera.c -shared -o linuxcamera.so -fPIC
gcc -I$JAVAHOME/include -I$JAVAHOME/include/linux -I/usr/include linuxcamera2.c -shared -o linuxcamera2.so -fPIC