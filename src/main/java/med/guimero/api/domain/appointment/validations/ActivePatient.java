package med.guimero.api.domain.appointment.validations;

import jakarta.validation.ValidationException;
import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.patient.PatientRepository;
import org.springframework.stereotype.Component;

@Component
public class ActivePatient implements AppointmentValidator {

    private PatientRepository patientRepository;
    public ActivePatient(PatientRepository patientRepository){
        this.patientRepository = patientRepository;
    }

    public void validate(AppointmentRegisterData data){
        if (data.patientId() == null) {return;}
        var activePatient =patientRepository.findByIdAndActive(data.patientId(), true);
        if (!activePatient.isPresent()) { throw new ValidationException
                ("El paciente debe estar activo.");}
    }
}
