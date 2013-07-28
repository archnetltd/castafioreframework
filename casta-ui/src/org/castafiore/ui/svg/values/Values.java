package org.castafiore.ui.svg.values;

public interface Values {
	
	public enum AnimationAdditionValues{replace,sum, none};
	
	public enum AnimationFillValues{remove,freeze};
	
	public enum AnimationRestartValues {always , whenNotActive , never};
	
	public enum AttributeTypesValues {CSS,XML,auto};
	
	public enum CalcModeValues {discrete , linear , paced , spline};
	
	public enum ColorInterpolationValues {auto , sRGB , linearRGB , inherit};
	//
	public enum ColorRenderingValues {auto , optimizeSpeed , optimizeQuality , inherit};
	
	public enum ImageRenderingValues{auto , inherit , optimizeSpeed , optimizeQuality , _moz_crisp_edges , _o_crisp_edges };
	
	public enum ShapeRenderingValue{auto , optimizeSpeed , crispEdges , geometricPrecision , inherit};
	
	public enum TextRenderingValues{auto , optimizeSpeed , optimizeLegibility , geometricPrecision , inherit};
	
	public enum DisplayValues {inline , block , list_item , run_in , compact , marker , table , inline_table , table_row_group , table_header_group , table_footer_group , table_row , table_column_group , table_column , table_cell , table_caption , none , inherit};
	
	public enum RuleValues {nonezero , evenodd , inherit};
	
	public enum OverflowValues{visible,hidden,auto,scroll};
	
	
	public enum PointerEventsValues{visiblePainted , visibleFill , visibleStroke , visible , painted , fill , stroke , all , none , inherit};
	
	public enum StrokeLineCapValues{butt , round , square , inherit};
	
	public enum StrokeLineJoinValues{miter , round , bevel , inherit};
	
	public enum TextAnchorValues{start , middle , end , inherit};
	
	public enum TextDecorationValues{none , underline , overline , line_through , blink , inherit};

	public enum VisibilityAttributes{visible , hidden , collapse , inherit};
	
	public enum FontStretchValues{wider,narrower,ultra_condensed,extra_condensed,condensed,semi_condensed,normal,semi_expanded,expanded,extra_expanded,ultra_expanded,inherit};
	
	public enum FontStyleValues{normal,italic,oblique,inherit};
	
	public enum FontVariantValues{normal,small_caps, inherit};
	
	public enum FontWeightValues{normal,bold,bolder,lighter,_100,_200,_300,_400,_500, _600,_700,_800, _900, inherit};
	
	public enum MarkerUnitValues{userSpaceOnUse , strokeWidth};
	
	public enum UnitValues{userSpaceOnUse , objectBoundingBox};
}
