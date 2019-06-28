package loger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LoggerProd implements MyLog {

    public static final Logger LOG = LoggerFactory.getLogger(LoggerProd.class);

    @Override
    public void info(String message) {
        LOG.info(message);

    }
}


