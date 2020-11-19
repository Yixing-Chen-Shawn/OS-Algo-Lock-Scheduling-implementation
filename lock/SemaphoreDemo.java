import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo{
	private Semaphore smp = new Semaphore(3);
	private Random random = new Random();

	class Task implements Runnable{
		private String id;
		Task(String id){
			this.id = id;
		}

		public void run(){
			try{
				smp.acquire();
				System.out.println("Thread " + id + " is working");
				Thread.sleep(random.nextInt(1000));
				smp.release();
				System.out.println("Thread " + id + " is over");
			}catch(InterruptedException e){}
		}
	}

	public static void main(String[] args){
		SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
		//create a cached Thread pool with numerous threads inside
		ExecutorService ThreadPool = Executors.newCachedThreadPool();
		ThreadPool.submit(semaphoreDemo.new Task("a"));
		ThreadPool.submit(semaphoreDemo.new Task("b"));
		ThreadPool.submit(semaphoreDemo.new Task("c"));
		ThreadPool.submit(semaphoreDemo.new Task("d"));
		ThreadPool.submit(semaphoreDemo.new Task("e"));
		ThreadPool.submit(semaphoreDemo.new Task("f"));
		ThreadPool.shutdown();
	}
}
