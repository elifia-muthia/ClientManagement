/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ia;

import java.io.*;
import java.sql.Connection;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author 44280
 */
public class User {

    Connection conn = null;
    Statement stat = null;
    ResultSet res = null;
    int regCode;
    Random r = new Random();
    String currentAcc;

    /*
    * Precondition: user class is instantiated in another class
    * Postcondition: connects to the database, randomly 
    *                generates registration code
    */
    public User() throws ClassNotFoundException {
        regCode = r.nextInt(10000);
        connectDB();
    }

    /*
    * Precondition: user clicks on generate code on GUI
    * Postcondition: integer from 0 to 9999 is generated and
    *                assigned to variable regCode
    */
    public int getRegCode() {
        regCode = r.nextInt(10000);
        return regCode;
    }

    /*
    * Precondition: called by the constructor
    * Postcondition: connection with the database is established
    */
    public void connectDB() throws ClassNotFoundException {
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/taxkeeper", "root", "");
            stat = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: user enters a registration code that is an integer
    * Postcondition: returns true if the entered code matches the registration
    *                code most recently generated
    */
    public boolean checkRegCode(int enteredCode) {
        System.out.println("reg code: " + regCode);
        return enteredCode == regCode;
    }

    /*
    * Precondition: user enters a username and password that is not blank and
    *               represented by a string
    * Postcondition: returns true if the username and password belongs to an
    *                account that has admin status in database and returns
    *                false otherwise
    */
    public boolean isAdmin(String username, String pass) {
        try {
            res = stat.executeQuery("SELECT * FROM user WHERE username = \'" + username + "\' AND password = \'" + pass + "\'");
            if (res.next()) {
                if (res.getString("status").equals("admin")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    * Precondition: username and password are not empty String values and
    *               status is either "admin" or "user"
    * Postcondition: username, password, and status are added as new accounts in
    *                the user table of the database and log table is updated with
    *                this new change
    */
    public void addUser(String username, String pass, String status) {
        try {
            stat.executeUpdate("INSERT INTO user (username, password, status) VALUES "
                    + "(\'" + username + "\', \'" + pass + "\', \'" + status + "\')");
            System.out.println("success");
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'New user " 
                    + username + " added\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: username is not an empty String value and entered by the user
    * Postcondition: returns true if the account with the same username exists and 
    *                returns false otherwise
    */
    public boolean checkIfAccExist(String username) {
        try {
            res = stat.executeQuery("SELECT username FROM user WHERE username = \'" + username + "\'");
            if (!res.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
    * Precondition: username and password are not empty String values and are
    *               entered by the user
    * Postcondition: returns true if the username and password is registered in 
    *                the database and returns false otherwise
    */
    public boolean verifyAcc(String username, String pass) {
        try {
            res = stat.executeQuery("SELECT username, password FROM user WHERE username = \'" + username + "\'");
            String userDB = "", passDB = "";
            while (res.next()) {
                userDB = res.getString("username");
                passDB = res.getString("password");
            }
            if (passDB.equals(pass)) {
                currentAcc = userDB;
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /*
    * Precondition: keyword is not an empty String value entered by user
    * Postcondition: returns an ArrayList of clients with names that matches 
    *                the keyword
    */
    public ArrayList getSearchResultsClients(String keyword) {
        ArrayList results = new ArrayList();
        try {
            // clients
            res = stat.executeQuery("SELECT * FROM client WHERE name LIKE '%" 
                    + keyword + "%'");
            while (res.next()) {
                //   results.add(res.getString("name"));
                int id = res.getInt("id");
                String name = res.getString("name");
                String address = res.getString("address");
                String post_code = res.getString("post_code");
                String tax_id_num = res.getString("tax_id_number");
                Client client = new Client(name, address, post_code, tax_id_num);
                client.setId(id);

                Blob blobImage = res.getBlob("image");
                if (blobImage != null) {
                    byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
                    client.setPic(byteImage);
                    System.out.println(name + " " + Arrays.toString(client.getPic()));
                } else {
                    System.out.println(name + " no image");
                }
                results.add(client);
                System.out.println("client found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    
    /*
    * Precondition: keyword is not an empty String value entered by user
    * Postcondition: returns an ArrayList of documents with names that matches the keyword
    */
    public ArrayList getSearchResultsDocs(String keyword) {
        ArrayList results = new ArrayList();
        try {
            res = stat.executeQuery("SELECT * FROM document WHERE DocName LIKE '%" + keyword + "%'");
            while (res.next()) {
                String DocName = res.getString("DocName");
                String TaxObjectNumber = res.getString("TaxObjectNumber");
                String PropertyAddress = res.getString("PropertyAddress");
                String CertNumber = res.getString("CertNumber");
                double LandArea = res.getDouble("LandArea");
                double BuildingArea = res.getDouble("BuildingArea");
                double MarketPrice = res.getDouble("MarketPrice");
                double LandTOSV = res.getDouble("LandTOSV");
                double BuildingTOSV = res.getDouble("BuildingTOSV");
                int MoneyDeposited = res.getInt("MoneyDeposited");
                Document doc = new Document(DocName, TaxObjectNumber, PropertyAddress, CertNumber, LandArea, BuildingArea,
                        MarketPrice, LandTOSV, BuildingTOSV, MoneyDeposited);
                Blob blobPdf = res.getBlob("pdf");
                byte[] bytePdf = blobPdf.getBytes(1, (int) blobPdf.length());
                doc.setPdf(bytePdf);
                results.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }
    
    /*
    * Precondition: method is called when clients page is displayed
    * Postcondition: returns an ArrayList of clients that are in the client 
    *                table of the database
    */
    public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList();
        try {
            res = stat.executeQuery("select * from client");
            while (res.next()) {
                Client client = new Client(res.getString("name"), 
                        res.getString("address"), res.getString("post_code"), 
                        res.getString("tax_id_number"));
                client.setId(res.getInt("id"));
                Blob blobImage = res.getBlob("image");
                if (blobImage != null) {
                    byte[] byteImage = blobImage.getBytes(1, (int) blobImage.length());
                    client.setPic(byteImage);
                } else {
                    System.out.println("hello");
                }
                clients.add(client);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clients;
    }
    
    /*
    * Precondition: user enters a client with no null values for name, address, post code, and tax id
    * Postcondition: adds the client onto the client table of the database and updates the log table with the recent change
    */
    public void addClient(Client client) {
        try {
            stat.executeUpdate("INSERT INTO client (name, address, post_code, tax_id_number) VALUES (\'" + client.getName() + "\', \'"
                    + client.getAddress() + "\', \'" + client.getPost_code() + "\', \'" + client.getTax_id_number() + "\')");
            System.out.println("success");
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'New client " + client.getName() + " added\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: the client variable has no null values for name, address, and post code
    * Postcondition: returns true if the client exists in the client table of the database and false otherwise
    */
    public boolean checkIfClientExist(Client client) {
        try {
            res = stat.executeQuery("SELECT * FROM client WHERE name = \'" + client.getName() + "\' and address = \'" + client.getAddress()
                    + "\' AND post_code = \'" + client.getPost_code() + "\'");
            if (!res.next()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /*
    * Precondition: client has no null values for name, address, post code, and id
    * Postcondition: client is deleted from the client table of the database and 
    *                the logs table is updated with the change
    */
    public void deleteClient(Client client) {
        try {
            stat.executeUpdate("DELETE FROM client WHERE name = \'" + client.getName() 
                    + "\' and address = \'" + client.getAddress() + "\' AND post_code = \'" 
                    + client.getPost_code() + "\' AND id = " + client.getId());
            System.out.println("success");
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'Client " 
                    + client.getName() + " deleted\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: newClient and oldClient has no null values for name, address, and post code
    * Postcondition: oldClient's information is replaced with newClient's information in the client table of the database and
    *                the change is updated in the logs table
    */
    public void updateClientInfo(Client newClient, Client oldClient) {
        try {
            stat.executeUpdate("UPDATE client SET name = \'" + newClient.getName() + "\', address = \'" + newClient.getAddress()
                    + "\', post_code = \'" + newClient.getPost_code() + "\' WHERE name = \'" + oldClient.getName() + "\' and address = \'" + oldClient.getAddress()
                    + "\' and post_code = \'" + oldClient.getPost_code() + "\'");
            System.out.println("success");
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'Updated " + oldClient.getName() + " client information\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: name is not an empty String value
    * Postcondition: returns the client id for the client with the name entered
    */
    public int getClientId(String name) {
        int id = 0;
        try {
            res = stat.executeQuery("SELECT id FROM client WHERE name = \'" + name + "\'");
            while (res.next()) {
                id = res.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /*
    * Precondition: none of the variables in client null except for id and pic, none
    *               of the variables in document is null except pdf and last_edited
    * Postcondition: document is added to the document table of the database along with the
    *                client id it is associated with and change is updated in the logs table
    */
    public void saveToPDF(Document document, Client client) {
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 18);
            content.moveTextPositionByAmount(220, 720);
            content.drawString("TAX PAYMENT SLIP");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 18);
            content.moveTextPositionByAmount(150, 700);
            content.drawString("LAND AND BUILDING TRANSFER DUTY");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 660);
            content.drawString("A. 1. Name: " + client.getName());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 640);
            content.drawString("   2. Address: " + client.getAddress());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 620);
            content.drawString("   3. Post Code: " + client.getPost_code());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 600);
            content.drawString("   4. Tax ID Number: " + client.getTax_id_number());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 560);
            content.drawString("B. 1. Tax Object Number: " + document.getTaxObjectNumber());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 540);
            content.drawString("   2. Property Address: " + document.getPropertyAddress());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 520);
            content.drawString("   3. Certificate Number: " + document.getCertNumber());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 480);
            content.drawString("C. 1. Market Price: Rp" + document.getMarketPrice());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 460);
            content.drawString("   2. i) Land Area: " + document.getLandArea() + " meter squared");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 440);
            content.drawString("      ii) Tax Object Sales Value / Meter Squared: Rp" + document.getLandTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 420);
            content.drawString("      iii) Area x Tax Object Sales Value / Meter Squared: Rp" + document.getLandAreaTimesTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 400);
            content.drawString("   3. i) Building Area: " + document.getBuildingArea() + " meter squared");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 380);
            content.drawString("      ii) Tax Object Sales Value / Meter Squared: Rp" + document.getBuildingTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 360);
            content.drawString("      iii) Area x Tax Object Sales Value / Meter Squared: Rp" + document.getBuildingAreaTimesTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 340);
            content.drawString("   4. Total: Rp" + document.getTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 300);
            content.drawString("D. Land and Building Transfer Duty Calculation");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 280);
            content.drawString("  1. Tax Object Acquisition Value: Rp" + document.getTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 260);
            content.drawString("  2. Non-Taxable Object Acquisition Value: Rp" + document.getNTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 240);
            content.drawString("  3. Taxable Object Acquisition Value: Rp" + document.getTaxableOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 220);
            content.drawString("  4. Land and Building Duty Fees Owed: Rp" + document.getLandBuildingDutyFees());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 200);
            content.drawString("  5. Pending Land and Building Duty Fees: Rp" + document.getPendingLandBuildingDutyFees());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 160);
            content.drawString("E. Deposit Amount: Rp" + document.getMoneyDeposited());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 140);
            content.drawString("   " + document.getMoneyDepositedWords());
            content.endText();

            content.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            doc.close();
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);

            // save in db
            String sql = "INSERT INTO document(client_id, DocName, TaxObjectNumber, PropertyAddress, CertNumber, LandArea,"
                    + "BuildingArea, MarketPrice, LandTOSV, BuildingTOSV, MoneyDeposited, pdf) VALUES (\'" + client.getId()
                    + "\', \'" + document.getDocName() + "\', \'" + document.getTaxObjectNumber() + "\', \'" + document.getPropertyAddress()
                    + "\', \'" + document.getCertNumber() + "\', " + document.getLandArea() + " ," + document.getBuildingArea()
                    + " ," + document.getMarketPrice() + " ," + document.getLandTOSV() + " ," + document.getBuildingTOSV()
                    + " ," + document.getMoneyDeposited() + " ,?)";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setBlob(1, bais);
            pstat.execute();
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'New document \"" + document.getDocName() + "\" added\', \'" 
                    + currentAcc + "\')");
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: client_id is an int that corresponds to an existing id in the client table of database
    * Postcondition: returns an ArrayList of document that has the client_id from the document table
    */
    public ArrayList<Document> getClientDocuments(int client_id) {
        ArrayList<Document> clientDocs = new ArrayList();
        try {
            res = stat.executeQuery("SELECT * from document WHERE client_id = \'" + client_id + "\'");
            while (res.next()) {
                String DocName = res.getString("DocName");
                String TaxObjectNumber = res.getString("TaxObjectNumber");
                String PropertyAddress = res.getString("PropertyAddress");
                String CertNumber = res.getString("CertNumber");
                double LandArea = res.getDouble("LandArea");
                double BuildingArea = res.getDouble("BuildingArea");
                double MarketPrice = res.getDouble("MarketPrice");
                double LandTOSV = res.getDouble("LandTOSV");
                double BuildingTOSV = res.getDouble("BuildingTOSV");
                int MoneyDeposited = res.getInt("MoneyDeposited");
                int docid = res.getInt("id");
                Document doc = new Document(DocName, TaxObjectNumber, PropertyAddress, CertNumber, LandArea, BuildingArea,
                        MarketPrice, LandTOSV, BuildingTOSV, MoneyDeposited);
                Blob blobPdf = res.getBlob("pdf");
                byte[] bytePdf = blobPdf.getBytes(1, (int) blobPdf.length());
                doc.setPdf(bytePdf);
                doc.setDocId(docid);
                clientDocs.add(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientDocs;
    }

    /*
    * Precondition: doc is a document with no null values for docName and docId
    * Postcondition: doc is deleted from the document table of the database and the change
    *                updated in the logs table
    */
    public void deleteDocument(Document doc) {
        try {
            stat.executeUpdate("DELETE FROM document WHERE DocName = \'" + doc.getDocName() + "\' AND id = " + doc.getDocId());
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'Document \"" + doc.getDocName() + "\" deleted\', \'" + currentAcc + "\')");
            System.out.println("success");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: none of the variables in client null except for id and pic, none
    *               of the variables in document is null except pdf and last_edited
    * Postcondition: document is changed with updated information and pdf is replaced
    *                and change is updated in the logs table
    */
    public void updateDocument(Document document, Client client) {
        try {
            PDDocument doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 18);
            content.moveTextPositionByAmount(220, 720);
            content.drawString("TAX PAYMENT SLIP");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_BOLD, 18);
            content.moveTextPositionByAmount(150, 700);
            content.drawString("LAND AND BUILDING TRANSFER DUTY");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 660);
            content.drawString("A. 1. Name: " + client.getName());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 640);
            content.drawString("   2. Address: " + client.getAddress());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 620);
            content.drawString("   3. Post Code: " + client.getPost_code());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 600);
            content.drawString("   4. Tax ID Number: " + client.getTax_id_number());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 560);
            content.drawString("B. 1. Tax Object Number: " + document.getTaxObjectNumber());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 540);
            content.drawString("   2. Property Address: " + document.getPropertyAddress());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 520);
            content.drawString("   3. Certificate Number: " + document.getCertNumber());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 480);
            content.drawString("C. 1. Market Price: Rp" + document.getMarketPrice());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 460);
            content.drawString("   2. i) Land Area: " + document.getLandArea() + " meter squared");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 440);
            content.drawString("      ii) Tax Object Sales Value / Meter Squared: Rp" + document.getLandTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 420);
            content.drawString("      iii) Area x Tax Object Sales Value / Meter Squared: Rp" + document.getLandAreaTimesTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 400);
            content.drawString("   3. i) Building Area: " + document.getBuildingArea() + " meter squared");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 380);
            content.drawString("      ii) Tax Object Sales Value / Meter Squared: Rp" + document.getBuildingTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 360);
            content.drawString("      iii) Area x Tax Object Sales Value / Meter Squared: Rp" + document.getBuildingAreaTimesTOSV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 340);
            content.drawString("   4. Total: Rp" + document.getTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 300);
            content.drawString("D. Land and Building Transfer Duty Calculation");
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 280);
            content.drawString("  1. Tax Object Acquisition Value: Rp" + document.getTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 260);
            content.drawString("  2. Non-Taxable Object Acquisition Value: Rp" + document.getNTOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 240);
            content.drawString("  3. Taxable Object Acquisition Value: Rp" + document.getTaxableOAV());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 220);
            content.drawString("  4. Land and Building Duty Fees Owed: Rp" + document.getLandBuildingDutyFees());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 200);
            content.drawString("  5. Pending Land and Building Duty Fees: Rp" + document.getPendingLandBuildingDutyFees());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 160);
            content.drawString("E. Deposit Amount: Rp" + document.getMoneyDeposited());
            content.endText();

            content.beginText();
            content.setFont(PDType1Font.TIMES_ROMAN, 12);
            content.moveTextPositionByAmount(80, 140);
            content.drawString("   " + document.getMoneyDepositedWords());
            content.endText();

            content.close();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            doc.close();
            byte[] data = baos.toByteArray();
            ByteArrayInputStream bais = new ByteArrayInputStream(data);

            String sql = "UPDATE document SET DocName = \'" + document.getDocName() + "\', TaxObjectNumber = \'" + document.getTaxObjectNumber()
                    + "\', PropertyAddress = \'" + document.getPropertyAddress() + "\', CertNumber = \'" + document.getCertNumber() + "\', LandArea = "
                    + document.getLandArea() + ", BuildingArea = " + document.getBuildingArea() + ", MarketPrice = " + document.getMarketPrice()
                    + ", LandTOSV = " + document.getLandTOSV() + ", BuildingTOSV = " + document.getBuildingTOSV() + ", MoneyDeposited = "
                    + document.getMoneyDeposited() + ", pdf = ? WHERE client_id = " + client.getId() + " AND id = " + document.getDocId();
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setBlob(1, bais);
            pstat.execute();
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'Updated \"" + document.getDocName() + "\" document information\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: file is not a null byte array that represents the selected image
    *               from user, id is an int that belongs to a client that exists in the database,
    *               name is the name of the client
    * Postcondition: image is uploaded for client with the id to the client table in the
    *                database and the logs table is updated with the change
    */
    public void uploadImage(byte[] file, int id, String name) {
        ByteArrayInputStream bais = new ByteArrayInputStream(file);
        try {
            String sql = "UPDATE client SET image = ? WHERE id = " + id;
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setBlob(1, bais);
            pstat.execute();
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'Image added for \"" + name + "\"\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: file is not a null byte array that represents the selected document
    *               from user, id is an int that belongs to a client that exists in the database,
    *               name is the name of the client
    * Postcondition: document is uploaded in the client table in the database
    *                and the logs table is updated with the change
    */
    public void uploadDoc(byte[] file, int id, String name) {
        ByteArrayInputStream bais = new ByteArrayInputStream(file);
        try {
            String sql = "INSERT INTO document(client_id, DocName, pdf) VALUES (" + id + ", \'" 
                    + name + "\', ?)";
            PreparedStatement pstat = conn.prepareStatement(sql);
            pstat.setBlob(1, bais);
            pstat.execute();
            stat.executeUpdate("INSERT INTO log (change_made, account) VALUES (\'New document \"" 
                    + name + "\" added\', \'" + currentAcc + "\')");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    * Precondition: method is called when the logs page is displayed
    * Postcondition: returns an ArrayList of Record that consists of all the
    *                entries in the logs table of the database
    */
    public ArrayList<Record> getLogs() {
        ArrayList<Record> logs = new ArrayList<Record>();
        try {
            res = stat.executeQuery("select * from log order by id desc");
            while (res.next()) {
                String change = res.getString("change_made");
                String account = res.getString("account");
                Timestamp time = res.getTimestamp("time");
                logs.add(new Record(change, account, time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logs;
    }

}
