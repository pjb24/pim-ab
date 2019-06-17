package view;

import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import controller.MemberService;
import controller.MemberServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.Member;

public class MemberViewController implements Initializable {
	@FXML	private TextField tfEmail;
	@FXML	private TextField tfName;
	@FXML	private TextField tfBirth;
	@FXML	private TextField tfAddress;
	@FXML	private TextField tfContact;
	
	@FXML	private Button btnCreate;
	@FXML	private Button btnUpdate;
	@FXML	private Button btnDelete;

	@FXML 	private TableView<Member> tableViewMember;
	@FXML	private TableColumn<Member, String> columnEmail;
	@FXML	private TableColumn<Member, String> columnName;
	@FXML	private TableColumn<Member, String> columnBirth;
	@FXML	private TableColumn<Member, String> columnAge;
	@FXML	private TableColumn<Member, String> columnSex;
	@FXML	private TableColumn<Member, String> columnAddress;
	@FXML	private TableColumn<Member, String> columnAreaCode;
	@FXML	private TableColumn<Member, String> columnContact;

	@FXML	private Button btnFindByAddress;
	@FXML	private Button btnFindBySex;
	@FXML	private Button btnFindByAge;
	@FXML	private TextArea taFindResult;
	@FXML	private TextField tfFindCondition;
	
	// Member : model이라고도 하고 DTO, VO 라고도 함
	// 시스템 밖에 저장된 정보를 객체들간에 사용하는 정보로 변환한 자료구조 또는 객체
	private final ObservableList<Member> data = FXCollections.observableArrayList();
	// 목록 : 이중연결리스트는 아니지만 리스트의 특징과 배열 특징을 잘 혼용해 놓은 클래스 ArrayList 
	ArrayList<Member> memberList;
	MemberService memberService;
	
	public MemberViewController() {
		
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		memberService = new MemberServiceImpl();
		// 람다식 : java 8  함수형 언어 지원 
				
		columnEmail.setCellValueFactory(cvf -> cvf.getValue().emailProperty());
		columnName.setCellValueFactory(cvf -> cvf.getValue().nameProperty());
		columnBirth.setCellValueFactory(cvf -> cvf.getValue().birthProperty());
		columnAge.setCellValueFactory(cvf -> cvf.getValue().ageProperty());
		columnSex.setCellValueFactory(cvf -> cvf.getValue().sexProperty());
		columnAddress.setCellValueFactory(cvf -> cvf.getValue().addressProperty());
		columnAreaCode.setCellValueFactory(cvf -> cvf.getValue().areaCodeProperty());
		columnContact.setCellValueFactory(cvf -> cvf.getValue().contactProperty());
		
		tableViewMember.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> showMemberInfo(newValue));

		btnCreate.setOnMouseClicked(event -> handleCreate());		
		btnUpdate.setOnMouseClicked(event -> handleUpdate());
		btnDelete.setOnMouseClicked(event -> handleDelete());
		
		btnFindByAddress.setOnMouseClicked(event -> handleFindByAddress());
		btnFindBySex.setOnMouseClicked(event -> handleFindBySex());
		btnFindByAge.setOnMouseClicked(event -> handleFindByAge());
		
		loadMemberTableView();
	}
	/*
	String str = ""; // 인스턴스 변수 - 객체 변수, 객체가 존재하는 동안 메모리에 존재
	@FXML 
	private void handleExecute() { // event source, listener, handler
		str = str + tfExecute.getText() + "\n";
		//str = ts.setTextArea(tfExecute.getText());
		/*
		str = taExecute.getText();
		String name = tfExecute.getText();
		str = str + ts.appendTextArea(name);
		*//*
		taExecute.setText(str);
	}
	*/
	
	private void showMemberInfo(Member member) {
		if (member != null) {
			tfEmail.setText(member.getEmail());
			tfName.setText(member.getName());
			tfBirth.setText(member.getBirth());
			tfAddress.setText(member.getAddress());
			tfContact.setText(member.getContact());
		}
		 else {
			 tfEmail.setText("");
		     tfName.setText("");
		     tfBirth.setText("");
		     tfAddress.setText("");
		     tfContact.setText("");
		 }
	}
	
	private void loadMemberTableView() {
		memberList = memberService.readList();
		for(Member m : memberList) {
			data.add(m);
		}
		tableViewMember.setItems(data);
	}
	
	
	@FXML 
	private void handleCreate() { // event source, listener, handler
		if(tfEmail.getText().length() > 0) {
			Member newMember = 
					new Member(tfEmail.getText(), tfName.getText(), tfBirth.getText(), "", "", tfAddress.getText(), "", tfContact.getText());
			if (memberService.findByEmail(newMember) < 0) {
				data.add(newMember);
				tableViewMember.setItems(data);
				memberService.create(newMember);
			}
			else {
				showAlert("이메일을 중복으로 등록할 수 없습니다.");
			}
		} else
			showAlert("이메일 입력오류");
	}
	@FXML 
	private void handleUpdate() {
		Member newMember = new Member(tfEmail.getText(), tfName.getText(), tfBirth.getText(), "", "", tfAddress.getText(), "", tfContact.getText());

		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			tableViewMember.getItems().set(selectedIndex, newMember);
			memberService.update(newMember);			
		} else {
			showAlert("수정 실패.");          
        }
	}
	
	@FXML 
	private void handleDelete() {
		int selectedIndex = tableViewMember.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			memberService.delete(tableViewMember.getItems().remove(selectedIndex));			
		} else {
			showAlert("삭제 실패.");
        }
	}
	
	@FXML 
	private void handleFindByAddress() {
		String condition = tfFindCondition.getText();
		taFindResult.setText("");
		if (condition.length() >= 0) {
			List<Member> searched = memberService.findByAddress(condition);
			if (searched.size() > 0) {
				int no = 1;
				for (Member m : searched) {
					taFindResult.appendText(no++ + " ) " + m.getAddress() + " : " + m.getEmail() + " : " + m.getName() + " \n");
				}
			}
			else
				taFindResult.setText("검색 조건에 맞는 정보가 없습니다.");
		} else {
			showAlert("검색 조건을 입력하세요.");
        }
	}
	
	@FXML 
	private void handleFindBySex() {
		String condition = tfFindCondition.getText();
		taFindResult.setText("");
		if (condition.length() >= 0) {
			List<Member> searched = memberService.findBySex(condition);
			if (searched.size() > 0) {
				int no = 1;
				for (Member m : searched) {
					taFindResult.appendText(no++ + " ) " + m.getSex() + " : " + m.getEmail() + " : " + m.getName() + " \n");
				}
			}
			else
				taFindResult.setText("검색 조건에 맞는 정보가 없습니다.");
		} else {
			showAlert("검색 조건을 입력하세요.");
        }
	}
	
	@FXML 
	private void handleFindByAge() {
		String condition = tfFindCondition.getText();
		taFindResult.setText("");
		if (condition.length() >= 0) {
			List<Member> searched = memberService.findByAge(condition);
			if (searched.size() > 0) {
				int no = 1;
				for (Member m : searched) {
					taFindResult.appendText(no++ + " ) " + m.getAge() + " : " + m.getEmail() + " : " + m.getName() + " \n");
				}
			}
			else
				taFindResult.setText("검색 조건에 맞는 정보가 없습니다.");
		} else {
			showAlert("검색 조건을 입력하세요.");
        }
	}
	
	private void showAlert(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainApp.getRootStage());
        alert.setTitle("알림");
        alert.setContentText("확인 : " + message);            
        alert.showAndWait();
	}

	private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

}
