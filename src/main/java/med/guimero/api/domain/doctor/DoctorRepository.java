package med.guimero.api.domain.doctor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Page<Doctor> findAllByActive(Boolean active, Pageable paging);
    Optional<Doctor> findByIdAndActive(Long id, Boolean active);
    Optional <Doctor> findByNameAndActive(String name, Boolean active);

    //Save, FindAll, FindById, Delete ya se encuentran.

    @Query("""
            select d from Doctor d
            where d.active= true and
            d.specialty=:specialty and
            d.id not in(
            select a.doctor.id from Appointment a
            where a.date=:date
            )
            order by rand()
            limit 1
            """
    )
    Doctor chooseDoctorBySpecialtyAndDate(Specialty specialty, LocalDateTime date);


}
