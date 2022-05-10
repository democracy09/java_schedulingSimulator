package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import util.ReAlert;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable{

    //combo box에서 알고리즘을 표시하기 위한 ObservableList
    ObservableList<String> algorithms = FXCollections.observableArrayList("FCFS", "SJF", "HRN", "NonPrePriority",
            "PrePriority","RR", "SRT");

    //FXML 파일과 연결하기 위한 변수
    //콤보박스
    @FXML
    private ComboBox<String> algorithmBox;
    //프로세스 개수를 입력받기 위한 text field
    @FXML
    private TextField processCount;
    //프로세스 정보들을 입력받기 위한 화면으로 넘어가는 버튼
    @FXML
    private Button next;


    //화면 초기화 메소드
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithmBox.setItems(algorithms);      //콤보박스에서의 알고리즘 이름들 설정
        processCount.setPromptText("최소 3개");        //프로세스 개수를 최소 3개라고 알려주기 위한 프롬프트 설정
    }


    //"다음" 버튼을 눌렀을 때의 onPress 메소드
    public void pressNext() throws IOException {
        ReAlert alert = new ReAlert();      //경고를 표시하기위해 재설정한 ReAlert 객체
        int count;      //입력받은 프로세스 개수를 저장하는 변수

        //만약 프로세스 개수의 text field가 비었을 경우 경고 발생
        if(!processCount.getText().isBlank()){
            count = Integer.parseInt(processCount.getText());       //text field가 비지 않았을 경우 count에 값을 저장
        }else{
            alert.setAlert(Alert.AlertType.WARNING, "프로세스 개수를 입력해주세요");     //warning 타입으로 경고 발생
            alert.showAlert();
            return;     //메소드 종료
        }

        //콤보 박스에서 값을 선택하지 않았을 경우 경고 발생
        if(algorithmBox.getValue()==null){
            alert.setAlert(Alert.AlertType.WARNING, "알고리즘 종류를 선택해주세요.");     //warning 타입으로 경고 발생
            alert.showAlert();
            return;     //메소드 종료
        }
        //프로세스가 최소 3개 미만일 경우 경고
        if(count<3){
            alert.setAlert(Alert.AlertType.WARNING, "프로세스 개수는 최소 3개 입력해주세요.");      //warning 타입으로 경고 발생
            alert.showAlert();
            processCount.clear();       //프로세스 개수 text field 초기화
            return;     //메소드 종료
        }

        //다음 화면 fxml 파일을 로드하는 부분
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/InputPage.fxml"));
        //다음 화면의 따로 만들어진 label의 namespace를 통해 매개변수 전달
        fxmlLoader.getNamespace().put("algorithm", (String) algorithmBox.getValue());
        fxmlLoader.getNamespace().put("process", count);
        Parent input = fxmlLoader.load();
        Scene scene = new Scene(input);
        Stage stage = (Stage) next.getScene().getWindow();      //새로운 window 불러옴
        stage.setScene(scene);      //새로운 scene으로 화면 변경
    }
}
