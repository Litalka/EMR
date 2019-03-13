package com.emr.models;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import com.emr.models.CommonFactory.eTrauma;

public class HighestRiskDiagnosis implements IDiagnosis{

	@Override
	public boolean run(Record record) {
		// returns whether the patient should be treated first, 
		// the oldest patient 
		// with the most traumas should be treated first.
		RecordsRepository recordsRepository = RecordsRepository.getInstance();
		Collection<Record> records = recordsRepository.getRecords();
		Record oldestPatient = records.stream().max(Comparator.comparing(Record::getAge)).get();
		float patientAge = record.getAge();
		// check age
		if(oldestPatient.getAge() > patientAge) {
			return false;
		}
		
		if(record.getTrauma().equals(eTrauma.NONE)) {
			// get all records where age is equal to patientAge 
			// and has trauma
			List<Record> oldestPatientsWithTrauma = records.stream()
					.filter(x -> x.getAge() == oldestPatient.getAge())
					.filter(r -> !(r.getTrauma().equals(eTrauma.NONE)))
					.collect(Collectors.toList());
			// there is patient with the same age and has trauma
			if(!oldestPatientsWithTrauma.isEmpty()) {
				return false;
			}
		}
		
		// patient is the oldest and he has trauma
		return true;
	}
}
