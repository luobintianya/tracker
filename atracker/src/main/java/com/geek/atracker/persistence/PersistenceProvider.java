package com.geek.atracker.persistence;

import com.geek.atracker.service.PersistenceService;
import com.geek.atracker.service.RedisPersistenceService;

public class PersistenceProvider {

	private static PersistenceService persistenService;

	private PersistenceProvider() {
	};

	public static PersistenceService getPersistenceService() {
		
		if (persistenService == null) {
			persistenService = new RedisPersistenceService();
		} 
		return persistenService;
	}

}
