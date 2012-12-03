package factory.graphics;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

// "Static" class for loading list of images from a specified folder 
public enum GraphicAnimation
{;
	public static ArrayList<Image> loadAnimationFromFolder(String folderPath, int startingIndex, String fileExtension)
	{
		ArrayList<Image> imageList = new ArrayList<Image>();
		int i = startingIndex;
		while(true)
		{
			String filePath = folderPath + i + fileExtension;
			File f = new File(filePath);
			//System.out.println(filePath);
			if(f.exists())
				imageList.add(new ImageIcon(filePath).getImage());
			else
				break;
			i++;
		}
		return imageList;
	}
}
