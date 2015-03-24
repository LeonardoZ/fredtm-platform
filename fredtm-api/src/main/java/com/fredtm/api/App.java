package com.fredtm.api;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
/*
 * REST Def
 * =================
 * PUT = Update info returns 200
 * POST = Create info returns 201+created entity
 * GET = Pull info returns 200+pulled info
 * DELETE = Exclude info returns 200
 */
@ApplicationPath("/f")
public class App extends ResourceConfig {
	
	public App() {
		packages("com.fredtm.api");
		register(FredtmEventListener.class);
	}
	

}
