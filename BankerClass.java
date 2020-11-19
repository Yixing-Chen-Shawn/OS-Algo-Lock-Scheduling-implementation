import java.util.Scanner;

public class BankerClass{
	int[] Available = {10,5,7};
	int[][] Max = new int[3][3];
	int[][] Allocation = new int[3][3];
	int[][] Need = new int[3][3];
	int[][] Request = new int[3][3];
	int[] Work = new int[3];

	int id = 0;
	Scanner in = new Scanner(System.in);
	//default constructor
	public BankerClass(){
			
	}

	//set initilised system variable, and see if it is in the safe state
	public void setSystemVariable(){
		setMax();
		setAllocation();
		printSystemVariable();
		SecurityAlgo();
	}
	//set maximum demand for each process currently in the system
	public void setMax(){
		System.out.println("pls set maximum demand of each process in a system: ");
		for(int i=0; i < 3; i++){
			System.out.println("Pls type in the maximum demand for process P" + i + ":");
			for(int j=0; j < 3; j++){
				Max[i][j] = in.nextInt();
			}
		}
	}
	//set the number of resources of each type currently allocated to each process
	public void setAllocation(){
		System.out.println("Pls set the number of resources of each type allocated to each process: ");
		for(int i=0; i < 3; i++){
			System.out.println("Pls type in the amount of resources allocated to Process P" + i + ":");
			for(int j=0; j < 3; j++){
				Allocation[i][j] = in.nextInt();
			}
		}
		System.out.println("Available=Available-Allocation.");
		System.out.println("Need=Max-Allocation");
		//set Allocation matrix
		for(int i=0; i < 3; i++){
			for(int j=0; j < 3; j++){
				Available[i] = Available[i] - Allocation[j][i];
			}
		}
		//set Need matrix: remaining resources need of each process
		for(int i=0; i < 3; i++){
			for(int j=0; j < 3; j++){
				Need[i][j] = Max[i][j] - Allocation[i][j];
			}
		}
	}

	public void printSystemVariable(){
		System.out.println("Current resources allocation below: ");
		System.out.println("Process "+" Max " + " Allocation" + " Need" + " Available");
		
		for(int i=0; i < 3; i++){
			System.out.print("P"+i+" ");
			for(int j=0; j < 3; j++){
				System.out.print(Max[i][j]+" ");
			}
			System.out.print("| ");
			for(int j=0; j < 3; j++){
				System.out.print(Allocation[i][j]+" ");
			}
			System.out.print("| ");
			for(int j=0; j < 3; j++){
				System.out.print(Need[i][j] + " ");
			}
			System.out.print("| ");
			if(i==0){
				for(int j=0; j < 3; j++){
					System.out.print(Available[j]+" ");
				}
			}
			System.out.println();
		}
	}
	//set request resources
	public void setRequest(){
		System.out.println("Pls input the process ID to request resources: ");
		//set the process ID 
		id = in.nextInt();
		System.out.println("Pls input the amount of each resource: ");
		for(int j=0; j < 3; j++){
			Request[id][j] = in.nextInt();
		}
		System.out.println("Process P"+id+" has requested for each resource: (" + Request[id][0]+", " + Request[id][1]+", "+Request[id][2]+").");

		BankerAlgo();
	}

	//Banker Algo 
	public void BankerAlgo(){
		boolean T=true;

		//see if the amount requested is smaller than need
		if(Request[id][0] <= Need[id][0] && Request[id][1] <= Need[id][1] && Request[id][2] <= Need[id][2]){
			//see if the amount requested is smaller than the amount allocated
			if(Request[id][0] <= Available[0] && Request[id][1] <= Available[1] && Request[id][2] <= Available[2]){
				for(int i=0; i < 3; i++){
					Available[i] -= Request[id][i];
					Allocation[id][i] += Request[id][i];
					Need[id][i] -= Request[id][i];
				}
			}else{
				System.out.println("DO NOT HAVE ENOUGH RESOURCES TO ALLOCATE CURRENTLY, PROCESS P" + id + " IS NOW WAITING. ");
				T = false;
			}
		}else{
			System.out.println("PROCESS P" + id + "'s request has exceeded the maixmium demand(Need).");
			T=false;
		}
		if(T==true){
			printSystemVariable();
			System.out.println("Now entering security algorithm: ");
			SecurityAlgo();
		}
	}
	//security algorithm
	public void SecurityAlgo(){
		//Initialise the Finish boolean variable
		boolean[] Finish = {false, false, false};
		//number of completed processes
		int count = 0;
		//looping circles
		int circle = 0;
		//safe sequence
		int[] S = new int[3];

		for(int i=0; i < 3; i++){
			Work[i] = Available[i];
		}
		boolean flag = true;
		while(count < 3){
			if(flag){
				System.out.println("Process "+ " Work " + " Allocation " + " Need " + " Work+Allocation ");
				flag = false;
			}
			for(int i=0; i < 3; i++){
				if(Finish[i] == false && Need[i][0] <= Work[0] && Need[i][1] <= Work[1] && Need[i][2] <= Work[2]){
					System.out.print("P"+i+" ");
					for(int k=0; k < 3; k++){
						System.out.print(Work[k]+" ");
					}
					System.out.print("| ");
					for(int j=0; j < 3; j++){
						Work[j] += Allocation[i][j];
					}
					Finish[i] = true;
					S[count] = i;

					count++;
					for(int j=0; j < 3; j++){
						System.out.print(Allocation[i][j]+" ");
					}
					System.out.print("| ");
					for(int j=0; j < 3; j++){
						System.out.print(Need[i][j]+" ");
					}
					System.out.print("| ");
					for(int j=0; j < 3; j++){
						System.out.print(Work[j] + " ");
					}
					System.out.println();
				}
			}
		circle++;

		if(count == 3){
			System.out.print("now exists a safe sequence: ");
			for(int i=0; i < 3; i++){
				System.out.print("P" + S[i]);
			}
			System.out.println(". Therefore it can allocate! ");
			break;
		}
		if(count < circle){
			count = 5;
			System.out.println("now the system is in unsafe state, so safe sequence does not exist. ");
			break;
		}
	}
	}
}
