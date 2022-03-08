package jkesle.crm.dto;

public class CountryDTO {
    private int countryId;
    private String countryName;

    public CountryDTO(){};

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

}
