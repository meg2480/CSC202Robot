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
	//Used as a master inventory list at the package facility
	private static String filename = "Packages";
	//Text files which serve as a dropping point for packages
	private static String station1 = "station1";
	private static String station2 = "station2";
	private static String station3 = "station3";
	private static String station4 = "station4";
	private static String station5 = "station5"; //MSB of 5 packages
	private static String station6 = "station6";
	private static String station7 = "station7";
	private static String station8 = "station8";
	private static String station9 = "station9";   //Marked for special delivery
	private static String station10 = "station10"; //Marked for special delivery
	private static String station12 = "station12"; //4 slots plus labeled for immediate delivery
	private static String station14 = "station14"; //4 slots plus labeled for 5 day storage

	//Position between 2 stations
	private static String currentpos0 = "pick up station"; //homestation
	private static String currentpos1 = "12 & 14";
	private static String currentpos2 = "1 & 2";
	private static String currentpos3 = "3 & 4";
	private static String currentpos4 = "5 & 6";
	private static String currentpos5 = "7 & 8";
	private static String currentpos6 = "9 & 10";
	private static String currentpos7 = "dropoff station";

	private static int[] a = new int[80];                  //Stores master inventory list
	private static List<Integer> even = new ArrayList<>(); //Sorted even serial # packages 
	private static List<Integer> odd = new ArrayList<>();  //Sorted odd serial # packages
	private static List<Integer> MSB = new ArrayList<>();  //Stored all MSB 5 serials

	public robot() {
		currentpos = 0;
	}
	public robot(int cpos) {
		currentpos = cpos;
	}
	//Reads the master inventory list and saves it into an array
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
	//Display array method used for testing.
	public static void displayArray(int a[]) {
		for(int i = 0; i < a.length; i++) {
			System.out.println(a[i]);
		}
	}
	//This method sorts the array into two seperate even & odd array lists
	public static void classify() {
		for(int i = 0 ; i < a.length ; i++){
			if(a[i] % 2 == 0)  //used to determinate if even 
				even.add(a[i]);
			else               // if not add to odd array list
				odd.add(a[i]);
		}
	}
	//Checks the list for MSB 5 serials
	public static void checkMSB (int[] a) { 
		for(int i = 0; i < a.length; i++) {
			if (((int)a[i]/10000) == 5)  //divides an element by 10000 and if it equals 5 then
				MSB.add(a[i]);			// add it to MSB array list
		}

	}
	//Displays the sorted lists for testing
	public static void displaySortedList(List<Integer> list){
		for(Integer i : list)
			System.out.println(i);
	}
	//Tells the bot to move forward down the line of stations to a specific station
	//Dropoff is the specfic location that is provided to the robot
	public void moveForwards(int dropOff) {
		int i = 0;
		//Checks if the given dropoff number is within the bounds of the compound 
		if(dropOff < pickupStation || dropOff > locationOfStations || dropOff == currentpos)
			System.out.println("You entered a station number that is out of bounds");
		else
			//if the above checks out then, print out the robots current position.
			if(dropOff > currentpos) {
				System.out.println("The robot is currently by station # " + currentpos0 + "\n");

				while(dropOff > currentpos || dropOff == currentpos-1) {
					//This while loop ensures that as long as the robot's currentpos is lower
					//Then the dropOff value, the loop will continue until it's currentpos is = dropOff
					//If the given station is further down the line from dropOff, 
					//The robot will move forward one step
					currentpos++;
					if(currentpos == 1) { 
						//When the currentpos is 1, then the robot will be between stations 12 & 14
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos1 + "\n");
						//When the currentpos equals the dropoff(provided to the robot) the robot will
						//stop moving at that location, and while loop ends
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos1 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 2) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos2 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos2 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 3) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos3 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos3 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 4) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos4 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos4 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 5) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos5 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos5 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 6) {
						System.out.println("The robot is now moving forward towards stations: "
								+ currentpos6 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff) {
							System.out.println("The robot has stopped moving. Currently by stations: "
									+ currentpos6 + "\n");
						}
					}
					//While continues same as above  until the robot has reached its destination
					//If position 2 is the robots current position, print its progress
					else if(currentpos == 7) {
						System.out.println("The robot is now at station: "
								+ currentpos7 + "\n");
						//If the robots position equals its destination, print its location, end loop
						if(currentpos == dropOff){
							System.out.println("The robot has stopped moving. Currently at: " 
									+ currentpos7 + "\n");
						}
					}
				}

			}
	}
	//Method provides robot the ability to return to any previous station/position
	public void moveBackwards(int dropOff) {
		//If statement checks for bounds given to the robot is valid
		if(dropOff < pickupStation || dropOff > locationOfStations || dropOff == currentpos)
			System.out.println("You entered a station number that is out of bounds");
		else
			//As long as the destination is below the currentpos then enter the while loop
			if(dropOff < currentpos) {
				while(dropOff < currentpos && currentpos > 0) {
					//Robot takes a step back and goes through conditions
					currentpos--;
					//If robot is at position 6 print its progress
					if(currentpos == 6) {
						System.out.println("The Robot is now moving backwards towards stations : " 
								+ currentpos6 + "\n");
						//If position 6 is its destination then the robot has reached its destination
						//end loop here
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos6 + "\n");
						}
					}
					//If robot is at position 5 print its progress
					else if(currentpos == 5) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos5 + "\n");
						//If position 5 is its destination then the robot has reached its destination
						//end loop here
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos5 + "\n");
						}
					}
					//If robot is at position 4 print its progress
					else if(currentpos == 4) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos4 + "\n");
						//If position 5 is its destination then the robot has reached its destination
						//end loop here
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos4 + "\n");
						}
					}
					//If robot is at position 3 print its progress
					else if(currentpos == 3) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos3 + "\n");
						//If position 3 is its destination then the robot has reached its destination
						//end loop here
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos3 + "\n");
						}
					}
					//If robot is at position 2 print its progress
					else if(currentpos == 2) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos2 + "\n");
						//If position 2 is its destination then the robot has reached its destination
						//end loop here
						if(currentpos == dropOff) {
							System.out.println("The robot is now at station : "
									+ currentpos2 + "\n");
						}
					}
					//If robot is at position 1 print its progress
					else if(currentpos == 1) {
						System.out.println("The robot is now moving backwards towards stations : "
								+ currentpos1 + "\n");
						//If position 1 is its destination then the robot has reached its destination
						//end loop here
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
	//Allows robot to move right towards the odd numbered stations in order: 12, 1, 3, 5, 7, 9
	public void moveRight() throws NumberFormatException, IOException { //odd number serials
		//Boolean set to false as long as the robot has not moved packages
		boolean delievered = false;
		//checks bounds from positions 1 - 6 because robot cannot turn at the pickup or dropoff stations
		if(currentpos > 6 || currentpos < 1)
			System.out.println("You entered a station number that is out of bounds");
		while(delievered == false) {         //while loop with conditions
			//if the robot's current position is 1 (station 12) then the robot will drop a package
			if(currentpos == 1) {
				System.out.println("The robot turned right towards station 12 used for immediate deliveries");;
				//Writing the serial number to the appropriate station text file serves to show
				//the robot dropping off packages
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station12")));
				//loop to write the first 4 elements to file
				for(int i = 0; i < 4; i++) { 
					String str = odd.get(i).toString();  //Write elements from the odd sorted arraylist
					pw.write(str + "\n");
					System.out.println(odd.get(i) + " has been placed into this station"
							+ ", ready for immediate delivery.");
				}
				delievered = true; //boolean condition met, exit loop
				pw.close();        //close stream
			}
			//if the robot's current position is 2 (station 1) then the robot will drop a package
			else if(currentpos == 2) {
				System.out.println("The robot turned right towards station 1");
				//Writing the serial number to the appropriate station text file serves to show
				//the robot dropping off packages
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station1")));
				//loop to write the next 4 elements to file
				for(int i = 4; i < 8; i++) { 
					String str = odd.get(i).toString();  //Write elements from the odd sorted arraylist
					pw.write(str + "\n");
					System.out.println(odd.get(i) + " has been placed into this station.");
				}
				delievered = true; //boolean condition met, exit loop
				pw.close();        //close stream
			}
			//if the robot's current position is 3 (station 3) then the robot will drop a package
			else if(currentpos == 3) {
				System.out.println("The robot turned right towards station 3");
				//Writing the serial number to the appropriate station text file serves to show
				//the robot dropping off packages
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station3")));
				//loop to write the next 8 elements to file
				for(int i = 8; i < 16; i++) {
					String str = odd.get(i).toString(); //Write elements from the odd sorted arraylist
					pw.write(str + "\n");
					System.out.println(odd.get(i) + " has been placed into this station.");

				}
				delievered = true;   //boolean condition met, exit loop
				pw.close();          //close stream
			}
			//if the robot's current position is 4 (station 5) then the robot will drop a package
			else if(currentpos == 4) {
				System.out.println("The robot turned right towards station 5 used for serials with 5");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station5")));
				//loop to write the 8 elements from the sorted MSB array list to file
				for(int i = 0; i < 8; i++) { 
					String str = MSB.get(i).toString();
					pw.write(str + "\n");
					System.out.println(MSB.get(i) + " has been placed into this station.");
				}
				delievered = true;   //boolean condition met, exit loop
				pw.close();          //close stream
			}
			//if the robot's current position is 5 (station 7) then the robot will drop a package
			else if(currentpos == 5) {
				System.out.println("The robot turned right towards station 7 "
						+ "used for items less than 50kg");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station7")));
				//loop to write the next 8 elements from the sorted odd array list
				for(int i = 16; i < 24; i++) {
					String str = odd.get(i).toString();
					pw.write(str + "\n");
					System.out.println(odd.get(i) + " has been placed into this station. "
							+ "Packages meet the weight requirement of less than 50kg");
				}
				delievered = true;    //boolean condition met, exit loop
				pw.close();           //close stream
			}
			//if the robot's current position is 6 (station 9) then the robot will drop a package
			else if(currentpos == 6) {
				System.out.println("The robot turned right towards station 9 "
						+ "used for special deliveries");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station9")));
				//loop to write the final 8 elements from the sorted odd array list
				for(int i = 32; i < 40; i++) {
					String str = odd.get(i).toString();
					pw.write(str + "\n");
					System.out.println(odd.get(i) + " has been placed into this station, "
							+ "ready for special delivery.");
				}
				delievered = true;  //boolean condition met, exit loop
				pw.close();         //close stream
			}
		} //Write that robot is going to drop off a package, insert method to write to the station text file
	}
	//Allows robot to move right towards the odd numbered stations in order: 14, 2, 4, 6, 8, 10
	public void moveLeft() throws IOException { 
		//boolean with condition
		boolean delievered = false;
		//checks bounds
		if(currentpos > 6 || currentpos < 1)
			System.out.println("You entered a station number that is out of bounds");
		//Boolean set to false as long as the robot has not moved packages
		while(delievered == false) {
			//if the robot's current position is 1 (station 14) then the robot will drop a package
			if(currentpos == 1) {
				System.out.println("The robot turned right towards station 14 "
						+ "used for 5 day storage");
				//loop to write the first 4 elements from the sorted even array list
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station14")));
				for(int i = 0; i < 4; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed for storage of 5 days.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
			else if(currentpos == 2) {
				//if the robot's current position is 2 (station 2) then the robot will drop a package
				System.out.println("The robot turned right towards station 2");
				//loop to write the next 4 elements from the sorted even array list
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station2")));
				for(int i = 4; i < 8; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed into this station.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
			else if(currentpos == 3) {
				//if the robot's current position is 3 (station 4) then the robot will drop a package
				System.out.println("The robot turned right towards station 4");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station4")));
				//loop to write the next 8 elements from the sorted even array list
				for(int i = 8; i < 16; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed into this station.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
			else if(currentpos == 4) {
				//if the robot's current position is 4 (station 6) then the robot will drop a package
				System.out.println("The robot turned right towards station 6");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station6")));
				//loop to write the next 8 elements from the sorted even array list
				for(int i = 16; i < 24; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed into this station.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
			else if(currentpos == 5) {
				//if the robot's current position is 5 (station 8) then the robot will drop a package
				System.out.println("The robot turned right towards station 8");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station8")));
				//loop to write the next 8 elements from the sorted even array list
				for(int i = 24; i < 32; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed into this station.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
			else if(currentpos == 6) {
				//if the robot's current position is 6 (station 10) then the robot will drop a package
				System.out.println("The robot turned right towards station 10 used for special deliveries");
				PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("Station10")));
				//loop to write the final 8 elements from the sorted even array list
				for(int i = 32; i < 40; i++) {
					String str = even.get(i).toString();
					pw.write(str + "\n");
					System.out.println(even.get(i) + " has been placed into this station,"
							+ "ready for special delivery.");
				}
				delievered = true;	//boolean condition met, exit loop
				pw.close();
			}
		} //Write that robot is going to drop off a package, insert method to write to the station text file
	}

	public static void main(String[] args) throws IOException { 

		readFile(filename); //Read from the master list and places into []a
		//displayArray(a);	//Display master list from array

		classify();  //Sort the saved array into even and odd arrayList
		checkMSB(a); //Check for MSB 5 from the array and save to MSB array List
		
		robot rob = new robot();
		rob.moveForwards(1); //Move towards station 12 & 14
		rob.moveRight();     //Turn to station 12 and drops packages off
		rob.moveBackwards(0);
		rob.moveForwards(1); //Move towards station 12 & 14
		rob.moveLeft();      //Turn to station 14 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(2); //Move towards station 1 & 2
		rob.moveRight();     //Turn towards station 1 and drops packages off
		rob.moveBackwards(0); 
		rob.moveForwards(2); //Move towards station 1 & 2
		rob.moveLeft();		 //Turn towards station 2 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(3); //Move towards station 3 & 4
		rob.moveRight();     //Turn towards station 3 and drops packages off
		rob.moveBackwards(0);
		rob.moveForwards(3); //Move towards station 3 & 4
		rob.moveLeft();		 //Turn towards station 4 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(4); //Move towards station 5 & 6
		rob.moveRight();     //Turn towards station 5 and drops packages off
		rob.moveBackwards(0);
		rob.moveForwards(4); //Move towards station 5 & 6
		rob.moveLeft();		 //Turn towards station 6 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(5); //Move towards station 7 & 8
		rob.moveRight();     //Turn towards station 7 and drops packages off
		rob.moveBackwards(0);
		rob.moveForwards(5); //Move towards station 7 & 8
		rob.moveLeft();		 //Turn towards station 8 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(6); //Move towards station 9 & 10
		rob.moveRight();     //Turn towards station 9 and drops packages off
		rob.moveBackwards(0);
		rob.moveForwards(6); //Move towards station 9 & 10
		rob.moveLeft();		 //Turn towards station 10 and drops packages off
		rob.moveBackwards(0);

		rob.moveForwards(7);  //Rob patrols to the end of the line searching for misplaced packages
		rob.moveBackwards(0); //Rob returns to pickup station

	}
}


