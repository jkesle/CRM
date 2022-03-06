package jkesle.crm.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Entity
@Getter
@EqualsAndHashCode
@ToString
public class Country {

    public enum CountryEnum {
        UNITED_STATES("US"),
        CANADA("CA"),
        UNITED_KINGDOM("UK");

        private final String abbreviation;

        CountryEnum(String abbreviation) {
            this.abbreviation = abbreviation;
        }

        public String getAbbreviation() {
            return abbreviation;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    @Enumerated(EnumType.STRING)
    @Column(name = "country_name")
    private CountryEnum countryName;

}
