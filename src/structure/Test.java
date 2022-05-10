package structure;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Test {
    public static void main(String[] args){


        ObservableList<ProcessProperty> list = FXCollections.observableArrayList();
        list.add(new ProcessProperty("p1", 0, 30, 3));
        list.add(new ProcessProperty("p2", 3, 18, 2));
        list.add(new ProcessProperty("p3", 6, 9, 1));
        PreAlgorithm algorithm = new PreAlgorithm(list);
        algorithm.timeQuantum = 10;
        ObservableList<ProcessProperty> result = algorithm.RR(list);
        algorithm.getAvg(result);
        result.forEach(System.out::println);
    }
}
