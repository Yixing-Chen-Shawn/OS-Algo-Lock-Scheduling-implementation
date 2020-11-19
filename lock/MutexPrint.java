import java.util.concurrent.Semaphore;

public class MutexPrint{
	private final Semaphore smp = new Semaphore(1);

	public void print(String str) throws InterruptedException{
		smp.acquire();

		System.out.println(Thread.currentThread().getName() + " entering");
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName() + " printing " + str);
		System.out.println(Thread.currentThread().getName() + " exiting");
		//release semaphore
		smp.release();
	}

	public static void main(String[] args){
		final MutexPrint printer = new MutexPrint();

		//initiate 10 threads for printing task
		for(int i=0; i<10; i++){
			//create the thread 10 times
			new Thread(){
				public void run(){
					try{
						printer.print("i am the current thread");
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}.start();
		}
	}
}
