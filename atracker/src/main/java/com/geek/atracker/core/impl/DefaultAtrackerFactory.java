package com.geek.atracker.core.impl;

import com.geek.atracker.core.AtrackerFactory;
import com.geek.atracker.core.AtrackerMaster;

public class DefaultAtrackerFactory implements AtrackerFactory< AtrackerMaster> {  
	public  AtrackerMaster getInstance() {  
			return new DefaultAtrackerMaster(); 
	}
	 
}
