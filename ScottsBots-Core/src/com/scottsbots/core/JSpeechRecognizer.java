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

package com.scottsbots.core;

public interface JSpeechRecognizer {

	/**
	 * Allocates device for speech input.
	 */
	
    public void open();
    
    /**
     * Deallocates device for speech input.
     */
    
    public void close();
    
    /**
     * Starts listening for speech and returns a string representing what was heard.
     * 
     * @return String - The returned speech as recognized by implementation
     */
    
    public String listen();
    
}