package com.emr.models;

import java.util.concurrent.ConcurrentHashMap;

public interface IExternalStorage {

	ConcurrentHashMap<Integer, Record> readRecords();

	void writeRecord(Record record);

}
