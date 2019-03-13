package com.emr.models;

import com.emr.models.CommonFactory.eTrauma;

public class TraumasDiagnosis implements IDiagnosis{

	@Override
	public boolean run(Record record) {
		return !record.getTrauma().equals(eTrauma.NONE);
	}

}
