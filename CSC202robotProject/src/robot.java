import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.*;

/*
 * Simulate the operation of a simple robot. 
 * The robot moves in four directions: forward, backward, right, and left.
 * The robot moves items and place them in the right slots in each station.
 * There are 10 stations plus the pick up station
 * Pick up station is the initial start where the robot picks up items
 * 	Stipulations
 * 		8 items can be placed in each station
 * 		The nearest station must be filled before placing items in the next station
 * 		The items are marked with ID or serial # (5digit)
 * 		The items with even serial number go to the left and odds go to the right
 * 		The last slot #7 is reserved for light items which weigh less than 50kg.
 * 		MSB digit 5 items must go to slot #5
 * 		By receiving the message unload, the robot unloads station #5 and place into refrigeration container until empty
 * 		Station 12 and 14 has 4 slots
 * 		Station 12 marked for immediate delivery
 * 		Station 14 requires 5 day storage
 * 		Station 9 and 10 are for special delivery
 */
public class robot {
	private int currentpos;

	private static final int locationOfStations = 7;
	private static final int pickupStation = 0;
	private static String filename = "Packages";
	private static String station1 = "station1";
	private static String station2 = "station2";
	private static String station3 = "station3";
	private static String station4 = "station4";
	private static String station5 = "station5";
	private static String station6 = "station6";
	private static String station7 = "station7";
	private static String station8 = "station8";
	private static String station9 = "station9";
	private static String station10 = "station10";
	private static String station11 = "station11";
	private static String station12 = "station12";
	private static String station14 = "station14";
	private static int[] a = new int[80];
	private static String currentpos0 = "pick up station";
	private static String currentpos1 = "12 & 14";
	private static String currentpos2 = "1 & 2";
	private static String currentpos3 = "3 & 4";
	private static String currentpos4 = "5 & 6";
	private static String currentpos5 = "7 & 8";
	private static String currentpos6 = "9 & 10";
	private static String currentpos7 = "dropoff station";
    private static List<Integer> even = new ArrayList<>();
    private static List<Integer> odd = new ArrayList<>();
    private static List<Integer> fives = new ArrayList<>();
    private static List<Integer> MSB = new ArrayList<>();
	public robot() {
		currentpos = 0;
	}
	public robot(int cpos) {
		currentpos = cpos;
	}
	public static void readFile(String filename) throws IOException, NumberFormatException {
		String inLine;
		BufferedReader br = new BufferedReader(new FileReader(filename));
		int i = 0;
		while(( inLine = br.readLine()) !=null) {
			for(int j = 0; j < a.length; j++) {
				int b = Integer.parseInt(inLine);
				a[i] = b;
			}
			i++;
		}
		br.close();
	}

	public static void displayArray(int a[]) {
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}

	public static void classify() {
		for(int i = 0 ; i < a.length ; i++){
	        if(a[i] % 2 == 0)
	            even.add(a[i]);
	        else
	        	odd.add(a[i]);
		}
	}
	public static void checkMSB (int[] a) {
		for(int i = 0; i < a.length; i++) {
			if (((int)a[i]/10000) == 5)
				MSB.add(a[i]);
		}

	}
	public static void displaySortedList(List<Integer> list){
        for(Integer i : list)
            System.out.println(i);
    }
	public void moveForwards(int dropOff) {
		int i = 0;
		if(dropOff < pickupStation || dropOff > locationOfStations || dropOff == currentpos)
			System.out.println("You entered a station number that is out of bounds");
		else
			if(dropOff > currentpos) {
				System.out.println("The robot is currently by station # " + currentpos0 + "\n");
				while(dropOff > currentpos || dropOff == currentpos-1) {
					currentpos++;
					if(currentpos == 1){
						System.out.println("The robot is now moving forward towards stations: "
							+ currentpos1 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos1 + "\n");
							
						}
					}
					else if(currentpos == 2) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos2 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos2 + "\n");
						}
					}
					else if(currentpos == 3) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos3 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos3 + "\n");
						}
					}
					else if(currentpos == 4) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos4 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos4 + "\n");
						}
					}
					else if(currentpos == 5) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos5 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos5 + "\n");
						}
					}
					else if(currentpos == 6) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos6 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos6 + "\n");
						}
					}
					else if(currentpos == 7) {
						System.out.println("The robot is now at station: "
								+ currentpos7 + "\n");
						if(currentpos == dropOff){
						System.out.println("The robot has stopped moving. Currently at: " 
								+ currentpos7 + "\n");
						}
					}
				}
				
			}
	}
	public void moveBackwards(int dropOff) {
		if(dropOff < pickupStation || dropOff > locationOfStations || dropOff == currentpos)
			System.out.println("You entered a station number that is out of bounds");
		else
			if(dropOff < currentpos) {
				while(dropOff < currentpos && currentpos > 0) {
					currentpos--;
					if(currentpos == 6) {
						System.out.println("The Robot is now moving backwards towards stations : " 
							+ currentpos6 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos6 + "\n");
						}
					}
					else if(currentpos == 5) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos5 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos5 + "\n");
						}
					}
					else if(currentpos == 4) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos4 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos4 + "\n");
						}
					}
					else if(currentpos == 3) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos3 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos3 + "\n");
						}
					}
					else if(currentpos == 2) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos2 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos2 + "\n");
						}
					}
					else if(currentpos == 1) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos1 + "\n");
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos1 + "\n");
						}
					}
					else if(currentpos == 0) {
						System.out.println("The robot has returned to home Station: "
								+ currentpos0 + "\n");
					}
			}
		}
	}
	public void moveRight() throws NumberFormatException, IOException { //odd number serials
		boolean delievered = false;
		if(currentpos > 6 || currentpos < 1)
			System.out.println("You entered a station number that is out of bounds");
			while(delievered == false) {
				if(currentpos == 1) {
				System.out.println("The robot turned right towards station 12 used for immediate deliveries");;
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station12")));
					//for(Integer i : odd) {
					for(int i = 0; i < 4; i++) {
						String str = odd.get(i).toString();
						pw.write(str + "\n");
						//pw.println(i + " has been placed into this station.");
						//pw.flush();
						System.out.println(odd.get(i) + " has been placed into this station"
								+ ", ready for immediate delivery.");
					
					}
				delievered = true;
				pw.close();
				}
				else if(currentpos == 2) {
					System.out.println("The robot turned right towards station 1");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station1")));
					for(int i = 4; i < 8; i++) {
						String str = odd.get(i).toString();
						pw.write(str + "\n");
						System.out.println(odd.get(i) + " has been placed into this station.");
					
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 3) {
					System.out.println("The robot turned right towards station 3");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station3")));
					for(int i = 8; i < 16; i++) {
						String str = odd.get(i).toString();
						pw.write(str + "\n");
						System.out.println(odd.get(i) + " has been placed into this station.");
					
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 4) {
					System.out.println("The robot turned right towards station 5 used for serials with 5");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station5")));
					for(int i = 0; i < 8; i++) { //change this to MSB so redo the loop i = 0
						String str = MSB.get(i).toString();
						pw.write(str + "\n");
						System.out.println(MSB.get(i) + " has been placed into this station.");
					}
					delievered = true;
					pw.close();
					//create unload method to have robot deliver to refrigation container
					}
				else if(currentpos == 5) {
					System.out.println("The robot turned right towards station 7 "
							+ "used for items less than 50kg");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station7")));
					for(int i = 16; i < 24; i++) {
						String str = odd.get(i).toString();
						pw.write(str + "\n");
						System.out.println(odd.get(i) + " has been placed into this station. "
								+ "Packages meet the weight requirement of less than 50kg");
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 6) {
					System.out.println("The robot turned right towards station 9 "
							+ "used for special deliveries");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station9")));
					for(int i = 32; i < 40; i++) {
						String str = odd.get(i).toString();
						pw.write(str + "\n");
						System.out.println(odd.get(i) + " has been placed into this station, "
								+ "ready for special delivery.");
					}
					delievered = true;
					pw.close();
					}
			} //Write that robot is going to drop off a package, insert method to write to the station text file
	}
	public void moveLeft() throws IOException { //even number serials
		boolean delievered = false;
		if(currentpos > 6 || currentpos < 1)
			System.out.println("You entered a station number that is out of bounds");
			while(delievered == false) {
				if(currentpos == 1) {
				System.out.println("The robot turned right towards station 14 "
						+ "used for 5 day storage");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station14")));
				for(int i = 0; i < 4; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed for storage of 5 days.");
				}
				delievered = true;
				pw.close();
				}
				else if(currentpos == 2) {
					System.out.println("The robot turned right towards station 2");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station2")));
					for(int i = 4; i < 8; i++) {
						String str = even.get(i).toString();
						pw.write(str + "\n");
						System.out.println(even.get(i) + " has been placed into this station.");
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 3) {
					System.out.println("The robot turned right towards station 4");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station4")));
					for(int i = 8; i < 16; i++) {
						String str = even.get(i).toString();
						pw.write(str + "\n");
						System.out.println(even.get(i) + " has been placed into this station.");
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 4) {
					System.out.println("The robot turned right towards station 6");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station6")));
					for(int i = 16; i < 24; i++) {
						String str = even.get(i).toString();
						pw.write(str + "\n");
						System.out.println(even.get(i) + " has been placed into this station.");
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 5) {
					System.out.println("The robot turned right towards station 8");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station8")));
					for(int i = 24; i < 32; i++) {
						String str = even.get(i).toString();
						pw.write(str + "\n");
						System.out.println(even.get(i) + " has been placed into this station.");
					}
					delievered = true;
					pw.close();
					}
				else if(currentpos == 6) {
					System.out.println("The robot turned right towards station 10 used for special deliveries");
					PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station10")));
					for(int i = 32; i < 40; i++) {
						String str = even.get(i).toString();
						pw.write(str + "\n");
						System.out.println(even.get(i) + " has been placed into this station,"
								+ "ready for special delivery.");
					}
					delievered = true;
					pw.close();
					}
			} //Write that robot is going to drop off a package, insert method to write to the station text file
	}

	public static void main(String[] args) throws IOException { 

		readFile(filename); //Read from the master list and places into []a
		//displayArray(a);	//Display master list from array
		
		classify();
		checkMSB(a);
		System.out.println("even sorted list"); 
       // displaySortedList(even);
        System.out.println("odd sorted list");
       // displaySortedList(odd);  
        
        robot rob = new robot();
        rob.moveForwards(1); //Move towards station 12 & 14
        rob.moveRight();     //Turn to station 12
        rob.moveBackwards(0);
        rob.moveForwards(1); //Move towards station 12 & 14
        rob.moveLeft();      //Turn to station 14
        rob.moveBackwards(0);
        
        rob.moveForwards(2); //Move towards station 1 & 2
        rob.moveRight();     //Turn towards station 1
        rob.moveBackwards(0);
        rob.moveForwards(2); //Move towards station 1 & 2
        rob.moveLeft();		 //Turn towards station 2
        rob.moveBackwards(0);
        
        rob.moveForwards(3); //Move towards station 3 & 4
        rob.moveRight();     //Turn towards station 3
        rob.moveBackwards(0);
        rob.moveForwards(3); //Move towards station 3 & 4
        rob.moveLeft();		 //Turn towards station 4
        rob.moveBackwards(0);
		
        rob.moveForwards(4); //Move towards station 5 & 6
        rob.moveRight();     //Turn towards station 5
        rob.moveBackwards(0);
        rob.moveForwards(4); //Move towards station 5 & 6
        rob.moveLeft();		 //Turn towards station 6
        rob.moveBackwards(0);
        
        rob.moveForwards(5); //Move towards station 7 & 8
        rob.moveRight();     //Turn towards station 7
        rob.moveBackwards(0);
        rob.moveForwards(5); //Move towards station 7 & 8
        rob.moveLeft();		 //Turn towards station 8
        rob.moveBackwards(0);
        
        rob.moveForwards(6); //Move towards station 9 & 10
        rob.moveRight();     //Turn towards station 9
        rob.moveBackwards(0);
        rob.moveForwards(6); //Move towards station 9 & 10
        rob.moveLeft();		 //Turn towards station 10
        rob.moveBackwards(0);
        
        rob.moveForwards(7); 

	}
}


