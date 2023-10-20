package dev.juligame.Jconfig;

public class Main {
  public static void main(String[] args) {
    System.out.println("Hello world!");
    test t = new test("asd", "asd");
    t.load();

    System.out.println(t.num);
    t.num = 40;
    System.out.println(t.num);
    System.out.println(t.utf8);
    t.save();
  }
}