package org.castafiore.persistence;

import java.io.File;

import javax.sql.DataSource;

import org.castafiore.spring.SpringUtil;
import org.castafiore.ui.UIException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvProducer;
import org.dbunit.dataset.stream.StreamingDataSet;
import org.dbunit.operation.DatabaseOperation;

public class DatabaseImporter implements Runnable{
	
	public String dir;
	
	
	
	public DatabaseImporter(String dir) {
		super();
		this.dir = dir;
	}

	public void imporT ()throws Exception{
		
		//CsvDataSet set = new CsvDataSet(new File(dir));
		
		CsvProducer producer = new CsvProducer(new File(dir));
		
		IDatabaseConnection connection = new DatabaseConnection(SpringUtil.getBeanOfType(DataSource.class).getConnection());
		IDataSet set = new StreamingDataSet(producer);
		//DatabaseOperation.TRUNCATE_TABLE.execute(connection, set);
		DatabaseOperation.CLEAN_INSERT.execute(connection, set);
		//connection.
		
	}

	@Override
	public void run() {
		try{
		imporT();
		}catch(Exception e){
			throw new UIException(e);
		}
		
	}

}
