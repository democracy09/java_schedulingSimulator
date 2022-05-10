package structure;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

//결과 화면에서 평균시간을 따로 표시하기 위한 테이블뷰에서 사용될 프로퍼티
public class AvgProperty{
    public DoubleProperty avgWaitTime;      //평균대기시간
    public DoubleProperty avgResponseTime;      //평균응답시간
    public DoubleProperty avgTurnATime;     //평균반환시간

    //멤버변수마다 프로퍼티 객체를 생성하는 생성자
    public AvgProperty(double avgWaitTime, double avgResponseTime, double avgTurnATime) {
        this.avgResponseTime = new SimpleDoubleProperty(avgResponseTime);
        this.avgTurnATime = new SimpleDoubleProperty(avgTurnATime);
        this.avgWaitTime = new SimpleDoubleProperty(avgWaitTime);
    }

    public double getAvgWaitTime() {
        return avgWaitTime.get();
    }

    public DoubleProperty avgWaitTimeProperty() {
        return avgWaitTime;
    }

    public void setAvgWaitTime(double avgWaitTime) {
        this.avgWaitTime.set(avgWaitTime);
    }

    public double getAvgResponseTime() {
        return avgResponseTime.get();
    }

    public DoubleProperty avgResponseTimeProperty() {
        return avgResponseTime;
    }

    public void setAvgResponseTime(double avgResponseTime) {
        this.avgResponseTime.set(avgResponseTime);
    }

    public double getAvgTurnATime() {
        return avgTurnATime.get();
    }

    public DoubleProperty avgTurnATimeProperty() {
        return avgTurnATime;
    }

    public void setAvgTurnATime(double avgTurnATime) {
        this.avgTurnATime.set(avgTurnATime);
    }
}
