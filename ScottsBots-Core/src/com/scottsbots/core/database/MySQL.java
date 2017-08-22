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

package com.scottsbots.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.scottsbots.core.JDatabase;

public class MySQL implements JDatabase {

	private Connection conn;
	private String host;
	private String user;
	private String password;
	private String database;

	public MySQL(String h, String d, String u, String p) {
		host = h;
		user = u;
		password = p;
		database = d;
	}

	public void open() {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://" + host + "/"
					+ database + "?user=" + user + "&password=" + password);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn = null;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public static java.sql.Date toSqlDate(java.util.Date utilDate) {
		return new java.sql.Date(utilDate.getTime());
	}

	public static void main(String[] args) {
		MySQL test = new MySQL("localhost", "test", "root", "password");
		test.open();
		test.close();
		System.out.println("done " + new java.util.Date());
	}
}
