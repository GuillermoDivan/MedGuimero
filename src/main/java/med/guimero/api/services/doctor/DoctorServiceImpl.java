package med.guimero.api.services.doctor;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import med.guimero.api.domain.doctor.*;
import med.guimero.api.domain.doctor.Doctor;
import med.guimero.api.repositories.DoctorRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    @Override
    public DoctorShowData save(DoctorRegisterData doctorRegisterData) {
        Doctor doctor = new Doctor(doctorRegisterData);
        this.doctorRepository.save(doctor);
        return new DoctorShowData(doctor);
    }

    @Override
    public Page<DoctorShowData> findAll(boolean active,Pageable paging) {
        return this.doctorRepository.findAllByActive(active, paging).map(DoctorShowData::new);
    }

    @Override
    public DoctorShowData findById(Long id) throws EntityNotFoundException{
        Doctor doctor = this.doctorRepository.findByIdAndActive(id, true).
                orElseThrow(EntityNotFoundException::new);
        DoctorShowData DoctorShowData = new DoctorShowData(doctor);
        return DoctorShowData;
    }

    @Override
    public DoctorShowData findByName(String name) throws EntityNotFoundException{
        Doctor doctor = this.doctorRepository.findByNameAndActive(name, true).
                orElseThrow(EntityNotFoundException::new);
        DoctorShowData DoctorShowData = new DoctorShowData(doctor);
        return DoctorShowData;
    }

    @Override
    public DoctorShowData update(DoctorUpdateData doctorUpdateData) throws EntityNotFoundException{
        Doctor doctor = this.doctorRepository.findById(doctorUpdateData.id())
                .orElseThrow(EntityNotFoundException::new);
        if (doctor.getActive()){
        if (doctor.getName() != null) {
            doctor.setName(doctorUpdateData.name());
        }
        if (doctor.getEmail() != null) {
            doctor.setEmail(doctorUpdateData.email());
        }
        if (doctor.getPhone() != null) {
            doctor.setPhone(doctorUpdateData.phone());
        }
        if (doctor.getAddress() != null) {
            doctor.setAddress(doctor.getAddress());
        }
            this.doctorRepository.save(doctor);}
            return new DoctorShowData(doctor);
        }

    @Override
    public boolean turnOffDoctor(Long id) throws EntityNotFoundException{
        Doctor doctorToTurnOff = this.doctorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (doctorToTurnOff.getActive()) {
            doctorToTurnOff.setActive(false);
            this.doctorRepository.save(doctorToTurnOff);
            return true;
        }
        return false;
    }

    @Override
    public boolean reactivateDoctor(Long id) throws EntityNotFoundException{
        Doctor doctor = this.doctorRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (!doctor.getActive()) {
            doctor.setActive(true);
            this.doctorRepository.save(doctor);
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