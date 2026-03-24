public class Main {
    public static void main(String[] args) {
        try {
            CommandFactory factory = new CommandFactory();

            // 3. Создаем парсер и передаем ему фабрику
            Calculator calculator = new Calculator(factory);

            // 4. Запускаем выполнение файла
            calculator.calculate(args[0]);

        } catch (Exception e) {
            // Согласно методичке: выводим ошибку и не «падаем» (хотя Main — это финал)
            System.err.println("Критическая ошибка запуска: " + e.getMessage());
        }
    }
}
