package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MemberFileWriter {
	BufferedWriter bw = null;
	FileWriter fw = null;
	public MemberFileWriter(File f) throws IOException {
		fw = new FileWriter(f);
	}
	public void saveMember(ArrayList<Member> memberList) {
		for(Member m : memberList) {
			try {
				fw.write(m.getEmail() + "\t");
				fw.write(m.getPw() + "\t");
				fw.write(m.getName() + "\t");
				fw.write(m.getBirth() + "\t");
				fw.write(m.getAge() + "\t");
				fw.write(m.getAddress() + "\t");
				fw.write(m.getContact() + "\n");
				fw.flush();
			} catch (IOException e) {
			}			
		}
	}
}
