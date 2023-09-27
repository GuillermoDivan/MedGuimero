package med.guimero.api.domain.doctor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import med.guimero.api.domain.address.AddressData;

public record DoctorRegisterData(
        @NotBlank String name,
        @NotNull Specialty specialty,
        @NotBlank @Pattern(regexp = "\\d{3,10}") String license,
        @NotBlank @Pattern(regexp = "\\d{8,20}") String phone,
        @NotBlank @Email String email,
        @NotNull @Valid AddressData address) {
}


