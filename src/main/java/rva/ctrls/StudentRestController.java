package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import rva.jpa.Grupa;
import rva.jpa.Projekat;
import rva.jpa.Student;
import rva.repository.GrupaRepository;
import rva.repository.ProjekatRepository;
import rva.repository.StudentRepository;

@CrossOrigin
@RestController
@Api(tags = {"Student CRUD operacije"})

public class StudentRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private GrupaRepository grupaRepository;
	
	@Autowired
	private ProjekatRepository projekatRepository;
	
	@GetMapping("student")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka")
	public Collection<Student> getStudenti(){
		return studentRepository.findAll();
	}
	
	@GetMapping("student/{id}")
	@ApiOperation(value = "Vraća studenta iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public Student getStudent(@PathVariable("id") Integer id) {
		return studentRepository.getOne(id);
	}
	
	@GetMapping("studentIndex/{brIndx}")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka koji u broju indeksa sadrže string prosleđen kao path varijabla")
	public Collection<Student> studentiIndx(@PathVariable("brIndx") String brIndx) {
		return studentRepository.findByBrojIndeksaContainingIgnoreCase(brIndx);
	}
	
	@GetMapping("studentGrupa/{id}")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka čiji je id vrednost grupe prosleđena kao path varijabla")
	public Collection<Student> studentiGrupe(@PathVariable("id") Integer id){
		Grupa g = grupaRepository.getOne(id);
		return studentRepository.findByGrupa(g);
	}
	
	@GetMapping("studentProjekat/{id}")
	@ApiOperation(value = "Vraća kolekciju svih studenata iz baze podataka čiji je id vrednost projekta prosleđena kao path varijabla")
	public Collection<Student> studentiProjekti(@PathVariable("id") Integer id){
		Projekat p = projekatRepository.getOne(id);
		return studentRepository.findByProjekat(p);
	}
	
	@PostMapping("student")
	@ApiOperation(value = "Upisuje studenta u bazu podataka")
	public ResponseEntity<Student> insertStudent(@RequestBody Student student) {
		if(!studentRepository.existsById(student.getId())) {
			studentRepository.save(student);
			return new ResponseEntity<Student>(HttpStatus.OK);
		}
		return new ResponseEntity<Student>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("student")
	@ApiOperation(value = "Modifikuje postojećeg studenata u bazi podataka čiji je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
		if(!studentRepository.existsById(student.getId())) {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}
		studentRepository.save(student);
		return new ResponseEntity<Student>(HttpStatus.OK);
	}
	
	@DeleteMapping("student/{id}")
	@ApiOperation(value = "Briše studenta iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public  ResponseEntity<Student> deleteStudent(@PathVariable("id") Integer id) {
		if(!studentRepository.existsById(id)) {
			return new ResponseEntity<Student>(HttpStatus.NO_CONTENT);
		}
		studentRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"student\"(\"id\",\"ime\",\"prezime\", \"broj_indeksa\", \"grupa\", \"projekat\")"
					+ "VALUES(-100, 'Test Ime', 'Test Prezime', 'TTg2021', 1, 1)"
					);
		}
		return new ResponseEntity<Student>(HttpStatus.OK);
	}
}

