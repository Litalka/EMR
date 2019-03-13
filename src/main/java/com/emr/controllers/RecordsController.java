package com.emr.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.emr.models.Logic;
import com.emr.models.Record;


@RestController
public class RecordsController {
	Logic logic = Logic.getInstance();
	
	@RequestMapping(value = RecordsRestURIConstants.ADD_RECORD, method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Object> addRecord(
    		@RequestParam(RestControllerKeys.AGE) float age,
    		@RequestParam(RestControllerKeys.BLOOD_COUNT) float blood_count,
    		@RequestParam(RestControllerKeys.GENDER) String gender,
    		@RequestParam(value=RestControllerKeys.TRAUMA, defaultValue="None") String trauma
    		) {
		
		StringBuilder errorMessage = new StringBuilder();
		if(!logic.parameterValidation(age, blood_count, gender, trauma, errorMessage)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
		}
		
		Record record = logic.addRecord(age, blood_count, gender, trauma);
		
		return ResponseEntity.ok(record);
    }

	
	@RequestMapping(value = RecordsRestURIConstants.GET_PATIENTS_DIAGNOSIS, method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Object> getPatientsDiagnosis(
    		@PathVariable("id") int record_id,
    		@PathVariable("type") String type
    		) {
		
		StringBuilder errorMessage = new StringBuilder();
		if(!logic.parameterValidationPatientsDiagnosis(record_id, type, errorMessage)){
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
		}
		
		boolean result = logic.getPatientsDiagnosis(record_id, type);
		
		return ResponseEntity.ok(result);
    }
}
