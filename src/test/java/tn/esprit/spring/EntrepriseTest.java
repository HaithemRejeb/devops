package tn.esprit.spring;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;
import tn.esprit.spring.services.IEntrepriseService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EntrepriseTest {

	private static final String MSG = "entrepriseTest1";
	@Autowired
	IEntrepriseService entrepriseService;

	@Autowired
	EntrepriseRepository entrepriseRepository;

	@Autowired
	DepartementRepository departementRerpository;

	int entrepriseId, departementId;

	@Before
	public void initialisation() {
		Entreprise entreprise = new Entreprise("Esprit", "Education");
		Departement departement = new Departement("mobile");
		entrepriseId = entrepriseService.ajouterEntreprise(entreprise);
		departementId = entrepriseService.ajouterDepartement(departement);

	}

	@After
	public void suppression() {
		entrepriseRepository.deleteById(entrepriseId);
		departementRerpository.deleteById(departementId);
	}

	@Test
	public void ajouterEntrepriseTest() {
		assertTrue("ajout  entreprise echouer", entrepriseRepository.findById(entrepriseId).isPresent());
	}

	@Test
	public void ajouterDepartementTest() {
		assertTrue("ajout departrement echouer", departementRerpository.findById(departementId).isPresent());
	}

	@Test
	public void affecterDepartementAEntrepriseTest() {
		Entreprise entreprise = new Entreprise();
		Departement departement = new Departement();
		int idDep = departementRerpository.save(departement).getId();
		int idEnt = entrepriseRepository.save(entreprise).getId();
		assertTrue("affectation echouer", entrepriseService.affecterDepartementAEntreprise(idDep, idEnt));
		departementRerpository.deleteById(idDep);
		entrepriseRepository.deleteById(idEnt);
	}

	@Test
	public void getAllDepartementsNamesByEntrepriseTest() {
		Entreprise ent = new Entreprise("entreprise To find", "");

		int entreId = entrepriseService.ajouterEntreprise(ent);
		Departement department = new Departement("department Test 1");

		int depId = entrepriseService.ajouterDepartement(department);

		Departement department2 = new Departement("department Test 2");

		int depId2 = entrepriseService.ajouterDepartement(department2);

		entrepriseService.affecterDepartementAEntreprise(depId, entreId);
		entrepriseService.affecterDepartementAEntreprise(depId2, entreId);

		List<String> result = entrepriseService.getAllDepartementsNamesByEntreprise(entreId);

		assertTrue("test echouer", result.contains("department Test 1") && result.size() == 2);
		entrepriseService.deleteDepartementById(depId);
		entrepriseService.deleteDepartementById(depId2);
		entrepriseService.deleteEntrepriseById(entreId);

	}
	@Test
	public void deleteEntreprisebyIdTest() throws InterruptedException  {
		int id=entrepriseRepository.save(new Entreprise()).getId();
		entrepriseService.deleteEntrepriseById(id);
		assertFalse("test echouer", entrepriseRepository.findById(id).isPresent());
	}
	@Test
	public void supprimerDepartementTest()
	{
		int id=departementRerpository.save(new Departement()).getId();
	     entrepriseService.deleteDepartementById(id);
	     assertFalse("test echouer",departementRerpository.findById(id).isPresent());
	}
	@Test
	public void getEntrepriseByIdTest()
	{
		assertNotNull("test echouer", entrepriseRepository.findById(entrepriseId));
	}

}