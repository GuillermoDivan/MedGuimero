package med.guimero.api.services;

import med.guimero.api.domain.appointment.AppointmentRegisterData;
import med.guimero.api.domain.appointment.AppointmentShowData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AppointmentService {

    AppointmentShowData save(AppointmentRegisterData appointmentRegisterData);
    Page<AppointmentShowData> findAll(Pageable paging);
    Page<AppointmentShowData> findAllDoctorAppointments(Long doctorId, Pageable paging);
    Page<AppointmentShowData> findAllPatientAppointments(Long patientId, Pageable paging);
    boolean delete(Long id);

}
