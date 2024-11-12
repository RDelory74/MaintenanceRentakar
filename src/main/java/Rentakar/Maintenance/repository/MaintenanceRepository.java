package Rentakar.Maintenance.repository;

import Rentakar.Maintenance.model.Maintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {

    @Query("Select o From Maintenance o where o.id=:id ")
    List<Maintenance> getById(int id);

    @Query("Select v From Maintenance v where v.hasVehicule=:hasVehicule")
    Maintenance getMaintenancesByHasVehicule(int hasVehicule);
}
