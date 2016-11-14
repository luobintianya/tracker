package com.geek.atracker.service;

import com.geek.atracker.data.TrackerInfo;
import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.threads.AtrackerThreadPool;
import com.geek.atracker.threads.DefaultAtrackerThreadPool;
import com.geek.atracker.utils.AtrackerContants;

 
/**
 * thread pool service 
 * @author Robin
 *
 */
public class AtrackerThreadPoolService {
	
	private static AtrackerThreadPool threadPools;
	
	
	private AtrackerThreadPoolService(){ 
	}
	
	public synchronized static AtrackerThreadPool getAtrackPoolInstance() {

		if (threadPools == null) {
			threadPools = new DefaultAtrackerThreadPool(
					AtrackerContants.maxWorkers);
		}
		return threadPools;
	}

	public static void equeue(TrackerInfo<? extends BaseInfo> info) {
		if (threadPools == null) {
			threadPools = getAtrackPoolInstance();
		} 
		threadPools.equeue(info);

	}
}
