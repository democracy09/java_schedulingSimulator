package structure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

//비선점형 알고리즘을 구현하기 위한 클래스
public class NonPreAlgorithm extends Algorithm{

    //생성자
    public NonPreAlgorithm(ObservableList<ProcessProperty> list) {
        resultList = FXCollections.observableArrayList();       //결과 리스트에 observableArrayList 객체생성
        count = list.size();        //받은 프로세스 리스트의 크기 저장
    }

    //FCFS 알고리즘
    public ObservableList<ProcessProperty> FCFS(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        resultList = getProcessTime(list);      //결과리스트에 시간들을 포함한 정렬된 리스트 저장
        return resultList;      //결과리스트 반환
    }

    //SJF 알고리즘
    public ObservableList<ProcessProperty> SJF(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        //첫번째 프로세스만 따로 저장
        ProcessProperty first = new ProcessProperty(list.stream().findFirst().get());
        list.remove(0);     //첫번째 프로세스 리스트에서 제거
        //나머지 프로세스들을 서비스시간으로 정렬
        list.sort(Comparator.comparingInt(ProcessProperty::getBurstTime));
        //빼놓은 첫번째 프로세스를 다시 맨 앞에 추가
        list.add(0, first);

        resultList = getProcessTime(list);      //결과리스트에 시간들을 포함한 정렬된 리스트 저장
        return resultList;      //결과리스트 반환
    }

    //HRN 알고리즘
    public ObservableList<ProcessProperty> HRN(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬

        while(list.size()!=0){      //리스트에 아이템이 없을때까지 반복
            //첫번째 프로세스만 따로 저장
            ProcessProperty first = new ProcessProperty(list.stream().findFirst().get());
            //HRN 알고리즘의 우선순위를 계산하기위해 반환시간과 대기시간 설정
            first.setTurnATime(first.getBurstTime());
            first.setWaitTime(first.getArrivalTime());
            list.remove(0);     //첫번째 프로세스를 리스트에서 제거
            resultList.add(first);      //결과리스트에 시간이 포함된 첫번째 프로세스 추가

            for(ProcessProperty p2 : list){     //리스트의 각 프로세스마다
                //따로 저장된 첫번째 프로세스의 반환시간까지의 대기시간계산
                p2.setWaitTime((first.getArrivalTime()+first.getTurnATime())-p2.getArrivalTime());
                //우선순위 = (대기시간 + 서비스시간) / 서비스시간
                p2.setPriority((p2.getWaitTime()+p2.getBurstTime())/p2.getBurstTime());
            }
            //우선순위의 역순으로 정렬
            list.sort(Comparator.comparingInt(ProcessProperty::getPriority).reversed());
        }
        resultList = getProcessTime(resultList);    //정렬된 리스트의 시간들을 새로 계산한 후 결과 리스트에 저장

        return resultList;      //결과리스트 반환
    }

    //비선점형 우선순위 알고리즘
    public ObservableList<ProcessProperty> Priority(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        ProcessProperty first = list.get(0);        //첫번째 프로세스 따로 저장
        list.remove(0);     //리스트에서 첫번째 프로세스 제거
        list.sort(Comparator.comparingInt(ProcessProperty::getPriority));       //우선순위로 정렬
        list.add(0,first);      //리스트에 다시 첫번째 프로세스 추가

        resultList = getProcessTime(list);      //결과리스트에 시간들을 포함한 정렬된 리스트 저장
        return resultList;      //결과리스트 반환
    }

}
