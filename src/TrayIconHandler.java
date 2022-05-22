import java.awt.AWTException;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrayIconHandler {
	private static Logger LOGGER = LoggerFactory
			.getLogger(TrayIconHandler.class);

	private static TrayIcon trayIcon;

	public static boolean isExistsMenu(String menu) {
		if (isRegistered()) {
			return false;
		}

		for (int i = 0, j = getPopupMenu().getItemCount(); i < j; i++) {
			if (getPopupMenu().getItem(i) instanceof Menu) {
				Menu item = (Menu) getPopupMenu().getItem(i);
				if (item.getLabel().equals(menu)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public static void registerTrayIcon(Image image, String toolTip,
			ActionListener action) {
		if (SystemTray.isSupported()) {
			if (trayIcon != null) {
				trayIcon = null;
			}

//			trayIcon = new TrayIcon(image);
			trayIcon = new TrayIcon(image);
			trayIcon.setImageAutoSize(true);

			if (toolTip != null) {
				trayIcon.setToolTip(toolTip);
			}

			if (action != null) {
				trayIcon.addActionListener(action);
			}

			try {
				for (TrayIcon registeredTrayIcon : SystemTray.getSystemTray()
						.getTrayIcons()) {
					SystemTray.getSystemTray().remove(registeredTrayIcon);
				}

				SystemTray.getSystemTray().add(trayIcon);
			} catch (AWTException e) {
				LOGGER.error("I got catch an error during add system tray !", e);
			}
		} else {
			LOGGER.error("System tray is not supported !");
		}
	}
	
	public static void setToolTip(String toolTip) {
		if (isRegistered()) {
			return;
		}

		trayIcon.setToolTip(toolTip);
	}

	public static void setImage(Image image) {
		if (isRegistered()) {
			return;
		}

		trayIcon.setImage(image);
	}
	
	public static void displayMessage(String caption, String text,
			MessageType messageType) {
		if (isRegistered()) {
			return;
		}

		trayIcon.displayMessage(caption, text, messageType);
	}
	
	private static PopupMenu getPopupMenu() {
		PopupMenu popupMenu = trayIcon.getPopupMenu();

		if (popupMenu == null) {
			popupMenu = new PopupMenu();
		}

		return popupMenu;
	}

	private static void add(MenuItem item) {
		if (isNotRegistered()) {
			return;
		}

		PopupMenu popupMenu = getPopupMenu();
		popupMenu.add(item);

		trayIcon.setPopupMenu(popupMenu);
	}

	public static boolean isRegistered() {
		return (trayIcon != null && getPopupMenu() != null) ? true : false;
	}

	public static boolean isNotRegistered() {
		return !isRegistered();
	}

	public static void addItem(String label, ActionListener action) {
		MenuItem menuItem = new MenuItem(label);
		menuItem.addActionListener(action);

		add(menuItem);
	}
	
	protected static Image createImage(String path, String description){
		
		Path imgPath = Paths.get(path);
		
//		System.out.println(imgPath);
		
//		URL imageURL = TrayIconHandler.class.getResource(path);
		
		if(imgPath.equals(null)){
			System.err.println("해당 경로에서 찾을 수 없습니다" + path);
			return null;
		}else {
			return (new ImageIcon(imgPath.toString(), description)).getImage();
		}
	}	
}
