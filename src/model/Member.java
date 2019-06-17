package model;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Member { // DB의 레코드와 매핑되는 객체
	private StringProperty email; // DB의 필드와 매핑
	private StringProperty name;
	private StringProperty birth;
	private StringProperty age;
	private StringProperty sex;
	private StringProperty address;
	private StringProperty areaCode;
	private StringProperty contact;
	/*
    private final IntegerProperty zipcode;
    private ObjectProperty<LocalDate> birthday;
	*/
	public Member() {
		this(null, null, null, null, null, null, null, null);
	}
	
	public Member(String email, String name, String birth, String age, String sex, String address, String areaCode, String contact) {
		this.email = new SimpleStringProperty(email);
		this.name = new SimpleStringProperty(name);
		this.birth = new SimpleStringProperty(birth);
		this.age = new SimpleStringProperty(age);
		this.sex = new SimpleStringProperty(sex);
		this.address = new SimpleStringProperty(address);
		this.areaCode = new SimpleStringProperty(areaCode);
		this.contact = new SimpleStringProperty(contact);
	}
	
	public String getEmail() {
		return this.email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);
	}
    public StringProperty emailProperty() {
        return email;
    }
    
    public String getName() {
    	return this.name.get();
    }
    public void setName(String name) {
    	this.name.set(name);
    }
    public StringProperty nameProperty() {
        return name;
    }
    
	public String getBirth() {
		return this.birth.get();
	}
	public void setBirth(String birth) {
		this.birth.set(birth);
	}
    public StringProperty birthProperty() {
        return birth;
    }
    
    public String getAge() {
		return this.age.get();
	}
	public void setAge(String age) {
		this.age.set(age);
	}
    public StringProperty ageProperty() {
        return age;
    }
    
    public String getSex() {
		return this.sex.get();
	}
	public void setSex(String sex) {
		this.sex.set(sex);
	}
    public StringProperty sexProperty() {
        return sex;
    }
    
    public String getAddress() {
		return this.address.get();
	}
	public void setAddress(String address) {
		this.address.set(address);
	}
    public StringProperty addressProperty() {
        return address;
    }
    
    public String getAreaCode() {
		return this.areaCode.get();
	}
	public void setAreaCode(String areaCode) {
		this.areaCode.set(areaCode);
	}
    public StringProperty areaCodeProperty() {
        return areaCode;
    }
    
    public String getContact() {
		return this.contact.get();
	}
	public void setContact(String contact) {
		this.contact.set(contact);
	}
    public StringProperty contactProperty() {
        return contact;
    }
}
