package factory;

import java.util.*;
import java.io.*;
import java.io.Serializable;

public class SaveTest {
	Map<String,Part> partsList;
	Map<String,KitConfig> kitConfigList;
	public SaveTest() {
		
		partsList = new HashMap<String,Part>();
		
		partsList.put("Eye",new Part("Eye",1,"This is used to see.","Images/eye.png",1));
		//partsList.put("Body",new Part("Body",2,"This is used as the base.","Images/body.png",2));
		partsList.put("Hat",new Part("Hat",2,"This is used to cover the head.","Images/hat.png",2));
		partsList.put("Arm",new Part("Arm",3,"This is used to grab things.","Images/arm.png",2));
		partsList.put("Shoe",new Part("Shoe",4,"This is used to walk.","Images/shoe.png",2));
		partsList.put("Mouth",new Part("Mouth",5,"This is used to talk.","Images/mouth.png",1));
		partsList.put("Nose",new Part("Nose",6,"This is used to smell.","Images/nose.png",1));
		partsList.put("Moustache",new Part("Moustache",7,"This is used to look cool.","Images/moustache.png",1));
		partsList.put("Ear",new Part("Ear",8,"This is used to hear.","Images/ear.png",2));
		partsList.put("Cane",new Part("Cane",9,"A stylish cane.","Images/cane.png",2));
		partsList.put("Sword",new Part("Sword",10,"Vampiric Broadsword of Frost +10.","Images/sword.png",1));
		partsList.put("Tentacle",new Part("Tentacle",11,"A sticky appendage.","Images/tentacle.png",1));
		partsList.put("Wing",new Part("Wing",12,"A dragon's wing.","Images/wing.png",2));
		partsList.put("Ben",new Part("Ben",13,"A Ben","Images/ben.png",2));
		partsList.put("Alfonso",new Part("Alfonso",14,"An Alfonso","Images/alfonso.png",2));
		partsList.put("Austin",new Part("Austin",15,"An Austin","Images/austin.png",2));
		partsList.put("David",new Part("David",16,"A David","Images/david.png",2));
		partsList.put("Devon",new Part("Devon",17,"A Devon","Images/devon.png",2));
		partsList.put("George",new Part("George",18,"A George","Images/george.png",2));
		partsList.put("Devon",new Part("Devon",19,"A Devon","Images/devon.png",2));
		partsList.put("Joey",new Part("Joey",20,"A Joey","Images/joey.png",2));
		partsList.put("Mher",new Part("Mher",21,"A Mher","Images/mher.png",2));
		partsList.put("Minh",new Part("Minh",22,"A Minh","Images/minh.png",2));
		partsList.put("Ryan",new Part("Ryan",23,"A Ryan","Images/ryan.png",2));
		partsList.put("Stephanie",new Part("Stephanie",24,"A Stephanie","Images/stephanie.png",2));
		partsList.put("Marc",new Part("Marc",25,"A Marc","Images/marc.png",2));

		
			kitConfigList = new HashMap<String,KitConfig>();
			   
			KitConfig newKitConfig = new KitConfig("CS 200");
			newKitConfig.listOfParts.add(partsList.get("Stephanie"));
			newKitConfig.listOfParts.add(partsList.get("Mher"));	
			newKitConfig.listOfParts.add(partsList.get("Minh"));
			newKitConfig.listOfParts.add(partsList.get("George"));
			newKitConfig.listOfParts.add(partsList.get("Devon"));
			newKitConfig.listOfParts.add(partsList.get("Joey"));
			newKitConfig.listOfParts.add(partsList.get("Ben"));
			newKitConfig.listOfParts.add(partsList.get("Marc"));
			kitConfigList.put(newKitConfig.kitName,newKitConfig);


			newKitConfig = new KitConfig("CS 201");
			newKitConfig.listOfParts.add(partsList.get("David"));	
			newKitConfig.listOfParts.add(partsList.get("Alfonso"));
			newKitConfig.listOfParts.add(partsList.get("Ryan"));
			newKitConfig.listOfParts.add(partsList.get("Austin"));
			newKitConfig.listOfParts.add(partsList.get("Sword"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Wing"));
			newKitConfig.listOfParts.add(partsList.get("Shoe"));
			kitConfigList.put(newKitConfig.kitName,newKitConfig);
			
	}
	public void saveData() {
		FileOutputStream f;
		ObjectOutputStream o;
		try {    // uses output stream to serialize and save array of Players
			
			f = new FileOutputStream("InitialData/initialParts.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(partsList);
			o.close();
			System.out.println("It worked");
			
		} catch (FileNotFoundException ex) {
		//	o.close();
			ex.printStackTrace();
		} catch (IOException ex) {
		//	o.close();
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {    // uses output stream to serialize and save array of Players
			
			f = new FileOutputStream("InitialData/initialKitConfigs.ser");
			o = new ObjectOutputStream(f);
			o.writeObject(kitConfigList);
			o.close();
			System.out.println("It worked");
			
		} catch (FileNotFoundException ex) {
			//o.close();
			ex.printStackTrace();
		} catch (IOException ex) {
			//o.close();
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		SaveTest st = new SaveTest();
		st.saveData();
	}
	
	
}
