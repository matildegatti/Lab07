package it.polito.tdp.poweroutages.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	PowerOutageDAO podao;
	List<PowerOutage> sequenzafinale;
	int personecoinvolte;
	float oredisservizio;
	
	public Model() {
		podao = new PowerOutageDAO();
		personecoinvolte=0;
		sequenzafinale=new ArrayList<PowerOutage>();
		oredisservizio=0;
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}

	public List<PowerOutage> getSottoinsieme(int anni, int ore, Nerc nerc) {
		List<PowerOutage> parziale=new ArrayList<>();
		List<PowerOutage> powernerc=new ArrayList<>();
		
		powernerc=this.podao.getPowerOutageNerk(nerc);
		int livello=0;
		this.calcolaSottoinsieme(parziale,powernerc,livello,anni,ore);
		return sequenzafinale;
	}

	private void calcolaSottoinsieme(List<PowerOutage> parziale, List<PowerOutage> powernerc, int livello, int anni, int ore) {
		float totaleore=this.calcolaOreDisservizio(parziale);
		
		if(totaleore>0)
			return;
		else if(this.calcolaPersoneCoinvolte(parziale)>this.personecoinvolte) {
			this.personecoinvolte=this.calcolaPersoneCoinvolte(parziale);
			this.sequenzafinale=new ArrayList<>(parziale);
			this.oredisservizio=totaleore;
		}
		
		if(livello==powernerc.size())  //non ho più power outage da aggiungere
			return;
		
		parziale.add(powernerc.get(livello));
		
		if(this.calcolaDifferenzaAnni(parziale)<=anni) {
				this.calcolaSottoinsieme(parziale, powernerc, livello+1, anni, ore);
		}
		
		parziale.remove(powernerc.get(livello));
		this.calcolaSottoinsieme(parziale, powernerc, livello+1, anni, ore);
	}

	private int calcolaPersoneCoinvolte(List<PowerOutage> parziale) {
		int cont=0;
		for(PowerOutage p:parziale) {
			cont+=p.numpersone;
		}
		return cont;
	}

	private int calcolaDifferenzaAnni(List<PowerOutage> parziale) {
		if(parziale.size()==0)
			return 0;
		LocalDateTime ld=parziale.get(0).datainizio;
		LocalDateTime ldt=parziale.get(0).datafine;
		
		for(PowerOutage po:parziale) {
			if(po.datainizio.isBefore(ld))
				ld=po.datainizio;
			if(po.datafine.isAfter(ldt))
				ld=po.datafine;
		}
		
		return ldt.getYear()-ld.getYear();
	}

	private float calcolaOreDisservizio(List<PowerOutage> parziale) {
		float somma=0;
		for(PowerOutage p:parziale) {
			long inizio=p.datainizio.toEpochSecond(ZoneOffset.UTC);
			long fine=p.datafine.toEpochSecond(ZoneOffset.UTC);
			float diff=(float) (fine-inizio);
			somma+=(diff/3600);
		}
		return somma;
	}
	
	public int getPersoneCoinvolte() {
		return this.personecoinvolte;
	}
	
	public float getOreDisservizio() {
		return this.oredisservizio;
	}
	

}
