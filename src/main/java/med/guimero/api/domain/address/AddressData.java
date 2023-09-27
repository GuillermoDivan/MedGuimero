package med.guimero.api.domain.address;
import jakarta.validation.constraints.NotBlank;

public record AddressData(
        @NotBlank String city,
        @NotBlank String district,
        @NotBlank String street,
        @NotBlank String number,
        @NotBlank String complement) {

}
