import uuid
import hashlib



while True: #Have user enter their username & password
    userName = input("Enter username: ")
    passwordEntered = input("Enter password: ")
    

    loginSuccess(False)
    checkData = open("credentials.txt" "r")
    
    while checkData.readline():
        record = checkData.readline()
        #Splits records in file by seperating them by tab
        columns = record.split()
        
        #Compares credentials in file with username
        if columns[0].strip()==(userName):
            #Compared credentials with password
            if columns[1].strip()==(passwordEntered):
                loginSuccess(True)
                
                break
#Once logged in, the user will be prompt
if loginSuccess(True):
    exitChoice = input("Would you like to log out? (y/n)")

    #User chooses to logout
    if exitChoice.lower()==('y'):
        print("You have successfully logged out.")
        break
    else:
        loginSuccess(False)
 
#If user inputted incorrect username/password 
else:
    #Add 1 to number of attempts
    passwordAttempt = passwordAttempt + 1
    
    #Once password attempts reach 3, it will log user out
    if passwordAttempt == 3:
        print("Maximum login attempts reached.")
        print("Exiting Program.")
        
        break
    #Attempts haven't been reached, prompt user to re-enter correct credentials
    else:
        print("Incorrect username/password. Please try again.")
    