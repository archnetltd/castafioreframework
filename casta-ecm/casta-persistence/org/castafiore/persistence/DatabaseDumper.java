package org.castafiore.persistence;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.castafiore.persistence.ui.EXBackup;
import org.castafiore.spring.SpringUtil;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.ForwardOnlyResultSetTableFactory;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.csv.CsvDataSetWriter;
import org.dbunit.ext.postgresql.PostgresqlDataTypeFactory;

public  class DatabaseDumper implements Runnable{

	public void export()throws Exception{
		IDatabaseConnection connection = new DatabaseConnection(SpringUtil.getBeanOfType(DataSource.class).getConnection());
		DatabaseConfig config = connection.getConfig();
        config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, EXBackup.factory);
        config.setProperty(DatabaseConfig.PROPERTY_RESULTSET_TABLE_FACTORY, new ForwardOnlyResultSetTableFactory());
        config.setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, 2);
        config.setProperty(DatabaseConfig.PROPERTY_FETCH_SIZE, 2);
        
        QueryDataSet partial = new QueryDataSet(connection);
        partial.addTable("WFS_FILE", "select * from WFS_FILE where absolutePath like '/root/users/elieandsons/%'");
      //  StreamingDataSet sd = new StreamingDataSet(new Datasetp)
        File dir = new File(EXBackup.backupdir + "/" + System.currentTimeMillis() + "-schedular" );
        
        CsvDataSetWriter.write(partial, dir);
        zip(dir);
        dir.delete();
	}
	
	@Override
	public void run() {
		try{
		export();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	private void zip(File inFolder)throws Exception{
		File outFolder=new File(inFolder.getAbsolutePath() + ".zip");
		ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
		BufferedInputStream in = null;
		byte[] data    = new byte[1000];
		String files[] = inFolder.list();
		for (int i=0; i<files.length; i++){
			in = new BufferedInputStream(new FileInputStream(inFolder.getPath() + "/" + files[i]), 1000);                  
			out.putNextEntry(new ZipEntry(files[i])); 
			int count;
			while((count = in.read(data,0,1000)) != -1) {
				out.write(data, 0, count);
			}
			out.closeEntry();
		}
		out.flush();
		out.close();
	}
	
 
    public static void main(final String[] args) {
        try {
    		
    		BasicDataSource so = new BasicDataSource();
    		so.setUsername("postgres");
    		so.setPassword("postgres");
    		so.setUrl("jdbc:postgresql://localhost:5432/casta_3");
    		so.setDriverClassName("org.postgresql.Driver");
        	Connection connectio =so.getConnection();
    		
        	
        	
          // DataSource source = SpringUtil.getBeanOfType(DataSource.class);
           IDatabaseConnection connection = new DatabaseConnection(connectio);
           DatabaseConfig config = connection.getConfig();
           config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new PostgresqlDataTypeFactory());
           IDataSet dataset = connection.createDataSet();
           
          
          CsvDataSetWriter.write(dataset, new File("c://java/opooo.csv"));
           
           
        } catch (Exception e) {
           // LOGGER.error("", e);
        	e.printStackTrace();
        }
    }


	

}
