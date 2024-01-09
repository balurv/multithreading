package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));
        List<HackerThread> list = new ArrayList<>();
        AscendingHackerThread ascendingHackerThread = new AscendingHackerThread(vault);
        DescendingHackerThread descendingHackerThread = new DescendingHackerThread(vault);
        RandomHackerThread randomHackerThread = new RandomHackerThread(vault);
        PoliceThread policeThread = new PoliceThread();
        list.add(ascendingHackerThread);
        list.add(descendingHackerThread);
        list.add(randomHackerThread);
//        list.add(policeThread);
        for (Thread thread : list) {
            thread.start();
        }
        policeThread.start();

    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return this.password == guess;
        }
    }

    private static class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("current Thread:" + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password" + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password" + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class RandomHackerThread extends HackerThread {
        public RandomHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            while (true) {
                Random random = new Random();
                // Generate a random number within the 0 .. max_password range
                int randomNumber = random.nextInt(MAX_PASSWORD - 0 + 1) + 0;

                if (vault.isCorrectPassword(randomNumber)) {
                    System.out.println(this.getName() + " guessed the password" + randomNumber);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(i);
            }
            System.out.println("Game over hackers!");
            System.exit(0);
        }
    }
}