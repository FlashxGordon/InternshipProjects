package com.sfa22.ScaleTickets.data;


import com.sfa22.ScaleTickets.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findTicketsByTicketPrice(double ticketPrice);

    List<Ticket> findTicketsByFirstName(String firstName);

    List<Ticket> findTicketsByLastName(String lastName);

    List<Ticket> findTicketsByFirstNameAndLastName(String firstName, String lastName);

    List<Ticket> findAllByDiscount_Code(String code);

    List<Ticket> findAllByTrip_Route_ArrivalCity(String arrivalCity);

    List<Ticket> findAllByTrip_Route_DepartureCity(String departureCity);

    List<Ticket> findAllByTrip_ArrivalDate(LocalDateTime arrivalDate);

    List<Ticket> findAllByTrip_DepartureDate(LocalDateTime departureDate);

    @Query(value = "select * from tickets ti inner join trip tr " +
            "on ti.trip_trip_id = tr.trip_id where tr.departure_date < ?1", nativeQuery = true)
    List<Ticket> findByDepartureDateLessThan(LocalDateTime dateTime);

    @Query(value = "select * from tickets ti inner join trip tr " +
            "on ti.trip_trip_id = tr.trip_id where tr.departure_date >= ?1", nativeQuery = true)
    List<Ticket> findByDepartureDateGreaterThanEqual(LocalDateTime dateTime);

    @Query(value = "select * from tickets ti inner join trip tr " +
            "on ti.trip_trip_id = tr.trip_id where tr.arrival_date < ?1", nativeQuery = true)
    List<Ticket> findByArrivalLessThan(LocalDateTime dateTime);

    @Query(value = "select * from tickets ti inner join trip tr " +
            "on ti.trip_trip_id = tr.trip_id where tr.arrival_date >= ?1", nativeQuery = true)
    List<Ticket> findByArrivalDateGreaterThanEqual(LocalDateTime dateTime);


}
