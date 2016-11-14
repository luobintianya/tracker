package com.geek.atracker.service;

import com.geek.atracker.data.TrackerInfo;
import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.persistence.PersistenceStrategy;
import com.geek.atracker.persistence.place.redis.RedisPersistenceStrategy;

public class RedisPersistenceService implements PersistenceService {
	private PersistenceStrategy redisStore= new RedisPersistenceStrategy();

	public void persistence(TrackerInfo<? extends BaseInfo> info) {
			
		redisStore.saveTrackerInfo(info);
		
	}

}
