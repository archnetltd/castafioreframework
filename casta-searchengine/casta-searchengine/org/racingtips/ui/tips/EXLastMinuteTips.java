package org.racingtips.ui.tips;

import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.ui.EXRaceBody;
import org.racingtips.tips.LastMinuteTips;
import org.racingtips.tips.Tips;

public class EXLastMinuteTips extends EXTipsCard{

	public EXLastMinuteTips()throws Exception {
		
		super();
		addClass("EXLastMinuteTips");
		if(getDescendentOfType(EXRaceBody.class).getChildren().size() == 0){
			getDescendentOfType(EXRaceBody.class).addChild(new EXContainer("tr", "tr").setText("<td text-align='center' colspan='7' class='info-message'>Please note that Last minute tips will be published by Saturday 10 AM. So stay tuned</td>"));
		}
	}
	
	
	@Override
	public Class<? extends Tips> getClazz() {
		return LastMinuteTips.class;
	}

	@Override
	public String getTitle() {
		return "Last Minute Tips";
	}

}
