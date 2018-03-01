package io.github.areguig.jonad;

import java.util.Date;
import java.util.Optional;

import static io.github.areguig.jonad.Mapper.toApiPassenger;

public class MonadicBookingProcessor {


    private static BookingResponse process(BookingRequest bookingRequest) {

        BookingRequest.Passenger bookingPassenger = Optional.ofNullable(bookingRequest)
                .map(BookingRequest::passenger)
                .orElseThrow(() -> new RuntimeException("Bad Booking Request"));

        Try<ExternalApi.Passenger> apiPassenger = Try.with(() -> bookingPassenger)
                .flatMap(p -> Try.with(() -> toApiPassenger(p)));


        Try<ExternalApi.Passenger> passengerReply
                = apiPassenger.flatMap(passenger -> Try.with(() -> ExternalApi.registerPassengerRecord1(passenger))
                .recoverWith(e -> Try.with(() -> ExternalApi.registerPassengerRecord2(passenger))));


        Try<ExternalApi.Booking> bookReply = passengerReply
                .flatMap(pr -> Try.with(() -> ExternalApi.book(new ExternalApi.Booking()
                        .passenger(pr)
                        .arrivalLocation(bookingRequest.arrivalLocation())
                        .arrivalTime(bookingRequest.arrivalTime())
                        .departureLocation(bookingRequest.departureLocation()))));


        return new BookingResponse().arrivalLocation(bookReply.get().arrivalLocation())
                .arrivalTime(bookReply.get().arrivalTime())
                .bookId(bookReply.get().bookId())
                .departureLocation(bookReply.get().departureLocation())
                .arrivalLocation(bookReply.get().arrivalLocation())
                .passenger(bookingRequest.passenger().registered(passengerReply.get().registered()));
    }

    public static void main(String[] a) {
        System.out.println(process(
                new BookingRequest().arrivalLocation("CDG")
                        .arrivalTime(new Date())
                        .departureLocation("NCE")
                        .departureTime(new Date())
                        .passenger(new BookingRequest.Passenger().firstName("Homer")
                                .lastName("Simpson")
                                .email("hsimpson@gmail.com"))
        ));
    }
}
