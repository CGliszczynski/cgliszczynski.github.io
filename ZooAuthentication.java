/*Courtney Gliszczynski
IT145
*/

//importing java functions needed for the program
import java.io.File;
import java.security.MessageDigest;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

//Defining file name
public class ZooAuthentication {
    
    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner (System.in);
        
        //Define variables
        String userName = "";
        String passwordEntered = "";
        int passwordAttempt = 0;
        String userInfo = "";
        String exitChoice = "";
        String menuChoice = "";
        boolean clockedIn;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        boolean loggedIn = true;

        
        /*Start a while loop until there was a successful login or the max
        number of attempts have been met.*/
        while(true) {
            //Have user enter their username & password
            System.out.println("Enter username: ");
            userName = scnr.nextLine();
            System.out.println("Enter password: ");
            passwordEntered = scnr.nextLine();
            
            //Hash code inserted. Coverts given password to hash password.
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(passwordEntered.getBytes());
            byte[] digest = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }
            //Assign boolean value for (un)successful login
            boolean loginSuccess = false;
            Scanner checkData = new Scanner (new File("credentials.txt"));
            
            while(checkData.hasNextLine()) {
                userInfo = checkData.nextLine();
                //Splits the records in the file by seperating them by tab.
                String columns [] = userInfo.split("\t");
                
                //Compares credentials in file with username inputted.
                if(columns[0].trim().equals(userName)) {
                    //Compares credentials in file with password inputted.
                    if(columns[1].trim().equals(sb.toString())) {
                        /*Sets the login Success to true because the username
                        and password are correct*/
                        loginSuccess = true;
                        
                        Scanner inputReader = new Scanner(new File(columns[3].trim()+".txt"));
                        
                        while(inputReader.hasNextLine()) {
                            //Outputs the corresponding message depnding on user
                            System.out.println(inputReader.nextLine());
                        }
                        break;
                    }
                }
            }
            //Once logged in, the user will be given a menu.
            if(loginSuccess) {
            	while(true) {
            		String mainMenu = ("Welcome" + "\n"
            				+ "Please Select An Option: " + "\n"
            				+ "1. Punch In" + "\n"
            				+ "2. Punch Out" + "\n"
            				+ "3. Logout");
                
            		System.out.println(mainMenu);
               
            		menuChoice = scnr.nextLine(); //Reads users input
                
            		int menuChoiceInt = Integer.parseInt(menuChoice); //Converts input into integer
                
            		//Used switch so the user would be returned to menu after
            		switch(menuChoiceInt) { 
            		
            		//if user isn't clocked in, it punched them in.
            		case 1: {
            			if(clockedIn = false) {
            				Date date = new Date();
            				System.out.println("Punched In: " + "\n" + date);
            				clockedIn = true;
            			}
            			//Otherwise it returns to the menu
            			else {
            				System.out.println("Already Punched In");
            			}
            			continue;
            		}
                
            		case 2:
            			//if user isn't punched out, it does so
            			if(clockedIn = true) {
            				Date date = new Date();
            				System.out.println("Punched Out: " + "\n" + date);
            				clockedIn = false;
            			}
            			//otherwise returns to menu
            			else {
            				System.out.println("Already Punched Out");
            			}
            			continue;
            		
            		case 3:
            			//Logs user off of program
            			System.out.println("You have successfully logged out.");
            			break;
            		}
        			break;
                
            	}
            }
            
            //If user inputted incorrect username/password.
            else {
                //Adds 1 to the number of attempts
                passwordAttempt = (passwordAttempt + 1);
                
                //Once password attempts reach 3, it will log user out
                if(passwordAttempt==3) {
                    System.out.println("Maximum login attempts reached.");
                    System.out.println("Exiting Program.");
                    
                    break;
                }
                /*Attempts haven't been reached, prompt user to re-enter correct
                credentials*/
                else {
                    System.out.println("Incorrect username/password. Please try again.");
                }
            }

        }
    }
    
}
