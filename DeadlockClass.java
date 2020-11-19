import java.util.concurrent.locks.*;

class A implements Runnable{
	private Lock first, second;

	public A(Lock first, Lock second){
		this.first = first;
		this.second = second;
	}

	public void run(){
		try{
			first.lock();
			System.out.println("Thread A got first lock");

			try{
				Thread.sleep(3000);
			}catch(InterruptedException e){}
			second.lock();
			System.out.println("Thread A got second lock.");
		//do something
		}finally{
			first.lock();
			second.lock();
		}
	}
}

class B implements Runnable{
	private Lock first, second;

	public B(Lock first, Lock second){
		this.first = first;
		this.second = second;
	}

	public void run(){
		try{
			second.lock();
			System.out.println("Thread B got second lock.");
			//do something
			try{
				Thread.sleep(3000);
			}catch(InterruptedException e){}
			first.lock();
			System.out.println("Thread B got first lock.");
			//do something
		}finally{
			second.unlock();
			first.unlock();
		}
	}
}

public class DeadlockClass{
	public static void main(String[] args){
		Lock lockX = new ReentrantLock();
		Lock lockY = new ReentrantLock();

		Thread threadA = new Thread(new A(lockX, lockY));
		Thread threadB = new Thread(new B(lockX, lockY));

		threadA.start();
		threadB.start();
	}
}
