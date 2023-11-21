package med.guimero.api.services.appointment;

import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.appointment.AppointmentShowData;
import med.guimero.api.domain.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentShowData save(AppointmentRegisterData appointmentRegisterData);
    Doctor chooseDoctor(AppointmentRegisterData data);
    Page<AppointmentShowData> findAll(Pageable paging);
    Page<AppointmentShowData> findAllDoctorAppointments(Long doctorId, Pageable paging);
    Page<AppointmentShowData> findAllPatientAppointments(Long patientId, Pageable paging);
    boolean delete(Long id);


}
