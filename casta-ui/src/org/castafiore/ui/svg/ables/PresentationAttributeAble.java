package org.castafiore.ui.svg.ables;

import java.awt.Color;

import org.castafiore.ui.Dimension;
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
import org.castafiore.ui.svg.values.Values.VisibilityAttributes;

public interface PresentationAttributeAble extends CoreAttributeAble {
	
	public String getAlignmentBaseline();

	public void setAlignmentBaseline(String alignmentBaseline);

	public String getBaselineShift() ;

	public void setBaselineShift(String baselineShift) ;

	public String getClip();

	public void setClip(String clip) ;

	public String getClipPath() ;

	public void setClipPath(String clipPath);

	public RuleValues getClipRule() ;

	public void setClipRule(RuleValues clipRule);

	public Color getColor() ;

	public void setColor(Color color) ;

	public ColorInterpolationValues getColorInterpolation();

	public void setColorInterpolation(ColorInterpolationValues colorInterpolation) ;

	public ColorInterpolationValues getColorInterpolationFilters() ;

	public void setColorInterpolationFilters(ColorInterpolationValues colorInterpolationFilters);

	public String getColorProfile() ;

	public void setColorProfile(String colorProfile) ;

	public ColorRenderingValues getColorRendering() ;

	public void setColorRendering(ColorRenderingValues colorRendering);

	public String getCursor() ;

	public void setCursor(String cursor);

	public String getDirection() ;

	public void setDirection(String direction) ;

	public DisplayValues getDisplay();

	public void setDisplay(DisplayValues display);

	public String getDominantBaseline() ;

	public void setDominantBaseline(String dominantBaseline);

	public boolean getEnableBackground() ;

	public void setEnableBackground(boolean enableBackground) ;

	public String getFill();

	public void setFill(String fill) ;

	public OpacityValues getFillOpacity() ;

	public void setFillOpacity(OpacityValues fillOpacity) ;

	public RuleValues getFillRule() ;

	public void setFillRule(RuleValues fillRule);

	public String getFilter() ;

	public void setFilter(String filter) ;

	public Color getFloodColor() ;

	public void setFloodColor(Color floodColor) ;

	public OpacityValues getFloodOpacity() ;

	public void setFloodOpacity(OpacityValues floodOpacity) ;

	public String getFont() ;

	public void setFont(String font);

	public String getFontFamily() ;

	public void setFontFamily(String fontFamily) ;

	public Dimension getFontSize() ;

	public void setFontSize(Dimension fontSize) ;

	public double getFontSizeAdjust() ;

	public void setFontSizeAdjust(double fontSizeAdjust);

	public FontStretchValues getFontStretch() ;

	public void setFontStretch(FontStretchValues fontStretch) ;

	public FontStyleValues getFontStyle() ;

	public void setFontStyle(FontStyleValues fontStyle) ;

	public FontVariantValues getFontVariant() ;

	public void setFontVariant(FontVariantValues fontVariant);

	public FontWeightValues getFontWeight() ;

	public void setFontWeight(FontWeightValues fontWeight) ;

	public String getGlyphOrientationHorizontal() ;

	public void setGlyphOrientationHorizontal(String glyphOrientationHorizontal) ;

	public String getGlyphOrientationVertical() ;

	public void setGlyphOrientationVertical(String glyphOrientationVertical);

	public ImageRenderingValues getImageRendering() ;

	public void setImageRendering(ImageRenderingValues imageRendering);

	public Dimension getKerning();

	public void setKerning(Dimension kerning);

	public Dimension getLetterSpacing();

	public void setLetterSpacing(Dimension letterSpacing);

	public Color getLightingColor() ;

	public void setLightingColor(Color lightingColor);

	public String getMarker() ;

	public void setMarker(String marker);

	public String getMarkerEnd() ;

	public void setMarkerEnd(String markerEnd) ;

	public String getMarkerMid();

	public void setMarkerMid(String markerMid) ;

	public String getMarkerStart() ;

	public void setMarkerStart(String markerStart);

	public String getMask() ;

	public void setMask(String mask);

	public OpacityValues getOpacity() ;

	public void setOpacity(OpacityValues opacity);

	public OverflowValues getOverflow();

	public void setOverflow(OverflowValues overflow) ;

	public PointerEventsValues getPointerEvents() ;

	public void setPointerEvents(PointerEventsValues pointerEvents) ;

	public ShapeRenderingValue getShapeRendering() ;

	public void setShapeRendering(ShapeRenderingValue shapeRendering);

	public Color getStopColor() ;

	public void setStopColor(Color stopColor);

	public OpacityValues getStopOpacity() ;

	public void setStopOpacity(OpacityValues stopOpacity);

	public String getStroke();

	public void setStroke(String stroke) ;

	public String getStrokeDasharray();

	public void setStrokeDasharray(String strokeDasharray);

	public Dimension getStrokeDashoffset();

	public void setStrokeDashoffset(Dimension strokeDashoffset);

	public StrokeLineCapValues getStrokeLinecap() ;

	public void setStrokeLinecap(StrokeLineCapValues strokeLinecap) ;

	public StrokeLineJoinValues getStrokeLinejoin();

	public void setStrokeLinejoin(StrokeLineJoinValues strokeLinejoin);

	public String getStrokeMiterlimit() ;

	public void setStrokeMiterlimit(String strokeMiterlimit);

	public OpacityValues getStrokeOpacity();

	public void setStrokeOpacity(OpacityValues strokeOpacity) ;

	public Dimension getStrokeWidth() ;

	public void setStrokeWidth(Dimension strokeWidth) ;

	public TextAnchorValues getTextAnchor() ;

	public void setTextAnchor(TextAnchorValues textAnchor);

	public TextDecorationValues getTextDecoration() ;

	public void setTextDecoration(TextDecorationValues textDecoration) ;

	public TextRenderingValues getTextRendering() ;

	public void setTextRendering(TextRenderingValues textRendering) ;

	public String getUnicodeBidi();

	public void setUnicodeBidi(String unicodeBidi) ;

	public VisibilityAttributes getVisibility() ;

	public void setVisibility(VisibilityAttributes visibility) ;

	public Dimension getWordSpacing();

	public void setWordSpacing(Dimension wordSpacing);

	public String getWritingMode();

	public void setWritingMode(String writingMode) ;

}
