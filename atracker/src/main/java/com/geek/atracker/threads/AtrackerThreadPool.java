package com.geek.atracker.threads;

import java.util.concurrent.CountDownLatch;

import com.geek.atracker.data.TrackerInfo;
import com.geek.atracker.data.tracking.BaseInfo;
import com.geek.atracker.queue.WorkerValueQueue;
import com.geek.atracker.queue.executer.AtrackerWorkerThread;
import com.geek.atracker.queue.executer.PersistenceWorker;
import com.geek.atracker.queue.executer.impl.DefaultPersistenceWorker;
import com.geek.atracker.service.AtrackerQueueService;

/**
 * this a thread group , we use  threads get data from queue and
 *  save data to persistence service.
 * 
 * 
 * @author Robin
 *
 */
public abstract class AtrackerThreadPool {

	private volatile AtrackerWorkerThread[] workerThreads;
	private boolean ignoreError;
	private boolean hasErrors;
	private boolean finished;
 
	private final CountDownLatch workersEndedSignal;
	private WorkerValueQueue<TrackerInfo<?extends BaseInfo>> queue;
	private final int maxWorkers;
	public AtrackerThreadPool(int maxWorkers){
		queue=  AtrackerQueueService.getQueueInstance(maxWorkers);
		this.maxWorkers=maxWorkers;  
		this.ignoreError=true;
		this.workersEndedSignal=new CountDownLatch(maxWorkers); 
		ensureHasWorkerThread();
	}
	 
	
	protected void ensureHasWorkerThread(){
		if (this.workerThreads != null)
		{
			return;
		}
		CountDownLatch workersStartSignal = null;
		synchronized (this)
		{
			if (this.workerThreads == null)
			{
				workersStartSignal = new CountDownLatch(1);
				final AtrackerWorkerThread[] threads = new AtrackerWorkerThread[this.maxWorkers];
				for (int i = 0; i < this.maxWorkers; ++i)
				{
					threads[i] = new AtrackerWorkerThread(createWorker(i), workersStartSignal, this.workersEndedSignal); 
					threads[i].start();
				} 
				this.workerThreads = threads;
			}
		}
		if (workersStartSignal == null)
		{
			return;
		}
		workersStartSignal.countDown();
	} 

	private final WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo<?extends BaseInfo>> doWhilePut = new WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo<?extends BaseInfo>>()
	{ 
		@Override
		public boolean execute(final WorkerValueQueue<TrackerInfo<?extends BaseInfo>> paramWorkerValueQueue, final TrackerInfo<?extends BaseInfo> paramE)
		{
			System.out.println("doWhilePut");
			return (isAllWorkerDead() == false);// if have one is alive then true otherwise if false
		}
	};

 

	private final WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo<?extends BaseInfo>> doWhileWait = new WorkerValueQueue.ExecuteWhileWaiting<TrackerInfo<?extends BaseInfo>>()
	{
		@Override
		public boolean execute(final WorkerValueQueue<TrackerInfo<?extends BaseInfo>> paramWorkerValueQueue, final TrackerInfo<?extends BaseInfo> info)
		{ 
			if (isAllWorkerDead() != false)
			{
				hasErrors = true; 
				return false;
			} 
			return true;
		}
	};


	private final boolean isAllWorkerDead()// all worker is die?
	{
		for (int i = 0; i < this.maxWorkers; ++i)
		{
			if (this.workerThreads[i].isAlive())
			{
				return false;
			}
		}
		return true;
	}
	 
	public void equeue(TrackerInfo<?extends BaseInfo> info) {
		
		if (this.finished)
		{
			throw new IllegalStateException("master is already finished - cannot enqueue " + info);
		}

		ensureHasWorkerThread(); 
		getQueue().put(info, this.doWhilePut);   
	} 
	 
	protected void waitForEmptyQueue()
	{
		getQueue().waitUntilEmpty(this.doWhileWait);
	} 
	
	protected PersistenceWorker createWorker( int threadID) {
		return new DefaultPersistenceWorker(this, "DefaultPersistenceWorker Worker <" + " " + (threadID + 1) + " of " + this.maxWorkers + ">", threadID);
	}
	
	public final TrackerInfo<?extends BaseInfo> fetchNext(PersistenceWorker worker) throws InterruptedException {
		if ((this.finished) || ((!(this.hasErrors)) && (this.finished))) {
			return null;
		} 
		int workNum=worker.getWorkNumber();
		TrackerInfo<?extends BaseInfo> temp=getQueue().take(workNum);
		//System.out.println(workNum+" "+temp.toString());
		return temp;
	}
	
	


	

	/**
	 * @return the ignoreError
	 */
	public boolean isIgnoreError() {
		return ignoreError;
	}
	/**
	 * @param ignoreError the ignoreError to set
	 */
	public void setIgnoreError(boolean ignoreError) {
		this.ignoreError = ignoreError;
	} 
	  
	/**
	 * @return the queue
	 */
	public WorkerValueQueue<TrackerInfo<?extends BaseInfo>> getQueue() {
 
		return queue;
	}
	
	public void clearWorkerNumber(final PersistenceWorker worker)
	{
		getQueue().clearValueTaken(worker.getWorkNumber());
	}
	
	public boolean notifyFinished(PersistenceWorker worker,TrackerInfo<?extends BaseInfo> trackInfo) { 
	 	System.out.println(trackInfo.toString());  
	    getQueue().clearValueTaken(worker.getWorkNumber());
		return (!(this.finished));
	}
	/**
	 * @param queue the queue to set
	 */
	public void setQueue(WorkerValueQueue<TrackerInfo<?extends BaseInfo>> queue) {
		this.queue = queue;
	}

}
