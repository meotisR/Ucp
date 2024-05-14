package com.ucp.service;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DirectionsLeg;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.util.List;

public class Distance {
    public static Integer getFullDistance(String start, String end, List<String> waypoints) {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("AIzaSyAvd6hxDzFtGnFVdRugtVZSswOpd3uaUEM")
                .build();

        try {
            DirectionsApiRequest request = DirectionsApi.newRequest(context)
                    .origin(start)
                    .destination(end)
                    .waypoints(waypoints.toArray(new String[0]))
                    .optimizeWaypoints(true)
                    .mode(com.google.maps.model.TravelMode.DRIVING);

            DirectionsResult result = request.await();
            DirectionsRoute route = result.routes[0];
            DirectionsLeg[] legs = route.legs;

            int distance = 0;
            int duration = 0;

            for (DirectionsLeg leg : legs) {
                distance += leg.distance.inMeters;
                duration += leg.duration.inSeconds;
            }

            return distance / 1000;
        } catch (Exception e) {
            // Обработка ошибки
        }

        return null;
    }
}
