package demo.pgi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import demo.pgi.entity.Students;

public interface StudentRepository extends JpaRepository<Students, Integer> {
    Students findByUsername(String username);
    Students findByEmail(String email);
}