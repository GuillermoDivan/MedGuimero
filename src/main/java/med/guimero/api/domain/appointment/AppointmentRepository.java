package med.guimero.api.domain.appointment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Page<Appointment> findAllByDoctorId(Long doctorId, Pageable paging);
    Page<Appointment> findAllByPatientId(Long patientId, Pageable paging);
    Boolean existsByPatientIdAndDateBetween (Long patientId,
                                           LocalDateTime firstSchedule, LocalDateTime lastSchedule);
    Boolean existsByDoctorIdAndDate(Long doctorId, LocalDateTime date);
}
