package io.github.areguig.jonad;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

class ExternalApi {

    static ExternalApi.Passenger registerPassengerRecord1(ExternalApi.Passenger passenger) {

        Passenger result = new Passenger()
                .firstName(passenger.firstName())
                .lastName(passenger.lastName())
                .email(passenger.email())
                .registered(true);
            throw new RuntimeException("Unable to register " + passenger);
        //System.out.println("registering passenger (1)");
        //return result;

    }


    static ExternalApi.Passenger registerPassengerRecord2(ExternalApi.Passenger passenger) {
        System.out.println("registering passenger (2)");
        return new ExternalApi.Passenger()
                .firstName(passenger.firstName())
                .lastName(passenger.lastName())
                .email(passenger.email())
                .registered(true);
    }


    static ExternalApi.Booking book(ExternalApi.Booking booking) {
        System.out.println("Booking.. ");
        //throw new RuntimeException("Unable to book ");
        return booking.bookId(1 + (Math.random() * 1000));
    }


    @Data
    @Accessors(fluent = true, chain = true)
    static class Booking {

        private ExternalApi.Passenger passenger;
        private String departureLocation;
        private String arrivalLocation;
        private Date departureTime;
        private Date arrivalTime;
        private Double bookId;
    }


    @Data
    @Accessors(fluent = true, chain = true)
    static class Passenger {
        private String firstName;
        private String lastName;
        private String email;
        private boolean registered;
    }
}
