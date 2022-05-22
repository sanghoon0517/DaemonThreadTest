import java.awt.Toolkit;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;

import javax.swing.ImageIcon;

/**
 * @author 전상훈
 * 
 * 데몬스레드를 돌리기 위해서 작성했지만
 * 
 * 메인 스레드 하나로 돌릴때가 더 이용률을 낮춰준다.
 *
 */
public class DaemonTest/* extends Thread*/{
	
//	private File dir = new File("C:/dev/test/");
	
	private static void deleteTxtFile(File file) {
		file.delete();
	}
	
	private static void threadSleep(long millsec) {
		try {
			Thread.sleep(millsec);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Override
//	public void run() {
//		// TODO Auto-generated method stub
////		synchronized(this) {
//			
//			if(!dir.exists()) {
//				dir.mkdirs();
//			}
//			
//			if(Thread.currentThread().isDaemon()) {
//				//무한루프 돌리기
//				System.out.println("데몬이 실행중입니다");
//				while(true) {
//					File[] txtList = dir.listFiles(new FilenameFilter() {
//						
//						@Override
//						public boolean accept(File dir, String name) {
//							// TODO Auto-generated method stub
//							boolean isTxt = false;
//							
//							if(name.toLowerCase().startsWith("새") && name.toLowerCase().endsWith(".txt") == true) isTxt = true;
//							
//							return isTxt;
//						}
//					});
//					
//					if(txtList.length > 0) {
//						threadSleep(600);
//						try {
//							for(File txt : txtList) {
//								//파일이 존재합니다.
//								System.out.println("파일이 존재합니다.");
//								threadSleep(800);
//								deleteTxtFile(txt);
//								System.out.println("파일을 삭제했습니다.");
//								threadSleep(800);
//							}
//						} catch(Exception e) {
//							threadSleep(400);
//							return;
//						}
//					}
//					
//					threadSleep(10000);
//				}
//			} else {
//				return;
//			}
//			
////		}
//		
//	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		TrayIconHandler.registerTrayIcon(
				Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemResource("blue_icon.png")),
				"TestDaemon",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// Open your application here.
						System.exit(0);
					}
				}
		);
		
		TrayIconHandler.addItem("Exit", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("메인 스레드 종료");
				System.exit(0);
			}
		});
		
		TrayIconHandler.displayMessage("TestDaemon", "The program is runnung!", MessageType.INFO);
		
//		DaemonTest daemon = new DaemonTest();
//		daemon.setDaemon(true);
//		daemon.start();
		
		File dir = new File("C:/dev/test/");
		
		while(true) {
			if(!dir.exists()) {
				dir.mkdirs();
			}
			
//			if(Thread.currentThread().isDaemon()) {
				//무한루프 돌리기
//				System.out.println("데몬이 실행중입니다");
			System.out.println("프로그램이 실행중입니다.");
				while(true) {
					File[] txtList = dir.listFiles(new FilenameFilter() {
						
						@Override
						public boolean accept(File dir, String name) {
							// TODO Auto-generated method stub
							boolean isTxt = false;
							
							if(name.toLowerCase().startsWith("새") && name.toLowerCase().endsWith(".txt") == true) isTxt = true;
							
							return isTxt;
						}
					});
					
					if(txtList.length > 0) {
//						threadSleep(600);
						try {
							for(File txt : txtList) {
								//파일이 존재합니다.
								System.out.println("파일이 존재합니다.");
								threadSleep(400);
								deleteTxtFile(txt);
								System.out.println("파일을 삭제했습니다.");
								threadSleep(400);
							}
						} catch(Exception e) {
//							threadSleep(400);
							return;
						}
					}
					
					threadSleep(800);
				}
//			} else {
//				return;
//			}
		}

	}

}
