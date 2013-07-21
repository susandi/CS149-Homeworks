
import java.util.*;

public class FIFOAlgo {

 int max_pages=100;
 
 private int high=9;
 private int low=0;
 int []priority=new int[5];
 int lasti=0;
 static String []physicalMemo=new String[4];

 String pageRef;
 
 public void genPageRef()
 {	
	int r=0, firstNum=0,lastNum=0;
	Random rangen=new Random();
	firstNum=rangen.nextInt(3)+2;
	StringBuffer sb=new StringBuffer();
	//clean up null in phyMemo 
	for(int i=0;i<4;i++)
		 physicalMemo[i]=" ";
	
	for(int i=0;i<max_pages;i++)
	{		
			int distance=0;
			r=rangen.nextInt(high-low)+low;//gen random number between 0 thru 9

		 	
			if(0<=r && r<7)
			{	int tmp=rangen.nextInt(3);//generate random number either 0,1,2; if 2,then the number is -1
			
				if(tmp==2) tmp=-1; 
				distance=tmp;
				
				if(i==0) {lastNum=firstNum+distance; System.out.println("first Num before: "+firstNum);}
				else	
				{ 	
					lastNum=lastNum+distance; 
					if(lastNum<0 || lastNum>9) lastNum=rangen.nextInt(10);
					

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
	 refToPhysicalMemo(pageRef);
	 
 
 }
 
 private void refToPhysicalMemo(String prf)
 {	int i=0,count=0, availSlot=0;
 	int checkBit=0;
 	System.out.println("----------First In First Out------------");
 	System.out.println("**Page ref numbers: "+prf+"**\n");
 do
	 {	
		if(searchEmpSlotInPhyMemo()!=-1)  //if there is empty slot
		{		
	
				checkBit=checkSamePageRef(prf.substring(i,i+1));
			
			
				if (checkBit==999 )//if there is no redundant ref number, fill up the slot
				{	count++;
					availSlot=searchEmpSlotInPhyMemo();	
					physicalMemo[availSlot]=prf.substring(i,i+1);
					priority[availSlot]=findLargest()+1;
					
					System.out.println("Added to memory: "+physicalMemo[availSlot]);
					System.out.println();
					if(count==4 || i==max_pages-1)
					{
						if(i==max_pages-1) lasti=max_pages-1;
						FIFO();
					}
				
				}
				else System.out.println("same page reference! skip "+physicalMemo[checkBit]); 
				i++;
		}
		else //if there is no slot, start FIFO, take one page out at a time
		{ 
			checkBit=checkSamePageRef(prf.substring(i,i+1));
			//			System.out.println("read ch in i: "+i+" , "+prf.substring(i,i+1));	
			if(i==max_pages-1) lasti=max_pages-1;	
			if(checkBit!=999){System.out.println("same page reference! skip "+physicalMemo[checkBit]);  i++;}
			 FIFO();
		
		}
		
		//prevRef=Integer.parseInt(prf.substring(i,i+1));
	 }while(i<max_pages);
	

	 System.exit(0);
 }
private void FIFO()
{
	
	
	System.out.println("------PageRef------");
	 for( int x=0;x<4;x++)	
	 	System.out.print("| phymem["+x+"]: "+physicalMemo[x]+"   | ");
	 	
	 System.out.println();
	
	 for( int x=0;x<4;x++)
		 System.out.print("| priority["+x+"]: "+priority[x]+" | ");
	 System.out.println();
	 
	 if(lasti!=max_pages-1)
	 {	
		 for(int i=0;i<4;i++)
		 {
			if (priority[i]==1) 
			{ 	
				System.out.println(physicalMemo[i]+" was out"); 
				priority[i]=-1;  
				physicalMemo[i]=" ";
			 
			}
			else priority[i]=priority[i]-1;	
		}
	 }
	 
	
}
 public int findLargest()
 {
	 int largest=priority[0];
	 
	 for(int x=0; x<priority.length; x++)
	 {
		 if(priority[x]>largest)
			 largest = priority[x];
	 }
	
	 return largest;
 }

public static void main(String args[])
{
	FIFOAlgo fifo=new FIFOAlgo();
	fifo.genPageRef();

}
private int checkSamePageRef(String pf)
{
	 for(int i=0;i<4;i++)
	 {	
		
		  if (physicalMemo[i].contains(pf)) return i;

	 }
	   return 999;
}
private int searchEmpSlotInPhyMemo()
{	int i;
	 for( i=0;i<4;i++)
	 {	
	
		 if(physicalMemo[i]==" ")

			 return i;

	 }
	 return -1;
}
}
