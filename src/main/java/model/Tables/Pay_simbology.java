/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

import java.util.Date;

/**
 * Es una representacion de la tabla Pay_simbology dentro de la base de datos. 
 * @author Luis Morales
 */
public class Pay_simbology {
    private int id_pay;
    private String month;
    private String description_pay;
    private double cost_pay;
    private String period_pay;
    private Object deadline_pay;

    public Pay_simbology(int id_pay, String month, String description_pay, double cost_pay, 
            String period_pay, Object deadline_pay) {
        this.id_pay = id_pay;
        this.month = month;
        this.description_pay = description_pay;
        this.cost_pay = cost_pay;
        this.period_pay = period_pay;
        this.deadline_pay = deadline_pay;
    }

    public int getId_pay() {
        return id_pay;
    }

    public void setId_pay(int id_pay) {
        this.id_pay = id_pay;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDescription_pay() {
        return description_pay;
    }

    public void setDescription_pay(String description_pay) {
        this.description_pay = description_pay;
    }

    public double getCost_pay() {
        return cost_pay;
    }

    public void setCost_pay(double cost_pay) {
        this.cost_pay = cost_pay;
    }

    public String getPeriod_pay() {
        return period_pay;
    }

    public void setPeriod_pay(String period_pay) {
        this.period_pay = period_pay;
    }

    public Object getDeadline_pay() {
        return deadline_pay;
    }

    public void setDeadline_pay(Date deadline_pay) {
        this.deadline_pay = deadline_pay;
    }
    
    
    
    
}
