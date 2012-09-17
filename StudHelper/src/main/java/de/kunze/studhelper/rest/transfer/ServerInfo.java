package de.kunze.studhelper.rest.transfer;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ServerInfo {

	public ServerInfo() {}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	private String name;
	private String version;
	
}
