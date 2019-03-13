package com.emr.models;

import com.emr.models.CommonFactory.eGender;

public class BloodCountDiagnosis implements IDiagnosis{
	public static final float BLOOD_RANGE_MALE_MIN = 4.35f;
	public static final float BLOOD_RANGE_MALE_MAX = 5.65f;
	public static final float BLOOD_RANGE_FEMALE_MIN = 3.92f;
	public static final float BLOOD_RANGE_FEMALE_MAX = 5.13f;
	
	public static final String DIAGNOSIS_TRAUMAS = "traumas";
	
	@Override
	public boolean run(Record record) {
		// returns whether the blood count is in good range: 
		// Male – 4.35 to 5.65,
		// Female – 3.92-5.13.
		boolean isGoodRange = false;
		eGender gender = record.getGender();
		float bloodCount = record.getBlood_count();
		
		switch(gender) {
		case MALE:
			isGoodRange = (bloodCount >= BLOOD_RANGE_MALE_MIN && bloodCount <= BLOOD_RANGE_MALE_MAX);
			break;
		case FEMALE:
			isGoodRange = (bloodCount >= BLOOD_RANGE_FEMALE_MIN && bloodCount <= BLOOD_RANGE_FEMALE_MAX);
			break;
		default:
			// error
			System.out.println("ERROR in BloodCountDiagnosis - unknown gender = " + gender);
			break;
				
		}
		
		return isGoodRange;
	}

}
