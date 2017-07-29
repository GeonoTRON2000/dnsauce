package org.thegt.dnsauce.io;
import java.io.*;
import java.util.Scanner;
import org.thegt.dnsauce.*;

@SuppressWarnings("resource")
public class DNSIO {
	private static File hostsFile;
	private static FileOutputStream hostsOutput;
	static {
		try {
			hostsFile = new File("C:\\Windows\\System32\\drivers\\etc\\hosts");
			new FileInputStream(hostsFile);
			hostsOutput = new FileOutputStream(hostsFile, true);
		}
		catch (IOException e) {
			try {
				hostsFile = new File("/etc/hosts");
				new FileInputStream(hostsFile);
				hostsOutput = new FileOutputStream(hostsFile, true);
			}
			catch (IOException ex) {
				System.err.println("Hosts file not found.");
				System.exit(1);
			}
		}
	}
	
	public static void add (DNSEntry entry) throws IOException {
		Scanner s = new Scanner(new FileInputStream(hostsFile));
		s.useDelimiter("\\A");
		String hostsFileContents = s.hasNext() ? s.next() : "";
		if (!hostsFileContents.contains(entry.toString()) && !entry.getHost().trim().toLowerCase().equals("localhost")) {
			new PrintStream(hostsOutput).println(entry.toString());
			System.out.println("Entry added:");
			System.out.println(entry.toString());
		}
	}
	
	public static void add (DNSEntry[] entries) throws IOException {
		for (DNSEntry entry : entries) {
			add(entry);
		}
	}
	
	public static void remove (DNSEntry entry) throws IOException {
		Scanner s = new Scanner(new FileInputStream(hostsFile));
		String newHostsFile = "";
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (line != entry.toString()) {
				newHostsFile += line+ "\n";
			}
		}
		FileOutputStream eraser = new FileOutputStream(hostsFile, false);
		eraser.write(new String().getBytes());
		eraser.close();
		hostsOutput.write(newHostsFile.getBytes());
		System.out.println("Removed entry:");
		System.out.println(entry.toString());
	}
	
	public static void remove (DNSEntry[] entries) throws IOException {
		for (DNSEntry entry : entries) {
			remove(entry);
		}
	}
	
	public static void clear () throws IOException {
		Scanner s = new Scanner(new FileInputStream(hostsFile));
		while (s.hasNextLine()) {
			String line = s.nextLine();
			if (!line.contains("#") && !new DNSEntry(line).getHost().trim().toLowerCase().equals("localhost")) {
				remove(new DNSEntry(line));
			}
		}
		System.out.println("Entries cleared.");
	}
	
	public static void write(DNSEntry[] entries) throws IOException {
		clear();
		add(entries);
	}	
}