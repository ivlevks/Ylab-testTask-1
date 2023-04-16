package com.ivlevks.lesson02.sequences;

public class SequencesImpl implements Sequences {

    @Override
    public void a(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * 2);
        }
    }

    @Override
    public void b(int n) {
        int number = 1;
        for (int i = 0; i < n; i++) {
            System.out.println(number);
            number += 2;
        }
    }

    @Override
    public void c(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i);
        }
    }

    @Override
    public void d(int n) {
        for (int i = 1; i <= n; i++) {
            System.out.println(i * i * i);
        }
    }

    @Override
    public void e(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                System.out.println(1);
            } else System.out.println(-1);
        }
    }

    @Override
    public void f(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                System.out.println(i);
            } else System.out.println(-i);
        }
    }

    @Override
    public void g(int n) {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                System.out.println(i * i);
            } else System.out.println(-(i * i));
        }
    }

    @Override
    public void h(int n) {
        int number = 1;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                System.out.println(number++);
            } else System.out.println(0);
        }
    }

    @Override
    public void i(int n) {
        int previous = 1;
        for (int i = 1; i <= n; i++) {
            int next = previous * i;
            System.out.println(next);
            previous = next;
        }
    }

    @Override
    public void j(int n) {
        int previous = 1;
        int current = 0;
        for (int i = 1; i <= n; i++) {
            int next = previous + current;
            System.out.println(next);
            previous = current;
            current = next;
        }
    }
}
