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
import rva.jpa.Projekat;
import rva.jpa.Smer;
import rva.repository.SmerRepository;

@CrossOrigin
@RestController
@Api(tags = {"Smer CRUD operacije"})
public class SmerRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SmerRepository smerRepository;
	
	@GetMapping("smer")
	@ApiOperation(value = "Vraća kolekciju svih smerova iz baze podataka")
	public Collection<Smer> getSmerovi() {
		return smerRepository.findAll();
	}
	
	@GetMapping("smer/{id}")
	@ApiOperation(value = "Vraća smer iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public Smer getSmer(@PathVariable("id") Integer id) {
		return smerRepository.getOne(id);
	}
	
	@GetMapping("smerNaziv/{naziv}")
	@ApiOperation(value = "Vraća kolekciju svih smerova iz baze podataka koji u nazivu sadrže string prosleđen kao path varijabla")
	public Collection<Smer> getSmerByNaziv(@PathVariable("naziv") String naziv) {
		return smerRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("smer")
	@ApiOperation(value = "Upisuje smer u bazu podataka")
	public ResponseEntity<Smer> insertSmer(@RequestBody Smer smer) {
	if(!smerRepository.existsById(smer.getId())) {
		smerRepository.save(smer);
		return new ResponseEntity<Smer>(HttpStatus.OK);
	}
		return new ResponseEntity<Smer>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("smer")
	@ApiOperation(value = "Modifikuje postojeći smer u bazi podataka")
	public ResponseEntity<Smer> updateSmer(@RequestBody Smer smer){
		if(!smerRepository.existsById(smer.getId())) {
			return new ResponseEntity<Smer>(HttpStatus.NO_CONTENT);
		}
		smerRepository.save(smer);
		return new ResponseEntity<Smer>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("smer/{id}")
	@ApiOperation(value = "Briše smer iz baze podataka čiji je id vrednost prosleđena kao path varijabla")
	public  ResponseEntity<Smer> deleteSmer(@PathVariable("id") Integer id) {
		if(!smerRepository.existsById(id)) {
			return new ResponseEntity<Smer>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from grupa where smer = " + id);
		smerRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"smer\"(\"id\",\"naziv\",\"oznaka\")"
					+ "VALUES(-100, 'Naziv Test', 'Test Oznaka')"
					);
		}
		return new ResponseEntity<Smer>(HttpStatus.OK);
	}
	
}

