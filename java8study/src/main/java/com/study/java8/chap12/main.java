package com.study.java8.chap12;

public class main {

	public static void main(String[] args) throws InterruptedException {

		// 과거 Thread 생성 방식
		Thread orgthread = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				System.out.println("Thread: " + Thread.currentThread().getName());
			}
		});
		orgthread.start();

		// java8 의 Lambda 방식의 Thread 생성방식.
		// Runnable이 java8 에서 함수형인터페이스 변경이되었다.
		Thread ldthread = new Thread(() -> {
			System.out.println("ldThread: " + Thread.currentThread().getName());
		});
		ldthread.start();

		//////////////////////////
		Thread ldIntThread = new Thread(() -> {
			try {
				Thread.sleep(1000L);
				// InterruptedException 는 위 Sleep 명령어를 통해 자는동안 깨워지면 걸린다.
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Thread: " + Thread.currentThread().getName());

		});
		ldIntThread.start();
		System.out.println("Thread: " + Thread.currentThread().getName());
		
		/////////////////////////
		// Interrupt
		Thread ldIntThread2 = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(1000L);
					// InterruptedException 는 위 Sleep 명령어를 통해 자는동안 깨워지면 걸린다.
				} catch (InterruptedException e) {
					System.out.println("InterruptedException!");
					return;
				}
				System.out.println("Thread: " + Thread.currentThread().getName());
			}
		});
		ldIntThread2.start();
		System.out.println("Thread: " + Thread.currentThread().getName());
		Thread.sleep(3000L);

		// interrupt 는 종료시키는게 아닌 깨우는 역할을 하는것이다.
		ldIntThread2.interrupt();

		////////////////////////////////////
		// join
		Thread ldJoinThread2 = new Thread(() -> {
			try {
				System.out.println("start thread.");
				Thread.sleep(3000L);
				// InterruptedException 는 위 Sleep 명령어를 통해 자는동안 깨워지면 걸린다.
			} catch (InterruptedException e) {
				System.out.println("InterruptedException!");
				return;
			}
			System.out.println("Thread: " + Thread.currentThread().getName());

		});
		
		ldJoinThread2.start();
		System.out.println("Thread: " + Thread.currentThread().getName());
		ldJoinThread2.join();
		System.out.println(ldJoinThread2 + " is finished");

	}

}
