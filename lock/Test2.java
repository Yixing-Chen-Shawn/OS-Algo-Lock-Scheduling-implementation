class Lock{
	boolean isLocked = false;
	Thread lockedBy = null;
	int lockedCount = 0;
	public synchronized void lock() throws InterruptedException{
		Thread thread = Thread.currentThread();
		while(isLocked && lockedBy != thread){
			wait();
		}
		isLocked = true;
		lockedCount++;
		lockedBy = thread;
	}

	public synchronized void unlock(){
		if(Thread.currentThread() == this.lockedBy){
			lockedCount--;
			if(lockedCount == 0){
				isLocked = false;
				notify();
			}
		}
	}
}



class Count implements Runnable{
	Lock lock = new Lock();
	public void run(){
		try{
			lock.lock();
			doAdd();
			lock.unlock();
			//doAdd();
		}catch(InterruptedException e){};
	}

	public void doAdd(){
		try{
			//lock.unlock();
			lock.lock();
			System.out.println("i am unlockable");
			lock.unlock();
		}catch(InterruptedException e){};
	}
}
public class Test2{
	public static void main(String[] args){
		Count cnt = new Count();
		Thread thread = new Thread(cnt);
		thread.start();
	}
}
