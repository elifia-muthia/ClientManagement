/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.util.ArrayList;

/**
 *
 * @author 44280
 */
public class Client {
    private int id;
    private String name, address, post_code, tax_id_number;
    private byte[] pic;

    public byte[] getPic() {
        return pic;
    }

    public void setPic(byte[] pic) {
        this.pic = pic;
    }

    public Client(String name, String address, String post_code, String tax_id_number) {
        this.name = name;
        this.address = address;
        this.post_code = post_code;
        this.tax_id_number = tax_id_number;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTax_id_number() {
        return tax_id_number;
    }

    public void setTax_id_number(String tax_id_number) {
        this.tax_id_number = tax_id_number;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPost_code(String post_code) {
        this.post_code = post_code;
    }
    
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPost_code() {
        return post_code;
    }

    @Override
    public String toString() {
        return "Client{" + "id=" + id + ", name=" + name + ", address=" + 
                address + ", post_code=" + post_code + ", image=" + pic.length +'}';
    }    
    
}
