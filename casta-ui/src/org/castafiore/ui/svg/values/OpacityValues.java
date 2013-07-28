package org.castafiore.ui.svg.values;

public enum OpacityValues {
    ZERO (0),
    ZERO_POINT_1   (0.1),
   
    ZERO_POINT_2   (0.2),
    ZERO_POINT_3   (0.3),
    ZERO_POINT_4   (0.4),
    ZERO_POINT_5   (0.5),
    ZERO_POINT_6   (0.6),
    ZERO_POINT_7   (0.7),
    ZERO_POINT_8   (0.8),
    ZERO_POINT_9   (0.9);
   
    private final double val; 
    OpacityValues(double val) {
        this.val =val;
       
    }
	@Override
	public String toString() {
		return val + "";
	}
    
}
