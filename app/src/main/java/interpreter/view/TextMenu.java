package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;

import java.util.Scanner;

public class TextMenu {
    private final Scanner scanner = new Scanner(System.in);
    private IStmt selected = null;

    public void show() {
        while (true) {
            System.out.println();
            System.out.println("1. Select a program");
            System.out.println("2. Run the selected program");
            System.out.println("0. Exit");
            System.out.print("> ");
            if (!scanner.hasNextLine()) return;
            String option = scanner.nextLine().trim();
            switch (option) {
                case "1" -> selectProgram();
                case "2" -> runProgram();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Unknown option");
            }
        }
    }

    private void selectProgram() {
        for (int i = 0; i < Examples.ALL.length; i++) {
            System.out.println((i + 1) + ". " + Examples.ALL[i]);
        }
        System.out.print("Program number: ");
        try {
            int nr = Integer.parseInt(scanner.nextLine().trim());
            selected = Examples.ALL[nr - 1];
            System.out.println("Selected: " + selected);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid program number");
        }
    }

    private void runProgram() {
        if (selected == null) {
            System.out.println("Select a program first");
            return;
        }
        PrgState state = PrgState.of(selected);
        Controller controller = new Controller(new Repository(state), true);
        try {
            controller.allStep();
        } catch (MyException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}