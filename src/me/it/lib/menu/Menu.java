package me.it.lib.menu;

public abstract class Menu implements MenuEventListener {

	private String name;
	private MenuItem[] items;
	private Menu[] subMenus;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuItem[] getItems() {
		return items;
	}

	public void setItems(MenuItem[] items) {
		this.items = items;
	}

	public Menu[] getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(Menu[] subMenus) {
		this.subMenus = subMenus;
	}

	public void Update() {
		for (MenuItem mi : items) {
			if (mi != null)
				mi.Update();
		}
	}
}
