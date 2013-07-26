package org.racingtips.ui.cards;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.horse.Horse;
import org.racingtips.mtc.ui.EXForms;
import org.racingtips.mtc.ui.EXHorseConf;
import org.racingtips.mtc.ui.EXTrackWork;

public class EXHorseCard extends EXXHTMLFragment{

	public EXHorseCard(String horseId) {
		super("", "templates/racingtips/EXHorseCard.xhtml");
		
		Horse horse = SpringUtil.getBeanOfType(MTCDTO.class).getHorse(horseId);
		
		String age = horse.getAge();
		
		String gear = horse.getGear();
		
		String name = horse.getHorse();
		
		String weight = horse.getWeight();
		//Name: RICH STRIKE</br>Age: 4</br>Breeding: STRIKE SMARTLY (CAN) - CIRCLE OF GOLD</br>Trainer: M. Vincent Allet</br>Stable: V. Allet</br>
		
		String txt = "Name: "+name+"</br>Age: "+age+"</br>Breeding: "+horse.getPedigree()+"</br>Stable: "+horse.getStable()+"</br>";
		
		
		addChild(new EXContainer("txt", "th").setText(txt));
		EXForms forms = new EXForms("forms", horseId);
		forms.getChild("save").setDisplay(false);
		
		addChild(forms);
		EXTrackWork tr = new EXTrackWork("trackwork", horseId);
		tr.getChild("save").setDisplay(false);
		addChild(tr);
		
		if("3RacingtipsAdmin".equals(Util.getRemoteUser())){
			addChild(new EXHorseConf("conf", horse.getHorseId(), horse.getHorse()));
		}
		
		
	}

}
