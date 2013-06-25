
import java.util.*;

public class ProcessScheduler {
	
	
	int []expRunTime=new int[10];
	int []priority=new int[4];
	float [][] arrivalTime;
	int []runTime=new int[10];
	String []processId=new String[12];
	int noOfProcess=0, totalRunTime=0;
	//this is process queue 
	LinkedList<String> myQueue= new LinkedList<String>();
	
	private void processGenerator()
	{	
		float min=0.0F;
		float max=100.0F;
		int low=5;int high=10;
		Random rangen=new Random();
		noOfProcess=rangen.nextInt(high-low)+low;
		arrivalTime=new float[noOfProcess][noOfProcess];
	
			for(int j=0;j<noOfProcess;j++)
		{	
				arrivalTime[j][1] =(float) (Math.random()*((max-min)+1));
		
				arrivalTime[j][0]=(float)j;
			
		}			
			
		java.util.Arrays.sort(arrivalTime,new java.util.Comparator<float[]>()
				{
					@Override
					public int compare( float[] a1,float[] b1)
					{
						return  (int) (a1[1]-b1[1]);
					}
					});
		 nameProcesses(arrivalTime, noOfProcess);
		
	}
	
	
	
	private void  nameProcesses(float[][] array,int n)
	{
		String []processNames= new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N"};
		//first add alphabetical process Names to processId, then push them to process queue
		for(int i=0;i<n;i++)
		{
			int num=(int) array[i][0];
			//push processId to process queue based on arrival time
			myQueue.add(processNames[num]);
		}
	
	}
	private int genRandomTime()
	{	
		Random rand=new Random();
		//get random burst time for each process
		for(int i=0;i<noOfProcess;i++)
		{	
			runTime[i]=rand.nextInt(10)+1;
			//count total time slots by adding all the burst time
			totalRunTime= totalRunTime+runTime[i];
		}
		
		System.out.println("\nTotal processes: "+noOfProcess);
		System.out.println("Total Time quantum: "+totalRunTime);
		System.out.println("\nProcess_ID\tarrivalTime\t\trunTime");
		
		for(int i=0;i<noOfProcess;i++)
		{
			      System.out.println(myQueue.get(i)+"\t\t"+arrivalTime[i][1]+"\t\t"+runTime[i]);//array[i][1]
		}
		return totalRunTime;
	}
	
	private void FCFS()
	{
	
	processGenerator();
	
	String []timeChart=new String[genRandomTime()];
	int i=0,lastindex=0,range=0,x=0;
					
		
	do {	
			String tmp=myQueue.getFirst();
		
			if(x==0)	range=runTime[x];	
			
			for(i=lastindex;i<range;i++)
			{	
					timeChart[i]=tmp;				
			}
			lastindex=range;//the end index of current process is the start index of the next process
			x++;
			range=range+runTime[x]; 
			
			myQueue.pop();
			
		}while(myQueue.peek()!=null && x<noOfProcess);
		//Everything in myQueue are popped, we no longer need process queue
		myQueue.clear();
		//printing out the time chart at the end
		for(int k=0;k<timeChart.length;k++)
			System.out.print(" "+timeChart[k]+" ");
		

	}
	
	public static void main(String[] args)
	{ 
		//comment it for now. a loop to run 5 times to find average
//		for(int i=0;i<5;i++)
//		{	
			ProcessScheduler ps=new ProcessScheduler();
			ps.FCFS();
//		}
	}
}
