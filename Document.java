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
public class Document {

    private String DocName, TaxObjectNumber, PropertyAddress, CertNumber;
    private double LandArea, BuildingArea, MarketPrice, LandTOSV, BuildingTOSV;
    private int MoneyDeposited, client_id, docid;
    private Timestamp last_edited;
    private byte[] pdf;

    public byte[] getPdf() {
        return pdf;
    }

    public void setPdf(byte[] pdf) {
        this.pdf = pdf;
    }
    
    public void setDocId(int docid) {
        this.docid = docid;
    }
    
    public int getDocId() {
        return docid;
    }
    
    public Document() {
    }

    public Document(String DocName, String TaxObjectNumber, String PropertyAddress, String CertNumber,
            double LandArea, double BuildingArea, double MarketPrice,
            double LandTOSV, double BuildingTOSV, int MoneyDeposited) {
        this.DocName = DocName;
        this.TaxObjectNumber = TaxObjectNumber;
        this.PropertyAddress = PropertyAddress;
        this.CertNumber = CertNumber;
        this.LandArea = LandArea;
        this.BuildingArea = BuildingArea;
        this.MarketPrice = MarketPrice;
        this.MoneyDeposited = MoneyDeposited;
        this.LandTOSV = LandTOSV;
        this.BuildingTOSV = BuildingTOSV;
        last_edited = new Timestamp(System.currentTimeMillis());
    }

    public Timestamp getLast_edited() {
        return last_edited;
    }

    public String getDocName() {
        return DocName;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public void setDocName(String DocName) {
        this.DocName = DocName;
    }

    public String getTaxObjectNumber() {
        return TaxObjectNumber;
    }

    public void setTaxObjectNumber(String TaxObjectNumber) {
        this.TaxObjectNumber = TaxObjectNumber;
    }

    public String getPropertyAddress() {
        return PropertyAddress;
    }

    public void setPropertyAddress(String PropertyAddress) {
        this.PropertyAddress = PropertyAddress;
    }

    public String getCertNumber() {
        return CertNumber;
    }

    public void setCertNumber(String CertNumber) {
        this.CertNumber = CertNumber;
    }

    public double getLandArea() {
        return LandArea;
    }

    public void setLandArea(double LandArea) {
        this.LandArea = LandArea;
    }

    public double getBuildingArea() {
        return BuildingArea;
    }

    public void setBuildingArea(double BuildingArea) {
        this.BuildingArea = BuildingArea;
    }

    public double getMarketPrice() {
        return MarketPrice;
    }

    public void setMarketPrice(double MarketPrice) {
        this.MarketPrice = MarketPrice;
    }

    public double getMoneyDeposited() {
        return MoneyDeposited;
    }

    public void setMoneyDeposited(int MoneyDeposited) {
        this.MoneyDeposited = MoneyDeposited;
    }

    public double getLandTOSV() {
        return LandTOSV;
    }

    public void setLandTOSV(double LandTOSV) {
        this.LandTOSV = LandTOSV;
    }

    public double getBuildingTOSV() {
        return BuildingTOSV;
    }

    public double getNTOAV() {
        return 80000000;
    }

    public void setBuildingTOSV(double BuildingTOSV) {
        this.BuildingTOSV = BuildingTOSV;
    }

    public double getLandAreaTimesTOSV() {
        return LandArea * LandTOSV;
    }

    public double getBuildingAreaTimesTOSV() {
        return BuildingArea * BuildingTOSV;
    }

    public double getTOAV() {
        return getLandAreaTimesTOSV() + getBuildingAreaTimesTOSV();
    }

    public double getTaxableOAV() {
        return getTOAV() - getNTOAV();
    }

    public double getLandBuildingDutyFees() {
        return 0.05 * getTaxableOAV();
    }

    public double getPendingLandBuildingDutyFees() {
        if (getLandBuildingDutyFees() - MoneyDeposited >= 0) {
            return getLandBuildingDutyFees() - MoneyDeposited;
        } else {
            return 0;
        }
    }

    /* Precondition: MoneyDeposited is not a null int value
    *  Postcondition: returns MoneyDeposited in words as a String
    */
    public String getMoneyDepositedWords() {
        String words = "";
        int money = MoneyDeposited;

        final String[] units = {"", " one", " two", " three", " four", 
            " five", " six", " seven", " eight", " nine",
            " ten", " eleven", " twelve", " thirteen", " fourteen", 
            " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"};
        final String[] tens = {"", "", " twenty", " thirty", " forty", 
            " fifty", " sixty", " seventy", " eighty", " ninety"};
        final String[] place = {"", " thousand", " million", " billion"};

        int placeIndex = 0;
        do {
            int temp = money % 1000;
            if (temp != 0) {
                int num = temp % 100;
                String tempWord = "";
                if (num < 20) {
                    System.out.println(num);
                    tempWord = units[num];
                } else {
                    tempWord = tens[num / 10] + units[num % 10];
                }
                if (temp / 100 > 0) {
                    tempWord = units[temp / 100] + " hundred" + tempWord;
                }
                words = tempWord + place[placeIndex] + words;
            }
            placeIndex++;
            money /= 1000;
        } while (money > 0);
        return words + " Rupiah";
    }
}
