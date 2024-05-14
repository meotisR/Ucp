package com.ucp.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GetRoute {
    private static String[][] cityMatrix;
    private static String[][] fullMatrix;

    static {
        // Заполнение матрицы городов
        cityMatrix = new String[][] {
                {"Каунас", "Рига", "Полоцк", "Городок", "Витебск"},
                {"Вильнюс", "Молодечно", "Логойск", "Лепель", "Орша"},
                {"Лида", "Дзержинск", "Минск", "Борисов", "Могилёв"},
                {"Гродно", "Барановичи", "Солигорск", "Бобруйск", "Жлобин"},
                {"Брест", "Кобрин", "Пинск", "Мозырь", "Гомель"},
        };
    }

    static {
        // Заполнение матрицы городов
        fullMatrix = new String[][] {
                {"Каунас", "Рига", "Полоцк", "Городок", "Витебск"},
                {"Вильнюс", "Молодечно", "Логойск", "Лепель", "Орша"},
                {"Лида", "Дзержинск, Беларусь", "Минск", "Борисов", "Могилёв"},
                {"Гродно", "Барановичи", "Солигорск", "Бобруйск", "Жлобин"},
                {"Брест", "Кобрин", "Пинск", "Мозырь", "Гомель"},
        };
    }

    public List<String> getRoute(String startCity, String endCity) {
        Location startLocation = findCityLocation(startCity);
        Location endLocation = findCityLocation(endCity);

        List<String> route = new ArrayList<>();
        route.add(startCity);

        Location pointer = startLocation;

        while (!pointer.equals(endLocation)) {
            if (endLocation.getY() > pointer.getY()) {
                pointer.setY(pointer.getY() + 1);
            } else if (endLocation.getY() < pointer.getY()) {
                pointer.setY(pointer.getY() - 1);
            } else if (endLocation.getX() > pointer.getX()) {
                pointer.setX(pointer.getX() + 1);
            } else if (endLocation.getX() < pointer.getX()) {
                pointer.setX(pointer.getX() - 1);
            }

            route.add(fullMatrix[pointer.getX()][pointer.getY()]);
        }

        return route;
    }

    public static boolean checkCityExists(String city) {
        for (String[] row : cityMatrix) {
            for (String cityName : row) {
                if (cityName.equals(city)) {
                    return true; // Город найден
                }
            }
        }

        return false; // Город не найден
    }

    private Location findCityLocation(String city) {
        for (int i = 0; i < cityMatrix.length; i++) {
            for (int j = 0; j < cityMatrix[i].length; j++) {
                if (cityMatrix[i][j].equals(city)) {
                    return new Location(i, j);
                }
            }
        }
        return null; // Город не найден
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class Location {
        private int x;
        private int y;

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }

            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }

            Location other = (Location) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}