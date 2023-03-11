package demo.pgi.services;

import demo.pgi.entity.Students;
import demo.pgi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private StudentRepository studentRepository;
    public Students saveStudent(Students student){
        return studentRepository.save(student);
    }

    public Students loginStudent(String username, String password) {
        Students s = this.studentRepository.findByUsername(username);
        if (s != null){
            if(s.getPassword().equals(password)){
                return s;
            }
        }
        return null;
    }
}
