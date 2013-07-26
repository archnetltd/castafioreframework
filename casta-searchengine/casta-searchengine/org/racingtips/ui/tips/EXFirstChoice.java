package org.racingtips.ui.tips;

import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.ui.EXRaceBody;
import org.racingtips.tips.FirstChoice;
import org.racingtips.tips.Tips;

public class EXFirstChoice extends EXTipsCard{

	public EXFirstChoice() throws Exception {
		super();
		addClass("EXFirstChoice");
		if(getDescendentOfType(EXRaceBody.class).getChildren().size() == 0){
			getDescendentOfType(EXRaceBody.class).addChild(new EXContainer("tr", "tr").setText("<td text-align='center' colspan='7' class='info-message'>Please note that Tips will be published by Friday 4PM. So stay tuned</td>"));
		}
		
	}

	@Override
	public Class<? extends Tips> getClazz() {
		return FirstChoice.class;
	}

	@Override
	public String getTitle() {
		return "1st Choice";
	}

}
