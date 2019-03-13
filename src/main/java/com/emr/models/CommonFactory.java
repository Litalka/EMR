package com.emr.models;


public class CommonFactory {
	private static CommonFactory factory = new CommonFactory();
	enum eGender { MALE, FEMALE }
	enum eTrauma { CUT, BURN, CRASH, NONE }
	enum eDiagnosis { TRAUMAS, HEALTHY_BLOOD_COUNT, HIGHEST_RISK }
	
	public static CommonFactory getInstance() {
		return factory;
	}

	public IDiagnosis getDiagnosisByType(String type) {
		eDiagnosis diagnosisType = eDiagnosis.valueOf(type.toUpperCase());
		
		switch(diagnosisType) {
		case TRAUMAS:
			return new TraumasDiagnosis();
		case HEALTHY_BLOOD_COUNT:
			return new BloodCountDiagnosis();
		case HIGHEST_RISK:
			return new HighestRiskDiagnosis();
		}
		
		return null;
	}

	public IExternalStorage getExternalStorage(String externalStorageType) {
		switch(externalStorageType) {
		case(EmrConstants.EXTERNAL_STORAGE_TYPE):
			return new FileExternalStorage(EmrConstants.FILE_EXTERNAL_STORAGE_PATH);
		}
		
		return null;
	}
}
