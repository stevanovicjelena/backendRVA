package rva.ctrls;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
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
import rva.jpa.Smer;
import rva.jpa.Student;
import rva.repository.GrupaRepository;
import rva.repository.SmerRepository;

@CrossOrigin
@RestController
@Api(tags = {"Grupa CRUD operacije"})
public class GrupaRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private GrupaRepository grupaRepository;
	
	@Autowired
	private SmerRepository smerRepository;
	
	@GetMapping("grupa")
	@ApiOperation(value = "Vraća kolekciju svih grupa iz baze podataka")
	public Collection<Grupa> getGrupe() {
		return grupaRepository.findAll();
	}
	
	@GetMapping("grupa/{id}")
	@ApiOperation(value = "Vraća grupu iz baze podataka čija je id vrednost prosleđena kao path varijabla ")
	public Grupa getGrupa(@PathVariable("id") Integer id) {
		return grupaRepository.getOne(id);
	}
	
	@GetMapping("grupaOznaka/{oznaka}")
	@ApiOperation(value = "Vraća kolekciju svih grupa iz baze podataka koje u oznaci sadrže string prosleđen kao path varijabla")
	public Collection<Grupa> getGrupeByOznaka(@PathVariable("oznaka") String oznaka) {
		return grupaRepository.findByOznakaContainingIgnoreCase(oznaka);
	}
	
	@GetMapping("grupaSmer/{id}")
	@ApiOperation(value = "Vraća kolekciju svih grupa iz baze podataka čija je id vrednost smera prosleđena kao path varijabla")
	public Collection<Grupa> grupeSmerovi(@PathVariable("id") Integer id){
		Smer s = smerRepository.getOne(id);
		return grupaRepository.findBySmer(s);
	}
	
	@PostMapping("grupa")
	@ApiOperation(value = "Upisuje grupu u bazu podataka")
	public ResponseEntity<Grupa> insertGrupa(@RequestBody Grupa grupa) {
		if(!grupaRepository.existsById(grupa.getId())) {
			grupaRepository.save(grupa);
			return new ResponseEntity<Grupa>(HttpStatus.OK);
		}
		return new ResponseEntity<Grupa>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("grupa")
	@ApiOperation(value = "Modifikuje postojeću grupu u bazi podataka čija je id vrednost prosleđena kao path varijabla")
	public ResponseEntity<Grupa> updateGrupa(@RequestBody Grupa grupa) {
		if(!grupaRepository.existsById(grupa.getId())) {
			return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
		}
		grupaRepository.save(grupa);
		return new ResponseEntity<Grupa>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("grupa/{id}")
	@ApiOperation(value = "Briše grupu iz baze podataka čija je id vrednost prosleđena kao path varijabla")
	public  ResponseEntity<Grupa> deleteGrupa(@PathVariable("id") Integer id) {
		if(!grupaRepository.existsById(id)) {
			return new ResponseEntity<Grupa>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from student where grupa = " + id);
		grupaRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"grupa\"(\"id\",\"oznaka\",\"smer\")"
					+ "VALUES(-100, 'Oznaka Test', 1)"
					);
		}
		return new ResponseEntity<Grupa>(HttpStatus.OK);
	}
	
	
}
