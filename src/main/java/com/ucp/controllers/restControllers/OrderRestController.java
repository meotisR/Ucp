package com.ucp.controllers.restControllers;

import com.ucp.models.Company;
import com.ucp.models.Order;
import com.ucp.repo.CompanyRepository;
import com.ucp.repo.OrderRepository;
import com.ucp.service.Linker;
import com.ucp.service.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class OrderRestController extends Main {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CompanyRepository companyRepository;

    @PostMapping("/create-order")
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> orderData) {

        Order order = new Order();

        order.setName((String) orderData.get("name"));
        String route = (String) orderData.get("route");
        order.setRoute(route);
        order.setDistance(Integer.valueOf((String) orderData.get("distance")));
        order.setPrice(Integer.valueOf((String) orderData.get("price")));
        order.setWeight(Integer.valueOf((String) orderData.get("weight")));
        order.setVolume(Integer.valueOf((String) orderData.get("volume")));

        Optional<Company> companyOptional = companyRepository.findById(Long.valueOf((Integer) orderData.get("company")));
        Company company = companyOptional.orElse(null);
        order.setCompany(company);
        order.setIsActive(true);

        // Совершаем компоновку
        Integer innerIdGenerationValue = company.getInnerIdGenerationValue();
        Integer compositionId = 0;

        if (innerIdGenerationValue == 0) {
            // Если это первый заказ компании
            compositionId = 1;
            company.setInnerIdGenerationValue(1);

            System.out.println("Это первый заказ!");
        } else {
            // Если заказы уже есть, то проверяем, нет ли похожих маршрутов
            List<Order> orders = company.getOrders();

            for (Order currentOrder : orders) {
                // Отсеиваем уже выполненные заказы
                if (currentOrder.isActive() == true) {
                    if (Linker.isCommonSegment(currentOrder.getRoute(), route)) {
                        compositionId = currentOrder.getCompositionId();
                        System.out.println("Найден похожий заказ!");
                    }
                }
            }
        }

        // Если маршрут не первый и не найдено похожих
        if (compositionId == 0) {
            System.out.println("Маршрут не первый и не найдено похожих!");
            compositionId = innerIdGenerationValue + 1;
            company.setInnerIdGenerationValue(innerIdGenerationValue + 1);
        }

        order.setCompositionId(compositionId);
        order.setUser(getUser());

        orderRepository.save(order);

        return ResponseEntity.ok(String.valueOf(getUser().getId()));
        }
    }

