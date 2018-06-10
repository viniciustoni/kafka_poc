package com.vinicius.mapper.entity;

import static com.vinicius.utils.AvroUtils.createSchema;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;

import com.vinicius.mapper.DataMapper;
import com.vinicius.mapper.avro.AvroDataMapper;

public abstract class AvroEntityDataMapper<T> implements DataMapper<T> {

    private final AvroDataMapper avroDataMapper;
    private final Schema schema;

    public AvroEntityDataMapper(String schemaPath) {
	this.schema = createSchema(schemaPath);
	this.avroDataMapper = new AvroDataMapper(this.schema);
    }

    public T to(byte[] data) {
	return parseObjectToObjet(avroDataMapper.to(data));
    }

    public byte[] from(T object) {
	return avroDataMapper.from(parseObjectToGenericRecord(object));
    }

    protected Schema getSchema() {
	return this.schema;
    }

    protected abstract T parseObjectToObjet(final GenericRecord object);

    protected abstract GenericRecord parseObjectToGenericRecord(final T object);
}
