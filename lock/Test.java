class Lock{
	private boolean isLocked = false;
	public synchronized void lock() throws InterruptedException{
		while(isLocked){
			System.out.println(isLocked);
			wait();
		}
		isLocked = true;
	}
	public synchronized void unlock(){
		isLocked = false;
		notify();
	}
}

class Count implements Runnable{
	Lock lock = new Lock();
	public void run(){
		try{
			lock.lock();
			doAdd();
			lock.unlock();
			doAdd();
		}catch(InterruptedException e){};
	}

	public void doAdd(){
		try{
			lock.unlock();
			lock.lock();
			System.out.println("i am unlockable");
			//lock.unlock();
		}catch(InterruptedException e){};
	}
}
public class Test{
	public static void main(String[] args){
		Count cnt = new Count();
		Thread thread = new Thread(cnt);
		thread.start();
	}
}

	


