package demo.pgi.services;

import demo.pgi.entity.Student;
import demo.pgi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainService {
    @Autowired
    private StudentRepository studentRepository;
    public Student saveStudent(Student student){
        return studentRepository.save(student);
    }

    public Student loginStudent(String username, String password) {
        Student s = this.studentRepository.findByUsername(username);
        if (s != null){
            if(s.getPassword().equals(password)){
                return s;
            }
        }
        return null;
    }
}
