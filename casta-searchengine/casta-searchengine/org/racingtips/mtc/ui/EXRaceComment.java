package org.racingtips.mtc.ui;

import java.text.SimpleDateFormat;

import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.StringUtil;
import org.racingtips.mtc.Race;

public class EXRaceComment extends EXXHTMLFragment{

	public EXRaceComment(String name, Race race) {
		super(name, "templates/racingtips/EXComments.xhtml");
		addChild(new EXContainer("title", "span").setText("Prediction"));
		String submitted = "Submitted by " + "Malleck Beharry" + " on " + new SimpleDateFormat("EEEE dd/MM/yyyy - mm:hh").format(race.getDateCreated().getTime());
		addChild(new EXContainer("submittedBy", "div").addClass("submitted").setText(submitted));
		String summary = "Race prediction will be available on Friday 4PM. Stay tuned.";
		if(StringUtil.isNotEmpty(race.getSummary())){
			summary = race.getSummary();
		}
		addChild(new EXContainer("summary", "p").setText(summary));
		
	}

}
