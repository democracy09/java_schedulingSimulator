package structure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Optional;

//선점형과 비선점형 알고리즘의 공통된 부분을 선언한 알고리즘 클래스
public class Algorithm {
    public double avgWaitTime;      //평균대기시간
    public double avgResponseTime;      //평균응답시간
    public double avgTurnATime;     //평균반환시간
    public ObservableList<ProcessProperty> resultList;      //반환할 결과리스트
    public double count;        //입력된 프로세스 개수

    //프로세스마다 대기시간, 응답시간, 반환시간을 계산하는 메소드
    public ObservableList<ProcessProperty> getProcessTime(ObservableList<ProcessProperty> list) {
        //대기시간, 응답시간, 반환시간을 포함한 프로세스 정보를 저장할 ObservableList
        ObservableList<ProcessProperty> resultList = FXCollections.observableArrayList();

        //첫번째 프로세스의 시간들을 계산하기 위해 리스트의 첫번재 아이템을 꺼내 저장
        ProcessProperty first = new ProcessProperty(list.stream().findFirst().get());
        //처음 도착한 프로세스는 별도로 계산
        list.stream().findFirst().get().setTurnATime(first.getBurstTime());     //반환시간 = 서비스시간
        list.stream().findFirst().get().setResponseTime(first.getArrivalTime());        //응답시간 = 도착시간
        list.stream().findFirst().get().setWaitTime(first.getArrivalTime());        //대기시간 = 도착시간
        resultList.add(list.stream().findFirst().get());        //계산된 시간들을 포함한 프로세스를 결과 리스트에 추가

        //스트림의 reduce연산을 이용하여 두번째 프로세스부터 시간 계산
        Optional<ProcessProperty> result = list.stream().reduce((p1, p2) -> {
            //대기시간 = (전 프로세스의 도착시간 + 전 프로세스의 반환시간) - 해당 프로세스의 도착시간
            p2.setWaitTime((p1.getArrivalTime() + p1.getTurnATime()) - p2.getArrivalTime());
            //응답시간 = (전 프로세스의 도착시간 + 전 프로세스의 반환시간) - 해당 프로세스의 도착시간
            p2.setResponseTime((p1.getArrivalTime() + p1.getTurnATime()) - p2.getArrivalTime());
            //반환시간 = 해당 프로세스의 대기시간 + 해당 프로세스의 서비스시간
            p2.setTurnATime(p2.getWaitTime() + p2.getBurstTime());
            resultList.add(p2);     //계산된 시간들을 포함한 프로세스를 결과 리스트에 추가

            return p2;      //계산된 프로세스를 반환하여 그 다음 프로세스와 연산
        });

        return resultList;      //결과 리스트 반환
    }

    //평균시간을 계산하는 메소드
    public void getAvg(ObservableList<ProcessProperty> list) {
        //평균대기시간 = 전체 프로세스의 대기시간의 합 / 프로세스 개수
        avgWaitTime = list.stream().mapToDouble(ProcessProperty::getWaitTime).sum() / count;
        avgResponseTime = avgWaitTime;      //평균응답시간 = 평균대기시간
        //평균반환시간 = 전체 프로세스의 반환시간의 합 / 프로세스 개수
        avgTurnATime = list.stream().mapToDouble(ProcessProperty::getTurnATime).sum() / count;
    }
}
