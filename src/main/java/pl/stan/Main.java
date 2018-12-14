package pl.stan;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import pl.stan.controller.DisplayController;

public class Main {

  public static void main(String[] args) throws Exception {
         Executor threadExecutor = Executors.newCachedThreadPool();
         new DisplayController().startServer(8000, threadExecutor);
         System.out.println("Library continues..");
  }
}
