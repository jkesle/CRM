package jkesle.crm.server.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FirstLevelDivision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int firstLevelDivisionId;

    private String firstLevelDivisionName;

    private String firstLevelDivisionAbbreviation;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
}
