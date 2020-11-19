import java.util.Scanner;

public class LOOK{
	public static void main(String[] args){
		int init_head, prev_head;

		System.out.println("Enter initial header location: ");
		Scanner in = new Scanner(System.in);
		init_head = in.nextInt();

		System.out.println("Enter Previous header location: ");
		prev_head = in.nextInt();

		System.out.println("Enter number of disk locations: ");
		int n = in.nextInt();

		int[] disk_location = new int[n];
		int dl = 0, seek_time = 0, f=0;

		System.out.println("Enter disk locations: ");
		for(int i=0; i < n; i++){
			disk_location[i] = in.nextInt();
		}

		int[] visited = new int[n+1];
		System.out.println("Disk Location \t Seek Time");

		if(prev_head <= init_head){
			f=0;
		}else{
			f=1;
		}

		for(int i=0; i < n; i++){
			int pos = -1;
			int min = 10000;
			for(int j=0; j < n; j++){
				if(f == 0){
					if(disk_location[j] > init_head &&  min > Math.abs(disk_location[j] - init_head) && visited[j] ==0){
						min = Math.abs(disk_location[j] - init_head);
						pos = j;
					}
				}else if(f == 1){
					if(disk_location[j] <= init_head && min > Math.abs(disk_location[j] - init_head) && visited[j] == 0){
						pos = j;
						min = Math.abs(disk_location[j] - init_head);
					}
				}
			}

			if(pos == -1){
				if(f == 0){
					f = 1;
				}else{
					f = 0;
				}
				System.out.println("-------------------------------------------------");
				System.out.println("Changing Directions");
				System.out.println("-------------------------------------------------");
				System.out.println("Disk Location \t Seek Time");
				i--;
				continue;
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
