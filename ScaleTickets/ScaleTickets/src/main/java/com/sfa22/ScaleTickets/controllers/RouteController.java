package com.sfa22.ScaleTickets.controllers;


import com.sfa22.ScaleTickets.dtos.RouteDto;
import com.sfa22.ScaleTickets.services.interfaces.RouteService;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/v1")
@Validated
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }


    @GetMapping("/routes")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {

        return ResponseEntity.ok(routeService.getAllRoutes());
    }


    @GetMapping(value = "/routes", params = {"departureCity"})
    public ResponseEntity<List<RouteDto>> getRouteByDepartureCity(@RequestParam(name = "departureCity") String departureCity) {

        return ResponseEntity.ok(routeService.getRouteByDepartureCity(departureCity));
    }


    @GetMapping(value = "/routes", params = {"arrivalCity"})
    public ResponseEntity<List<RouteDto>> getRouteByArrivalCity(@RequestParam(name = "arrivalCity") String arrivalCity) {

        return ResponseEntity.ok(routeService.getRouteByArrivalCity(arrivalCity));
    }


    @GetMapping(value = "/routes", params = {"priceGreaterThan"})
    public ResponseEntity<List<RouteDto>> findByTripPriceGreaterThan(@RequestParam(name = "priceGreaterThan") double price) {

        return ResponseEntity.ok(routeService.findByTripPriceGreaterThan(price));
    }


    @GetMapping(value = "/routes", params = {"priceEquals"})
    public ResponseEntity<List<RouteDto>> getByTripPriceEquals(@RequestParam(name = "priceEquals") double price) {

        return ResponseEntity.ok(routeService.getByTripPriceEquals(price));
    }


    @GetMapping(value = "/routes", params = {"priceLessThan"})
    public ResponseEntity<List<RouteDto>> findByTripPriceLessThan(@RequestParam(name = "priceLessThan") double price) {

        return ResponseEntity.ok(routeService.findByTripPriceLessThan(price));
    }


    @GetMapping(value = "/routes", params = {"departureCity", "arrivalCity"})
    public ResponseEntity<RouteDto> findByDepartureCityAndArrivalCity(
            @RequestParam(name = "departureCity") String departureCity,
            @RequestParam(name = "arrivalCity") String arrivalCity) {

        return ResponseEntity.ok(routeService.findByDepartureCityAndArrivalCity(departureCity, arrivalCity));
    }


    @GetMapping("/routes/{id}")
    public ResponseEntity<RouteDto> findRouteById(
            @PathVariable int id) {

        return ResponseEntity.ok(routeService.findRouteById(id));
    }


    @PostMapping(value = "/routes")
    public ResponseEntity<Void> saveRoute(@Valid @RequestBody RouteDto routeDto) throws IOException {

        int newRouteID = routeService.saveRoute(routeDto);

        return ResponseEntity.created(URI.create("api/v1/routes/" + newRouteID)).build();
    }


    @PutMapping("/routes/{id}")
    public ResponseEntity<Boolean> updateRoute(@PathVariable int id, @Valid @RequestBody RouteDto routeDto) throws IOException {

        return ResponseEntity.ok(routeService.updateRoute(id, routeDto));
    }


    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Boolean> deleteRoute(@PathVariable int id) {

        return ResponseEntity.ok(routeService.deleteRoute(id));
    }


}