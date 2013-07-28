package org.castafiore.ui.svg.container;

import java.awt.Color;

import org.castafiore.ui.Dimension;
import org.castafiore.ui.svg.SVGContainer;
import org.castafiore.ui.svg.ables.ConditionalProcessingAttributeAble;
import org.castafiore.ui.svg.ables.PresentationAttributeAble;
import org.castafiore.ui.svg.values.OpacityValues;
import org.castafiore.ui.svg.values.Values.ColorInterpolationValues;
import org.castafiore.ui.svg.values.Values.ColorRenderingValues;
import org.castafiore.ui.svg.values.Values.DisplayValues;
import org.castafiore.ui.svg.values.Values.FontStretchValues;
import org.castafiore.ui.svg.values.Values.FontStyleValues;
import org.castafiore.ui.svg.values.Values.FontVariantValues;
import org.castafiore.ui.svg.values.Values.FontWeightValues;
import org.castafiore.ui.svg.values.Values.ImageRenderingValues;
import org.castafiore.ui.svg.values.Values.OverflowValues;
import org.castafiore.ui.svg.values.Values.PointerEventsValues;
import org.castafiore.ui.svg.values.Values.RuleValues;
import org.castafiore.ui.svg.values.Values.ShapeRenderingValue;
import org.castafiore.ui.svg.values.Values.StrokeLineCapValues;
import org.castafiore.ui.svg.values.Values.StrokeLineJoinValues;
import org.castafiore.ui.svg.values.Values.TextAnchorValues;
import org.castafiore.ui.svg.values.Values.TextDecorationValues;
import org.castafiore.ui.svg.values.Values.TextRenderingValues;
import org.castafiore.ui.svg.values.Values.UnitValues;
import org.castafiore.ui.svg.values.Values.VisibilityAttributes;

public class SVGMask extends SVGContainer implements ConditionalProcessingAttributeAble,PresentationAttributeAble{

	public SVGMask(String name) {
		super(name, "mask");
		
	}
	
	public UnitValues getMaskUnits(){
		return UnitValues.valueOf(getAttribute("maskUnits"));
	}
	
	public void setMaskUnits(UnitValues val){
		setAttribute("maskUnits", val.toString());
	}
	
	public UnitValues getMaskContentUnits(){
		return UnitValues.valueOf(getAttribute("maskContentUnits"));
	}
	
	public void setMaskContentUnits(UnitValues val){
		setAttribute("maskContentUnits", val.toString());
	}
	
	public Dimension getX(){
		return Dimension.parse(getAttribute("x"));
	}
	
	public Dimension getY(){
		return Dimension.parse(getAttribute("y"));
	}
	
	
	public void setX(Dimension x){
		setAttribute("x", x.toString());
	}
	
	
	public void setY(Dimension y){
		setAttribute("y", y.toString());
	}

	@Override
	public String getRequiredExtensions() {
		return getAttribute("requiredExtensions");
	}

	@Override
	public String getRequiredFeatures() {
		return getAttribute("requiredFeatures");
	}

	@Override
	public String getSystemLanguage() {
		return getAttribute("systemLanguage");
	}

	@Override
	public void setRequiredExtensions(String requiredExtensions) {
		setAttribute("requiredExtensions", requiredExtensions);
		
	}

	@Override
	public void setRequiredFeatures(String requiredFeatures) {
		setAttribute("requiredFeatures", requiredFeatures);
		
	}

	@Override
	public void setSystemLanguage(String systemLanguage) {
		setAttribute("systemLanguage", systemLanguage);
		
	}
	
	@Override
	public String getAlignmentBaseline() {
		return getAttribute("alignment-baseline");
	}

	@Override
	public String getBaselineShift() {
		return getAttribute("baseline-shift");
	}

	@Override
	public String getClip() {
		return getAttribute("clip");
	}

	@Override
	public String getClipPath() {
		return getAttribute("clip-path");
	}

	@Override
	public RuleValues getClipRule() {
		return RuleValues.valueOf(getAttribute("clip-rule")) ;
	}

	@Override
	public Color getColor() {
		return Color.getColor(getAttribute("color"));
	}

	@Override
	public ColorInterpolationValues getColorInterpolation() {
		return ColorInterpolationValues.valueOf(getAttribute("color-interpolation"));
	}

	@Override
	public ColorInterpolationValues getColorInterpolationFilters() {
		return ColorInterpolationValues.valueOf(getAttribute("color-interpolation-filters"));
	}

	@Override
	public String getColorProfile() {
		return getAttribute("color-profile");
	}

	@Override
	public ColorRenderingValues getColorRendering() {
		return ColorRenderingValues.valueOf(getAttribute("color-rendering"));

	}

	@Override
	public String getCursor() {
		return getAttribute("cursor");
	}

	@Override
	public String getDirection() {
		return getAttribute("direction");
	}

	@Override
	public DisplayValues getDisplay() {
		return DisplayValues.valueOf(getAttribute("display"));
	}

	@Override
	public String getDominantBaseline() {
		return getAttribute("dominant-baseline");
	}

	@Override
	public boolean getEnableBackground() {
		return Boolean.parseBoolean(getAttribute("enable-background"));
	}

	@Override
	public String getFill() {
		return getAttribute("fill");
	}

	@Override
	public OpacityValues getFillOpacity() {
		return OpacityValues.valueOf(getAttribute("fill-opacity"));
	}

	@Override
	public RuleValues getFillRule() {
		return RuleValues.valueOf(getAttribute("fill-rule"));
	}

	@Override
	public String getFilter() {
		return getAttribute("filter");
	}

	@Override
	public Color getFloodColor() {
		return Color.getColor(getAttribute("flood-color"));
	}

	@Override
	public OpacityValues getFloodOpacity() {
		return OpacityValues.valueOf(getAttribute("flood-opacity"));
	}

	@Override
	public String getFont() {
		return getAttribute("font");
	}

	@Override
	public String getFontFamily() {
		return getAttribute("font-family");
	}

	@Override
	public Dimension getFontSize() {
		return Dimension.parse(getAttribute("font-size"));
	}

	@Override
	public double getFontSizeAdjust() {
		return Double.parseDouble(getAttribute("font-size-adjust"));
	}

	@Override
	public FontStretchValues getFontStretch() {
		return FontStretchValues.valueOf(getAttribute("font-stretch"));
	}

	@Override
	public FontStyleValues getFontStyle() {
		return FontStyleValues.valueOf("font-style");
	}

	@Override
	public FontVariantValues getFontVariant() {
		return FontVariantValues.valueOf(getAttribute("font-variant"));
	}

	@Override
	public FontWeightValues getFontWeight() {
		return FontWeightValues.valueOf(getAttribute("font-weight"));
	}

	@Override
	public String getGlyphOrientationHorizontal() {
		return getAttribute("glyph-orientation-horizontal");
	}

	@Override
	public String getGlyphOrientationVertical() {
		return getAttribute("glyph-orientation-vertical");
	}

	@Override
	public ImageRenderingValues getImageRendering() {
		return ImageRenderingValues.valueOf(getAttribute("image-rendering"));
	}

	@Override
	public Dimension getKerning() {
		return Dimension.parse(getAttribute("kerning"));
	}

	@Override
	public Dimension getLetterSpacing() {
		return Dimension.parse(getAttribute("letter-spacing"));
	}

	@Override
	public Color getLightingColor() {
		return Color.getColor(getAttribute("lighting-color"));
	}

	@Override
	public String getMarker() {
		return getAttribute("marker");
	}

	@Override
	public String getMarkerEnd() {
		return getAttribute("marker-end");
	}

	@Override
	public String getMarkerMid() {
		return getAttribute("marker-mid");
	}

	@Override
	public String getMarkerStart() {
		return getAttribute("marker-start");
	}

	@Override
	public String getMask() {
		return getAttribute("mask");
	}

	@Override
	public OpacityValues getOpacity() {
		return OpacityValues.valueOf(getAttribute("opacity"));
	}

	@Override
	public OverflowValues getOverflow() {
		return OverflowValues.valueOf(getAttribute("overflow"));
	}

	@Override
	public PointerEventsValues getPointerEvents() {
		return PointerEventsValues.valueOf(getAttribute("pointer-events"));
	}

	@Override
	public ShapeRenderingValue getShapeRendering() {
		return ShapeRenderingValue.valueOf(getAttribute("shape-rendering"));
	}

	@Override
	public Color getStopColor() {
		return Color.getColor(getAttribute("stop-color"));
	}

	@Override
	public OpacityValues getStopOpacity() {
		return OpacityValues.valueOf(getAttribute("stop-opacity"));
	}

	@Override
	public String getStroke() {
		return getAttribute("stroke");
	}

	@Override
	public String getStrokeDasharray() {
		return getAttribute("stroke-dasharray");
	}

	@Override
	public Dimension getStrokeDashoffset() {
		return Dimension.parse(getAttribute("stroke-dashoffset"));
	}

	@Override
	public StrokeLineCapValues getStrokeLinecap() {
		return StrokeLineCapValues.valueOf(getAttribute("stroke-lincap"));
	}

	@Override
	public StrokeLineJoinValues getStrokeLinejoin() {
		return StrokeLineJoinValues.valueOf(getAttribute("stroke-linejoin"));
	}

	@Override
	public String getStrokeMiterlimit() {
		return  getAttribute("stroke-miterlimit");
	}

	@Override
	public OpacityValues getStrokeOpacity() {
		return OpacityValues.valueOf(getAttribute("stroke-opacity"));
	}

	@Override
	public Dimension getStrokeWidth() {
		return Dimension.parse(getAttribute("stroke-width"));
	}

	@Override
	public TextAnchorValues getTextAnchor() {
		return TextAnchorValues.valueOf(getAttribute("text-anchor"));
	}

	@Override
	public TextDecorationValues getTextDecoration() {
		return TextDecorationValues.valueOf(getAttribute("text-decoration"));
	}

	@Override
	public TextRenderingValues getTextRendering() {
		return TextRenderingValues.valueOf(getAttribute("text-rendering"));
	}

	@Override
	public String getUnicodeBidi() {
		return getAttribute("unicode-bidi");
	}

	@Override
	public VisibilityAttributes getVisibility() {
		return VisibilityAttributes.valueOf(getAttribute("visibility"));
	}

	@Override
	public Dimension getWordSpacing() {
		return Dimension.parse(getAttribute("word-spacing"));
	}

	@Override
	public String getWritingMode() {
		return getAttribute("writing-mode");
	}

	@Override
	public void setAlignmentBaseline(String alignmentBaseline) {
		setAttribute("alignment-baseline", alignmentBaseline);
		
	}

	@Override
	public void setBaselineShift(String baselineShift) {
		setAttribute("baseline-shift", baselineShift);
		
	}

	@Override
	public void setClip(String clip) {
		setAttribute("clip", clip);
		
	}

	@Override
	public void setClipPath(String clipPath) {
		setAttribute("clip-path", clipPath);
		
	}

	@Override
	public void setClipRule(RuleValues clipRule) {
		setAttribute("clip-rule", clipRule.toString());
		
	}

	@Override
	public void setColor(Color color) {
		setAttribute("color", "rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")");
		
	}

	@Override
	public void setColorInterpolation(
			ColorInterpolationValues colorInterpolation) {
		setAttribute("color-interpolation", colorInterpolation.toString());
		
	}

	@Override
	public void setColorInterpolationFilters(
			ColorInterpolationValues colorInterpolationFilters) {
		setAttribute("color-interpolation-filters", colorInterpolationFilters.toString());
		
	}

	@Override
	public void setColorProfile(String colorProfile) {
		setAttribute("color-profile", colorProfile);
		
	}

	@Override
	public void setColorRendering(ColorRenderingValues colorRendering) {
		setAttribute("color-rendering", colorRendering.toString());
		
	}

	@Override
	public void setCursor(String cursor) {
		setAttribute("cursor", cursor);
		
	}

	@Override
	public void setDirection(String direction) {
		setAttribute("direction", direction);
		
	}

	@Override
	public void setDisplay(DisplayValues display) {
		setAttribute("display", display.toString());
		
	}

	@Override
	public void setDominantBaseline(String dominantBaseline) {
		setAttribute("dominant-baseline", dominantBaseline);
		
	}

	@Override
	public void setEnableBackground(boolean enableBackground) {
		setAttribute("enable-background", enableBackground + "");
		
	}

	@Override
	public void setFill(String fill) {
		setAttribute("fill", fill);
		
	}

	@Override
	public void setFillOpacity(OpacityValues fillOpacity) {
		setAttribute("fill-opacity", fillOpacity.toString());
		
	}

	@Override
	public void setFillRule(RuleValues fillRule) {
		setAttribute("fill-rule", fillRule.toString());
		
	}

	@Override
	public void setFilter(String filter) {
		setAttribute("filter", filter);
		
	}

	@Override
	public void setFloodColor(Color color) {
		setAttribute("flood-color", "rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")");
		
	}

	@Override
	public void setFloodOpacity(OpacityValues floodOpacity) {
		setAttribute("flood-opacity", floodOpacity.toString());
		
	}

	@Override
	public void setFont(String font) {
		setAttribute("font", font);
		
	}

	@Override
	public void setFontFamily(String fontFamily) {
		setAttribute("font-family", fontFamily);
		
	}

	@Override
	public void setFontSize(Dimension fontSize) {
		setAttribute("font-size", fontSize.toString());
		
	}

	@Override
	public void setFontSizeAdjust(double fontSizeAdjust) {
		setAttribute("font-size-adjust", fontSizeAdjust + "");
		
	}

	@Override
	public void setFontStretch(FontStretchValues fontStretch) {
		setAttribute("font-stretch", fontStretch.toString());
		
	}

	@Override
	public void setFontStyle(FontStyleValues fontStyle) {
		setAttribute("font-style", fontStyle.toString());
		
	}

	@Override
	public void setFontVariant(FontVariantValues fontVariant) {
		setAttribute("font-variant", fontVariant.toString());
		
	}

	@Override
	public void setFontWeight(FontWeightValues fontWeight) {
		setAttribute("font-weight", fontWeight.toString());
		
	}

	@Override
	public void setGlyphOrientationHorizontal(String glyphOrientationHorizontal) {
		setAttribute("glyph-orientation-horizontal", glyphOrientationHorizontal);
		
	}

	@Override
	public void setGlyphOrientationVertical(String glyphOrientationVertical) {
		setAttribute("glyph-orientation-vertical", glyphOrientationVertical);
		
	}

	@Override
	public void setImageRendering(ImageRenderingValues imageRendering) {
		setAttribute("image-rendering", imageRendering.toString());
		
	}

	@Override
	public void setKerning(Dimension kerning) {
		setAttribute("kerning", kerning.toString());
		
	}

	@Override
	public void setLetterSpacing(Dimension letterSpacing) {
		setAttribute("letter-spacing", letterSpacing.toString());
		
	}

	@Override
	public void setLightingColor(Color color) {
		setAttribute("lighting-color", "rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")");
		
	}

	@Override
	public void setMarker(String marker) {
		setAttribute("marker", marker.toString());
		
	}

	@Override
	public void setMarkerEnd(String markerEnd) {
		setAttribute("marker-end", markerEnd);
		
	}

	@Override
	public void setMarkerMid(String markerMid) {
		setAttribute("marker-mid", markerMid);
		
	}

	@Override
	public void setMarkerStart(String markerStart) {
		setAttribute("marker-start", markerStart);
		
	}

	@Override
	public void setMask(String mask) {
		setAttribute("mask", mask);
		
	}

	@Override
	public void setOpacity(OpacityValues opacity) {
		setAttribute("opacity", opacity.toString());
		
	}

	@Override
	public void setOverflow(OverflowValues overflow) {
		setAttribute("overflow", overflow.toString());
		
	}

	@Override
	public void setPointerEvents(PointerEventsValues pointerEvents) {
		setAttribute("pointer-events", pointerEvents.toString());
		
	}

	@Override
	public void setShapeRendering(ShapeRenderingValue shapeRendering) {
		setAttribute("shape-rendering", shapeRendering.toString());
		
	}

	@Override
	public void setStopColor(Color color) {
		setAttribute("stop-color", "rgb("+color.getRed()+","+color.getGreen()+","+color.getBlue()+")");
		
	}

	@Override
	public void setStopOpacity(OpacityValues stopOpacity) {
		setAttribute("stop-opacity", stopOpacity.toString());
		
	}

	@Override
	public void setStroke(String stroke) {
		setAttribute("stroke", stroke);
		
	}

	@Override
	public void setStrokeDasharray(String strokeDasharray) {
		setAttribute("stroke-dasharray", strokeDasharray);
		
	}

	@Override
	public void setStrokeDashoffset(Dimension strokeDashoffset) {
		setAttribute("stroke-dashoffset", strokeDashoffset.toString());
		
	}

	@Override
	public void setStrokeLinecap(StrokeLineCapValues strokeLinecap) {
		setAttribute("stroke-linecap", strokeLinecap.toString());
		
	}

	@Override
	public void setStrokeLinejoin(StrokeLineJoinValues strokeLinejoin) {
		setAttribute("stroke-linejoin", strokeLinejoin.toString());
		
	}

	@Override
	public void setStrokeMiterlimit(String strokeMiterlimit) {
		setAttribute("stroke-miterlimit", strokeMiterlimit);
		
	}

	@Override
	public void setStrokeOpacity(OpacityValues strokeOpacity) {
		setAttribute("stroke-opacity", strokeOpacity.toString());
		
	}

	@Override
	public void setStrokeWidth(Dimension strokeWidth) {
		setAttribute("stroke-width", strokeWidth.toString());
		
	}

	@Override
	public void setTextAnchor(TextAnchorValues textAnchor) {
		setAttribute("text-anchor", textAnchor.toString());
		
	}

	@Override
	public void setTextDecoration(TextDecorationValues textDecoration) {
		setAttribute("text-decoration", textDecoration.toString());
		
	}

	@Override
	public void setTextRendering(TextRenderingValues textRendering) {
		setAttribute("text-rendering", textRendering.toString());
		
	}

	@Override
	public void setUnicodeBidi(String unicodeBidi) {
		setAttribute("unicode-bidi", unicodeBidi);
		
	}

	@Override
	public void setVisibility(VisibilityAttributes visibility) {
		setAttribute("visibility", visibility.toString());
		
	}

	@Override
	public void setWordSpacing(Dimension wordSpacing) {
		setAttribute("word-spacing", wordSpacing.toString());
		
	}

	@Override
	public void setWritingMode(String writingMode) {
		setAttribute("writing-mode", writingMode);
		
	}
}
