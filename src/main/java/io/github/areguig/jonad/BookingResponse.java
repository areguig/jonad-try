package io.github.areguig.jonad;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true, chain = true)
public class BookingResponse {
    private BookingRequest.Passenger passenger;
    private String departureLocation;
    private String arrivalLocation;
    private Date departureTime;
    private Date arrivalTime;
    private Double bookId;
    private List<String> messages;
}
