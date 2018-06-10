package com.vinicius.mapper.avro;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import com.twitter.bijection.Injection;
import com.twitter.bijection.avro.GenericAvroCodecs;
import com.vinicius.mapper.DataMapper;

public class AvroDataMapper implements DataMapper<GenericRecord> {
    private final Schema schema;

    public AvroDataMapper(Schema schema) {
	this.schema = schema;
    }

    @Override
    public GenericRecord to(byte[] value) {
	Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);
	return recordInjection.invert(value).get();
    }

    @Override
    public byte[] from(GenericRecord data) {
	Injection<GenericRecord, byte[]> recordInjection = GenericAvroCodecs.toBinary(schema);
	return recordInjection.apply(data);
    }
}
