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
		partsList.put("Body",new Part("Body",2,"This is used as the base.","Images/body.png",2));
		partsList.put("Hat",new Part("Hat",3,"This is used to cover the head.","Images/hat.png",2));
		partsList.put("Arm",new Part("Arm",4,"This is used to grab things.","Images/arm.png",2));
		partsList.put("Shoe",new Part("Shoe",5,"This is used to walk.","Images/shoe.png",2));
		partsList.put("Mouth",new Part("Mouth",6,"This is used to talk.","Images/mouth.png",1));
		partsList.put("Nose",new Part("Nose",7,"This is used to smell.","Images/nose.png",1));
		partsList.put("Moustache",new Part("Moustache",8,"This is used to look cool.","Images/moustache.png",1));
		partsList.put("Ear",new Part("Ear",9,"This is used to hear.","Images/ear.png",2));
		partsList.put("Cane",new Part("Cane",10,"A stylish cane.","Images/cane.png",2));
		partsList.put("Sword",new Part("Sword",11,"Vampiric Broadsword of Frost +10.","Images/sword.png",1));
		partsList.put("Tentacle",new Part("Tentacle",12,"A sticky appendage.","Images/tentacle.png",1));
		partsList.put("Wing",new Part("Wing",13,"A dragon's wing.","Images/wing.png",2));

		
			kitConfigList = new HashMap<String,KitConfig>();
			   
			KitConfig newKitConfig = new KitConfig("Mr.PotatoHead");
			newKitConfig.listOfParts.add(partsList.get("Eye"));
			newKitConfig.listOfParts.add(partsList.get("Body"));	
			newKitConfig.listOfParts.add(partsList.get("Hat"));
			newKitConfig.listOfParts.add(partsList.get("Arm"));
			newKitConfig.listOfParts.add(partsList.get("Shoe"));
			newKitConfig.listOfParts.add(partsList.get("Mouth"));
			newKitConfig.listOfParts.add(partsList.get("Nose"));
			newKitConfig.listOfParts.add(partsList.get("Eye"));
			kitConfigList.put(newKitConfig.kitName,newKitConfig);

			newKitConfig = new KitConfig("Kit2");
			newKitConfig.listOfParts.add(partsList.get("Body"));	
			newKitConfig.listOfParts.add(partsList.get("Mouth"));
			newKitConfig.listOfParts.add(partsList.get("Eye"));
			newKitConfig.listOfParts.add(partsList.get("Arm"));
			newKitConfig.listOfParts.add(partsList.get("Arm"));
			kitConfigList.put(newKitConfig.kitName,newKitConfig);

			newKitConfig = new KitConfig("Cthulhu");
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));	
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Tentacle"));
			newKitConfig.listOfParts.add(partsList.get("Sword"));
			kitConfigList.put(newKitConfig.kitName,newKitConfig);


			newKitConfig = new KitConfig("PotatoDragon");
			newKitConfig.listOfParts.add(partsList.get("Body"));	
			newKitConfig.listOfParts.add(partsList.get("Wing"));
			newKitConfig.listOfParts.add(partsList.get("Arm"));
			newKitConfig.listOfParts.add(partsList.get("Eye"));
			newKitConfig.listOfParts.add(partsList.get("Wing"));
			newKitConfig.listOfParts.add(partsList.get("Cane"));
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
