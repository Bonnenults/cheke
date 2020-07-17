/**
 * 
 */
package com.autozi.common.core.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author haocheng
 * 
 */
public class MessagerProcessor<T> extends Thread {
    private static Logger Logger = LoggerFactory.getLogger(MessagerProcessor.class);

	LinkedBlockingQueue<T> queue;
	ProcessorHandlerHelper<T> helper;

	boolean isRunning = true;

	/**
	 * 执行异步同步Cluster的线程池
	 */
	private ExecutorService messagerProcessorPool;

	public MessagerProcessor(LinkedBlockingQueue<T> queue, ProcessorHandlerHelper<T> helper) {
		this.queue = queue;
		this.helper = helper;
		messagerProcessorPool = Executors.newFixedThreadPool(1);
	}

	public void run() {
		while (isRunning) {
			process();
		}
	}

	private void process() {
		T commands = null;

		try {
			commands = queue.take();

			if (commands != null && messagerProcessorPool != null)
				messagerProcessorPool.execute(new WorkerJob(commands));
		} catch (InterruptedException e) {
			Logger.warn("messager Process stoped!");
		} catch (Exception ex) {
			Logger.error("messager Process error!", ex);
		}

	}

	public void stopProcess() {
		isRunning = false;

		try {
			if (messagerProcessorPool != null)
				messagerProcessorPool.shutdown();

			messagerProcessorPool = null;

			interrupt();
		} catch (Exception ex) {
			Logger.error(ex.toString());
		}
	}
	
	class WorkerJob implements java.lang.Runnable {
		T commands;

		public WorkerJob(T commands) {
			this.commands = commands;
		}

		public void run() {
			if (commands != null) {
				helper.run(commands);
			}
		}

	}
}
