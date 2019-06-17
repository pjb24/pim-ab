package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.beans.property.SimpleStringProperty;

public class MemberFileReader {
	Scanner sc = null;
	
	public MemberFileReader(File f) throws FileNotFoundException {
		sc = new Scanner(f);
	}
	public ArrayList<Member> readMember() {
		ArrayList<Member> retObj = new ArrayList<Member>();
		while(sc.hasNext()) {
			Member m = new Member();
			String strArr[] = sc.nextLine().split("\t");
			m.setEmail(strArr[0]);
			m.setName(strArr[1]);
			m.setBirth(strArr[2]);
			m.setAge(strArr[3]);
			m.setSex(strArr[4]);
			m.setAddress(strArr[5]);
			m.setAreaCode(strArr[6]);
			m.setContact(strArr[7]);
			retObj.add(m);			
		}
		return retObj;
	}
}