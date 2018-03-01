package io.github.areguig.jonad;

public final class Mapper {

    static ExternalApi.Passenger toApiPassenger(BookingRequest.Passenger passenger){
        return new ExternalApi.Passenger()
                .firstName(passenger.firstName())
                .lastName(passenger.lastName())
                .email(passenger.email());
    }
}
