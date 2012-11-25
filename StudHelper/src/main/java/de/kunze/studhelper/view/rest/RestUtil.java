package de.kunze.studhelper.view.rest;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

//import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;

public class RestUtil {
	protected Client client;
    protected ObjectMapper mapper = new ObjectMapper();
    protected WebResource webResource;
	
    protected Logger logger = LoggerFactory.getLogger(RestUtil.class);
    
    protected final static String UNIVERSITY = "university";
	protected final static String DEPARTMENT = "department";
	protected final static String DEGREECOURSE = "degreecourse";
    
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
