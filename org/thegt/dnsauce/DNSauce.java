package org.thegt.dnsauce;
import org.thegt.dnsauce.db.DataBase;
import org.thegt.dnsauce.io.DNSIO;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DNSauce {
	
	public static void main (String[] args) {
		System.out.println("DNSauce V1.0");
		System.out.println("Copyright © GeonoTRON2000 2012");
		System.out.println("Connecting to database...");
		DNSEntry[] updatedFile;
		int entryCount = 0;
		DataBase db = null;
		try {
			db = new DataBase();
			System.out.println("Connected.  Fetching DNS...");
			ResultSet rs = db.execute("SELECT * FROM `dns`");
			rs.beforeFirst();
			while (rs.next()) {
				entryCount++;
			}
			updatedFile = new DNSEntry[entryCount];
			rs.beforeFirst();
			int i = 0;
			while (rs.next()) {
				updatedFile[i] = new DNSEntry(rs.getString("hostname"), rs.getString("address"));
				i++;
			}
			System.out.println("Fetched.  Updating hosts file...");
			DNSIO.write(updatedFile);
			System.out.println("Done.");
		}
		catch (SQLException e) {
			System.err.println("SQL Error: "+e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if (db != null) {
				try {
					db.close();
				} catch (SQLException e) {
					System.err.println("SQL Error: "+e.getMessage());
				}
			}
		}
	}

}