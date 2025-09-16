package test.utilities;

import org.apache.logging.log4j.LogManager; 
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.spi.ExtendedLogger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.core.Layout;
/**
 * Centralized logging utility using Log4j2.
 *
 * ✅ No need to declare a logger in each class. ✅ Automatically resolves caller
 * class (no "LoggerUtility" in logs). ✅ Supports formatted messages. ✅ Can
 * update log file path dynamically.
 */
public class LoggerUtil {

	private static final String DEFAULT_APPENDER_NAME = "FileLogger";
	private static final String FQCN = LoggerUtil.class.getName();
	private static final ExtendedLogger BASE_LOGGER = (ExtendedLogger) LogManager.getRootLogger();

	/** Format safely to avoid exceptions during logging */
	private static String formatSafe(String msg, Object... args) {
		try {
			return (args == null || args.length == 0) ? msg : String.format(msg, args);
		} catch (Exception e) {
			return msg + " [!! formatting error: " + e.getMessage() + "]";
		}
	}

	/** Unified log method */
	private static void log(Level level, String msg, Object... args) {
		Message message = new SimpleMessage(formatSafe(msg, args));
		BASE_LOGGER.logIfEnabled(FQCN, level, null, (Message) message, null);
	}

	// ==========================
	// Logging Shortcuts
	// ==========================
	public static void info(String msg, Object... args) {
		log(Level.INFO, msg, args);
	}

	public static void warn(String msg, Object... args) {
		log(Level.WARN, msg, args);
	}

	public static void error(String msg, Object... args) {
		log(Level.ERROR, msg, args);
	}

	public static void debug(String msg, Object... args) {
		log(Level.DEBUG, msg, args);
	}

	public static void trace(String msg, Object... args) {
		log(Level.TRACE, msg, args);
	}

	public static void fatal(String msg, Object... args) {
		log(Level.FATAL, msg, args);
	}

	// ==========================
	// Dynamic Log File Path
	// ==========================
	public static void setLogFilePath(String logFilePath) {
	    // Get the LoggerContext for the current Log4j2 configuration
	    LoggerContext context = (LoggerContext) LogManager.getContext(false);
	    Configuration config = context.getConfiguration();
	    
	    // Get the logger configuration for the root logger
	    LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);

	    // Get the old RollingFileAppender from the current configuration
	    RollingFileAppender oldAppender = (RollingFileAppender) loggerConfig.getAppenders().get(DEFAULT_APPENDER_NAME);

	    if (oldAppender == null) {
	        // If the appender is not found, print an error and return
	        System.err.println("❌ RollingFileAppender '" + DEFAULT_APPENDER_NAME + "' not found in log4j2.xml.");
	        return;
	    }

	    // Retrieve layout, file pattern, and triggering policy from the old appender
	    Layout<?> layout = oldAppender.getLayout();
	    String filePattern = oldAppender.getFilePattern();
	    var triggeringPolicy = oldAppender.getTriggeringPolicy();

	    // Ensure all necessary components are available
	    if (layout == null || filePattern == null || triggeringPolicy == null) {
	        System.err.println("❌ Missing necessary components (layout, filePattern, or triggeringPolicy) from the old appender.");
	        return;
	    }

	    // Create a new RollingFileAppender with the updated log file path and other configurations
	    RollingFileAppender newAppender = RollingFileAppender.newBuilder()
	            .setName(DEFAULT_APPENDER_NAME)              // Set a valid appender name
	            .withFileName(logFilePath)                    // New log file path
	            .withFilePattern(filePattern)                 // Retain the old file pattern (e.g., date-based rollover)
	            .withAppend(true)                             // Append to the file (if it exists)
	            .withPolicy(triggeringPolicy)                 // Retain the old triggering policy (time/size based)
	            .setLayout(layout)                            // Retain the old layout (e.g., pattern layout)
	            .setConfiguration(config)                     // Use the current configuration
	            .build();

	    // Check if the new appender was created successfully
	    if (newAppender == null) {
	        System.err.println("❌ Failed to create new RollingFileAppender.");
	        return;
	    }

	    // Start the new appender
	    newAppender.start();

	    // Remove the old appender from the logger configuration and add the new appender
	    loggerConfig.removeAppender(DEFAULT_APPENDER_NAME);
	    loggerConfig.addAppender(newAppender, null, null);

	    // Update the loggers with the new configuration
	    context.updateLoggers();

	    // Log the success of the operation
	    info("Log file path updated to: %s", logFilePath);
	}


}
