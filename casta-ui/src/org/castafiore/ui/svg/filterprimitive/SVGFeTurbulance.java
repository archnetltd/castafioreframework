package org.castafiore.ui.svg.filterprimitive;


public class SVGFeTurbulance extends AbstractFe {
	

	public SVGFeTurbulance(String name) {
		super(name, "feTurbulance");
	}
	public String getBaseFrequency() {
		return getAttribute("baseFrequency");
	}
	public void setBaseFrequency(String baseFrequency) {
		setAttribute("baseFrequency", baseFrequency);
	}
	public String getNumOctaves() {
		return getAttribute("numOctaves");
	}
	public void setNumOctaves(String numOctaves) {
		setAttribute("numOctaves", numOctaves);
	}
	public String getSeed() {
		return getAttribute("seed");
	}
	public void setSeed(String seed) {
		setAttribute("seed", seed);
	}
	public String getStitchTiles() {
		return getAttribute("stitchTiles");
	}
	public void setStitchTiles(String stitchTiles) {
		setAttribute("stitchTiles", stitchTiles);
	}

	
	
}
