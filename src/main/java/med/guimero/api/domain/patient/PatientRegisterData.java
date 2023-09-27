package med.guimero.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.guimero.api.domain.address.AddressData;

import java.time.LocalDate;

public record PatientRegisterData(
    @NotBlank String name,
    @NotBlank @Pattern(regexp = "\\d{8}") String dni,
    @NotNull LocalDate birthdate,
    @NotBlank @Pattern(regexp = "\\d{8,20}") String phone,
    @NotBlank @Email String email,
    @NotNull @Valid AddressData address){
}
