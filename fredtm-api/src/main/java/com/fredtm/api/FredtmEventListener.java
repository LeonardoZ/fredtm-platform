package com.fredtm.api;

import org.glassfish.jersey.server.monitoring.ApplicationEvent;
import org.glassfish.jersey.server.monitoring.ApplicationEventListener;
import org.glassfish.jersey.server.monitoring.RequestEvent;
import org.glassfish.jersey.server.monitoring.RequestEventListener;

public class FredtmEventListener implements ApplicationEventListener  {

	@Override
	public void onEvent(ApplicationEvent ae) {
		
	}

	@Override
	public RequestEventListener onRequest(RequestEvent re) {
        System.out.println("Request started");
        // return the listener instance that will handle this request.
        return new MyRequestEventListener();
	}
	public class MyRequestEventListener implements RequestEventListener {
	    private final long startTime;
	 
	    public MyRequestEventListener() {
	        startTime = System.currentTimeMillis();
	    }
	 
	    @Override
	    public void onEvent(RequestEvent event) {
	        switch (event.getType()) {
	            case RESOURCE_METHOD_START:
	                System.out.println("Resource method "
	                    + event.getUriInfo().getMatchedResourceMethod()
	                        .getHttpMethod() + " "+event.getUriInfo().getPath());
	                break;
	            case FINISHED:
	                System.out.println("Request " 
	                    + " finished. Processing time "
	                    + (System.currentTimeMillis() - startTime) + " ms.");
	                break;
			default:
				break;
	        }
	    }
	}
}
