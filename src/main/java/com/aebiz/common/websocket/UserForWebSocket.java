package com.aebiz.common.websocket;

import java.security.Principal;

public final class UserForWebSocket implements Principal {


	
	 	private final String name;

	    public UserForWebSocket(String name) {
	        this.name = name;
	    }

	    @Override
	    public String getName() {
	        return name;
	    }

	
	
	
}
