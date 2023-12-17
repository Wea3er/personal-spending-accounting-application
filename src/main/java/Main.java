import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ExpenseTracker expenseTracker = new ExpenseTracker();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("купить(к)|статистика по котегории(ск)|вся статистика(вс)|выгрузить в excel(вг)|выйти(в)");
            var key = scanner.next();

            if(key.equals("к")) buyMode(expenseTracker);

            if(key.equals("ск")) statsCategoryMode(expenseTracker);
            if(key.equals("вс")) {
                expenseTracker.showTotalExpenses();
                expenseTracker.showPercentageDistribution();
                expenseTracker.showCategoryExpenses();
            }
            if(key.equals("вг")) {
                System.out.println("Введите путь к файлу, чтобы сохранить расходы в Excel:");
                String filePath = scanner.next();
                expenseTracker.saveExpensesToExcel(filePath);
            }
            if(key.equals("в")) break;
        }
    }
    private static void statsCategoryMode(ExpenseTracker expenseTracker){
        System.out.println("Введите категорию:");
        Scanner scanner = new Scanner(System.in);
        var category = scanner.nextLine();
        expenseTracker.showStats(category);
    }
    private static void buyMode(ExpenseTracker expenseTracker){
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите категорию расходов:");
            String category = scanner.nextLine();

            System.out.println("Введите сумму расходов:");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline

            expenseTracker.recordExpense(amount, category);

            System.out.println("Если хотите записать ещё один товар нажмите 'д'");
            String answer = scanner.nextLine().toLowerCase();
            if (!answer.equals("д")) {
                break;
            }
        }
    }
}
