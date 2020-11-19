import java.util.Scanner;

public class FCFS{
	public static void main(String[] args){
		int init_head, n;
		System.out.println("Enter your initial header location: ");
		Scanner in = new Scanner(System.in);
		init_head = in.nextInt();
		System.out.println("Enter number of disk locations: ");
		n = in.nextInt();
		int[] disk_location = new int[n];
		int dl=0, seek_time = 0;
		System.out.println("please enter all your disk locations: ");
		for(int i=0; i < n; i++){
			disk_location[i] = in.nextInt();
		}
		System.out.println("Order \tSeek Time:");
		dl = init_head;
		for(int i=0; i < n; i++){
			System.out.print(disk_location[i] + "\t\t ");
			dl = Math.abs(dl - disk_location[i]);
			System.out.println(dl);
			seek_time+=dl;
			dl = disk_location[i];
		}
		System.out.println();
		System.out.println("Total Seek Time: " + seek_time);
		in.close();
	}
}
