package Rentakar.Maintenance.controller;

import Rentakar.Maintenance.model.Maintenance;
import Rentakar.Maintenance.service.MaintenanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MaintenanceController {

    private final MaintenanceService maintenanceService;

    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }


    @GetMapping("/maintenances")
    public List<Maintenance> getAllMaintenances() {
        return maintenanceService.getAllMaintenances();
    }
    @GetMapping("/maintenance/{id}")
    public Maintenance getMaintenanceById(@PathVariable int id) {
        return maintenanceService.getMaintenanceById(id);
    }
    @GetMapping("/maintenance/{hasVehicule}")
    public Maintenance getMaintenanceByVehicule(@PathVariable int hasVehicule) {
        return  maintenanceService.getMaintenanceByVehiculeId(hasVehicule);
    }
    @PostMapping("/maintenance")
    public Maintenance addMaintenance(@RequestBody Maintenance maintenance) {
        return maintenanceService.saveMainteance(maintenance);
    }
    @PutMapping("/mainteance/{id}")
    public Maintenance updateMaintenance(@PathVariable int id, @RequestBody Maintenance maintenance) {
        return maintenanceService.updateMaintenanceById(id, maintenance);
    }


}
