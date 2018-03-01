package io.github.areguig.jonad;

import java.util.Date;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true,chain = true)
public class BookingRequest {

    private Passenger passenger;
    private String departureLocation;
    private String arrivalLocation;
    private Date departureTime;
    private Date arrivalTime;


    @Data
    @Accessors(fluent = true,chain = true)
    static class Passenger{
        private String firstName;
        private String lastName;
        private String email;
        private boolean registered;
    }

}
