package med.guimero.api.domain.appointment;
import med.guimero.api.domain.doctor.Specialty;
import java.time.LocalDateTime;

public record AppointmentShowData(Long id, Long patientId, Long doctorId,
                                  LocalDateTime date, Specialty specialty) {

    public AppointmentShowData(Appointment appointment) {
        this(appointment.getId(), appointment.getPatient().getId(),
                appointment.getDoctor().getId()
                ,appointment.getDate(), appointment.getSpecialty());
    }
}
