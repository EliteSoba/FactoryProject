Team 2
======
 #Hashtags
=======
Factory Project

Members:<p>
  CS201:<p>
    David Bay, <-- Manager
    Ryan Cleary,
    Alfonso Garza,
    Austin Puri<p>
  CS200:<p>
    Joey Huang,
    Minh La,
    Tobias Lee,
    George Li,
    Benjamin Mayeux,
    Marc Mendiola,
    Devon Meyer,
    Mher Poghosyan,
<<<<<<< HEAD
    Stephanie Reagle

checkout v.1 branch
=======
    Stephanie Reagle <-- Manager

Start Instructions<p>
Compilation:<br>
WHEN IN src/:<br>
javac -sourcepath . factory/masterControl/*.java<br>
javac -sourcepath . factory/managers/*.java<br>
Running:<p>
   Run SaveTest.java to update the initial data<p>
   To begin the Factory there are a few procedural steps which must be taken.
   First, the user must start the MasterControl server, the agents will start within the MasterControl. 
   There is no need to start the agents seperatley.<p>
<p>
   Once MasterControl has started the user is required to specify the number of clients which will connect
   to the MasterControl.<p>
   *If the user wants to start all the clients they need to input 0 into the command line.<p>
   *The user must then start the number of clients they have specified.<p>
   <p>
   *The clients wont display until all of them have been started by the user.<p>
   *Once the MasterControl has the correct number of clients connected it will send the "GO" command
    and the Factory simulation will begin.<p>

   Client Controls and Restrictions<p>
   *KitManager<p> 
     if the user deletes a part then all the KitConfigurations containing that part will be
     deleted. We decided this was the best course of action because the KitConfigurations which had the part
     previously will now be potentially unwanted Configurations.<p>
   *PartsManager<p>
     If the user wishes to look at the properties of the part and edit the part. They also get access to 
     modify and delete the part.<p>
   *The other Managers are pretty self-explanatory.<p>
   
 
   
   
   
   
   
>>>>>>> v.2
