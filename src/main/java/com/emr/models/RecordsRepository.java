package com.emr.models;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class RecordsRepository {
	private static RecordsRepository recordsRepositoryInstance = null;
	/* The key is recordId  */
	private ConcurrentHashMap<Integer, Record> records = new ConcurrentHashMap<Integer, Record>();;
	// handle write and read to file for backup data 
	private IExternalStorage storage; 
	
	private RecordsRepository() {
		storage = CommonFactory.getInstance().getExternalStorage(EmrConstants.EXTERNAL_STORAGE_TYPE);
		// get data from file if exist
		records = storage.readRecords();
	}
	
	public static RecordsRepository getInstance() {
		if(recordsRepositoryInstance == null) {
			recordsRepositoryInstance = new RecordsRepository();
		}
		
		return recordsRepositoryInstance;
	}

	public void addRecord(Record record) {
		// add record to map
		records.put(record.getId(), record);
		
		// save record to file
		storage.writeRecord(record);
	}

	public Record getRecord(int record_id) {
		return records.get(record_id);
	}

	public Collection<Record> getRecords() {
		return records.values();
	}

	public int getSize() {
		return records.size();
	}

	public boolean contain(int record_id) {
		return records.containsKey(record_id);
	}
}
