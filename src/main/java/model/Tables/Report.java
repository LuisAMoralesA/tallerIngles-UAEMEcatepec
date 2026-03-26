/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Report dentro de la base de datos. 
 * @author Luis Morales
 */
public class Report {
   private int id_report;
   private double first_partial_report;
   private double second_partial_report;
   private double avg_report;

    public Report(int id_report, double first_partial_report, double second_partial_report, double avg_report) {
        this.id_report = id_report;
        this.first_partial_report = first_partial_report;
        this.second_partial_report = second_partial_report;
        this.avg_report = avg_report;
    }

    public int getId_report() {
        return id_report;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public double getFirst_partial_report() {
        return first_partial_report;
    }

    public void setFirst_partial_report(double first_partial_report) {
        this.first_partial_report = first_partial_report;
    }

    public double getSecond_partial_report() {
        return second_partial_report;
    }

    public void setSecond_partial_report(double second_partial_report) {
        this.second_partial_report = second_partial_report;
    }

    public double getAvg_report() {
        return avg_report;
    }

    public void setAvg_report(double avg_report) {
        this.avg_report = avg_report;
    }
   
   
}
