package me.it.hes.core.menus;

import me.it.lib.menu.*;

public class MainMenu extends Menu {
	
	private MenuButton btnPlay;
	private MenuButton btnLoad;
	private MenuButton btnOption;
	private MenuButton btnQuit;
	
	public MainMenu() {
		this.btnPlay = new MenuButton(this, "Play");
		this.btnLoad = new MenuButton(this, "Load");
		this.btnOption = new MenuButton(this, "Options");
		this.btnQuit = new MenuButton(this, "Quit");
		this.setName("Main Menu");
		this.setItems(new MenuItem[] {btnPlay, btnLoad, btnOption, btnQuit});
	}

	@Override
	public void OnClick(MenuItem sender) {
		
	}

	@Override
	public void OnHover(MenuItem sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnPress(MenuItem sender) {
		// TODO Auto-generated method stub

	}

	@Override
	public void OnRelease(MenuItem sender) {
		// TODO Auto-generated method stub

	}

}
