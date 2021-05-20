# Java8
## 7부 CompletableFuture
### 14. 자바 Concurrent 프로그래밍 소개

  - Concurrent 소프트웨어
    - 동시에 여러 작업을 할 수 있는 소프트웨어
    - 예) 웹 브라우저로 유튜브를 보면서 키보드로 문서에 타이핑을 할 수 있다.
    - 예) 녹화를 하면서 인텔리J로 코딩을 하고 워드에 적어둔 문서를 보거나 수정할 수 있다.

  - 자바에서 지원하는 Concurrent 프로그래밍
    - 멀티프로세싱 (ProcessBuilder)
    - 멀티쓰레드
  - 자바 멀티쓰레드 프로그래밍
    - Thread / Runnable

  - Thread 상속

  ```java
    public static void main(String[] args) {
        HelloThread helloThread = new HelloThread();
        helloThread.start();
        System.out.println("hello : " + Thread.currentThread().getName());
    }
     static class HelloThread extends Thread {
        @Override
        public void run() {
            System.out.println("world : " + Thread.currentThread().getName  );
        }
    }
   ```
  - Runnable 구현 또는 람다
  ```java
  Thread thread = new Thread(() -> System.out.println("world : " + Thread.currentThread().getName()));
  thread.start();
  System.out.println("hello : " + Thread.currentThread().getName());
  ```

  - 쓰레드 주요 기능
    - 현재 쓰레드 멈춰두기 (sleep): 다른 쓰레드가 처리할 수 있도록 기회를 주지만 그렇다고 락을 놔주진 않는다. (잘못하면 데드락 걸릴 수 있겠죠.)
    - 다른 쓰레드 깨우기 (interrupt): 다른 쓰레드를 깨워서 interruptedException을 발생 시킨다. 그 에러가 발생했을 때 할 일은 코딩하기 느림. 종료 시킬 수도 있고 계속 하던 일 할 수도 있고.
    - 다른 쓰레드 기다리기 (join): 다른 쓰레드가 끝날 때까지 기다린다.
  - 참고:
    - https://docs.oracle.com/javase/tutorial/essential/concurrency/
    - https://docs.oracle.com/javase/8/docs/api/java/lang/Thread.html#interrupt--
    


    
    





    
