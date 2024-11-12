package Rentakar.Maintenance.service;

import Rentakar.Maintenance.model.Maintenance;
import Rentakar.Maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class MaintenanceService {

    //attributs

    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private final RestTemplate restTemplate;

    @Value("${service.order.url}")
    private String orderServiceUrl;

    @Value("${service.vehicule.url}")
    private String vehiculeServiceUrl;

    @Value("${service.license.url}")
    private String licenseServiceUrl;


    //injection
    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository, RestTemplate restTemplate) {
        this.maintenanceRepository = maintenanceRepository;
        this.restTemplate = restTemplate;
    }

    // méthode

    public List<Maintenance> getAllMaintenances() {
        System.out.print("Fetching all Maintenances ||");
        return maintenanceRepository.findAll();
    }

    public Maintenance getMaintenanceById(int id) {
        Maintenance maintenance = maintenanceRepository.findById(id).get();
        if(maintenance == null) {
            System.out.println("Maintenance not found");
        }
        System.out.println("Maintenance with id: " + id + " found  ||");
        System.out.println(maintenance);
        return maintenance;
    }


    public Maintenance getMaintenanceByVehiculeId(int hasVehicule) {
       Maintenance maintenance = maintenanceRepository.findById(hasVehicule).get();
       if(maintenance == null) {
           System.out.println("Maintenance not found");
       }
        System.out.println("Mainteance with Vehicule id: " + hasVehicule + " found  ||");
       System.out.println(maintenance);
       return maintenance;
    }

    public Maintenance saveMainteance(Maintenance maintenance) {
        System.out.println("Order with id: " + maintenance.getId() + " saved  ||");
        return maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenace(int id) {
        System.out.println("Maintenance with id: " + id + " deleted  ||");
        maintenanceRepository.deleteById(id);
    }

    public Maintenance updateMaintenanceById(int id, Maintenance maintenance) {
        Maintenance updatedOrder = maintenanceRepository.findById(id).orElse(null);
        if (updatedOrder != null) {
            maintenanceRepository.deleteById(id);
            updatedOrder.setStartDate(maintenance.getStartDate());
            updatedOrder.setEndDate(maintenance.getEndDate());
            updatedOrder.setHasVehicule(maintenance.getHasVehicule());
            updatedOrder.setStatement(maintenance.getStatement());
            System.out.println("Maintenance with id: " + id + " updated ||");
            return maintenanceRepository.save(updatedOrder);
        } else {
            System.out.println("Maintenance with id: " + id + " not found  ||");
            return null;
        }
    }

}
