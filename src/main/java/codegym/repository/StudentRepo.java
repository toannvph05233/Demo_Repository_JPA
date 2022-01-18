package codegym.repository;

import codegym.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface StudentRepo extends PagingAndSortingRepository<Student, Long> {


    @Query(value = "select s from Student s join ClassRoom c on s.classRoom.id = c.id where c.name =:name")
    public ArrayList<Student> findByNameClassZoom(@Param(value = "name") String name);

    @Query(nativeQuery = true, value = "select * from student order by name")
    public Page<Student> findAllOrderByName(Pageable pageable);}
