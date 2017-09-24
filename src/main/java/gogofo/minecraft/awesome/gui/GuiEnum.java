package gogofo.minecraft.awesome.gui;

public enum GuiEnum {
	GENERATOR("generator"),
	FUSER("fuser"),
	CHARGER("charger"),
	GRINDER("grinder"),
	ELECTRIC_FURNACE("electric_furnace"),
	TELEPORTER("teleporter"),
	SORTING_PIPE("sorting_pipe"),
	EXTRACTOR("extractor"),
	CONSTRUCTOR("constructor");

	private String guiName;
	
	private GuiEnum(String guiName) {
		this.guiName = String.format("awesome:%s", guiName);
	}
	
	public String guiName() {
		return guiName;
	}
}
