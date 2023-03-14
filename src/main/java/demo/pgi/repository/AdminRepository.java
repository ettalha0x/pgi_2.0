package demo.pgi.repository;

import demo.pgi.entity.Admins;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admins, Integer> {
    Admins findByUsername(String username);
}