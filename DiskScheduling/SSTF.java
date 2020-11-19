import java.util.Scanner;

public class SSTF{
	public static void main(String[] args){
		int init_head, n;

		System.out.println("Enter initial header location: ");
		Scanner in = new Scanner(System.in);
		init_head = in.nextInt();

		System.out.println("Enter number of disk locations: ");
		n = in.nextInt();

		int[] disk_location = new int[n];
		int seek_time=0;

		System.out.println("Enter disk locations: ");
		for(int i=0; i < n; i++){
			disk_location[i] = in.nextInt();
		}

		int[] visited = new int[n+1];
		System.out.println("Disk Location \t Seek Time");
		
		for(int i=0; i < n; i++){
			int min = 100000;
			int pos = 0;
			for(int j=0; j < n; j++){
				if(Math.abs(disk_location[j] - init_head) < min){
					if(visited[j] ==0){
						min = Math.abs(disk_location[j] - init_head);
						pos = j;
					}
				}
			}
			visited[pos] = 1;
			seek_time += Math.abs(disk_location[pos] - init_head);
			System.out.println(disk_location[pos] + "\t\t " + seek_time);
			init_head = disk_location[pos];
		}
		System.out.println();
		System.out.println("Total Seek Time: " + seek_time);
		in.close();
	}
}
