package org.castafiore.facebook.ui;

import org.castafiore.facebook.FacebookGraphAPIClient;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.EXGrid;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ResourceUtil;

public class EXInviteFriends extends EXContainer{

	public EXInviteFriends(String name) {
		super(name, "div");
		setAttribute("style", "margin: auto; width: 600px; padding: 12px; border-radius: 5px 5px 5px 5px; background: -moz-linear-gradient(center top, rgb(36, 36, 39) 0%, rgb(21, 21, 22) 100% ) repeat scroll 0px 0px transparent; box-shadow: 5px 5px 5px black;");
		addChild(new EXXHTMLFragment("sd", ResourceUtil.getDownloadURL("classpath", "org/castafiore/facebook/ui/InviteInfo.xhtml")));
		
		
		EXGrid grid = new EXGrid("", 4, 1);
		String facebooklogin = SpringUtil.getBeanOfType(FacebookGraphAPIClient.class).getAuthorizationUrl("180567415418654", "ef8be0651a0d0aa94a51ec454b9a9165", "http://www.emallofmauritius.com/templates.html?facebook=true");
		Container facebook = new EXContainer("", "a").setAttribute("href", facebooklogin).setText("<img src=http://icons.iconarchive.com/icons/yootheme/social-bookmark/72/social-facebook-box-blue-icon.png></img>");
		
		grid.getCell(0	, 0).addChild(facebook);
		grid.getCell(1, 0).setText("<img src=http://icons.iconarchive.com/icons/yootheme/social-bookmark/72/social-google-box-blue-icon.png></img>");
		grid.getCell(2, 0).setText("<img src=http://icons.iconarchive.com/icons/yootheme/social-bookmark/72/social-linkedin-box-blue-icon.png></img>");
		grid.getCell(3, 0).setText("<img src=http://icons.iconarchive.com/icons/yootheme/social-bookmark/72/social-twitter-box-blue-icon.png></img>");
		//addChild(grid);
		
		addChild(new EXContainer("div", "div").setAttribute("style", "padding: 8px; background: none repeat scroll 0px 0px white; margin-top: 12px;").addChild(grid));
		
		//facebook
		//linkedin
		//google+
		//twitter
		//
	}

}
