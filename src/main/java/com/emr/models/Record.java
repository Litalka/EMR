package com.emr.models;

import java.util.concurrent.atomic.AtomicInteger;
import com.emr.models.CommonFactory.eGender;
import com.emr.models.CommonFactory.eTrauma;


public class Record {
	private static final AtomicInteger count = new AtomicInteger(0);
	private int id;
	private float age;
	private float blood_count;
	private eGender gender;
	private eTrauma trauma;
	
	public Record(float age, float blood_count, String gender, String trauma) {
		this.setId(count.incrementAndGet());
		this.setAge(age);
		this.setBlood_count(blood_count);
		try {
			this.setGender(eGender.valueOf(gender.toUpperCase()));
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.setTrauma(eTrauma.valueOf(trauma.toUpperCase()));
		}catch(Exception ex) {
			ex.printStackTrace();
			this.setTrauma(eTrauma.NONE);
		}
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getAge() {
		return age;
	}

	public void setAge(float age) {
		this.age = age;
	}

	public float getBlood_count() {
		return blood_count;
	}

	public void setBlood_count(float blood_count) {
		this.blood_count = blood_count;
	}

	public eGender getGender() {
		return gender;
	}

	public void setGender(eGender gender) {
		this.gender = gender;
	}

	public eTrauma getTrauma() {
		return trauma;
	}

	public void setTrauma(eTrauma trauma) {
		this.trauma = trauma;
	}

	public static void updateAutoIncrement(int index) {
		count.set(index);
	}
}
