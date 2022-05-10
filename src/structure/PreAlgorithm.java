package structure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;

//선점형 알고리즘을 구현하기 위한 클래스
public class PreAlgorithm extends Algorithm{
    public int timeQuantum;     //시간할당량

    //생성자
    public PreAlgorithm(ObservableList<ProcessProperty> list) {
        resultList = FXCollections.observableArrayList();
        count = list.size();
    }

    //라운드로빈 알고리즘
    public ObservableList<ProcessProperty> RR(ObservableList<ProcessProperty> list) {
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        int passedTime = 0;     //지난 시간을 저장하는 변수
        int tmp;        //임시변수

        ProcessProperty first = new ProcessProperty(list.get(0));       //첫번째 프로세스 따로 저장
        if (first.getBurstTime() - timeQuantum > 0) {       //첫 프로세스의 서비스시간이 시간할당량보다 클 경우
            passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
            list.remove(0);     //첫번째 프로세스를 리스트에서 제거
            ProcessProperty insert = new ProcessProperty(first);        //다시 리스트에 추가할 프로세스 객체 생성
            insert.setArrivalTime(timeQuantum);     //새로운 프로세스의 도착시간 = 시간할당량
            //새로운 프로세스의 서비스시간 = 서비스시간 - 시간할당량
            insert.setBurstTime(insert.getBurstTime() - timeQuantum);
            list.add(insert);       //리스트에 새로운 프로세스 추가
            first.setBurstTime(timeQuantum);        //첫 프로세스의 서비스시간 = 시간할당량
            resultList.add(first);      //결과 리스트에 첫 프로세스 추가
        } else if (first.getBurstTime() == timeQuantum) {       //첫 프로세스의 서비스시간이 시간할당량과 같을 경우
            passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
            list.remove(0);     //첫번째 프로세스를 리스트에서 제거
            resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
        } else {        //첫 프로세스의 서비스시간이 시간할당량보다 작을 경우
            passedTime += first.getBurstTime();     //첫 프로세스의 서비스시간만큼 시간할당량 증가
            list.remove(0);     //첫번째 프로세스를 리스트에서 제거
            first.setTurnATime(first.getBurstTime());       //첫 프로세스의 반환시간 = 서비스시간
            resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
        }

        while (!list.isEmpty()) {       //리스트에 프로세스가 없을 때 까지
            ProcessProperty p = new ProcessProperty(list.get(0));       //리스트의 첫번재 프로세스 따로 저장
            tmp = p.getBurstTime() - timeQuantum;       //임시변수를 첫번째 프로세스의 서비스시간 - 시간할당량으로 저장
            if (tmp > 0) {      //첫번째 프로세스의 서비스 시간이 시간할당량보다 클 경우
                passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
                list.remove(0);     //첫번째 프로세스를 리스트에서 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                insert.setBurstTime(timeQuantum);       //새로운 프로세스의 서비스시간 = 시간할당량
                resultList.add(insert);     //결과리스트에 새로운 프로세스 추가

                p.setArrivalTime(passedTime);       //첫번째 프로세스의 도착시간 = 지난시간
                p.setBurstTime(tmp);        //첫번째 프로세스의 서비스시간 = 첫번째 프로세스의 서비스시간 - 시간할당량
                list.add(p);        //시간이 계산된 첫번째 프로세스를 다시 리스트의 맨 뒤에 추가
            } else if (tmp == 0) {      //첫번째 프로세스의 서비스 시간이 시간할당량과 같을 경우
                passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
                list.remove(0);     //첫번재 프로세스를 리스트에서 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                resultList.add(insert);     //결과리스트에 추가
            } else {        //첫번째 프로세스의 서비스시간이 시간할당량보다 작을 경우
                passedTime += p.getBurstTime();     //첫번째 프로세스의 서비스시간만큼 지난 시간 증가
                list.remove(0);     //첫번째 프로세스를 리스트에서 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                resultList.add(insert);     //결과리스트에 추가
            }
        }

        resultList = getProcessTime(resultList);        //스케줄링이 끝난 리스트에서 시간들을 계산하여 결과리스트에 저장
        return resultList;      //결과 리스트 반환
    }

    //SRT 알고리즘
    public ObservableList<ProcessProperty> SRT(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        ProcessProperty first = new ProcessProperty(list.get(0));       //첫번째 프로세스 따로 저장
        list.remove(0);     //첫번째 프로세스를 리스트에서 제거
        int passedTime = 0;     //지난 시간
        int tmp;        //임시 변수

        if (first.getBurstTime() - timeQuantum > 0) {       //첫번째 프로세스의 서비스시간이 시간할당량보다 클 경우
            passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
            ProcessProperty insert = new ProcessProperty(first);        //리스트에 다시 삽입하기 위한 프로세스 객체 생성
            insert.setArrivalTime(timeQuantum);     //새로운 프로세스의 도착시간 = 시간할당량
            //새로운 프로세스의 서비스시간 = 서비스시간 - 시간할당량
            insert.setBurstTime(insert.getBurstTime() - timeQuantum);
            list.add(insert);       //리스트에 새로운 프로세스 추가
            first.setBurstTime(timeQuantum);        //첫번째 프로세스의 서비스시간 = 시간할당량
            resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
        } else if (first.getBurstTime() == timeQuantum) {       //첫번째 프로세스의 서비스시간 = 시간할당량
            passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
            resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
        } else {        //첫번째 프로세스의 서비스시간이 시간할당량보다 작다면
            passedTime += first.getBurstTime();     //첫번째 프로세스의 서비스시간만큼 지난 시간 증가
            first.setTurnATime(first.getBurstTime());       //첫번째 프로세스의 반환시간 = 프로세스의 서비스시간
            resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
        }
        while (!list.isEmpty()) {       //리스트에 프로세스가 없을때까지
            list.sort(Comparator.comparingInt(ProcessProperty::getBurstTime));      //남은 서비스시간대로 리스트 정렬
            ProcessProperty p = new ProcessProperty(list.get(0));       //첫번째 프로세스 따로 저장
            tmp = p.getBurstTime() - timeQuantum;       //임시변수를 (프로세스의 서비스시간 - 시간할당량)으로 저장
            if (tmp > 0) {      //첫번째 프로세스의 서비스시간이 시간할당량보다 클 경우
                passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
                list.remove(0);     //첫번째 프로세스를 리스트에서 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                insert.setBurstTime(timeQuantum);       //새로운 프로세스의 서비스시간 = 시간할당량
                resultList.add(insert);     //결과리스트에 새로운 프로세스 추가

                p.setArrivalTime(passedTime);       //첫번째 프로세스의 도착시간 = 지난 시간
                p.setBurstTime(tmp);        //첫번째 프로세스의 서비스시간 = 서비스시간 - 시간할당량
                list.add(p);        //리스트의 맨 뒤에 다시 첫번째 프로세스 추가
            } else if (tmp == 0) {      //첫번째 프로세스의 서비스시간이 시간할당량과 같을 경우
                passedTime += timeQuantum;      //시간할당량만큼 지난 시간 증가
                list.remove(0);     //리스트에서 첫번째 프로세스 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                resultList.add(insert);     //결과리스트에 새로운 프로세스 추가
            } else {        //첫번째 프로세스의 서비스시간이 시간할당량보다 작을 경우
                passedTime += p.getBurstTime();     //첫번째 프로세스의 서비스시간만큼 지난 시간 증가
                list.remove(0);     //첫번째 프로세스를 리스트에서 제거
                ProcessProperty insert = new ProcessProperty(p);        //결과리스트에 삽입할 프로세스 객체 생성
                resultList.add(insert);     //결과 리스트에 새로운 프로세스 추가
            }
        }

        resultList = getProcessTime(resultList);        //스케줄링이 끝난 리스트에서 시간들을 계산하여 결과리스트에 저장
        return resultList;      //결과 리스트 반환
    }

    //선점형 우선순위 알고리즘
    public ObservableList<ProcessProperty> Priority(ObservableList<ProcessProperty> list){
        list.sort(Comparator.comparingInt(ProcessProperty::getArrivalTime));        //도착시간으로 정렬
        int passedTime = 0;     //지난 시간

        while(passedTime == list.get(0).getArrivalTime()){      //지난시간이 첫번째 프로세스의 도착시간과 같을 동안 반복
            ProcessProperty first = new ProcessProperty(list.get(0));       //첫번째 프로세스를 따로 저장
            list.remove(0);     //첫번째 프로세스를 리스트에서 삭제
            //첫번째 프로세스의 우선순위가 리스트의 프로세스의 우선순위보다 낮을 경우
            if(first.getPriority() > list.get(0).getPriority()){
                //지난 시간을 (리스트의 프로세스의 도착시간 - 첫번째 프로세스의 도착시간)만큼 증가
                passedTime += list.get(0).getArrivalTime() - first.getArrivalTime();
                //리스트에 다시 추가할 새로운 프로세스 객체 생성
                ProcessProperty insert = new ProcessProperty(first);
                insert.setArrivalTime(passedTime);      //새로운 프로세스의 도착시간 = 지난 시간
                //새로운 프로세스의 서비스시간 = 새로운 프로세스의 서비스시간 - (리스트의 프로세스의 도착시간 - 첫번째 프로세스의 도착시간)
                insert.setBurstTime(insert.getBurstTime() - (list.get(0).getArrivalTime()-first.getArrivalTime()));
                list.add(insert);       //리스트에 새로운 프로세스 추가
                //첫번째 프로세스의 서비스시간 = 첫번째 프로세스의 서비스시간 - 새로운 프로세스의 서비스시간
                first.setBurstTime(first.getBurstTime() - insert.getBurstTime());
                resultList.add(first);      //결과리스트에 첫번째 프로세스 추가
            }else{      //첫번째 프로세스의 우선순위가 리스트의 프로세스의 우선순위보다 높을 경우
                passedTime += first.getBurstTime();     //첫번째 프로세스의 서비스시간만큼 지난 시간 증가
                //결과리스트에 삽입할 새로운 프로세스 객체 생성
                ProcessProperty insert = new ProcessProperty(first);
                resultList.add(insert);     //결과리스트에 새로운 프로세스 추가
            }
        }

        list.sort(Comparator.comparingInt(ProcessProperty::getPriority));       //리스트를 우선순위로 정렬
        while(!list.isEmpty()){     //리스트에 프로세스가 없을 때까지
            ProcessProperty first = new ProcessProperty(list.get(0));       //첫번째 프로세스 따로 저장
            list.remove(0);     //리스트에서 첫번째 프로세스 제거
            resultList.add(first);      //첫번째 프로세스를 결과리스트에 추가
        }

        resultList = getProcessTime(resultList);        //스케줄링이 끝난 리스트에서 시간들을 계산하여 결과리스트에 저장
        return resultList;      //결과리스트 반환
    }
}