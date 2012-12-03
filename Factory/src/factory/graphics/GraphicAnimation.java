package factory.graphics;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;

// "Static" class for loading list of images from a specified folder 
public enum GraphicAnimation
{;
	/**
	 * Loads as many pictures as it can find in consecutive order from the specified folder.
	 * @param folderPath Directory in which the files are located. Example: "Images/"
	 * @param startingIndex File number to start incrementing from when loading images
	 * @param fileExtension Extension of the file, i.e. ".png"
	 * @return list of the images (ArrayList<Image>)
	 */
	public static ArrayList<Image> loadAnimationFromFolder(String folderPath, int startingIndex, String fileExtension)
	{
		ArrayList<Image> imageList = new ArrayList<Image>();
		int i = startingIndex;
		// Loop until no more files found.
		while(true)
		{
			String filePath = folderPath + i + fileExtension;
			File f = new File(filePath);
			//System.out.println(filePath);
			if(f.exists())		// image exists
				imageList.add(new ImageIcon(filePath).getImage());
			else				// image does not exist
				break;
			i++;
		}
		return imageList;
	}
}
