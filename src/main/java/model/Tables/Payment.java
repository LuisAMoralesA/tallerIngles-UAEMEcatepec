/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Tables;

/**
 * Es una representacion de la tabla Payment dentro de la base de datos. 
 * @author Luis Morales
 */
public class Payment {
    private int id_payment;
    private boolean register_payment;
    private boolean pay_1;
    private boolean pay_2;
    private boolean pay_3;
    private boolean pay_4;
    private boolean pay_5;
    private boolean pay_6;
    private boolean pay_7;
    private int payment_status;

    public Payment(int id_payment, boolean register_payment, boolean pay_1, boolean pay_2, boolean pay_3, 
            boolean pay_4, boolean pay_5, boolean pay_6, boolean pay_7, int payment_status) {
        this.id_payment = id_payment;
        this.register_payment = register_payment;
        this.pay_1 = pay_1;
        this.pay_2 = pay_2;
        this.pay_3 = pay_3;
        this.pay_4 = pay_4;
        this.pay_5 = pay_5;
        this.pay_6 = pay_6;
        this.pay_7 = pay_7;
        this.payment_status = payment_status;
    }

    public int getId_payment() {
        return id_payment;
    }

    public void setId_payment(int id_payment) {
        this.id_payment = id_payment;
    }

    public boolean isRegister_payment() {
        return register_payment;
    }

    public void setRegister_payment(boolean register_payment) {
        this.register_payment = register_payment;
    }

    public boolean isPay_1() {
        return pay_1;
    }

    public void setPay_1(boolean pay_1) {
        this.pay_1 = pay_1;
    }

    public boolean isPay_2() {
        return pay_2;
    }

    public void setPay_2(boolean pay_2) {
        this.pay_2 = pay_2;
    }

    public boolean isPay_3() {
        return pay_3;
    }

    public void setPay_3(boolean pay_3) {
        this.pay_3 = pay_3;
    }

    public boolean isPay_4() {
        return pay_4;
    }

    public void setPay_4(boolean pay_4) {
        this.pay_4 = pay_4;
    }

    public boolean isPay_5() {
        return pay_5;
    }

    public void setPay_5(boolean pay_5) {
        this.pay_5 = pay_5;
    }

    public boolean isPay_6() {
        return pay_6;
    }

    public void setPay_6(boolean pay_6) {
        this.pay_6 = pay_6;
    }

    public boolean isPay_7() {
        return pay_7;
    }

    public void setPay_7(boolean pay_7) {
        this.pay_7 = pay_7;
    }

    public int getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(int payment_status) {
        this.payment_status = payment_status;
    }
    
    
}
