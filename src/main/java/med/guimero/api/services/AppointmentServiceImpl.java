package med.guimero.api.services;

import med.guimero.api.domain.appointment.*;
import med.guimero.api.domain.appointment.validations.AppointmentValidator;
import med.guimero.api.domain.doctor.Doctor;
import med.guimero.api.domain.doctor.DoctorRepository;
import med.guimero.api.domain.patient.PatientRepository;
import med.guimero.api.infra.errors.IntegrityValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(PatientRepository patientRepository,
                                  DoctorRepository doctorRepository,
                                  AppointmentRepository appointmentRepository)
    { this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;}

    @Autowired
    List<AppointmentValidator> appointmentValidatorList;


    @Override
    public AppointmentShowData save(AppointmentRegisterData data){
        if (!patientRepository.findById(data.patientId()).isPresent()){
            throw new IntegrityValidation("Id paciente no encontrado");
        }
        if (data.doctorId()!=null && !doctorRepository.existsById(data.doctorId())){
            throw new IntegrityValidation("Id médico no encontrado");
        }

        appointmentValidatorList.forEach(v->v.validate(data));

        var patient = patientRepository.findById(data.patientId()).get();
        var doctor = chooseDoctor(data);
        var appointment = new Appointment(null, patient, doctor, data.date(), doctor.getSpecialty());
        appointmentRepository.save(appointment);
        return new AppointmentShowData(appointment);
    }


    public Doctor chooseDoctor(AppointmentRegisterData data){
        if(data.doctorId()!=null){
            return doctorRepository.getReferenceById(data.doctorId());
        }
        if(data.specialty()==null){
            throw new IntegrityValidation("La especialidad no debe ser nula.");
        }
        return doctorRepository.chooseDoctorBySpecialtyAndDate(data.specialty(), data.date());
    }

    /*Save original, previo a aplicar lógicas de validación del negocio.
    @Override
    public AppointmentShowData save(AppointmentRegisterData appointmentRegisterData) {
        Appointment appointment = new Appointment(appointmentRegisterData);
        this.appointmentRepository.save(appointment);
        return new AppointmentShowData(appointment);
    }*/

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
        this.appointmentRepository.deleteById(id);
        return true;
    }
}
