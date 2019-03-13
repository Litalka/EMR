package com.emr.models;

import com.emr.models.CommonFactory.eDiagnosis;
import com.emr.models.CommonFactory.eGender;
import com.emr.models.CommonFactory.eTrauma;

public class Logic {
	private static Logic logic = new Logic();
	private RecordsRepository recordsRepository = RecordsRepository.getInstance(); 
	
	private Logic() { 
	}
	
	public static Logic getInstance() {
		return logic;
	}

	public Record addRecord(float age, float blood_count, String gender, String trauma) {
		Record record = new Record(age, blood_count, gender, trauma);
		recordsRepository.addRecord(record);
		
		return record;
	}

	public boolean getPatientsDiagnosis(int record_id, String type) {
		Record record = recordsRepository.getRecord(record_id);
		IDiagnosis diagnosis = CommonFactory.getInstance().getDiagnosisByType(type);
		return diagnosis.run(record);
	}

	public boolean parameterValidation(float age, float blood_count, String gender, String trauma, StringBuilder errorMsg) {
		if (age <=0 ) {
			errorMsg.append("age must be bigger than 0");
			return false;
		}
		if(blood_count < 3.5 || blood_count > 6) {
			errorMsg.append("blood_count must be number between 3.5 and 6");
			return false;
		}
		try {
			eGender.valueOf(gender.toUpperCase());
		}catch(Exception ex) {
			errorMsg.append("gender must be Male or Female");
			return false;
		}
		try {
			eTrauma.valueOf(trauma.toUpperCase());
		}catch(Exception ex) {
			errorMsg.append("trauma is not valid");
			return false;
		}
		
		return true;
	}

	public boolean parameterValidationPatientsDiagnosis(int record_id, String type, StringBuilder errorMessage) {
		if(record_id < 0 && !recordsRepository.contain(record_id)) {
			errorMessage.append("record_id is not valid");
			return false;
		}
		
		try {
			eDiagnosis.valueOf(type.toUpperCase());
		}catch(Exception ex) {
			errorMessage.append("Diagnosis type is not valid");
			return false;
		}

		return true;
	}
}
