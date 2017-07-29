package org.thegt.dnsauce;
import java.util.regex.*;

public class DNSEntry {
	private String hostname;
	private String address;
	
	public DNSEntry(String hostname, String address) {
		this.hostname = hostname;
		this.address = address;
	}
	
	public DNSEntry(String entryData) {
		Pattern entryPattern = Pattern.compile("(\\b\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\b) ([0-9A-Za-z\\-\\.]+)$");
		Matcher m = entryPattern.matcher(entryData.trim());
		if (m.matches()) {
			this.address = m.group(1);
			this.hostname = m.group(2);
		}
		else if (entryData.length() < 1) {
			this.address = "";
			this.hostname = "";
		}
		else {
			throw new IllegalArgumentException("Data is incorrectly formatted.  Re-check entry:\r\n"+entryData.trim());
		}
	}
	
	public String toString () {
		return address + " " + hostname;
	}
	
	public String getHost () {
		return hostname;
	}
	
	public String getAddr () {
		return address;
	}

}
