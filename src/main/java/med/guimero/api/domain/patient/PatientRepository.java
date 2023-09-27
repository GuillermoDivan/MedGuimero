package med.guimero.api.domain.patient;
import med.guimero.api.domain.doctor.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository <Patient, Long> {
    Page<Patient> findAllByActive(Boolean active, Pageable paging);
    Optional<Patient> findByName(String name);
    Optional<Patient> findByIdAndActive(Long id, Boolean active);
    Optional <Patient> findByNameAndActive(String name, Boolean active);
}

