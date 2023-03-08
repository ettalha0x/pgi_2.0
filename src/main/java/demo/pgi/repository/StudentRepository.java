package demo.pgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import demo.pgi.entity.Student;
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByUsername(String username);
}
