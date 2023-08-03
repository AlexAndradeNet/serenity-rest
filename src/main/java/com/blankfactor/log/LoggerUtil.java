/* BlankFactor (C)2023 */
package com.blankfactor.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtil {
    private static final Logger logger = LoggerFactory.getLogger(LoggerUtil.class);

    public static void info(String message) {
        logger.info(message);
    }
}
