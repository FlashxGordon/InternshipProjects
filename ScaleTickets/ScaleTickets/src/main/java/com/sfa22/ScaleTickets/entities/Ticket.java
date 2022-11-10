package com.sfa22.ScaleTickets.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticket_id", unique = true, updatable = false, insertable = false, nullable = false)
    private int ticketId;

    @Column(name = "first_name", length = 30, unique = false, updatable = true, insertable = true, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 30, unique = false, updatable = true, insertable = true, nullable = false)
    private String lastName;

    @Column(name = "email", unique = false, updatable = true, insertable = true, nullable = true)
    private String email;

    @Column(name = "phone_number", length = 30, unique = false, updatable = true, insertable = true, nullable = true)
    private String phoneNumber;

    @Column(name = "seat_number", nullable = false)
    private int seatNumber;

    @Column(name = "ticket_price", unique = false, updatable = true, insertable = true, nullable = false)
    private double ticketPrice;

    @ManyToOne(optional = false)
    private Trip trip;

    @ManyToOne(optional = true)
    private Discount discount;

    public Ticket() {
    }

    public Ticket(String firstName, String lastName, String email, String phoneNumber,
                  double ticketPrice, Trip trip, Discount discount,
                  int seatNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ticketPrice = ticketPrice;
        this.trip = trip;
        this.discount = discount;
        this.seatNumber = trip.getRemainingSeats();
    }

    public Ticket(int ticketId, String firstName, String lastName,
                  String email, String phoneNumber, double ticketPrice,
                  Trip trip, Discount discount, int seatNumber) {
        this.ticketId = ticketId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.ticketPrice = ticketPrice;
        this.trip = trip;
        this.discount = discount;
        this.seatNumber = trip.getRemainingSeats();
    }

    public Ticket(int ticketId, String firstName, String lastName,
                  String email, String phoneNumber, double ticketPrice,
                  Trip trip, int seatNumber) {
        this.ticketId = ticketId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.trip = trip;
    }

    public Ticket(String firstName, String lastName,
                  String email, String phoneNumber, double ticketPrice,
                  Trip trip, int seatNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.seatNumber = seatNumber;
        this.ticketPrice = ticketPrice;
        this.trip = trip;
    }

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return ticketId == ticket.ticketId && seatNumber == ticket.seatNumber && Double.compare(ticket.ticketPrice, ticketPrice) == 0 && Objects.equals(firstName, ticket.firstName) && Objects.equals(lastName, ticket.lastName) && Objects.equals(email, ticket.email) && Objects.equals(phoneNumber, ticket.phoneNumber) && Objects.equals(trip, ticket.trip) && Objects.equals(discount, ticket.discount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ticketId, firstName, lastName, email, phoneNumber, seatNumber, ticketPrice, trip, discount);
    }
}
