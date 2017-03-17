package ch.slf.pro.osper.station.model;

import ch.slf.pro.osper.station.ApplicationFields;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by kryvych on 23/12/16.
 */
@Entity
@Table(name = "v_network", schema = ApplicationFields.SCHEMA)
public class Network {

    @Id
    @NotNull
    private String code;

    @Column(name = "descr")
    private String description;

    public Network() {
    }

    public Network(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
