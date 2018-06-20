package com.ebitg.adminLte;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Menu {
	private String title;
	private String href;
	private boolean active;
	private String icon;
	private List<Menu> subMenus = new ArrayList<Menu>();

	public static void main(String[] args) {
		Menu menu = new Menu();
	}

}
