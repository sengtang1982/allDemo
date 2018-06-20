package com.ebitg.mybatis.temp;

import java.util.List;

public class Menu {
	private int id;
	private String menuName;
	private List<Menu> childNodes;
	private Menu parent;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<Menu> getChildNodes() {
		return childNodes;
	}

	public void setChildNodes(List<Menu> childNodes) {
		this.childNodes = childNodes;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer(
				"id=" + id + ", menuName=" + menuName + "<--" + parent + ", childNodes=\n\t");
		if (childNodes != null && childNodes.size() > 0) {
			sb.append(childNodes);
		}
		return sb.toString();
	}

}
