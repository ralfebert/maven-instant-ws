package regm;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.maven.plugin.logging.Log;

public class MojoLoggingHandler extends Handler {

	Log log;

	public MojoLoggingHandler(Log log) {

		this.log = log;
	}

	@Override
	public void close() throws SecurityException {

	}

	@Override
	public void flush() {

	}

	@Override
	public void publish(LogRecord record) {

		if (record.getLevel() == Level.SEVERE) {

			if (record.getThrown() != null) {
				log.error(record.getMessage(), record.getThrown());
			}
			else {
				log.error(record.getMessage());
			}
		}
		else {

			if (record.getThrown() != null) {
				log.info(record.getMessage(), record.getThrown());
			}
			else {
				log.info(record.getMessage());
			}
		}

	}
}
