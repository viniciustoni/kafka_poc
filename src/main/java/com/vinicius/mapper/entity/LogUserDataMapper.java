package com.vinicius.mapper.entity;

import static com.vinicius.mapper.entity.enumerated.LogUserColumnName.DATA_REGISTRY;
import static com.vinicius.mapper.entity.enumerated.LogUserColumnName.USER_ID;
import static com.vinicius.mapper.entity.enumerated.LogUserColumnName.USER_NAME;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import com.vinicius.entity.LogUser;

public class LogUserDataMapper extends AvroEntityDataMapper<LogUser> {

    private static final String SCHEMA_PATH = "/avro/logUser.avro";

    public LogUserDataMapper() {
	super(SCHEMA_PATH);
    }

    @Override
    protected LogUser parseObjectToObjet(GenericRecord object) {

	final LogUser logUser = new LogUser();

	logUser.setUserId((Long) object.get(USER_ID.name()));
	logUser.setUserName(String.valueOf(object.get(USER_NAME.name())));

	Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTimeInMillis((Long) object.get(DATA_REGISTRY.name()));

	logUser.setDataRegistry(calendar.getTime());

	return logUser;
    }

    @Override
    protected GenericRecord parseObjectToGenericRecord(LogUser object) {

	GenericRecord record = new GenericData.Record(getSchema());

	record.put(USER_ID.name(), object.getUserId());
	record.put(USER_NAME.name(), object.getUserName());
	record.put(DATA_REGISTRY.name(), object.getDataRegistry().getTime());

	return record;
    }

}
