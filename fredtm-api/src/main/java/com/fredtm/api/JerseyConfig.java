package com.fredtm.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.fredtm.api.rest.AccountResources;
import com.fredtm.api.rest.OperationResources;
import com.fredtm.api.rest.SyncResources;

@Configuration
@ApplicationPath("/fredapi")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(OperationResources.class);
		register(AccountResources.class);
		register(SyncResources.class);
	}

}
