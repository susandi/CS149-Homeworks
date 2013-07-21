
import java.util.*;

public class SCHAlgo {

 int max_pages=20;
 
 private int high=9;
 private int low=0;
 int []priority=new int[4];
 int []refBit=new int[10];
 int arr[][]=new int[4][4];
 boolean fillbit=false;
 int checkBit=0;
 static String []physicalMemo=new String[4];

 String pageRef;
 
 public void genPageRef()
 {	
	/*int r=0, firstNum=0,lastNum=0;
	Random rangen=new Random();
	firstNum=rangen.nextInt(3)+2;
	StringBuffer sb=new StringBuffer();
	//clean up null in phyMemo 
	for(int i=0;i<4;i++)
		 physicalMemo[i]=" ";
	
	for(int i=0;i<max_pages;i++)
	{		
			int distance=0;
			r=rangen.nextInt(high-low)+low;
		 	
			if(0<=r && r<7)
			{	int tmp=rangen.nextInt(3);//generate random number either 0,1,2; if 2,then the number is -1
			
				if(tmp==2) tmp=-1; 
				distance=tmp;
				
				if(i==0) {lastNum=firstNum+distance; System.out.println("first Num before: "+firstNum);}
				else	
				{ 	

					lastNum=lastNum+distance; 
					if(lastNum<0 || lastNum>9) lastNum=rangen.nextInt(10);
					
//					System.out.println("distance: "+distance+ " lastNum: "+lastNum); 
				}
			}
			else if(7<=r && r<=9) 
			{
				int tmp1=rangen.nextInt(4)+2; //generate random number greater than 1 but less than 4	
				distance=tmp1;
				
				if(i==0){ lastNum=firstNum+distance; }

				else
				{	

					lastNum=lastNum+distance;
					if(lastNum<0 || lastNum>9) lastNum=rangen.nextInt(10);
	
				}
			}
			//convert int to string
			if (i==0) pageRef=String.valueOf(firstNum);
			else pageRef=String.valueOf(lastNum);
			//append each char string to pageRef string
			sb.append(pageRef);
	}
	pageRef=sb.toString(); //string buffer to string
	*/
	//clean up null in phyMemo 
		for(int i=0;i<4;i++)
			 physicalMemo[i]=" ";
	pageRef="21665557777012345678";
	 refToPhysicalMemo(pageRef);
	 
 
 }
 
 private void refToPhysicalMemo(String prf)
 {	int i=0, count=0, availSlot=0;String num;
 	
 	//set all refBits to 0 in the beginning
 		for( int x=0;x<4;x++)
 			refBit[x]=0;
 
 	System.out.println("----------Second Chance------------");
 	System.out.println("**Page ref numbers: "+prf+"**");
 do
	 {	
		if(searchEmpSlotInPhyMemo()!=-1)  //if there is empty slot
		{		
//			System.out.println("See i: "+i);
				num=prf.substring(i,i+1);
				checkBit=checkRefBit(num);
				
//				System.out.println("read ch: "+num);
				if (checkBit==999 )//if there is no reference bit set for the number, fill up the slot
				{	count++;
					availSlot=searchEmpSlotInPhyMemo();	
					physicalMemo[availSlot]=num;
					
					refBit[Integer.parseInt(num)]=1;
//					System.out.println("*refBit["+num+"]: "+refBit[Integer.parseInt(num)]);
					if(findLargest(priority)==0)priority[availSlot]=1;
					else priority[availSlot]=findLargest(priority)+1;
					
					
					System.out.println("Added to memory: "+physicalMemo[availSlot]);
					System.out.println();
					if(count==4)
					{
					
						 
						SCHAlgo();
					}
				}
				else if(checkBit>=0)//if the ref bit set for the number, gives its second chance
				{	
					refBit[checkBit]=refBit[checkBit]+1;
					int refIndex=searchRefInPhysicalMemo(checkBit)+1;//we added 1 to match with priority that starts at 1
//					System.out.println( " findlargest is now "+findLargest()+","+refIndex);
					//if the current reference is the largest, the reference is already in the end of the queue
					if(refIndex!=findLargest(priority)) priority[refIndex-1]=findLargest(priority);//to find actual index, we need to subtract 1
					
					System.out.println( checkBit+" needs second chance."+"\nPriority is now "+priority[refIndex-1]+"\n");		
				}	
			
				i++;	
				//else System.out.println("same page reference! skip one"); 
		}
		else //if there is no slot, start FIFO, take one page out at a time
		{ 
			
			 
			SCHAlgo();
			
		}
		
		//prevRef=Integer.parseInt(prf.substring(i,i+1));
	 }while(i<max_pages);
	

	 System.exit(0);
 }
 private int checkRefBit(String pf)
 {
 	int tmp=Integer.valueOf(pf);
 	 for(int i=0;i<10;i++)
 	 {	
 		
 		  if (refBit[tmp]>0)
 			 
 			 return tmp;
 			
 	 }
 		 
 	   return 999;//means there is no reference bit set for specific value
 	 
 }
private void SCHAlgo()
{	
	
	
		for(int i=0;i<4;i++)
		{
			 /*smallest priority has to leave first since it stays longest.*/
		
			if(priority[i]==findSmallest(priority))
			{
				System.out.println(physicalMemo[i]+" was out"); 
				priority[i]=-1;  
				int t=Integer.valueOf(physicalMemo[i]);
				refBit[t]=0;
				physicalMemo[i]=" ";
				
			 
			}
			else priority[i]=priority[i]-1;	
		
		}
//		int indx,indx1;
//		indx=findSmallestIn2D(arr);
//		System.out.println("smallest indx: "+indx);
//		indx1=Integer.valueOf(physicalMemo[arr[indx][0]]);
//		System.out.println("small value: "+indx1);
//		refBit[indx1]=0;
//		physicalMemo[arr[indx][0]]=" ";
//		priority[indx]=-1; 
////		System.out.println(physicalMemo[arr[indx][0]]+" was out"); 
//		System.out.println(indx1+" was out"); 
		
	
	System.out.println("\n------PageRef------");
	 for( int x=0;x<4;x++)	
	 	System.out.print("| phymem["+x+"]: "+physicalMemo[x]+"   | ");
	 	
	 System.out.println();
	
	 for( int x=0;x<4;x++)
		 System.out.print("| priority["+x+"]: "+priority[x]+" | ");
	 System.out.println();
	 
	for( int x=0;x<4;x++)
	{	int index;
		if(physicalMemo[x]==" ") index=x;
		else  index=Integer.parseInt(physicalMemo[x]);
		
		System.out.print("| refBit["+x+"]: "+refBit[index]+" | ");
		arr[x][0]=x;
		arr[0][x]=refBit[index];
		
	}
	System.out.println();
	
	
}
private int searchRefInPhysicalMemo(int n)
{	String tmp=Integer.toString(n);
	 for(int i=0;i<4;i++)
	 {	 if (physicalMemo[i].contains(tmp)) return i;}
	return 0;
}

 public int findLargest(int []pri)
 {
	 int largest=pri[0];
	 
	 for(int x=0; x<pri.length; x++)
	 {
		 if(pri[x]>largest)
			 largest = pri[x];
	 }
	
	 return largest;
 }
 public int findSmallestIn2D(int [][] aArr)
 {	int smidx=0;
	 int smallest=aArr[0][0];
	 
	 for(int x=0; x<aArr.length; x++)
	 {
		 if(aArr[0][x]<smallest)
		 { smallest = aArr[0][x];
		 	smidx=x;
		 }
	 }
	
	 return smidx;
 }

 public int findSmallest(int []pi)
 {
	 int smallest=pi[0];
	 
	 for(int x=0; x<pi.length; x++)
	 {
		 if(pi[x]<smallest)
			 smallest = pi[x];
	 }
	
	 return smallest;
 }
public static void main(String args[])
{
	SCHAlgo sch=new SCHAlgo();
	sch.genPageRef();

}

private int searchEmpSlotInPhyMemo()
{	int i;
	 for( i=0;i<4;i++)
	 {	
		//if(i==4) System.out.println("this is 4: "+physicalMemo[4]);
		 if(physicalMemo[i]==" ")
		 {  
			 //System.out.println("found slot: "+i);
			 return i;
		}
	 }
	 return -1;
}
}
