package med.guimero.api.services;
import jakarta.persistence.EntityNotFoundException;
import med.guimero.api.domain.doctor.*;
import med.guimero.api.domain.doctor.Doctor;
import med.guimero.api.domain.doctor.DoctorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Page<DoctorShowData> findAll(boolean active,Pageable paging) {
        return this.doctorRepository.findAllByActive(active, paging).map(DoctorShowData::new);
    }

    @Override
    public DoctorShowData findById(Long id) {
        Doctor doctor = this.doctorRepository.findByIdAndActive(id, true).
                orElseThrow(()-> new EntityNotFoundException());
        DoctorShowData DoctorShowData = new DoctorShowData(doctor);
        return DoctorShowData;
    }

    @Override
    public DoctorShowData findByName(String name) {
        Doctor doctor = this.doctorRepository.findByNameAndActive(name, true).
                orElseThrow(()-> new EntityNotFoundException());
        DoctorShowData DoctorShowData = new DoctorShowData(doctor);
        return DoctorShowData;
    }

    @Override
    public DoctorShowData save(DoctorRegisterData doctorRegisterData) {
        Doctor doctor = new Doctor(doctorRegisterData);
        this.doctorRepository.save(doctor);
        return new DoctorShowData(doctor);
    }

    @Override
    public DoctorShowData update(DoctorUpdateData doctorUpdateData) {
        Doctor doctor = new Doctor(doctorUpdateData);
        Doctor doctorToUpdate = this.doctorRepository.findById(doctor.getId()).orElse(null);

        if (doctorToUpdate.getActive()){
        if (doctor.getName() != null) {
            doctorToUpdate.setName(doctor.getName());
        }
        if (doctor.getEmail() != null) {
            doctorToUpdate.setEmail(doctor.getEmail());
        }
        if (doctor.getPhone() != null) {
            doctorToUpdate.setPhone(doctor.getPhone());
        }
        if (doctor.getAddress() != null) {
            doctorToUpdate.setAddress(doctor.getAddress());
        }
            this.doctorRepository.save(doctorToUpdate);}
            return new DoctorShowData(doctorToUpdate);
        }

    @Override
    public boolean turnOffDoctor(Long id) {
        Doctor doctorToTurnOff = this.doctorRepository.findById(id).orElse(null);
        if (doctorToTurnOff.getActive()) {
            doctorToTurnOff.setActive(false);
            this.doctorRepository.save(doctorToTurnOff);
            return true;
        }
        return false;
    }

    /*@Override
            public boolean delete(Long id) {
            this.doctorRepository.deleteById(id);
            return true;
    }*/

}