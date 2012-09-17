package de.kunze.studhelper.rest.impl;

import de.kunze.studhelper.rest.ressource.InfoRessource;
import de.kunze.studhelper.rest.transfer.ServerInfo;

public class InfoImpl implements InfoRessource {

	public ServerInfo getServerInfo() {
		
		ServerInfo result = new ServerInfo();
		result.setName("StudHelper");
		result.setVersion("0.0.1");
		
		return result;
	}

}
