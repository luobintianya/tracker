package com.geek.atracker.queue.executer;

import java.util.concurrent.CountDownLatch;

public class AtrackerWorkerThread extends Thread {
	private final PersistenceWorker worker;
	private final CountDownLatch startSignal;
	private final CountDownLatch endSignal;

	public AtrackerWorkerThread(PersistenceWorker worker, CountDownLatch start,
			CountDownLatch end) {
		super(worker);
		this.worker = worker;
		this.startSignal = start;
		this.endSignal = end;
		//setPriority(Thread.MIN_PRIORITY);//low level priority
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		setName(this.worker.getName());
		try {
			this.startSignal.await(); 
			beforeThread();
			super.run();

		} catch (final InterruptedException localInterruptedException)

		{
			localInterruptedException.printStackTrace();
		} finally {
			this.endSignal.countDown();
			finishedThread();
		}
	}
	
	protected void beforeThread() {
		
	}
	
	protected void finishedThread() {
		
	}
	
	
}
