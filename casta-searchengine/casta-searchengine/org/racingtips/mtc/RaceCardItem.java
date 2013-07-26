package org.racingtips.mtc;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Entity;

import org.castafiore.ui.UIException;
import org.castafiore.wfs.Util;
import org.castafiore.wfs.types.Article;
import org.castafiore.wfs.types.Directory;
import org.castafiore.wfs.types.Value;

@Entity
public class RaceCardItem extends Article implements Comparator<RaceCardItem>{
	
	public final static int HORSE_REMOVED = 5002;
	
	private String horseId;
	
	private String horseNumber;
	
	private String horse;
	
	private String stable;
	
	private String perf;
	
	private String age;
	
	private String gear;
	
	private String weight;
	
	private String jockey;
	
	private String draw;
	
	private String silk;
	
	private String horseWeight;
	
	private String openingOdd;
	
	private String recentOdd;
	
	private BigDecimal v1;
	
	private BigDecimal v2;
	private BigDecimal v3;
	private BigDecimal v4;
	
	
	
	

	public BigDecimal getV1() {
		return v1;
	}


	public void setV1(BigDecimal v1) {
		this.v1 = v1;
	}


	public BigDecimal getV2() {
		return v2;
	}


	public void setV2(BigDecimal v2) {
		this.v2 = v2;
	}


	public BigDecimal getV3() {
		return v3;
	}


	public void setV3(BigDecimal v3) {
		this.v3 = v3;
	}


	public BigDecimal getV4() {
		return v4;
	}


	public void setV4(BigDecimal v4) {
		this.v4 = v4;
	}


	public String getHorseWeight() {
		return horseWeight;
	}
	
	
	public void vote(int rank){
		//String user = Util.getRemoteUser();
		
		if(rank == 1){
			if(v1 == null){
				v1 = new BigDecimal(1);
			}
			v1 = v1.add(new BigDecimal(1));
		}
		
		if(rank == 2){
			if(v2 == null){
				v2 = new BigDecimal(1);
			}
			v2 = v2.add(new BigDecimal(1));
		}
		
		if(rank == 3){
			if(v3 == null){
				v3 = new BigDecimal(1);
			}
			v3 = v3.add(new BigDecimal(1));
		}
		
		
		if(rank == 4){
			if(v4 == null){
				v4 = new BigDecimal(1);
			}
			v4 = v4.add(new BigDecimal(1));
		}
		save();
		
		
////		if(user == null || user.trim().length() <=0){
////			throw new UIException("log");
////		}
////		
//		
//		Directory voteDir = (Directory)getChild("votes");
//		if(voteDir == null){
//			voteDir = createFile("votes", Directory.class);
//			Value val = voteDir.createFile(user, Value.class);
//			val.setString(rank + "");
//			save();
//			return;
//			
//		}else{
//			if(voteDir.getChild(user) == null){
//				Value val = voteDir.createFile(user, Value.class);
//				val.setString(rank + "");
//				val.save();
//				return;
//			}else{
//				throw new UIException("voted");
//			}
//		}
	}
	
	
	public String[] getVotedRanks(){
		String[] result = new String[]{"1", "1", "1", "1"};
		try{
			Directory voteDir = (Directory)getFile("votes");
			if(voteDir != null){
				List<Value> values = voteDir.getFiles(Value.class).toList();
				for(Value val : values){
					int index = Integer.parseInt(val.getString())-1;
					int currentVal = Integer.parseInt(result[index]);
					result[index] = (currentVal + 1) + "";
				}
				for(int i = 0; i < result.length; i++){
					BigDecimal percent =  ( new BigDecimal(result[i]).divide(new BigDecimal(values.size()))).multiply(new BigDecimal("100"));
					result[i] = percent.toString();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public String getPersonalComment(){
		String user = Util.getRemoteUser();
		
		if(user == null || user.trim().length() <=0){
			throw new UIException("log");
		}
		Value val = null;
		
		Directory voteDir = (Directory)getFile("personalComments");
		if(voteDir != null){
			val = (Value)voteDir.getFile(user);
			if(val != null){
				return val.getString();
			}
		}return "";
	}
	
	
	public void addSelection(String selection){
		String user = Util.getRemoteUser();
		
		if(user == null || user.trim().length() <=0){
			throw new UIException("log");
		}
		Value val = null;
		
		Directory voteDir = (Directory)getFile("selections");
		if(voteDir == null){
			voteDir = createFile("selections", Directory.class);
			val = voteDir.createFile(user, Value.class);
		}else{
			val = (Value)voteDir.getFile(user);
			if(val == null){
				val = voteDir.createFile(user, Value.class);
			}
		}
		val.setString(selection);
		save();
	}
	
	
	
	
	public String getSelection(){
		String user = Util.getRemoteUser();
		
		if(user == null || user.trim().length() <=0){
			throw new UIException("log");
		}
		Value val = null;
		
		Directory voteDir = (Directory)getFile("selections");
		if(voteDir != null){
			val = (Value)voteDir.getFile(user);
			if(val != null){
				return val.getString();
			}
		}return "";
	}
	
	
	public int getRank(String username){
		String user = Util.getRemoteUser();
		
		if(user == null || user.trim().length() <=0){
			throw new UIException("log");
		}
		Value val = null;
		
		Directory voteDir = (Directory)getFile("votes");
		if(voteDir != null){
			val = (Value)voteDir.getFile(user);
			if(val != null){
				return Integer.parseInt(val.getString());
			}
		}return 1;
	}
	
	public void addPersonalComment(String comment){
		String user = Util.getRemoteUser();
		
		if(user == null || user.trim().length() <=0){
			throw new UIException("log");
		}
		Value val = null;
		
		Directory voteDir = (Directory)getFile("personalComments");
		if(voteDir == null){
			voteDir = createFile("personalComments", Directory.class);
			val = voteDir.createFile(user, Value.class);
		}else{
			val = (Value)voteDir.getFile(user);
			if(val == null){
				val = voteDir.createFile(user, Value.class);
			}
		}
		val.setString(comment);
		save();
	}

	public void setHorseWeight(String horseWeight) {
		this.horseWeight = horseWeight;
	}

	public String getHorseNumber() {
		return horseNumber;
	}

	public void setHorseNumber(String horseNumber) {
		this.horseNumber = horseNumber;
	}

	public String getHorse() {
		return horse;
	}

	public void setHorse(String horse) {
		this.horse = horse;
	}

	public String getStable() {
		return stable;
	}

	public void setStable(String stable) {
		this.stable = stable;
	}

	public String getPerf() {
		return perf;
	}

	public void setPerf(String perf) {
		this.perf = perf;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGear() {
		return gear;
	}

	public void setGear(String gear) {
		this.gear = gear;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getJockey() {
		return jockey;
	}

	public void setJockey(String jockey) {
		this.jockey = jockey;
	}

	public String getDraw() {
		return draw;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}
	
	

	public String getHorseId() {
		return horseId;
	}

	public void setHorseId(String horseId) {
		this.horseId = horseId;
	}

	public String getSilk() {
		if(silk == null || silk.length() <=0){
			silk = "http://www.mauritiusturfclub.com/images/stories/casaq/48.png";
		}
		return silk;
	}

	public void setSilk(String silk) {
		this.silk = silk;
	}

	@Override
	public int compare(RaceCardItem o1, RaceCardItem o2) {
		try
		{
			Integer.parseInt(o1.horseNumber);
		}catch(Exception e){
			return 1;
		}
		try
		{
			Integer.parseInt(o2.horseNumber);
		}catch(Exception e){
			return -1;
		}
		return new Integer( o1.horseNumber).compareTo(new Integer(o2.horseNumber));
	}

	public String getOpeningOdd() {
		return openingOdd;
	}

	public void setOpeningOdd(String openingOdd) {
		this.openingOdd = openingOdd;
	}

	public String getRecentOdd() {
		return recentOdd;
	}

	public void setRecentOdd(String recentOdd) {
		this.recentOdd = recentOdd;
	}

	
}
