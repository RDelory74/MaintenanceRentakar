package Rentakar.Maintenance.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Maintenance {
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "vehicul_id")
    private int hasVehicule;
    @Column(name = "statement")
    private String statement;

    public Maintenance(int id, LocalDate startDate, LocalDate endDate, int hasVehicule, String statement) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hasVehicule = hasVehicule;
        this.statement = statement;
    }

    public Maintenance() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getHasVehicule() {
        return hasVehicule;
    }

    public void setHasVehicule(int hasVehicule) {
        this.hasVehicule = hasVehicule;
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }
}
