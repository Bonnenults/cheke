package com.autozi.common.service.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.autozi.common.utils.web.BeanUtils;


public class Manager {
	
	
	private BlockingQueue<AsynObject> queue = new LinkedBlockingQueue<AsynObject>();
	
	private static boolean start=false;
	
	protected static void copyProperties(Object orig, Object dest){
		BeanUtils.copyProperties(orig, dest);
	}
	
	@SuppressWarnings("rawtypes")
	protected static void populate(Object bean, Map properties){
		try {
			org.apache.commons.beanutils.BeanUtils.populate(bean, properties);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	protected void addAsynTask(Object obj,String methodName,Object... args) {
        try {
            if(!start){
                start=true;
                worker worker=new worker(queue);
                Thread th= new Thread(worker);
                th.start();
            }
            AsynObject asynObject=new AsynObject(obj,methodName,args);
            queue.add(asynObject);
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
	
	private class AsynObject{
		private Object target;
		private String methodName;
		private Object[] args;
		
		public AsynObject(Object obj, String methodName2, Object[] args2) {
			target=obj;
			methodName=methodName2;
			args=args2;
		}
		public Object getTarget() {
			return target;
		}
		
		public String getMethodName() {
			return methodName;
		}
	
		public Object[] getArgs() {
			return args;
		}
	}
	
	private class worker implements Runnable{
		private BlockingQueue<AsynObject> queue=null;
		
		public worker(BlockingQueue<AsynObject> queue1){
			queue=queue1;
		}
		
		public void run() {
			while(true){
				try {
                    if(!queue.isEmpty()){
                        AsynObject obj=queue.take();
                        Object[] args = obj.getArgs();
                        Class[] argsClass = new Class[args.length];
                        for (int i = 0, j = args.length; i < j; i++) {
                            argsClass[i] = args[i].getClass();
                        }
                        Object target = obj.getTarget();
                        Method method= target.getClass().getMethod(obj.getMethodName(), argsClass);
                        method.invoke(target, args);
                    }
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

}
