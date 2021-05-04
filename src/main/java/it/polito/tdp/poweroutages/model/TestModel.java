package it.polito.tdp.poweroutages.model;

import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		System.out.println(model.getNercList());
		
		PowerOutageDAO dao=new PowerOutageDAO();
		Nerc nerc=new Nerc(1,"ERCOT");
		List<PowerOutage> list=dao.getPowerOutageNerk(nerc);
		System.out.println(list);

	}

}
