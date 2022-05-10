package structure;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//입력화면에서 입력받는 테이블뷰와 결과화면에서 결과를 프로세스별 테이블뷰로 표시하기 위한 프로퍼티
public class ProcessProperty {
    private StringProperty processID;       //프로세스id
    private IntegerProperty arrivalTime;        //도착시간
    private IntegerProperty burstTime;      //서비스시간
    private IntegerProperty priority;       //우선순위

    private IntegerProperty waitTime;       //대기시간
    private IntegerProperty responseTime;       //응답시간
    private IntegerProperty turnATime;      //반환시간

    //멤버변수마다 프로퍼티 객체를 생성하는 생성자
    public ProcessProperty() {
        super();
        this.processID = new SimpleStringProperty();
        this.arrivalTime = new SimpleIntegerProperty();
        this.burstTime = new SimpleIntegerProperty();
        this.priority = new SimpleIntegerProperty();
        this.waitTime = new SimpleIntegerProperty();
        this.responseTime = new SimpleIntegerProperty();
        this.turnATime = new SimpleIntegerProperty();
    }
    public ProcessProperty(String processID, int arrivalTime, int BurstTime, int priority) {
        super();
        this.processID = new SimpleStringProperty();
        this.arrivalTime = new SimpleIntegerProperty();
        this.burstTime = new SimpleIntegerProperty();
        this.priority = new SimpleIntegerProperty();
        this.waitTime = new SimpleIntegerProperty();
        this.responseTime = new SimpleIntegerProperty();
        this.turnATime = new SimpleIntegerProperty();

        this.processID.set(processID);
        this.arrivalTime.set(arrivalTime);
        this.burstTime.set(BurstTime);
        this.priority.set(priority);
    }
    public ProcessProperty(ProcessProperty p){
        super();
        this.processID = new SimpleStringProperty();
        this.arrivalTime = new SimpleIntegerProperty();
        this.burstTime = new SimpleIntegerProperty();
        this.priority = new SimpleIntegerProperty();
        this.waitTime = new SimpleIntegerProperty();
        this.responseTime = new SimpleIntegerProperty();
        this.turnATime = new SimpleIntegerProperty();

        this.processID.set(p.getProcessID());
        this.arrivalTime.set(p.getArrivalTime());
        this.burstTime.set(p.getBurstTime());
        this.priority.set(p.getPriority());
    }

    //getter와 setter
    public String getProcessID() {
        return processID.get();
    }

    public StringProperty processIDProperty() {
        return processID;
    }

    public void setProcessID(String processID) {
        this.processID.set(processID);
    }

    public int getArrivalTime() {
        return arrivalTime.get();
    }

    public IntegerProperty arrivalTimeProperty() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime.set(arrivalTime);
    }

    public int getBurstTime() {
        return burstTime.get();
    }

    public IntegerProperty burstTimeProperty() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime.set(burstTime);
    }

    public int getPriority() {
        return priority.get();
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
    }

    public int getWaitTime() {
        return waitTime.get();
    }

    public IntegerProperty waitTimeProperty() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime.set(waitTime);
    }

    public int getResponseTime() {
        return responseTime.get();
    }

    public IntegerProperty responseTimeProperty() {
        return responseTime;
    }

    public void setResponseTime(int responseTime) {
        this.responseTime.set(responseTime);
    }

    public int getTurnATime() {
        return turnATime.get();
    }

    public IntegerProperty turnATimeProperty() {
        return turnATime;
    }

    public void setTurnATime(int turnATime) {
        this.turnATime.set(turnATime);
    }

    //알고리즘 테스트에서 결과를 확인하기 위한 toString 오버라이드
    @Override
    public String toString() {
        return "ProcessProperty{" +
                "processID=" + processID +
                ", arrivalTime=" + arrivalTime +
                ", BurstTime=" + burstTime +
                ", priority=" + priority +
                ", waitTime=" + waitTime +
                ", responseTime=" + responseTime +
                ", turnATime=" + turnATime +
                '}';
    }
}
