package org.racingtips.ui.tips;

import org.castafiore.ui.ex.EXContainer;
import org.racingtips.mtc.ui.EXRaceBody;
import org.racingtips.tips.AbstractTips;
import org.racingtips.tips.Banker;

public class EXBanker extends EXTipsCard{

	
	public EXBanker() throws Exception {
		super();
		addClass("EXBanker");
		if(getDescendentOfType(EXRaceBody.class).getChildren().size() == 0){
			getDescendentOfType(EXRaceBody.class).addChild(new EXContainer("tr", "tr").setText("<td text-align='center' colspan='7' class='info-message'>Please note that Tips will be published by Friday 4PM. So stay tuned</td>"));
		}
	}

	@Override
	public Class<? extends AbstractTips> getClazz() {
		return Banker.class;
	}

	@Override
	public String getTitle() {
		return "Banker";
	}
}
