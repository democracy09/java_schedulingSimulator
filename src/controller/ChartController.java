package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import structure.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChartController implements Initializable {

    //fxml 파일과 연결하기 위한 변수
    @FXML
    public Button prev;        //이전 화면으로 돌아가기 위한 버튼
    @FXML
    public NumberAxis xAxis;       //차트에서의 x축
    @FXML
    public CategoryAxis yAxis;     //차트에서의 y축
    @FXML
    public StackedBarChart chart;      //간트차트를 표현하기 위한 StackedBarChart
    @FXML
    public TableView<ProcessProperty> processTable;     //스케줄링 결과를 프로세스 별로 표시하기위한 테이블뷰
    @FXML
    public TableColumn<ProcessProperty, String> proID;      //스케줄링 결과 테이블뷰에서 프로세스id를 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Double> waitTime;       //스케줄링 결과 테이블뷰에서 대기시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Double> turnATime;      //스케줄링 결과 테이블뷰에서 반환시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Double> responseTime;       //스케줄링 결과 테이블뷰에서 응답시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Integer> arrTime;       //스케줄링 결과 테이블뷰에서 도착시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Integer> serTime;       //스케줄링 결과 테이블뷰에서 서비스타임을 표시하는 테이블칼럼
    @FXML
    public TableColumn<ProcessProperty, Integer> pri;       //스케줄링 결과 테이블뷰에서 우선순위를 표시하는 테이블 칼럼
    @FXML
    public TableView<AvgProperty> avgTable;     //평균 시간을 표시하기 위한 테이블 뷰
    @FXML
    public TableColumn<AvgProperty, Double> avgWaitTime;        //평균 시간 테이블뷰에서 평균대기시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<AvgProperty, Double> avgResponseTime;        //평균 시간 테이블뷰에서 평균응답시간을 표시하는 테이블칼럼
    @FXML
    public TableColumn<AvgProperty, Double> avgTurnATime;       //평균 시간 테이블뷰에서 평균반환시간을 표시하는 테이블칼럼

    //화면 초기화 메소드
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //스케줄링 결과 테이블뷰의 테이블 칼럼마다 변수을 지정하는 부분
        proID.setCellValueFactory(new PropertyValueFactory<>("processID"));
        waitTime.setCellValueFactory(new PropertyValueFactory<>("waitTime"));
        responseTime.setCellValueFactory(new PropertyValueFactory<>("responseTime"));
        turnATime.setCellValueFactory(new PropertyValueFactory<>("turnATime"));
        arrTime.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
        serTime.setCellValueFactory(new PropertyValueFactory<>("burstTime"));
        pri.setCellValueFactory(new PropertyValueFactory<>("priority"));

        //평균 시간 테이블뷰의 테이블 칼럼마다 변수을 지정하는 부분
        avgWaitTime.setCellValueFactory(new PropertyValueFactory<>("avgWaitTime"));
        avgResponseTime.setCellValueFactory(new PropertyValueFactory<>("avgResponseTime"));
        avgTurnATime.setCellValueFactory(new PropertyValueFactory<>("avgTurnATime"));

    }

    //이전 InputController에서 스케줄링 결과를 받아 차트와 표에 입력하는 메소드
    public void initData(ObservableList<ProcessProperty> list, Algorithm schedule){
        processTable.setItems(list);        //전달받은 결과 리스트를 테이블뷰에 저장
        //평균 시간 테이블뷰에 표시하기 위한 ObservableList 생성
        ObservableList<AvgProperty> avgList = FXCollections.observableArrayList();
        //전달받은 알고리즘 객체에서 평균 시간들로 프로퍼티 생성
        AvgProperty property = new AvgProperty(schedule.avgWaitTime, schedule.avgResponseTime, schedule.avgTurnATime);
        avgList.add(property);      //리스트에 프로퍼티 추가
        avgTable.setItems(avgList);     //리스트를 평균 시간 테이블뷰에 저장

        //차트로 스케줄링 결과를 표현하기 위한 부분
        list.forEach(process -> {       //전달받은 결과 리스트를 스트림으로 각각 하나씩
            XYChart.Series series = new XYChart.Series();       //새로운 series 객체 생성
            series.setName(process.getProcessID());     //series 객체 이름을 프로세스id로 설정
            //프로세스가 저장된 순서대로 서비스시간을 시리즈에 저장
            series.getData().add(new XYChart.Data(process.getBurstTime(), " "));        //y축은 하나이므로 빈 문자열로 지정
            chart.getData().add(series);        //차트객체에 해당 series 객체 저장
        });
    }

    //"이전" 버튼을 눌렀을 때 onPress 메소드
    public void pressPrev() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainPage.fxml"));
        Parent main = fxmlLoader.load();
        Scene scene = new Scene(main);
        Stage stage = (Stage) prev.getScene().getWindow();      //새로운 window 불러옴
        stage.setScene(scene);      //새로운 scene으로 화면 변경
    }


}
