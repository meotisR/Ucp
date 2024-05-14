package com.ucp.controllers.templateControllers;

import com.ucp.models.Company;
import com.ucp.models.Order;
import com.ucp.repo.*;
import com.ucp.service.Main;
import com.ucp.service.RouteData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CompanyController extends Main {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderRepository orderRepository;

    @GetMapping("create/company")
    public String createCompany(Model model) {
        if (getUser().getCompany() == null) {
            return "create-company";
        } else {
            return "error-page";
        }
    }


    @GetMapping("/company/{id}")
    public String company(@PathVariable("id") String id, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(id));
        Company company = companyOptional.orElse(null);

        model.addAttribute("company", company);

        // admin - это роль владельца компании
        // user - это роль гостя или клиента компании

        if (company.getUser() == getUser()) {
            model.addAttribute("role", "admin");
        } else {
            model.addAttribute("role", "user");
        }

        return "company";
    }

    @GetMapping("/edit/company/{id}")
    public String editCompany(@PathVariable("id") String id, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(id));
        Company company = companyOptional.orElse(null);

        model.addAttribute("company", company);

        if (getUser() != null) {
            model.addAttribute("user", getUser());
        } else {
            return "error-page";
        }

        if (getUser().getCompany() == company) {
            return "edit-company";
        } else {
            return "error-page";
        }
    }

    @GetMapping("/orders/company/{id}")
    public String companyOrdersTotal(@PathVariable("id") String id, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(id));
        Company company = companyOptional.orElse(null);

        model.addAttribute("company", company);

        List<Order> orders = company.getOrders();
        List<Order> activeOrders = orders.stream()
                .filter(Order::isActive)
                .collect(Collectors.toList());

        if (activeOrders.size() == 0) {
            model.addAttribute("emptiness", true);
        } else {
            model.addAttribute("emptiness", false);
        }

        model.addAttribute("orders", activeOrders);

        return "company-orders-total";
    }

    @GetMapping("/orders/optimal/company/{id}")
    public String companyOrdersOptimal(@PathVariable("id") String id, Model model) {
        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf(id));
        Company company = companyOptional.orElse(null);

        List<Order> orders = company.getOrders();
        List<Order> activeOrders = orders.stream()
                .filter(Order::isActive)
                .collect(Collectors.toList());

        List<RouteData> routeDataList = new ArrayList<>();

        if (activeOrders.size() != 0) {
            for (int i = 1; i <= company.getInnerIdGenerationValue(); i++) {
                RouteData routeData;

                List<Order> ordersByCompositionId = orderRepository.findAllByCompositionId(i);
                List<Order> activeOrdersByCompositionId = new ArrayList<>();
                for (Order order : ordersByCompositionId) {
                    if (order.isActive()) {
                        activeOrdersByCompositionId.add(order);
                    }
                }

                if (activeOrdersByCompositionId.size() != 0) {
                    routeData = new RouteData();
                    routeData.setOrderList(activeOrdersByCompositionId);
                    routeDataList.add(routeData);
                }
            }
        }

        model.addAttribute("routes", routeDataList);
        model.addAttribute("company", company);

        return "company-orders-optimal";
    }
}
