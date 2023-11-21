package med.guimero.api.services.appointment;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.appointment.*;
import med.guimero.api.repositories.AppointmentRepository;
import med.guimero.api.validations.AppointmentValidator;
import med.guimero.api.domain.doctor.Doctor;
import med.guimero.api.repositories.DoctorRepository;
import med.guimero.api.repositories.PatientRepository;
import med.guimero.api.infra.errors.IntegrityValidation;
import med.guimero.api.validations.CancelRequiredAnticipation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final List<AppointmentValidator> appointmentValidatorList;
    private final CancelRequiredAnticipation cancelRequiredAnticipation;

    @Override
    public AppointmentShowData save(AppointmentRegisterData registerData){
        if (registerData.id() != null) { throw new EntityExistsException(); }
        //Debería largar un error que indique que el id debe enviarse nulo para proceder a registrar.
        appointmentValidatorList.forEach(v->v.validate(registerData));

        var patient = patientRepository.findById(registerData.patientId()).get();
        var doctor = chooseDoctor(registerData);
        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setDate(registerData.date());
        appointment.setSpecialty(registerData.specialty());
        appointmentRepository.save(appointment);
        return new AppointmentShowData(appointment);
    }

    @Override
    public Doctor chooseDoctor(AppointmentRegisterData registerData){
        if(registerData.doctorId()!=null){
            return doctorRepository.getReferenceById(registerData.doctorId()); }
        if(registerData.specialty()==null){
            throw new IntegrityValidation("La especialidad no debe ser nula."); }
        return doctorRepository.chooseDoctorBySpecialtyAndDate(registerData.specialty(), registerData.date());
    }

    @Override
    public Page<AppointmentShowData> findAll(Pageable paging) {
        return this.appointmentRepository.findAll(paging).map(AppointmentShowData::new);
    }

    @Override
    public Page<AppointmentShowData> findAllDoctorAppointments
            (Long doctorId, Pageable paging) {
        return this.appointmentRepository.findAllByDoctorId(doctorId, paging)
                .map(AppointmentShowData::new);
    }

    @Override
    public Page<AppointmentShowData> findAllPatientAppointments
            (Long patientId, Pageable paging) {
        return this.appointmentRepository.findAllByPatientId(patientId, paging)
                .map(AppointmentShowData::new);
    }


    @Override
    public boolean delete(Long id) {
        Appointment appointment = this.appointmentRepository.findById(id).get();
        if (appointment == null) { return false;}
        cancelRequiredAnticipation.validate(id);
        this.appointmentRepository.deleteById(id);
        return true;
    }
}
