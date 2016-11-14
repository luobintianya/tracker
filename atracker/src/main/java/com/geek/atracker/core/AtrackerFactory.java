package com.geek.atracker.core;

public interface AtrackerFactory<T extends AtrackerMaster> {
	  
	public  T getInstance(); 

}
