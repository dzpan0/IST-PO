package hva.employee;

import hva.calculator.Calculator;

import java.io.Serial;
import java.io.Serializable;

public abstract class Employee implements Serializable {

    @Serial
    private static final long serialVersionUID = 202410091327L;

    private final String _id;
    private String _name;
    private Calculator _satisfactionCalculator;

    public Employee(String id, String name) {
        _id = id;
        _name = name;
        _satisfactionCalculator = null;
    }

    public String getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public void setSatisfactionCalculator(Calculator calculator) {
        _satisfactionCalculator = calculator;
    }

    public double calculateSatisfaction() {
        return _satisfactionCalculator.calculate();
    }

}
