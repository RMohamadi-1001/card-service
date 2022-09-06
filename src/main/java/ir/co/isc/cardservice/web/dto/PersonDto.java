package ir.co.isc.cardservice.web.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersonDto {

    private Long id;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String nationalCode;

    @NonNull
    private String homeAddress;

    @NonNull
    private String phoneNumber;
}
