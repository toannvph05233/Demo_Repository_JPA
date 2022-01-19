package codegym.service;

import codegym.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IStudentService {
    public Page<Student> findAll(Pageable pageable);
    public List<Student> findAll();
    public void save(Student student);
    public void delete(long id);
    public Optional<Student> findById(long id);
}
