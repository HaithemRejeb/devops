package tn.esprit.spring;


import java.util.List;



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
public class EntreprieTest {
	
	
	
	private static final String MSG = "entrepriseTest1";
	@Autowired
	IEntrepriseService entreService;

	
	@Autowired
	EntrepriseRepository entreRep;
	
	@Autowired
	DepartementRepository departementRerpository;

	Entreprise entreprise;
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void ajouterDepartementTest() {
		Departement depTest = new Departement(MSG);
		
		int idDepartement=entreService.ajouterDepartement(depTest);

		Departement dep =departementRerpository.findById(idDepartement).orElse(null);
		
		if(idDepartement!=0 && dep!=null && dep.getName().equals(MSG)) 
				
					entreService.deleteDepartementById(idDepartement);	

	}



     

	
	

	@Test
	public void ajouterEntrepriseTest  ()
	{
		Entreprise entrepTest = new Entreprise (MSG,"raisonTest1");
		int entreId = entreService.ajouterEntreprise(entrepTest);
		if(entreId!=0)
		{
		Entreprise ent =entreRep.findById(entreId).orElse(null);
		
		if(ent !=null && ent.getName().equals(MSG)) 		
			entreService.deleteEntrepriseById(entreId);}
		
	}
	
	
	@Test
	public void affecterDepartementAEntrepriseTest ()
	{
		Entreprise entreprise2 = new Entreprise ("entrepriseTest2","raisonTest2");
		Departement department = new Departement("departmenTest1");
		int entreId=entreService.ajouterEntreprise(entreprise2);
		int depId=entreService.ajouterDepartement(department);
		
		entreService.affecterDepartementAEntreprise(depId, entreId);
        List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
        if(result.contains("departmenTest1") && result.size()==1) {
    		entreService.deleteDepartementById(depId);
    		entreService.deleteEntrepriseById(entreId);
        }
	   
	   
	}
	
	@Test
	public void deleteEntreprisebyIdTest ()
	{
		Entreprise entreprise3 = new Entreprise ("entrepriseTest3","raisonTest3");
		int idEntreprise=entreService.ajouterEntreprise(entreprise3);
		
		if(idEntreprise!=0) {
			Entreprise ent =entreRep.findById(idEntreprise).orElse(null);
			if(ent!=null)
				entreService.deleteEntrepriseById(idEntreprise);

		}
	}
	
	@Test
	public void getEntreprisebyIdTest ()
	{
		Entreprise entreprise4 = new Entreprise ("entrepriseTest4","raisonTest4");
		int entreId = entreService.ajouterEntreprise(entreprise4);
		entreService.getEntrepriseById(entreId);
	
		
		if(entreprise !=null ) 		
			entreService.deleteEntrepriseById(entreId);}
		
		


	
	
	@Test
	public void suprimerDepartementTest() {
		Departement depTest = new Departement("production");
		int idDepartement=entreService.ajouterDepartement(depTest);
		
		if(idDepartement!=0) {
			Departement ent =departementRerpository.findById(idDepartement).orElse(null);
			if(ent!=null)
				entreService.deleteEntrepriseById(idDepartement);
			
		}
	}
	
	@Test
	public void  getAllDepartementsNamesByEntrepriseTest() {
		this.entreprise = new Entreprise();
		this.entreprise.setName("entreprise To find");
		int entreId=entreService.ajouterEntreprise(this.entreprise);
		Departement department= new Departement();
		department.setName("department Test 1");
		int depId=entreService.ajouterDepartement(department);
		
		Departement department2= new Departement();
		department2.setName("department Test 2");
		int depId2=entreService.ajouterDepartement(department2);
		
		entreService.affecterDepartementAEntreprise(depId, entreId);
		entreService.affecterDepartementAEntreprise(depId2, entreId);
		
		List<String> result = entreService.getAllDepartementsNamesByEntreprise(entreId);
		
		 if(result.contains("department Test 1") && result.size()==2) {
	    		entreService.deleteDepartementById(depId);
	    		entreService.deleteDepartementById(depId2);
	    		entreService.deleteEntrepriseById(entreId);
	        }
		
		
		
		
		
	}
	
	

	
}