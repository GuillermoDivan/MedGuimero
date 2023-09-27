package med.guimero.api.domain.doctor;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.guimero.api.domain.address.AddressData;

public record DoctorUpdateData(@NotNull Long id, String name, String phone,
                               String email, @Valid AddressData address) {
}
