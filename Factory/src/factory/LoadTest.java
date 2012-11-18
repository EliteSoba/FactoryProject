package factory;
import java.util.*;
import java.io.*;
import java.io.Serializable;

public class LoadTest {
	Map<String,Part> partsList;
	Map<String,KitConfig> kitConfigList;
	
	public LoadTest() {
		partsList = new HashMap<String,Part>();
		kitConfigList = new HashMap<String,KitConfig>();
	
	}

	public void loadData(){
		FileInputStream f;
		ObjectInputStream o;
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialParts.ser");
			o = new ObjectInputStream(f);
			partsList = (HashMap<String,Part>) o.readObject();
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
		}
		try{    // loads previously saved player data
			f = new FileInputStream("InitialData/initialKitConfigs.ser");
			o = new ObjectInputStream(f);
			kitConfigList = (HashMap<String,KitConfig>) o.readObject();
			System.out.println("Good");
			o.close();
		}catch(IOException e){
			e.printStackTrace();
		} catch(ClassNotFoundException c){
			c.printStackTrace();
		}
	}
	
	public void print() {
		System.out.println(partsList.get("Eye").name);
	}
	
	public static void main(String[] args) {
		LoadTest lt = new LoadTest();
		lt.loadData();
		lt.print();
		
		ArrayList<String> kits = lt.kitConfigsContainingPart("Eye");
		
		System.out.println(kits);
	}
	
	public ArrayList<String> kitConfigsContainingPart(String str) {
		KitConfig kitConfig = new KitConfig();
		String kitName = new String();
		ArrayList<String> affectedKits = new ArrayList<String>();

		
		Iterator itr = kitConfigList.entrySet().iterator();	
		while(itr.hasNext()) {					
			Map.Entry pairs = (Map.Entry)itr.next();	
			//String kitName = (String)pairs.getKey();
			kitConfig = (KitConfig)pairs.getValue();
			for (Part p:kitConfig.listOfParts) {
				if (p.name.equals(str)) {
					affectedKits.add((String)pairs.getKey());
					break;
				}
			}
		}
		return affectedKits;
	
	}

}
