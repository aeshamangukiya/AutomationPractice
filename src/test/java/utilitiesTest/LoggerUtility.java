package utilitiesTest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.appender.RollingFileAppender;

public class LoggerUtility {
	public static Logger logger;

	/**
	 * Updates Log4j2 configuration to change the log file path dynamically.
	 * 
	 * @param logFilePath New log file path
	 */
	public static void setLogFilePath(String logFilePath) {
		LoggerContext context = (LoggerContext) LogManager.getContext(false);
		Configuration config = context.getConfiguration();
		LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

		RollingFileAppender appender = (RollingFileAppender) loggerConfig.getAppenders().get("FileLogger");

		//System.out.println("Available Appenders: " + loggerConfig.getAppenders().keySet());

		if (appender == null) {
			System.out.println("ERROR: RollingFileAppender 'FileLogger' is missing! Check log4j2.xml.");
			return; // Exit method to prevent NullPointerException
		}

		// Remove old appender and add a new one with updated path
		loggerConfig.removeAppender("FileLogger");
		RollingFileAppender newAppender = RollingFileAppender.newBuilder().withFileName(logFilePath)
				.withFilePattern("logs/archive-%d{yyyy-MM-dd}.log.gz").withAppend(true).withName("FileLogger")
				.withPolicy(appender.getTriggeringPolicy()) // Avoids NullPointerException
				.withStrategy(appender.getManager().getRolloverStrategy()).withLayout(appender.getLayout())
				.withStrategy(null).build();

		newAppender.start();
		loggerConfig.addAppender(newAppender, null, null);
		context.updateLoggers();

		//System.out.println("✅ Log file path updated to: " + logFilePath);
	}

	public static void info(String message) {
		logger.info(message);
	}

	public static void warn(String message) {
		logger.warn(message);
	}

	public static void error(String message) {
		logger.error(message);
	}

	public static void debug(String message) {
		logger.debug(message);
	}
}
