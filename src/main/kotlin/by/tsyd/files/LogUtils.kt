package by.tsyd.files

import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author Dmitry Tsydzik
 * @since 6/19/18
 */
fun <T> getLogger(clazz: Class<T>): Logger = LoggerFactory.getLogger(clazz)