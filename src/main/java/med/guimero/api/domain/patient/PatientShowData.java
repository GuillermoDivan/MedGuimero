package med.guimero.api.domain.patient;

import java.time.LocalDate;

public record PatientShowData(Long id, String name, String dni, LocalDate birthdate,
                              String phone) {

    public PatientShowData(Patient patient){
        this(patient.getId(), patient.getName(), patient.getDni(), patient.getBirthdate(),
                patient.getPhone());
    }
}
