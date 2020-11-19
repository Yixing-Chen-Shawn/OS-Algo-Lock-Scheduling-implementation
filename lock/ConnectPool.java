import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ConnectPool{
	private final List<Conn> pool = new ArrayList<Conn>(3);
	private final Semaphore smp = new Semaphore(3);

	public ConnectPool(){
		pool.add(new Conn());
		pool.add(new Conn());
		pool.add(new Conn());
	}

	public Conn getConn() throws InterruptedException{
		smp.acquire();
		Conn c = null;
		synchronized(pool){
			c = pool.remove(0);
		}
		System.out.println(Thread.currentThread().getName() + " get a connectin "+ c);
		return c;
	}

	public void release(Conn c){
		pool.add(c);
		System.out.println(Thread.currentThread().getName() + " release a connection " +c);
		smp.release();
		
	}

	public static void main(String[] args){
		final ConnectPool pool = new ConnectPool();

		//first thread takes 3 secs of one single connection
		new Thread(){
			public void run(){
				try{
					Conn c = pool.getConn();
					Thread.sleep(3000);
					pool.release(c);
				}catch(InterruptedException e){
					e.printStackTrace();
				}
			};
		}.start();

		for(int i=0; i<3; i++){
			new Thread(){
				public void run(){
					try{
						Conn c = pool.getConn();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				};
			}.start();
		}
	}
	private class Conn{}
}
