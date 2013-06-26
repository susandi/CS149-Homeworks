
import java.util.*;


class sjf
{

  static int simrun=0;
  static int aflag=0;
  
  static float  simturntime;
  static float  simwaittime;
  static float  simrestime;
  
  
  
public static void main(String args[])
{

   if(aflag==0) {
		 simturntime=0;
		 simwaittime=0;
		 
	 }
	
	 System.out.println("Shortest Job First (SJF) Scheduling Simulation");	 
for(simrun=0;simrun<5;simrun++) {
int n=10; // no of process


int process[]=new int[n+1];
int burst[]=new int[n+1];
int waiting[]=new int[n+1];
int turn[]=new int[n+1];


for(int m=1;m<=n;m++)
{
	int newProcessDuration;
	Random rand0 = new Random();

	do {
        newProcessDuration = rand0.nextInt(10); // generate burst time (0 - 10)
      } while(newProcessDuration == 0);
	
process[m]=m;

burst[m]=newProcessDuration;


}


int temp;


for(int i=1;i<n;i++)
    {
    
    for(int j=1;j<n;j++)
        {
        
        if(burst[j+1]<burst[j])
            {
            
            temp=burst[j+1];
            burst[j+1]=burst[j];
            burst[j]=temp;
            
            temp=process[j+1];
            process[j+1]=process[j];
            process[j]=temp;
            
            }
        
        }
    
    }
    
turn[1]=burst[1];    


for(int i=2;i<=n;i++)
    {
    
    turn[i]=burst[i]+turn[i-1];
    waiting[i]=turn[i]-burst[i];
    
    }
    
int tot_turn=0,tot_wait=0,tot_res=0;    


for(int i=1;i<=n;i++)
    {
    
    tot_turn+=turn[i];
    tot_wait+=waiting[i];
    tot_res+=waiting[i];
    
    }
    
float avg_turn=(float)tot_turn/n;
float avg_wait=(float)tot_wait/n;
float avg_res=(float)tot_res/n;
simturntime += avg_turn;
simwaittime += avg_wait;
simrestime += avg_res;

System.out.println("Simulation : "+ (simrun+1));
System.out.println("+========+=============+=========+============+");
System.out.println("|Process |    Burst    | Waiting |  Response  |");
System.out.println("|        |    Time     |  Time   |    Time    |");
System.out.println("+========+=============+=========+============+");


for(int m=1;m<=n;m++)
    System.out.printf("|  %2d    |      %2d     |    %2d   |     %2d     | \n",process[m],turn[m],waiting[m], waiting[m]);
    
System.out.println("+========+=============+=========+============+");
System.out.println("Simulation  "+ (simrun+1) + " statistics:");

System.out.println("avg turn around time : "+avg_turn);
System.out.println("avg waiting time     : "+avg_wait);
System.out.println("avg response time    : "+avg_res);
System.out.println("+================================+");

}


System.out.println("Average Simulation Statistics");
System.out.println("avg turn around time : "+(simturntime/5));
System.out.println("avg waiting time     : "+(simwaittime/5));
System.out.println("avg response time    : "+(simrestime/5));
System.out.println("Average Throughput per 100 quanta: 10");

}




}

