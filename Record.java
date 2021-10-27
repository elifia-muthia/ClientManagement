/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.sql.Timestamp;

/**
 *
 * @author 44280
 */
public class Record {
    String change;
    String account;
    String time;

    public Record(String change, String account, Timestamp time) {
        this.change = change;
        this.account = account;
        this.time = String.valueOf(time);
    }

    public String getChange() {
        return change;
    }

    public String getAccount() {
        return account;
    }

    public String getTime() {
        return time;
    }
    
    
}
