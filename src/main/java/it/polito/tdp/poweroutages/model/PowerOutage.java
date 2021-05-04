package it.polito.tdp.poweroutages.model;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PowerOutage {

	LocalDateTime datainizio;
	LocalDateTime datafine;
	Nerc nerc;
	int numpersone;
	
	public PowerOutage(LocalDateTime datainizio, LocalDateTime datafine, Nerc nerc, int numpersone) {
		this.datainizio = datainizio;
		this.datafine = datafine;
		this.nerc = nerc;
		this.numpersone = numpersone;
	}

	@Override
	public String toString() {
		return datainizio + " " + datafine + " " + nerc + " "
				+ numpersone;
	}
}
