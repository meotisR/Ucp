package com.ucp.controllers.templateControllers;

import com.ucp.models.Company;
import com.ucp.repo.CompanyRepository;
import com.ucp.service.CompanyData;
import com.ucp.service.Distance;
import com.ucp.service.GetRoute;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class OrderController extends Main {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/create/order")
    public String createOrder(Model model) {
        return "fill-order-info";
    }

    @GetMapping("/find-order-options/{name}/{startLocation}/{endLocation}/{volume}/{weight}")
    public String findOrderOptions(@PathVariable("name") String name, @PathVariable("startLocation") String startLocation,
                                   @PathVariable("endLocation") String endLocation, @PathVariable("volume") String volume,
                                   @PathVariable("weight") String weight, Model model) {

        List<String> route = new ArrayList<>();

        if (GetRoute.checkCityExists(startLocation) == false && GetRoute.checkCityExists(startLocation) == false) {
            model.addAttribute("isValid", false);
        } else {
            GetRoute getRoute = new GetRoute();
            route = getRoute.getRoute(startLocation, endLocation);
        }

        model.addAttribute("route", route);

        Integer distance = Distance.getFullDistance(startLocation, endLocation, route);

        List<Company> companies = companyRepository.findAll();
        List<CompanyData> companyDataList = new ArrayList<>();
        for (int i = 0; i < companies.size(); i++) {
            CompanyData companyData = new CompanyData();

            companyData.setId(String.valueOf(companies.get(i).getId()));
            companyData.setName(companies.get(i).getName());
            companyData.setPhoto(companies.get(i).getPhoto());

            Integer price = (int) (Integer.valueOf(distance) * companies.get(i).getDistanceRate());
            companyData.setPrice(String.valueOf(price));

            companyDataList.add(companyData);
        }

        model.addAttribute("companies", companyDataList);

        return "order-options";
    }

    @GetMapping("/deal/{id}/{price}")
    public String deal(@PathVariable("id") String id, @PathVariable("price") String price, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(id));
        Company company = companyOptional.orElse(null);

        model.addAttribute("company", company);
        model.addAttribute("price", price);
        model.addAttribute("user", getUser());

        return "deal";
    }
}
