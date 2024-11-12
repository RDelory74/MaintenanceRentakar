package Rentakar.Maintenance.service;

import Rentakar.Maintenance.model.Maintenance;
import Rentakar.Maintenance.model.Vehicule;
import Rentakar.Maintenance.repository.MaintenanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@Service
public class MaintenanceService {

    //attributs
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private MaintenanceRepository maintenanceRepository;
    @Autowired
    private final RestTemplate restTemplate;

    private String serviceUrl;

    @Value("${service.order.url}")
    private String orderServiceUrl;
    @Value("${service.vehicule.url}")
    private String vehiculeServiceUrl;
    @Value("${service.license.url}")
    private String licenseServiceUrl;



    //injection



    @Autowired
    public MaintenanceService(MaintenanceRepository maintenanceRepository, RestTemplate restTemplate,DiscoveryClient discoveryClient) {
        this.maintenanceRepository = maintenanceRepository;
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
    }

    // méthode

    public void discoverAndConsumeService() {
        discoveryClient.getInstances("VehiculeRentakar")
                                .forEach(serviceInstance -> {
            String serviceUrl = serviceInstance.getUri().toString();
            System.out.println(serviceUrl);
        });
    }

    public String getTypeByVehiculeId(int vehiculeId) {
        discoverAndConsumeService();
        try {
            String url = UriComponentsBuilder
                    .fromHttpUrl(serviceUrl + "/type/" + vehiculeId)
                    .build()
                    .toUriString();

            System.out.print("Fetching Type attributes from vehiculeId:" + vehiculeId);
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<String>() {
                    }
            );
            String vehiculeType = response.getBody();
            System.out.println("Vehicule Type is" + vehiculeId + ": " + vehiculeType + " !");
            return vehiculeType;
        } catch (Exception e) {
            System.out.println("Error fetching type: " + e.getMessage());
            throw new RuntimeException("type fetching impossible", e);
        }
    }

    public Maintenance createByType(Maintenance maintenance, String vehiculeType) {
        if (vehiculeType.equalsIgnoreCase("moto")) {
            //tout les 1000 km ou tous les ans maintenance de 1 jours
        } else if (vehiculeType.equalsIgnoreCase("voiture")) {
            // tout les 100000 km mainteance de 3 jours
        } else if (vehiculeType.equalsIgnoreCase("utilitaire")) {
            //tous les 100000km mainteance de 3jour et tous les deux ans mainteance de 2 jours
        }
        return maintenanceRepository.save(maintenance);
    }

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

    public void deleteMaintenance(int id) {
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
