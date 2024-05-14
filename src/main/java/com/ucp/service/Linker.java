package com.ucp.service;

import java.util.Arrays;
import java.util.List;

public class Linker {
    public static boolean isCommonSegment(String route_1, String route_2) {

        String[] route_1_array = route_1.split(" ");
        List<String> route_1_list = Arrays.asList(route_1_array);

        String[] route_2_array = route_2.split(" ");
        List<String> route_2_list = Arrays.asList(route_2_array);

        int size1 = route_1_list.size();
        int size2 = route_2_list.size();

        // Проверяем каждый сегмент маршрута в route1
        for (int i = 0; i < size1 - 1; i++) {
            String currentCity = route_1_list.get(i);
            String nextCity = route_1_list.get(i + 1);

            // Проверяем каждый сегмент маршрута в route2
            for (int j = 0; j < size2 - 1; j++) {
                String currentCity2 = route_2_list.get(j);
                String nextCity2 = route_2_list.get(j + 1);

                // Если найден общий сегмент маршрута, возвращаем true
                if (currentCity.equals(currentCity2) && nextCity.equals(nextCity2)) {
                    return true;
                }
            }
        }

        // Общий сегмент маршрута не найден
        return false;
    }
}
