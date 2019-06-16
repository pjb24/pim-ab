package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import model.Member;
import model.MemberFileWriter;

public class MemberDAO {
	private ArrayList<Member> memberList = null;
	
	private File file = null;
	private MemberFileReader fr = null;
	private MemberFileWriter fw = null;
	
	public MemberDAO(File file) {	
		this.file = file;
		try {
			fr = new MemberFileReader(file);
			memberList = fr.readMember();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<Member> selectAll() { 
		// 입력한 메모리 상에 존재하는 모든 멤버 정보를 가져와 출력			
		return memberList;	
	}
	
	public Member selectMember(Member member) {
		int index = -1;
		if((index = searchByEmail(member)) >= 0)
			return memberList.get(index);
		else
			return null;
	}
	
	public int searchByEmail(Member member) { 
		int ret = -1; // ret가 0 이상이면 검색 성공, -1 이면 검색 실패
		int index = 0;
		for(Member m : memberList) {
			if(m.getEmail().equals(member.getEmail())) {
				ret = index;
				break;
			}
			index++;
		}				
		return ret;
	}
	
	public int insert(Member member) {
		int ret = -1;
		try {
			int index = searchByEmail(member);
			if(index < 0) { // -1이면 검색 실패, 등록 가능함
				fw = new MemberFileWriter(file);
				calculateAge(member);
				memberList.add(member);
				fw.saveMember(memberList);
				ret = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;
	}
	
	public int update(Member member) {
		int ret = -1; // 0 이상이면 해당 아이디가 존재하므로 수정, -1이하이면 수정 실패		
		try {
			int index = searchByEmail(member);
			if(index >= 0) { // -1이면 검색 실패, 수정 불가능
				fw = new MemberFileWriter(file);
				memberList.set(index, member);
				calculateAge(member);
				fw.saveMember(memberList);
				ret = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;
	}	
	
	public int delete(Member member) {		
		int ret = -1; // 0 이상이면 해당 아이디가 존재하므로 삭제, -1이하이면 삭제 실패
		try {
			int index = searchByEmail(member);
			if(index >= 0) { // -1이면 검색 실패, 수정 불가능
				fw = new MemberFileWriter(file);
				memberList.remove(member);
				fw.saveMember(memberList);
				ret = 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return ret;
	}
	
	public List<Member> searchByAddress(String address) {
		// 검색 결과를 저장할 ArrayList 형 객체 생성
		List<Member> searched = new ArrayList<Member>();
		for (Member m : memberList) {
			if (m.getAddress().equals(address)) {
				searched.add(m);	// 검색된 정보를 추가함
			}
			// 검색이 안된 경우 스킵
		}
		return searched;
	}
	
	public List<Member> searchByName(String name) {
		// 검색 결과를 저장할 ArrayList 형 객체 생성
		List<Member> searched = new ArrayList<Member>();
		for (Member m : memberList) {
			if (m.getName().equals(name)) {
				searched.add(m);	// 검색된 정보를 추가함
			}
			// 검색이 안된 경우 스킵
		}
		return searched;
	}
	
	public void calculateAge(Member member) {
		String birth = member.getBirth();
		Calendar cal = Calendar.getInstance();
		int year = cal.get(cal.YEAR) - Integer.parseInt(birth.substring(0, 4));
		String age = Integer.toString(year);
		member.setAge(age);
	}
	
	public void printMemberList() {
		for(Member m : memberList)
			System.out.println(m.getName() + ":" + m.getEmail());
	}
}
