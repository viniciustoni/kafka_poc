package com.vinicius.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vinicius.entity.LogUser;
import com.vinicius.processor.MessageProcessor;

public class LogUserProcessor implements MessageProcessor<LogUser> {

    private static final Logger logger = LoggerFactory.getLogger(LogUserProcessor.class);
    
    @Override
    public void processMessage(LogUser object) {
	logger.info("Processing message: {}", object);	
    }
}
