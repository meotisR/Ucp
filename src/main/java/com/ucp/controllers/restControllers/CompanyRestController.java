package com.ucp.controllers.restControllers;

import com.ucp.models.*;
import com.ucp.repo.*;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class CompanyRestController extends Main {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    OrderRepository orderRepository;

    @PostMapping("/create-company")
    public ResponseEntity<String> createCompany(@RequestBody Map<String, Object> companyData) {
        boolean exists = companyRepository.existsByName((String) companyData.get("name"));
        if (exists) {
            String errorMessage = "Копания с таким названием уже существует";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        } else {
            Company company = new Company();

            company.setName((String) companyData.get("name"));
            company.setEmail((String) companyData.get("email"));
            company.setDistanceRate(Double.parseDouble((String) companyData.get("distanceRate")));
            company.setVolumeRate(Double.parseDouble((String) companyData.get("volumeRate")));
            company.setWeightRate(Double.parseDouble((String) companyData.get("weightRate")));
            company.setPhoto((String) companyData.get("photo"));
            company.setDescription((String) companyData.get("description"));
            company.setDate(getStringDate());
            company.setUser(getUser());
            company.setInnerIdGenerationValue(0);

            companyRepository.save(company);

            return ResponseEntity.ok(String.valueOf(company.getId()));
        }
    }

    @PostMapping("/edit-company/{id}")
    public ResponseEntity<String> editCompany(@RequestBody Map<String, Object> companyData, @PathVariable("id") String id) {
        Optional<Company> schoolOptional = companyRepository.findById(Long.valueOf(id));
        Company company = schoolOptional.orElse(null);

        company.setName((String) companyData.get("name"));
        company.setEmail((String) companyData.get("email"));
        company.setDistanceRate(Double.parseDouble((String) companyData.get("distanceRate")));
        company.setVolumeRate(Double.parseDouble((String) companyData.get("volumeRate")));
        company.setWeightRate(Double.parseDouble((String) companyData.get("weightRate")));
        company.setPhoto((String) companyData.get("photo"));
        company.setDescription((String) companyData.get("description"));

        companyRepository.save(company);

        return ResponseEntity.ok("Профиль компании отредактирован!");
    }

    @PostMapping("/complete-orders/{id}")
    public ResponseEntity<String> completeOrders(@PathVariable("id") String id) {
        Optional<Company> schoolOptional = companyRepository.findById(Long.valueOf(id));
        Company company = schoolOptional.orElse(null);

        List<Order> orders = company.getOrders();
        for (Order order : orders) {
            order.setIsActive(false);
            orderRepository.save(order);
        }

        return ResponseEntity.ok("Заказы выполнены!");
    }
}
