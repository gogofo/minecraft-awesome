package gogofo.minecraft.awesome.inventory;

public class SlotCategoryIdToColor {
	public static int convert(int categoryId) {
    	switch (categoryId) {
    	case 0:
    		return 0x4F00FF00;
    	case 1:
    		return 0x4FFF0000;
    	case 2:
    		return 0x4F0000FF;
    	case 3:
    		return 0x4FFFFF00;
    	case 4:
    		return 0x4F00FFFF;
    	case 5:
    		return 0x4FFF00FF;
    	}
    	
    	return 0;
    }
}
