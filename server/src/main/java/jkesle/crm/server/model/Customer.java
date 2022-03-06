package jkesle.crm.server.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Customer class encapsulates the state information and behavior
 * to properly communicate with the mySQL database
 *
 * @author Joshua Kesler
 */
@Entity
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPhone;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private FirstLevelDivision division;

    public Customer() {
    }
}
