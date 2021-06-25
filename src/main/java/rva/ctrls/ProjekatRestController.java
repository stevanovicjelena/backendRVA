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
import rva.repository.ProjekatRepository;

@CrossOrigin
@RestController
@Api(tags = {"Projekat CRUD operacije"})
public class ProjekatRestController {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ProjekatRepository projekatRepository;
	
	@GetMapping("projekat")
	@ApiOperation(value = "Vraca kolekciju svih projekata iz baze podataka")
	public Collection<Projekat> getProjekti() {
		return projekatRepository.findAll();
	}
	
	@GetMapping("projekat/{id}")
	@ApiOperation(value = "Vraca projekat iz baze podataka ciji je id vrednost prosledjena kao path varijabla")
	public Projekat getProjekat(@PathVariable("id") Integer id) {
	return projekatRepository.getOne(id);	
	}
	
	@GetMapping("projekatNaziv/{naziv}")
	@ApiOperation(value = "Vraca kolekciju svih projekata iz baze podataka koji u nazivu sadrze string prosledjen kao path varijabla")
	public Collection<Projekat> getProjekatByNaziv(@PathVariable("naziv") String naziv){
		return projekatRepository.findByNazivContainingIgnoreCase(naziv);
	}
	
	@PostMapping("projekat")
	@ApiOperation(value = "Upisuje projekat u bazu podataka")
	public ResponseEntity<Projekat> insertProjekat(@RequestBody Projekat projekat) {
		if(!projekatRepository.existsById(projekat.getId())) {
			projekatRepository.save(projekat);
			return new ResponseEntity<Projekat>(HttpStatus.OK);
		}
		return new ResponseEntity<Projekat>(HttpStatus.CONFLICT);
	}
	
	@PutMapping("projekat")
	@ApiOperation(value = "Modifikuje postojeci projekat u bazi podataka ciji je id vrednost prosledjena kao path varijabla")
	public ResponseEntity<Projekat> updateProjekat(@RequestBody Projekat projekat) {
		if(!projekatRepository.existsById(projekat.getId())) {
			return new ResponseEntity<Projekat>(HttpStatus.NO_CONTENT);
		}
		projekatRepository.save(projekat);
		return new ResponseEntity<Projekat>(HttpStatus.OK);
	}
	
	@Transactional
	@DeleteMapping("projekat/{id}")
	@ApiOperation(value = "Brise projekat iz baze podataka ciji je id vrednost prosledjena kao path varijabla")
	public  ResponseEntity<Projekat> deleteProjekat(@PathVariable("id") Integer id) {
		if(!projekatRepository.existsById(id)) {
			return new ResponseEntity<Projekat>(HttpStatus.NO_CONTENT);
		}
		jdbcTemplate.execute("delete from student where projekat = " + id);
		projekatRepository.deleteById(id);
		if(id == -100) {
			jdbcTemplate.execute(
					"INSERT INTO \"projekat\"(\"id\", \"naziv\", \"oznaka\", \"opis\") "
					+ "VALUES(-100, 'Naziv Test', 'Test Oznaka', 'Test Opis)"
					);
		}
		return new ResponseEntity<Projekat>(HttpStatus.OK);
	}
	
	
}
