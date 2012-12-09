package de.kunze.studhelper.view.rest;

import java.io.Serializable;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class RestUtil implements Serializable {
	
	private static final long serialVersionUID = 930264314114982125L;
	
	protected final Client client;
    protected final static ObjectMapper mapper = new ObjectMapper();
    protected final WebResource webResource;
	
    protected Logger logger = LoggerFactory.getLogger(RestUtil.class);
    
    protected final static String UNIVERSITY = "university";
	protected final static String DEPARTMENT = "department";
	protected final static String DEGREECOURSE = "degreecourse";
	protected final static String PART = "part";
	protected final static String MODUL = "modul";
	protected final static String LECTURE = "lecture";
    
    public RestUtil() {
    	ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        
        this.client = Client.create(clientConfig);
        this.webResource = client.resource("http://localhost:8080/StudHelper/rest");
    } 
    
    protected boolean is2xx(int status) {
    	return (status >= 200 && status < 300);
    }
    
}
