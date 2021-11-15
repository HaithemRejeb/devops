package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Departement;

import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EntrepriseRepository;

@Service
public class EntrepriseServiceImpl implements IEntrepriseService {

	private final org.slf4j.Logger l =  LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    EntrepriseRepository entrepriseRepoistory;
	@Autowired
	DepartementRepository deptRepoistory;
	
	public int ajouterEntreprise(Entreprise entreprise) {
		l.trace("this is a trace message");
		l.error("this is a error message"); 
	
		entrepriseRepoistory.save(entreprise);
		return entreprise.getId();
	}

	public int ajouterDepartement(Departement dep) {
		deptRepoistory.save(dep);
		return dep.getId();
	}
	
	public Boolean affecterDepartementAEntreprise(int depId, int entrepriseId) {
		Boolean isAffect=false;
		Entreprise entrepriseOpt = entrepriseRepoistory.findById(entrepriseId).orElseThrow(null);
		Entreprise entreprise = entrepriseOpt;
		Departement departementOpt = deptRepoistory.findById(depId).orElseThrow(null);
		Departement departement = departementOpt;	
		if (departement != null){		
		    departement.setEntreprise(entreprise);
	        deptRepoistory.save(departement);
	        isAffect=true;
	        }
		return isAffect;
	}
	
	public List<String> getAllDepartementsNamesByEntreprise(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		
		List<String> depNames = new ArrayList<>();
		if (entreprise != null)
		    for(Departement dep : entreprise.getDepartements()){
		    	depNames.add(dep.getName());
		    }
		
		return depNames;
	}

	@Transactional
	public void deleteEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		if (entreprise != null)
		    entrepriseRepoistory.delete(entreprise);	
	}
	@Transactional
	public void deleteDepartementById(int depId) {
		Optional<Departement> departementOpt = deptRepoistory.findById(depId);
		Departement departement = null;
		if (departementOpt.isPresent())
			departement = departementOpt.get();
		if (departement != null)
			deptRepoistory.delete(departement);		
	}


	public Entreprise getEntrepriseById(int entrepriseId) {
		Optional<Entreprise> entrepriseOpt = entrepriseRepoistory.findById(entrepriseId);
		Entreprise entreprise = null;
		if (entrepriseOpt.isPresent())
			entreprise = entrepriseOpt.get();
		return entreprise;
	}

}
