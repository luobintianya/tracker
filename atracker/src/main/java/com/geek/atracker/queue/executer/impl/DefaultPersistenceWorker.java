package com.geek.atracker.queue.executer.impl;

import com.geek.atracker.queue.executer.PersistenceWorker;
import com.geek.atracker.threads.AtrackerThreadPool;

public class DefaultPersistenceWorker extends PersistenceWorker {

	public DefaultPersistenceWorker(AtrackerThreadPool poolservice, String name, int id) {
		super(poolservice, name, id); 
	}

}
