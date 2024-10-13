package HW1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class Main {

	static final Semaphore tables = new Semaphore(3);
	static final Semaphore waiter = new Semaphore(1);

	static List<Thread> list = new ArrayList<Thread>();

	private static boolean isAvailableHours = true;

	public static synchronized boolean isOpen () {
		return isAvailableHours;
	}

	public static synchronized void closeCafe () {
		isAvailableHours = false;
		System.err.println("=============Кафе закрили================");
	}

	public static void main(String[] args) throws InterruptedException {
		Runnable cafe = () -> {
			int visitors = 0;
			while(isOpen()) {
				Thread thr = new Thread(new People(), String.valueOf(visitors));
				thr.start();
				list.add(thr);
				visitors++;
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		Thread cafeThread = new Thread(cafe, "Кафе");
		cafeThread.start();
		Thread.sleep(6000);
		closeCafe();

		cafeThread.join();

		for(Thread thr: list){
			thr.join();
		}

		System.err.println("=============Персонал пішов додому================");

	}

}

