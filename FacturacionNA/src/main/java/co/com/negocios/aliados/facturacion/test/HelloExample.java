package co.com.negocios.aliados.facturacion.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import org.apache.log4j.PropertyConfigurator;

public class HelloExample{
	
	final static Logger logger = Logger.getLogger(HelloExample.class);
	
//	public static void main(String[] args) {
//	
//		HelloExample obj = new HelloExample();
//		obj.runMe("mkyong");
//		String log4jConfigFile = System.getProperty("user.dir")
//				+ File.separator + "log4j.properties";
//		PropertyConfigurator.configure(log4jConfigFile);
		
//		Properties props = new Properties();
//
//		try {
//			props.load(new FileInputStream("D:\\Proyectos\\HelloLogging\\log4j.properties"));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		props.setProperty("log4j.appender.File.File", "Folder where you want to store log files/" + "File Name");
//		
		
//		Properties properties=new Properties();
//	    properties.setProperty("log4j.rootLogger","TRACE,stdout,MyFile");
//	    properties.setProperty("log4j.rootCategory","TRACE");
//
//	    properties.setProperty("log4j.appender.stdout",     "org.apache.log4j.ConsoleAppender");
//	    properties.setProperty("log4j.appender.stdout.layout",  "org.apache.log4j.PatternLayout");
//	    properties.setProperty("log4j.appender.stdout.layout.ConversionPattern","%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
//
//	    properties.setProperty("log4j.appender.MyFile", "org.apache.log4j.RollingFileAppender");
//	    properties.setProperty("log4j.appender.MyFile.File", "my_example.log");
//	    properties.setProperty("log4j.appender.MyFile.MaxFileSize", "100KB");
//	    properties.setProperty("log4j.appender.MyFile.MaxBackupIndex", "1");
//	    properties.setProperty("log4j.appender.MyFile.layout",  "org.apache.log4j.PatternLayout");
//	    properties.setProperty("log4j.appender.MyFile.layout.ConversionPattern","%d{yyyy/MM/dd HH:mm:ss.SSS} [%5p] %t (%F) - %m%n");
//
//	    PropertyConfigurator.configure(properties);
//
//	    Logger logger = Logger.getLogger("MyFile");
//
//	    logger.fatal("This is a FATAL message.");
//	    logger.error("This is an ERROR message.");
//	    logger.warn("This is a WARN message.");
//	    logger.info("This is an INFO message.");
//	    logger.debug("This is a DEBUG message.");
//	    logger.trace("This is a TRACE message.");
		
//	}
	
	private void runMe(String parameter){
		
		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + parameter);
		}
		
		if(logger.isInfoEnabled()){
			logger.info("This is info : " + parameter);
		}
		
		logger.warn("This is warn : " + parameter);
		logger.error("This is error : " + parameter);
		logger.fatal("This is fatal : " + parameter);
		
	}
	
}
