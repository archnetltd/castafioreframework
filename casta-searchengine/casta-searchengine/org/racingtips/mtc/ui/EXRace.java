package org.racingtips.mtc.ui;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.castafiore.shoppingmall.ui.MallLoginSensitive;
import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.ViewModel;
import org.castafiore.ui.ex.form.EXCheckBox;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.list.DefaultDataModel;
import org.castafiore.ui.ex.form.list.EXSelect;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.utils.ComponentUtil;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Comment;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Meeting;
import org.racingtips.mtc.Race;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.ui.LeftNavSensitive;
import org.racingtips.ui.cards.CardsMenuModel;

public class EXRace extends EXXHTMLFragment implements Event, MallLoginSensitive{

	
	private String meetingPath = null;
	
	private String currentRacePath = null;
	public EXRace()throws Exception {
		super("EXRace", "templates/racingtips/EXRace.xhtml");
		addClass("bg-cont");
		
		MTCDTO dto = SpringUtil.getBeanOfType(MTCDTO.class);
		List<Meeting> meetings = dto.getSelectedMeetings();
		DefaultDataModel mmmm = new DefaultDataModel(meetings);		
		EXSelect select = new EXSelect("meetings", mmmm);
		select.setStyle("margin", "5px 0 5px 130px");
		addChild(select);
		select.addEvent(this, Event.CHANGE);
		
		for(int i = 1; i <= 8; i++){
			Container td = new EXContainer("race" + i, "td").setText("<a href=#e>"+i+"</a>").addEvent(this, Event.CLICK);
			td.setAttribute("raceid", i + "");
			addChild(td);
			if(i ==1){
				td.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
			}
		}
		
		addChild(new EXContainer("title", "span"));
		addChild(new EXContainer("length", "span"));
		addChild(new EXContainer("time", "span"));
		addChild(new EXContainer("rating", "span"));
		addChild(new EXRaceBody("raceCardBody"));
		
		addChild(new EXContainer("comments", "div").setDisplay(false));
		Meeting meeting = SpringUtil.getBeanOfType(MTCDTO.class).getCurrentMeeting();
		setMeeting(meeting);
		select.setValue(meeting);
		
		
		String[] as = new String[]{"showAllComments",  "showPrediction"};
		
		for(String name : as){
			//Container a = new EXContainer(name,"a").setText(EXPublicVote.UNCHECKED).addEvent(this, Event.CLICK).setAttribute("checked", "false");
			Container a = new EXContainer(name,"button").setStyleClass("form-submit").addEvent(this, Event.CLICK).setText(name.replace("show", "Show ").replace("All", "all "));
			addChild(a);
		}
		if("3RacingtipsAdmin".equals(Util.getRemoteUser())){
			addChild(new EXContainer("predictionInput", "button").addClass("form-submit").setText("Enter prediction").addEvent(this, CLICK));
			addChild(new EXTextArea("prediction").addEvent(this, Event.BLUR).setDisplay(false));
		}
	}
	
	
	public void setMinimal(){
		
		getChild("minimalCard").setStyleClass("form-submit form-submit-selected");
		getChild("standardCard").setStyleClass("form-submit");
		
		setTemplateLocation("templates/racingtips/EXRaceGlance.xhtml");
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.setTemplateLocation("templates/racingtips/EXRaceCardItemGlance.xhtml");
			
		}
	}
	
	
	
	public void setStandard(){
		getChild("minimalCard").setStyleClass("form-submit");
		getChild("standardCard").setStyleClass("form-submit form-submit-selected");
		setTemplateLocation("templates/racingtips/EXRace.xhtml");
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.setTemplateLocation("templates/racingtips/EXRaceCardItem.xhtml");
			
		}
	}
	public void addComment(String title, String summary){
		Race race = (Race)SpringUtil.getRepositoryService().getFile(currentRacePath, Util.getRemoteUser());
		String name = Util.getRemoteUser() + new Date().getTime();
		Comment comment = race.createComment(name);
		comment.setTitle(title);
		comment.setSummary(summary);
		race.save();
		Container comments = getChild("comments");
		comments.addChild(new EXRaceComment("", race));
	}
	
	public void setMeeting(Meeting meeting)throws Exception{
		Race race =  meeting.getRace("1");
		if(race != null){
			setRace(race);
			getChild("race1").setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
		}
		this.meetingPath = meeting.getAbsolutePath();
	}
	
	protected void setRace(Race race)throws Exception{
		setAttribute("path", race.getAbsolutePath());
		List<RaceCardItem> card = race.getRaceCard();
		getDescendentOfType(EXRaceBody.class).setRaceCard(card);
		getChild("title").setText(race.getTitle());
		getChild("length").setText(race.getLength());
		getChild("time").setText(race.getTime());
		getChild("rating").setText(race.getRating());
		this.currentRacePath = race.getAbsolutePath();
		Container comments = getChild("comments");
		comments.getChildren().clear();
		comments.setRendered(false);
		comments.addChild(new EXRaceComment("", race));
	 	
	}
	
	public void showHidePrediction(Container container){
		if(container.getStyleClass().equalsIgnoreCase("form-submit")){
			getChild("comments").setDisplay(true);
			container.setStyleClass("form-submit form-submit-selected");
			container.setText("Hide prediction");
		}else{
			getChild("comments").setDisplay(false);
			container.setStyleClass("form-submit");
			container.setText("Show prediction");
		}
	}
	
	
	public void showAllComments(){
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.showComment();
		}
	}
	
	public void showAllForms(){
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.showForms();
		}
	}
	
	public void showAllTrackWorks(){
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.showTrackWork();
		}
	}
	
	
	public void hideAllComments(){
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.hideComment();
		}
	}
	
	
	public void hideAllForms(){
		List<Container> result = new ArrayList<Container>();
		
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.hideForms();
		}
	}
	
	
	public void hideAllTrackWorks(){
		List<Container> result = new ArrayList<Container>();
		ComponentUtil.getDescendentsOfType(this, result, EXRaceCardItem.class);
		for(Container c : result){
			EXRaceCardItem item = (EXRaceCardItem)c;
			item.hideTrackWork();
		}
	}
	
	
	public void showHideTrackWork(EXCheckBox source){
		if(!source.isChecked()){
		//if("true".equals(source.getAttribute("checked"))){
			hideAllTrackWorks();
			//source.setAttribute("checked", "false");
			//source.setText(EXPublicVote.UNCHECKED);
		}else{
			showAllTrackWorks();
			//source.setAttribute("checked", "true");
			//source.setText(EXPublicVote.CHECKED);
		}
	}
	
	
	public void showHideForms(EXCheckBox source){
		if(!source.isChecked()){
		//if("true".equals(source.getAttribute("checked"))){
			hideAllForms();
			//source.setAttribute("checked", "false");
			//source.setText(EXPublicVote.UNCHECKED);
		}else{
			showAllForms();
			//source.setAttribute("checked", "true");
			//source.setText(EXPublicVote.CHECKED);
		}
	}
	
	
	public void showHideComments(Container container){
		
		if(container.getStyleClass().equalsIgnoreCase("form-submit")){
			showAllComments();
			container.setStyleClass("form-submit form-submit-selected");
			container.setText("Hide all comments");
		}else{
			hideAllComments();
			container.setStyleClass("form-submit");
			container.setText("Show all comments");
		}
	}
	
	

	public void executeAction(Container source) {
		//"showAllComments", "showAllTraining", "showAllForms"
		
		if(source.getName().equals("predictionInput")){
			getChild("prediction").setDisplay(!getChild("prediction").isVisible());
			return;
		}
		
		
		if(source.getName().equals("prediction")){
			Race race = (Race)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			race.setSummary( ((EXTextArea)source).getValue().toString());
			race.save();
			return;
		}
		
		if(source.getName().equalsIgnoreCase("minimalCard")){
			setMinimal();
			return;
		}else if(source.getName().equalsIgnoreCase("standardCard")){
			setStandard();
			return;
		}
		if(source.getName().equalsIgnoreCase("showAllComments")){
			showHideComments(source);
			return;
		}else if(source.getName().equalsIgnoreCase("showAllTraining")){
			showHideTrackWork((EXCheckBox)source);
			return;
		}else if(source.getName().equalsIgnoreCase("showAllForms")){
			showHideForms((EXCheckBox)source);
			return;
		}else if(source.getName().equalsIgnoreCase("showPrediction")){
			showHidePrediction(source);
			return;
		}
		
		try{
			if(getChild("prediction") != null){
				((EXTextArea)getChild("prediction")).setValue("");
				getChild("prediction").setDisplay(false);
			}
			if(source.getName().startsWith("race")){
				for(Container c : getChildren()){
					if(c.getName().startsWith("race")){
						c.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-menu.gif\") repeat scroll right 0 transparent");
					}
				}
			
				source.setStyle("background", "url(\"http://osc4.template-help.com/drupal_30257/themes/theme514/images/bg-li-act.gif\") repeat scroll 0 0 transparent");
				Race r =  ((Meeting)SpringUtil.getRepositoryService().getFile(meetingPath, Util.getRemoteUser())).getRace(source.getAttribute("raceid")); // meeting.getRaces().get(index);
				setRace(r);
			}else{
				EXSelect select = (EXSelect)source;
				Meeting meeting = (Meeting)select.getValue();
				meeting = ((Meeting)SpringUtil.getRepositoryService().getFile(meeting.getAbsolutePath(), Util.getRemoteUser()));
				setMeeting(meeting);
			}
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "block")).makeServerRequest(this);
	}

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		if(container.getName().equals("postComment")){
			getDescendentOfType(EXCommentForm.class).setDisplay(true);
			return true;
		}
		executeAction(container);
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		container.mergeCommand(new ClientProxy("#iamloading").setStyle("display", "none"));
		
	}

	@Override
	public void onLogin(String username) {
		// TODO Auto-generated method stub
		
	}



	

}
