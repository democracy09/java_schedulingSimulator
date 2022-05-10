package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;
import structure.NonPreAlgorithm;
import structure.PreAlgorithm;
import structure.ProcessProperty;
import util.ReAlert;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;


public class InputController implements Initializable {

    //fxml 파일과 연결하기 위한 변수
    @FXML
    public Button next;     //스케줄링 결과를 보여주는 화면으로 이동하는 버튼
    @FXML
    public Button prev;     //이전 메인화면으로 돌아가는 버튼
    @FXML
    public Button processAdd;       //프로세스를 추가하는 버튼
    @FXML
    public Button processDel;       //입력된 프로세스를 삭제하는 버튼
    @FXML
    public TextField processID;     //프로세스id를 입력하는 텍스트필드
    @FXML
    public TextField arrivalTime;       //도착시간을 입력하는 텍스트필드
    @FXML
    public TextField burstTime;     //서비스시간을 입력하는 텍스트필드
    @FXML
    public TextField priority;      //우선순위를 입력하는 텍스트필드
    @FXML
    public TextField timeQuantum;       //시간할당량을 입력하는 텍스트필드
    @FXML
    private TableView<ProcessProperty> processTable;        //입력된 프로세스들을 표로 표시하는 테이블뷰
    @FXML
    public TableColumn<ProcessProperty, String> proID;      //테이블뷰에서 프로세스id를 표시하는 테이블컬럼
    @FXML
    public TableColumn<ProcessProperty, Integer> arrTime;       //테이블뷰에서 도착시간을 표시하는 테이블컬럼
    @FXML
    public TableColumn<ProcessProperty, Integer> serTime;       //테이블뷰에서 서비스시간을 표시하는 테이블컬럼
    @FXML
    public TableColumn<ProcessProperty, Integer> pri;       //테이블뷰에서 우선순위를 표시하는 테이블 컬럼
    @FXML
    private Label algorithmName;        //전 화면에서 전달받은 알고리즘 종류를 받아 표시할 label
    @FXML
    private Label countLabel;       //전 화면에서 전달받은 프로세스 개수를 받을 label

    //입력받은 프로세스 정보를 저장하여 테이블뷰에 표시하기 위한 ObservableList
    public ObservableList<ProcessProperty> list = FXCollections.observableArrayList();
    String algorithm;       //전 화면에서 전달받은 알고리즘 종류를 저장할 변수
    int count;      //전 화면에서 전달받은 프로세스 개수를 저장할 변수
    ReAlert alert = new ReAlert();      //새로 설정한 경고 객체

    //화면 초기화 메소드
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        algorithm = algorithmName.getText();        //label로 받은 매개변수를 algorithm 변수에 저장
        count = Integer.parseInt(countLabel.getText());     //label로 받은 매개변수를 count 변수에 저장

        //화면에 알고리즘 종류를 표시하기 위한 부분
        if(algorithm.equals("NonPrePriority")){
            algorithmName.setText("비선점형 우선순위 알고리즘");        //한글로 바꾸어 표시
        }else if(algorithm.equals("PrePriority")){
            algorithmName.setText("선점형 우선순위 알고리즘");        //한글로 바꾸어 표시
        }else {
            algorithmName.setText(algorithm + " 알고리즘");
        }

        //텍스트 필드에 프롬프트를 설정하는 부분
        processID.setPromptText("프로세스ID");
        arrivalTime.setPromptText("도착시간");
        burstTime.setPromptText("서비스시간");
        priority.setPromptText("우선순위");
        timeQuantum.setPromptText("시간 할당량");

        //테이블 컬럼마다 값을 지정해주는 부분
        proID.setCellValueFactory(new PropertyValueFactory<>("processID"));
        arrTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        serTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        pri.setCellValueFactory(new PropertyValueFactory<>("priority"));
    }

    //"추가" 버튼을 눌렀을 때의 onPress 메소드
    public void pressAdd(){
        //입력받은 프로세스의 개수가 이전화면에서 설정한 프로세스의 개수보다 클 경우 경고 발생
        if(list.size() >= count){
            alert.setAlert(Alert.AlertType.WARNING, "프로세스가 모두 입력되었습니다.");       //warning 타입으로 경고 발생
            alert.showAlert();
            return;     //메소스 종료
        }

        //입력받은 프로세스 정보를 ObservableList에 저장하는 코드
        try {       //텍스트 필드에 값이 없을 경우 예외 처리
            ProcessProperty property = new ProcessProperty();       //프로세스 객 생성
            //알고리즘 종류가 우선순위 알고리즘일 경우 알고리즘을 추가로 입력받는 부분
            if (algorithm.equals("NonPrePriority") || algorithm.equals("PrePriority")) {
                property.setProcessID(processID.getText());
                property.setArrivalTime(Integer.parseInt(arrivalTime.getText()));
                property.setBurstTime(Integer.parseInt(burstTime.getText()));
                property.setPriority(Integer.parseInt(priority.getText()));
            } else{     //나머지 알고리즘 종류일 경우 프로세스id와 도착시간, 서비스시간만 입력받음
                property.setProcessID(processID.getText());
                property.setArrivalTime(Integer.parseInt(arrivalTime.getText()));
                property.setBurstTime(Integer.parseInt(burstTime.getText()));
            }
            list.add(property);     //입력받은 프로세스를 list에 추가
            processTable.setItems(list);        //테이블뷰를 새로운 list로 업데이트
        }catch (NumberFormatException e) {      //정보를 입력하지 않았거나 설정한 타입과 다른 값을 입력했을 경우 경고 발생
            alert.setAlert(Alert.AlertType.WARNING, "프로세스 정보를 입력해주세요.");
            alert.showAlert();
        }finally{       //모든 텍스트 필드를 초기화
            processID.clear();
            arrivalTime.clear();
            burstTime.clear();
            priority.clear();
        }
    }

    //"삭제" 버튼을 눌렀을 때의 onPress 메소드
    public void pressDel(){
        ObservableList<ProcessProperty> processSelected;        //선택된 프로세스를 저장하기 위한 ObservableList
        processSelected = processTable.getSelectionModel().getSelectedItems();      //선택된 프로세스로 새로운 list 업데이트
        try{        //프로세스를 선택하지 않고 삭제버튼을 눌렀을 경우 예외처리
            processSelected.forEach(list::remove);      //선택된 모든 프로세스를 삭제
        }catch(Exception e){        //모든 예외에 대해 처리
            alert.setAlert(Alert.AlertType.WARNING, "프로세스를 선택해주세요");        //warning 타입으로 경고 발생
            alert.showAlert();
        }
    }

    //"스케줄링" 버튼을 눌렀을 경우 onPress 메소드
    public void pressSchedule() throws IOException{
        NonPreAlgorithm nonPreSchedule = new NonPreAlgorithm(list);     //비선점형 알고리즘을 처리하기 위한 객체
        PreAlgorithm preSchedule = new PreAlgorithm(list);      //선점형 알고리즘을 처리하기 위한 객체
        //결과를 다음 화면에 전달하기 위한 ObservableList
        ObservableList<ProcessProperty> result = FXCollections.observableArrayList();
        try {       //선점형 알고리즘일 경우 시간 할당량을 입력하지 않았을 때 예외 처리
            preSchedule.timeQuantum = Integer.parseInt(timeQuantum.getText());      //시간 할당량 선점형 알고리즘 객체에 저장

            //알고리즘 별 스케줄링 실행
            if ("PrePriority".equals(algorithm)){
                result = preSchedule.Priority(list);
            }else if("RR".equals(algorithm)){
                result = preSchedule.RR(list);
            }else if( "SRT".equals(algorithm)) {
                result = preSchedule.SRT(list);
            } else if ("FCFS".equals(algorithm)) {
                result = nonPreSchedule.FCFS(list);
            } else if ("SJF".equals(algorithm)) {
                result = nonPreSchedule.SJF(list);
            } else if ("HRN".equals(algorithm)) {
                result = nonPreSchedule.HRN(list);
            } else if ("NonPrePriority".equals(algorithm)) {
                result = nonPreSchedule.Priority(list);
            }

            //선점형과 비선점형 각각 평균시간을 구하는 부분
            nonPreSchedule.getAvg(result);
            preSchedule.getAvg(result);

            //다음 화면 fxml 파일을 로드하는 부분
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ChartPage.fxml"));
            Parent main = fxmlLoader.load();
            Scene scene = new Scene(main);
            ChartController controller = fxmlLoader.getController();        //다음 화면의 컨트롤러를 로드
            //비선점형 알고리즘일 경우 NonPreSchedule과 result 변수 전달
            if(algorithm.equals("FCFS") || algorithm.equals("SJF")
                    || algorithm.equals("HRN") || algorithm.equals("nonPrePriority"))
                controller.initData(result, nonPreSchedule);
            else        //선점형 알고리즘일 경우 preSchedule과 result 변수 전달
                controller.initData(result, preSchedule);
            Stage stage = (Stage) next.getScene().getWindow();      //새로운 window 불러옴
            stage.setScene(scene);      //새로운 scene으로 화면 변경
        }catch(NumberFormatException e){        //시간할당량을 입력하지 않았을 경우 경고 발생
            alert.setAlert(Alert.AlertType.WARNING, "시간 할당량을 입력해주세요.");     //warning 타입으로 경고 발생
            alert.showAlert();
        }
    }

    //"이전" 버튼을 눌렀을 때 onPress 메소드
    public void pressPrev() throws IOException {
        //이전 화면 fxml 파일을 로드하는 부분
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
        Parent main = fxmlLoader.load();
        Scene scene = new Scene(main);
        Stage stage = (Stage) prev.getScene().getWindow();      //새로운 window 불러옴
        stage.setScene(scene);      //새로운 scene으로 화면 변경
    }
}
