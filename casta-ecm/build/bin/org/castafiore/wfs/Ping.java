package org.castafiore.wfs;

import java.util.TimerTask;

import org.castafiore.spring.SpringUtil;

public class Ping extends TimerTask{

	@Override
	public void run() {
		SpringUtil.getRepositoryService().getFile("/root", null);
		
	}

}
