package com.ucp.service;

import com.ucp.models.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RouteData {
    private List<Order> orderList;
}
