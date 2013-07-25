package com.maureva.autodownload.download.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;

import org.springframework.context.ApplicationContext;

import com.maureva.autodownload.download.Node;
import com.maureva.autodownload.download.Util;
import com.maureva.autodownload.download.iinet.Main;
import com.maureva.autodownload.process.concurrent.AutoDownloadResult;
import com.maureva.fileretriever.download.DownloadFileProcess;

public class DownloadBucket {
	
	private  ExecutorService executorService = Executors.newFixedThreadPool(800);
	
	private ApplicationContext context;
	
	private List<Future<AutoDownloadResult>> futures = new ArrayList<Future<AutoDownloadResult>>();
	
	
	
	public DownloadBucket(ApplicationContext context) {
		super();
		this.context = context;
	}



	public void addNode(final Node n, final String outputDir, boolean unzip, FileRenamer renamer, Map<String, String> decryptCtx){
		DownloadFileProcess process = new DownloadFileProcess(n, outputDir, unzip,renamer, decryptCtx);
		process.setApplicationContext(context);
		if(Main.CONCURRENT_DOWNLOAD)
			futures.add(executorService.submit(process));
		else{
			try{
				process.call();
			}catch(Exception e){
				Util.getLogger("LOGGER").log(Level.WARNING, n.getName() + "|download",e);
			}
		}
	}
	
	public void addNodeBlock(final Node n, final String outputDir, boolean unzip, FileRenamer renamer, Map<String, String> decryptCtx){
		DownloadFileProcess process = new DownloadFileProcess(n, outputDir, unzip,renamer, decryptCtx);
		process.setApplicationContext(context);
		try{
			process.process();
		}catch(Exception e){
			e.printStackTrace();
		}
		//futures.add(executorService.submit(process));
	}
	
	public boolean running(){
		for(Future<AutoDownloadResult> f : futures){
			if(f.isCancelled() || f.isDone()){
				
			}else{
				return true;
			}
			
			
		}
		return false;
	}

}
