
import java.util.*;

public class LRUAlgo {

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
				
				if(i==0) {lastNum=firstNum+distance; System.out.println("first Num : "+firstNum);}
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
	 
	//clean up null in phyMemo 
//		for(int i=0;i<4;i++)
//			 physicalMemo[i]=" ";
//	 pageRef="4471334545432677999010109";
//	 refToPhysicalMemo(pageRef);
 
 }
 
 private void refToPhysicalMemo(String prf)
 {	int i=0,count=0, availSlot=0;
 	int checkBit=0;
 	System.out.println("----------Least Recently Used------------\n");
 	System.out.println("**Page ref numbers: "+prf+"**\n");
 do
	 {
//	 System.out.println("\nread ch: "+i+" , "+prf.substring(i,i+1));
		if(searchEmpSlotInPhyMemo()!=-1)  //if there is empty slot
		{		
	
				checkBit=checkSamePageRef(prf.substring(i,i+1));
			
//				System.out.println("checkBit: "+checkBit);
				if (checkBit==999 )//if there is no redundant ref number, fill up the slot
				{	count++;
					availSlot=searchEmpSlotInPhyMemo();	
//					System.out.println("availslot: "+availSlot);
					
					physicalMemo[availSlot]=prf.substring(i,i+1);
					//priority[availSlot]=findLargest()+1;
					
					System.out.println("\nAdded to memory: "+physicalMemo[availSlot]);
					System.out.println();
					
					
					if(count==4 || i==max_pages-1)
					{
						if(i==max_pages-1) { lasti=max_pages-1;}
						LRU();
					}
				
				}
				else//if there is redundant, checkBit has the index of redundant value
				{	
					if(checkBit!=3 && i<max_pages-1)
					{	
						
						if( checkBit!=(Integer.valueOf( prf.substring(i+1,i+2))))//if the plus one neighbor is not empty, then the page is not in last seat of the queue, need to move to the end.
						{	
							//if(checkBit==(Integer.valueOf( prf.substring(i+1,i+2))))System.out.println("same value neighbor");
							SamePageRefMovetoEndOfQueue(checkBit);
						}
						
						
					}
					else if (i==max_pages-1) { SamePageRefMovetoEndOfQueue(checkBit); LRU();}
				}
				i++;
		}
		else //if there is no slot, start LRU, take one page out at a time
		{ 
			checkBit=checkSamePageRef(prf.substring(i,i+1));
//						System.out.println("read ch in i: "+i+" , "+prf.substring(i,i+1));	
			if(i==max_pages-1) lasti=max_pages-1;	
			if(checkBit!=999 && checkBit!=3 &&  checkBit!=(Integer.valueOf( prf.substring(i+1,i+2))))
			{	SamePageRefMovetoEndOfQueue(checkBit);  
				i++;
			}
			 LRU();
		
		}
		
		//prevRef=Integer.parseInt(prf.substring(i,i+1));
	 }while(i<max_pages);
	

	 System.exit(0);
 }
 private void SamePageRefMovetoEndOfQueue(int cb)
 {	String savePage=physicalMemo[cb];
//	 System.out.println("savePage: "+savePage);
		physicalMemo[cb]=" ";
		for(int c=cb;c<3;c++)
		{
			physicalMemo[c]=physicalMemo[c+1];
			//priority[c+1]=priority[c];
	
		}
		if(searchEmpSlotInPhyMemo()!=-1)
			physicalMemo[searchEmpSlotInPhyMemo()]=savePage;
		else physicalMemo[3]=savePage;
		//priority[3]=findLargest();
	
		System.out.println("same page reference! "+savePage+" was moved to the end of queue"); 

	 
 }
private void LRU()
{
	System.out.println();
	System.out.println("----------------------------------------------------------------------");
	System.out.println("------PageRef------");
	 for( int x=0;x<4;x++)	
	 	System.out.print("| phymem["+x+"]: "+physicalMemo[x]+"   | ");
	 	System.out.println();
	

	 
	 if(lasti!=max_pages-1)
	 {		
		 System.out.println(physicalMemo[0]+" was out");
		 physicalMemo[0]=" ";
		 
		 for(int c=0;c<4;c++) 
		 {	 if(c<3) physicalMemo[c]=physicalMemo[c+1];
			 else
				 physicalMemo[3]=" ";
		 }	
	 }
	 System.out.println("------AFTER------");
	 for( int x=0;x<4;x++)	
	 {	 	System.out.print("| phymem["+x+"]: "+physicalMemo[x]+"   | ");}
	 		
	 		System.out.println();
	 		System.out.println("----------------------------------------------------------------------");
	 		System.out.println();
}
// public int findLargest()
// {
//	 int largest=priority[0];
//	 
//	 for(int x=0; x<priority.length; x++)
//	 {
//		 if(priority[x]>largest)
//			 largest = priority[x];
//	 }
//	
//	 return largest;
// }

public static void main(String args[])
{
	LRUAlgo lru=new LRUAlgo();
	lru.genPageRef();

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
		 {
			 
			 	return i;
		 }
	 }
	 return -1;
}
}
