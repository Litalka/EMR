package com.emr.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

public class FileExternalStorage implements IExternalStorage{
	private String fileName;
	private File file;
	
	public FileExternalStorage(String fileName) {
		this.fileName = fileName;
		file = new File(fileName); 
	}

	@Override
	public ConcurrentHashMap<Integer, Record> readRecords() {
		ConcurrentHashMap<Integer, Record> recordsMap = new ConcurrentHashMap<Integer, Record>();

		try {
			verifyFileExists();
			
			if(file.length() > 0) {
				InputStream targetStream = new FileInputStream(file);
				Reader r = new InputStreamReader(targetStream, "UTF-8");
				JsonStreamParser p = new JsonStreamParser(r);
				parseJsonToRecords(recordsMap, p);
			}				
		} catch (IOException e) {
			System.out.println("ERROR in read records from file = " + file);
			e.printStackTrace();
		}
		
		return recordsMap;
	}

	private void parseJsonToRecords(ConcurrentHashMap<Integer, Record> recordsMap, JsonStreamParser p) {
		Gson gson = new GsonBuilder().create();
		int maxId = 0;

		while (p.hasNext()) {
			JsonElement e = p.next();
			if (e.isJsonObject()) {
				Record record = gson.fromJson(e, Record.class);
				int recordId = record.getId();
				recordsMap.put(recordId, record);
				
				if(recordId > maxId) {
					maxId = recordId;
				}
			}
		}
		
		// update id auto increment
		Record.updateAutoIncrement(maxId);
	}

	@Override
	public void writeRecord(Record record) {
	    try {
	    	verifyFileExists();
	    	Gson gson = new Gson();
	    	FileWriter writer = new FileWriter(file, true);
	    	synchronized (file) {
	    		// Java object to JSON, and save into a file
	    		gson.toJson(record, writer);
	    		writer.flush(); //flush data to file
	    		writer.close(); //close write  
	    	}
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
		
	}

	private void verifyFileExists() throws IOException {
		if(!file.exists()) {
			file.createNewFile();
		}
	}
	
}
