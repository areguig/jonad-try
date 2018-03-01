package io.github.areguig.jonad;

import java.util.Date;

import static io.github.areguig.jonad.Mapper.toApiPassenger;

public class LegacyBookingProcessor {

    private static BookingResponse process(BookingRequest bookingRequest) {

        if (bookingRequest == null || bookingRequest.passenger() == null) {
            throw new RuntimeException("Bad Booking Request");
        }

        ExternalApi.Passenger apiPassenger = toApiPassenger(bookingRequest.passenger());

        ExternalApi.Passenger passengerReply;
        try {
            passengerReply = ExternalApi.registerPassengerRecord1(apiPassenger);
        } catch (Exception e) {
            passengerReply = ExternalApi.registerPassengerRecord2(apiPassenger);
        }

        ExternalApi.Booking bookReply;
        try {
            bookReply = ExternalApi.book(new ExternalApi.Booking()
                    .passenger(passengerReply)
                    .arrivalLocation(bookingRequest.arrivalLocation())
                    .arrivalTime(bookingRequest.arrivalTime())
                    .departureLocation(bookingRequest.departureLocation()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new BookingResponse().arrivalLocation(bookReply.arrivalLocation())
                .arrivalTime(bookReply.arrivalTime())
                .bookId(bookReply.bookId())
                .departureLocation(bookReply.departureLocation())
                .arrivalLocation(bookReply.arrivalLocation())
                .passenger(bookingRequest.passenger().registered(passengerReply.registered()));
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




