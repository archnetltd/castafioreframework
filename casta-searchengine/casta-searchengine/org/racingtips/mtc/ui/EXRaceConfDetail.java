package org.racingtips.mtc.ui;

import java.io.InputStream;
import java.util.Map;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.Container;
import org.castafiore.ui.UIException;
import org.castafiore.ui.engine.ClientProxy;
import org.castafiore.ui.events.Event;
import org.castafiore.ui.ex.EXContainer;
import org.castafiore.ui.ex.form.EXInput;
import org.castafiore.ui.ex.form.EXTextArea;
import org.castafiore.ui.ex.form.EXUpload;
import org.castafiore.ui.scripting.EXXHTMLFragment;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Comment;
import org.castafiore.wfs.types.File;
import org.racingtips.mtc.MTCDTO;
import org.racingtips.mtc.Race;
import org.racingtips.mtc.RaceCardItem;
import org.racingtips.tips.AbstractTips;
import org.racingtips.tips.Banker;
import org.racingtips.tips.BankerOne;
import org.racingtips.tips.BankerTwo;
import org.racingtips.tips.FirstChoice;
import org.racingtips.tips.LastMinuteTips;
import org.racingtips.tips.Outsiders;
import org.racingtips.tips.Places;
import org.racingtips.tips.SelectionFour;
import org.racingtips.tips.SelectionOne;
import org.racingtips.tips.SelectionThree;
import org.racingtips.tips.SelectionTwo;

public class EXRaceConfDetail extends EXXHTMLFragment implements Event{

	private final static String[] buttons = new String[]{"firstChoice", "outsiders","places","Banker", "Banker1", "Banker2", "sel1", "sel2", "sel3", "sel4", "LastMinute"};
	private final static String[] labels = new String[]{"First Choice", "Outsiders","Places","Banker", "Rov 1", "Rov 2", "Sel 1", "Sel 2", "Sel 3", "Sel 4", "Last Minute"};
	private final static Class[] models = new Class[]{FirstChoice.class, Outsiders.class, Places.class, Banker.class,BankerOne.class, BankerTwo.class, SelectionOne.class, SelectionTwo.class, SelectionThree.class, SelectionFour.class, LastMinuteTips.class};
	
	public EXRaceConfDetail(String name, RaceCardItem item) {
		super(name, "templates/racingtips/EXRaceConfDetail.xhtml");
		setRaceCardItem(item);
		
	}
	
	public void setRaceCardItem(RaceCardItem item){
		
		setAttribute("path", item.getAbsolutePath());
		FirstChoice fc = item.getFirstChild(FirstChoice.class);
		Outsiders o = item.getFirstChild(Outsiders.class);
		Places p = item.getFirstChild(Places.class);
		BankerOne b1 = item.getFirstChild(BankerOne.class);
		BankerTwo b2 = item.getFirstChild(BankerTwo.class);
		Banker b = item.getFirstChild(Banker.class);
		SelectionOne s1 = item.getFirstChild(SelectionOne.class);
		SelectionTwo s2 = item.getFirstChild(SelectionTwo.class);
		SelectionThree s3 = item.getFirstChild(SelectionThree.class);
		SelectionFour s4 = item.getFirstChild(SelectionFour.class);
		LastMinuteTips lm = item.getFirstChild(LastMinuteTips.class);

		int index = 0;
		for(String s : buttons){
			Container c = new EXContainer(s, "button").setText(labels[index]).addClass("form-submit").addEvent(this, Event.CLICK);
			c.setAttribute("index", index + "");
			addChild(c);
			if(
					(fc != null && s.equals("firstChoice")) ||
					(o != null && s.equals("outsiders"))  ||
					(p != null && s.equals("places"))  ||
					(b1 != null && s.equals("Banker1")) ||
					(b2 != null && s.equals("Banker2")) ||
					(s1 != null && s.equals("sel1")) ||
					(s2 != null && s.equals("sel2")) ||
					(s3 != null && s.equals("sel3")) ||
					(s4 != null && s.equals("sel4")) ||
					(b != null && s.equals("Banker")) ||
					(lm != null && s.equals("LastMinute"))
			){
				c.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
				c.setStyle("color", "white");
				c.setAttribute("active", "true");
			}else{
				c.setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
				c.setStyle("color", "black");
				c.setAttribute("active", "false");
			}
			index++;
		}
		addChild(new EXTextArea("comment").setAttribute("rows", "5").setAttribute("cols", "35").addEvent(this, BLUR));
		Comment comment = item.getFirstChild(Comment.class);
		if(comment != null){
			getDescendentOfType(EXTextArea.class).setValue(comment.getSummary());
		}
		EXInput silk = new EXInput("silk");
		silk.setValue(item.getSilk());
		addChild(silk.addEvent(this, BLUR));
		
		EXInput openingOdd = new EXInput("openingOdd");
		EXInput closingOdd = new EXInput("closingOdd");
		addChild(openingOdd.addEvent(this, BLUR));
		addChild(closingOdd.addEvent(this, BLUR));
		
		if(item.getOpeningOdd() != null){
			openingOdd.setValue(item.getOpeningOdd());
		}else{
			openingOdd.setValue("0");
		}
		
		if(item.getRecentOdd() != null){
			closingOdd.setValue(item.getRecentOdd());
		}else{
			closingOdd.setValue("0");
		}
		addChild(new EXUpload("browseForms"));
		addChild(new EXUpload("browseTrackWork"));
		addChild( new EXContainer("importForms", "button").setText("Import Forms").addClass("form-submit").addEvent(this, Event.CLICK));
		addChild( new EXContainer("importTrackWork", "button").setText("Import TrackWork").addClass("form-submit").addEvent(this, Event.CLICK));
		
		addChild( new EXContainer("removeHorse", "button").setText("Remove horse").addClass("form-submit").addEvent(this, Event.CLICK));
	}
	
	public void saveOrUpdateComment(){
		String comment = getDescendentOfType(EXTextArea.class).getValue().toString();
		RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		Comment fComment = item.getFirstChild(Comment.class);
		if(fComment == null){
			fComment = item.createComment("ExpertTips");
			fComment.setAuthor(Util.getRemoteUser());
		}
		
		fComment.setSummary(comment);
		fComment.save();
	}

	@Override
	public void ClientAction(ClientProxy container) {
		container.makeServerRequest(this);
	}
	
	

	@Override
	public boolean ServerAction(Container container, Map<String, String> request)
			throws UIException {
		SpringUtil.getBeanOfType(MTCDTO.class).kill();
		
		if(container.getName().equals("removeHorse")){
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			if(item.getStatus() != RaceCardItem.HORSE_REMOVED){
				item.setStatus(RaceCardItem.HORSE_REMOVED);
				container.setText("Un remove horse");
			}
			else{
				item.setStatus(item.STATE_NEW);
				container.setText("Remove horse");
			}
			
			item.save();
			return true;
		}
		if(container.getName().equals("silk")){
			String silk = ((EXInput)container).getValue().toString();
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			item.setSilk(silk);
			item.save();
			return true;
		}else if(container.getName().equals("openingOdd")){
			String opodd = ((EXInput)container).getValue().toString();
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			item.setOpeningOdd(opodd);
			item.save();
			return true;
		}else if(container.getName().equals("closingOdd")){
			String clodd = ((EXInput)container).getValue().toString();
			RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
			item.setRecentOdd(clodd);
			item.save();
			return true;
		} else if(container.getName().equals("importForms")){
			try{
				
				RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
				String horseId = item.getHorseId();
				String horse = item.getHorse();
				
				EXUpload upl = (EXUpload)getChild("browseForms");
				InputStream stream = upl.getFile().getInputStream();
				
				SpringUtil.getBeanOfType(MTCDTO.class).importForms(stream, horseId, horse);
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
			
		}  else if(container.getName().equals("importTrackWork")){
			try{
				
				RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
				String horseId = item.getHorseId();
				String horse = item.getHorse();
				
				EXUpload upl = (EXUpload)getChild("browseTrackWork");
				InputStream stream = upl.getFile().getInputStream();
				
				SpringUtil.getBeanOfType(MTCDTO.class).importTrackWork(stream, horseId, horse);
				return true;
			}catch(Exception e){
				throw new UIException(e);
			}
			
		} 
		
		
		if(container instanceof EXTextArea){
			saveOrUpdateComment();
			return true;
		}
		String active = container.getAttribute("active");
		RaceCardItem item = (RaceCardItem)SpringUtil.getRepositoryService().getFile(getAttribute("path"), Util.getRemoteUser());
		int index = Integer.parseInt(container.getAttribute("index"));
		Class cl = models[index];
		AbstractTips tip = (AbstractTips)item.getFirstChild(cl);
		if(active.equals("true")){
			if(tip != null){
				File parent = tip.getParent();
				tip.remove();
				parent.save();
			}
			container.setStyle("background", "url(\"racingtips/images/bg-but.gif\") repeat-x scroll center top transparent");
			container.setStyle("color", "black");
			container.setAttribute("active", "false");
			
		}else if (active.equals("false")){
			if(tip == null){
				tip = (AbstractTips)item.createFile(cl.getSimpleName(), cl);
				tip.setHorseNumber(item.getHorseNumber());
				tip.setRaceNumber( ((Race)item.getParent()).getRaceNumber());
				tip.save();
			}
			container.setStyle("background", "url(\"racingtips/images/bg-li-act.gif\") repeat-x scroll center top transparent");
			container.setStyle("color", "white");
			container.setAttribute("active", "true");
		}
		
		return true;
	}

	@Override
	public void Success(ClientProxy container, Map<String, String> request)
			throws UIException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	

}
