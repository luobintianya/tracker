package com.geek.atracker.service;

import com.geek.atracker.data.TrackerInfo;
import com.geek.atracker.data.tracking.BaseInfo;

public interface PersistenceService {

	public void persistence(TrackerInfo<? extends BaseInfo> info);
	
}
