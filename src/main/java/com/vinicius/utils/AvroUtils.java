package com.vinicius.utils;

import org.apache.avro.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AvroUtils {

    private static final Logger logger = LoggerFactory.getLogger(AvroUtils.class);

    private AvroUtils() {
    }

    public static Schema createSchema(final String avroTemplatePath) {
	Schema schema = null;
	
	try {
	    schema = new Schema.Parser().parse(AvroUtils.class.getResourceAsStream(avroTemplatePath));
	} catch (Exception e) {
	    logger.error("Error to create schema.", e);
	    throw new RuntimeException(e);
	}
	
	return schema;
    }
}
