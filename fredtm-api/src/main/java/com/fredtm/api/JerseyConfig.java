package com.fredtm.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import com.fredtm.api.controller.OperationController;

@Configuration
@ApplicationPath("/fredtm-api")
public class JerseyConfig extends ResourceConfig {
	public JerseyConfig() {
		register(OperationController.class);
	}

}
