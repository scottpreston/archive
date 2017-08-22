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

package com.scottsbots.core.utils;

/**
 * Class for benchmarking and doing performance testing of various things.
 * 
 * @author scott
 *
 */
public class Benchmark {

	private String name = "Default benchmark";
	private long start = 0;
	private long elapsed = 0;
	
	public Benchmark() {}
	
	public Benchmark(String name) {
		this.name = name;
	}
	
	public void start() {
		start = System.currentTimeMillis();
		elapsed = 0;
	}

	public void stat(String name) {
		this.name = name;
		stat();
	}

	public void stat() {
		elapsed = System.currentTimeMillis() - start;
		System.out.println(name + " time(ms) :" + elapsed);
	}
	
	public void end(String name) {
		this.name = name;
		end();
	}
	
	public void end() {
		elapsed = System.currentTimeMillis() - start;
		start = 0;
		System.out.println(name + " time(ms) :" + elapsed);
	}
	
	public long getElapsed() {
		return elapsed;
	}
	
	public static void main(String[] args) {
		Benchmark b = new Benchmark("test");
		b.start();
		Utils.pause(89);
		b.end();
	
	}

}
