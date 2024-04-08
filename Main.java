import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandRunner commandRunner = new Solution();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter commands (type 'exit' to quit):");

        while (true) {
            System.out.print("> ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("exit")) {
                break;
            }

            String result = commandRunner.runCommand(command);
            System.out.println(result);
        }

        scanner.close();
    }
}