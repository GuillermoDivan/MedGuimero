package med.guimero.api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import med.guimero.api.domain.address.AddressData;

public record PatientUpdateData(@NotNull Long id, String name, String phone,
                                String email, @Valid AddressData address){}
