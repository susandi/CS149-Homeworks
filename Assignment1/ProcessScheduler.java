
import java.text.DecimalFormat;
import java.util.*;

public class ProcessScheduler {
	
	
	private static int count=0;
	private static int flag=0;
	
	float [][]avgArr=new float[5][5];		
	float [][]priorityNArrival;
	
	float [][] arrivalTime;
	float[] arrivalT;
	int []runTime=new int[15];
	float []turnaroundArr=new float[15];
	float []waitingTimeArr=new float[15];
	float []responseTimeArr=new float[15];
	
	int noOfProcess=0;

	//myQueue is process queue 
	LinkedList<String> myQueue= new LinkedList<String>();
	//tmp arraylist to sort arrival time
	List<Float> arvQueue=new ArrayList<Float>();
	
	private void processGenerator()
	{			
		int low=5;int high=15;
		Random rangen=new Random();
		
		noOfProcess=rangen.nextInt(high-low)+low;
//		noOfProcess=5;
		if(flag==0)//FCFS based on arrivalTime
		{	
			sortedArrivalTimeArr(noOfProcess);
		 }
		else if(flag==1) //HPF based on priority
		{
			sortedPriorityArr(noOfProcess);
			
		}
		
	}
	private void sortedArrivalTimeArr(int n)
	{	float min=0.0F;
		float max=10.0F;
		arrivalTime=new float[n][n];
		
		for(int j=0;j<n;j++)
		{		//since time chart starts with 0, the first process's arrival time is always 0.
				if(j==0){ arrivalTime[j][0]=(float)j; arrivalTime[j][1]=(float)j;}
				else
				{
					arrivalTime[j][0]=(float)j;				
					arrivalTime[j][1] =(float) (Math.random()*((max-min)+1));
				}
		}
		java.util.Arrays.sort(arrivalTime,new java.util.Comparator<float[]>()
				{
					@Override
					public int compare( float[] a1,float[] b1)
					{
						if(a1[1]<b1[1])return -1;
						else if(a1[1]==b1[1]) return 0;
						else return 1;
					}
					});
		 FCFSnameProcesses(arrivalTime, n);	
	}
	private void sortedPriorityArr(int n)
	{	float min=0.0F;
		float max=10.0F;
		priorityNArrival=new float[noOfProcess][noOfProcess];
		Random rand=new Random();
		for(int j=0;j<noOfProcess;j++)
		{	//start time chart with 0 by making the first process arrival time 0 and priority 1
			if(j==0) 
			{
				priorityNArrival[j][0]=(float)j;
				priorityNArrival[j][1]=1;
				priorityNArrival[j][2]=(float)j;
				
			}
			else
			{	priorityNArrival[j][0]=(float)j;
				priorityNArrival[j][1]=rand.nextInt(4)+1;//priority level
				priorityNArrival[j][2]=(float) (Math.random()*((max-min)+1));//arrival time
			}	
				
		}

		
		java.util.Arrays.sort(priorityNArrival,new java.util.Comparator<float[]>()
				{
					@Override
					public int compare( float[] a2,float[] b2)
					{
						if(a2[1]<b2[1])return -1;
						else if(a2[1]==b2[1]) return 0;
						else return 1;
					}
					});
				
		 HPFnameProcesses(priorityNArrival);	
	}

	private void  HPFnameProcesses(float[][] array)
	{	LinkedList<Float> arrivalQueue= new LinkedList<Float>();
	
		String []procNames= new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S"};
		int oneCnt=0,twoCnt=0,threeCnt=0,fourCnt=0;
		float[][]pArr=new float[noOfProcess][noOfProcess];
		float [][] indices=new float[noOfProcess][noOfProcess];
	
		for(int i=0;i<noOfProcess;i++)
		{ 
			int number=(int) array[i][0];
			
				myQueue.add(procNames[number]);
		}
		
		
		for(int i=0;i<noOfProcess;i++)
		{
			if(priorityNArrival[i][1]==1)
			{
				
				pArr[oneCnt][0]=priorityNArrival[i][2];
				oneCnt++;
			}
	
			if(priorityNArrival[i][1]==2)
			{
				pArr[twoCnt][1]=priorityNArrival[i][2];
				twoCnt++;
			}
		
			if(priorityNArrival[i][1]==3)
			{
				pArr[threeCnt][2]=priorityNArrival[i][2];
				threeCnt++;
			}
		
			if(priorityNArrival[i][1]==4)
			{
				pArr[fourCnt][3]=priorityNArrival[i][2];
				fourCnt++;
			}
		
		}
		if(oneCnt>0) sortedArray(pArr,0,oneCnt);
	
		if(twoCnt>0) sortedArray(pArr,1,twoCnt);
		
			
		if(threeCnt>0) sortedArray(pArr,2,threeCnt);
		
			
		if(fourCnt>0) sortedArray(pArr,3,fourCnt);
		
		//record the sorted array based on arrival time to original array
		for(int i=0;i<arvQueue.size();i++)
			priorityNArrival[i][2]=arvQueue.get(i);
	
		arvQueue.clear();
	}
	
	private void sortedArray(float[][] arr,final int col,int n)
	{ int t=0; 
	
		java.util.Arrays.sort(arr,new java.util.Comparator<float[]>()
			{
		@Override
		public int compare( float[] a1,float[] b1)
		{
			if(a1[col]<b1[col])return -1;
			else if(a1[col]==b1[col]) return 0;
			else return 1;
		}
		});
	
		for(int c=noOfProcess-n;c<noOfProcess;c++)
		{	
				arvQueue.add(arr[c][col]);
		}
			
	
	}
	
	private void  FCFSnameProcesses(float[][] array,int n)
	{
		String []processNames= new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S"};
		//first add alphabetical process Names to processId, then push them to process queue
		for(int i=0;i<n;i++)
		{	
			{	int num=(int) array[i][0];
				//push processId to process queue based on arrival time
				myQueue.add(processNames[num]);
			}
		}
	
	}
	private int genRandomTime()
	{	int totalRunTime=0;
		Random rand=new Random();
		//get random burst time for each process
	
		for(int i=0;i<noOfProcess;i++)
		{	
			runTime[i]=rand.nextInt(10)+1;
			
			//count total time slots by adding all the burst time
			totalRunTime= totalRunTime+runTime[i];
			
		}

		System.out.println("\nRound "+count);
		System.out.println("\nTotal processes: "+noOfProcess);
		System.out.println("Total Time quantum: "+totalRunTime);
		CalAvgTime();
		
		return totalRunTime;
	}
	
	private void CalAvgTime()
	{
				
		int t1=0,t2=0,d1=0,d2=0,waitTime=0,responseTime=0;
		float avgRTime=0.0f,avgTTime=0.0f,avgWTime=0.0f,throughput=0.0f;
		
		DecimalFormat df=new DecimalFormat("#.###");
		
		System.out.println("\nProcess_ID\tpriority\tarrivalTime\t\trunTime\t\tturnaroundTime\t\twaitingTime\t\tresponseTime");
		for(int i=0;i<noOfProcess;i++)
		{		  throughput=throughput+runTime[i];
				  //Turnaround=total runTime of processes-cur arrivalTime
				  t1=t1+runTime[i];
				  if(flag==0)
					  t2=(int)(t1-arrivalTime[i][1]);				  
				  else 
					  t2=(int)(t1-priorityNArrival[i][2]);
				  turnaroundArr[i]=t2;
				  
				  avgTTime+=turnaroundArr[i];
				  
				  if(i==0){ d1= d1+runTime[i];responseTime=0;}//add runTime[0]
				 
				  if(i>0)
				  { //wait time=total runTime of prev processes - cur arrivalTime
					  
					  if(flag==0)
				  		 d2=(int)(d1-arrivalTime[i][1]);	
				  	 else  
				  		 d2=(int)(d1-priorityNArrival[i][2]);
					  
					  
					 waitTime=d2;
					 d1= d1+runTime[i-1];//add current runTime
//					 responseTime=d1;
					 responseTime=waitTime;
				  }
				  waitingTimeArr[i]=waitTime;
				  responseTimeArr[i]=responseTime;
				  avgRTime+=responseTimeArr[i];
				  avgWTime+=waitingTimeArr[i];
				  if(flag==0)
			      System.out.println(myQueue.get(i)+"\t\t"+df.format(arrivalTime[i][1])+"\t\t\t"+runTime[i]+"\t\t"+turnaroundArr[i]+"\t\t\t"+waitingTimeArr[i]+"\t\t\t"+responseTimeArr[i]);//array[i][1]
				  else 
				      System.out.println(myQueue.get(i)+"\t\t"+df.format(priorityNArrival[i][1])+"\t\t"+df.format(priorityNArrival[i][2])+"\t\t\t"+runTime[i]+"\t\t"+turnaroundArr[i]+"\t\t\t"+waitingTimeArr[i]+"\t\t\t"+responseTimeArr[i]);//array[i][1]

		}
		avgTTime=avgTTime/noOfProcess;
		avgWTime=avgWTime/noOfProcess;
		avgRTime=avgRTime/noOfProcess;
		throughput=throughput/noOfProcess;

		if(count==1)
		{avgArr[0][0]=avgTTime;			
		avgArr[1][0]=avgWTime;
		avgArr[2][0]=avgRTime;
		avgArr[3][0]=throughput;	
		}
		else if(count==2)
		{ avgArr[0][1]=avgTTime;		
		avgArr[1][1]=avgWTime;
		avgArr[2][1]=avgRTime;
		avgArr[3][1]=throughput;	
		}
		else if(count==3)
		{ avgArr[0][2]=avgTTime;			
		avgArr[1][2]=avgWTime;
		avgArr[2][2]=avgRTime;
		avgArr[3][2]=throughput;	
		}
		else if(count==4)
		{ avgArr[0][3]=avgTTime;		
		avgArr[1][3]=avgWTime;
		avgArr[2][3]=avgRTime;
		avgArr[3][3]=throughput;	
		}
		else if(count==5)
		{ avgArr[0][4]=avgTTime;				
		avgArr[1][4]=avgWTime;
		avgArr[2][4]=avgRTime;
		avgArr[3][4]=throughput;	
		}
		//printing out average time for each round
		System.out.println("\nAvgTurnaroundTime\t\tAvgWaitingTime\t\tAvgResponseTime\t\tThroughput");
		System.out.println(df.format(avgTTime)+"\t\t\t\t"+df.format(avgWTime)+"\t\t\t"+df.format(avgRTime)+"\t\t\t"+df.format(throughput));//array[i][1]
				
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
			//printing out the time chart with processes
			for(int k=0;k<timeChart.length;k++)
				System.out.print(" "+timeChart[k]+" ");
				System.out.println("\n\n");		

	}
	private void printFiveRAvg()
	{	float avgTmp[]=new float[5];
		DecimalFormat dc=new DecimalFormat("#.###");
		for(int c=0;c<4;c++)
		{	avgTmp[c]=avgArr[c][0]+avgArr[c][1]+avgArr[c][2]+avgArr[c][3]+avgArr[c][4];}
		
		System.out.println("Total 5 rounds average:");
		System.out.println("+========+=============+=========+=========+=========+=========+=========+=========+");
		System.out.println("TurnaroundTime\t\tWaitingTime\t\tResponseTime\t\tThroughput");
		System.out.println("+========+=============+=========+=========+=========+=========+=========+=========+");
		//System.out.println(avgTmp[0]+" "+avgTmp[0]/count+"\t\t"+avgTmp[1]+" "+avgTmp[1]/count+"\t\t"+avgTmp[2]+" "+avgTmp[2]/count+"\t\t"+avgTmp[3]+" "+avgTmp[3]/count);	
		System.out.println(dc.format(avgTmp[0]/count)+"\t\t\t"+dc.format(avgTmp[1]/count)+"\t\t\t"+dc.format(avgTmp[2]/count)+"\t\t\t"+dc.format(avgTmp[3]/count));	
		
		
	}
	private void HPF_NP()
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
			//printing out the time chart with processes
			for(int k=0;k<timeChart.length;k++)
				System.out.print(" "+timeChart[k]+" ");
				System.out.println("\n\n");	
		
	}
	
	public static void main(String[] args)
	{ 	ProcessScheduler ps=new ProcessScheduler();
		
		if(flag==0)
		{
			do
			{	
				System.out.println("-------------------First Come First Serve simulation--------------");
				ps.FCFS();
				count++;
				
			}while(count<5);			
			ps.printFiveRAvg();//printing out average time for 5 rounds
			flag=1;count=0;
			
			do
			{		
				System.out.println("-------------------Highest Priority First_Nonpreemptive simulation--------------");
				ps.HPF_NP();
				count++;
				
			}while(count<5);			
			ps.printFiveRAvg();//printing out average time for 5 rounds
			flag=0;
			
		}
	}
}
