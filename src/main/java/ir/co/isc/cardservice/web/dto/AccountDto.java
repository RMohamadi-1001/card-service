package ir.co.isc.cardservice.web.dto;


import lombok.*;

@Data
@AllArgsConstructor
public class AccountDto {
    private Long id;

    @NonNull
    private String accountNumber;

    @NonNull
    private Long personId;

    @NonNull
    private Long balance;

    private Boolean isActive;
}
