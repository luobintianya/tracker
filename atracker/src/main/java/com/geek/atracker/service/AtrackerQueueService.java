package com.geek.atracker.service;

import com.geek.atracker.data.TrackerInfo;
import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.queue.WorkerValueQueue;
import com.geek.atracker.queue.impl.DefaultWorkerValueQueue;

/**
 * Queue service
 * @author Robin
 *
 */
public class AtrackerQueueService {

	 
	private static WorkerValueQueue<TrackerInfo<?extends BaseInfo>>  queue=null  ; 
	private AtrackerQueueService(){  
	};
	
	public static synchronized WorkerValueQueue<TrackerInfo<?extends BaseInfo>> getQueueInstance(int maxWorks) {
		
		if (queue == null) {
			queue = new DefaultWorkerValueQueue<TrackerInfo<?extends BaseInfo>>(maxWorks);
		}
		return queue;
	} 
}
