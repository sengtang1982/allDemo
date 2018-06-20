
package com.ebitg.swt.demo01;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ArmEvent;
import org.eclipse.swt.events.ArmListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class Demo01 {
	public static void main(String[] args) {
		System.out.println("BEGIN");
		Display display = new Display();
		Shell shell = new Shell(display);
		Display defaultDisplay = Display.getDefault();
		shell.setBounds(defaultDisplay.getPrimaryMonitor().getBounds());
		Menu mainMenu = new Menu(shell, SWT.BAR);
		MenuItem fileItem = new MenuItem(mainMenu, SWT.CASCADE);
		fileItem.setText("нд╪Ч&F");
		fileItem.addArmListener(new ArmListener() {

			@Override
			public void widgetArmed(ArmEvent e) {
				System.out.println("ArmListener.widgetArmed");
			}
		});
		fileItem.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("SelectionListener.widgetSelected");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				System.out.println("SelectionListener.widgetDefaultSelected");
			}
		});
		shell.setMenuBar(mainMenu);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
