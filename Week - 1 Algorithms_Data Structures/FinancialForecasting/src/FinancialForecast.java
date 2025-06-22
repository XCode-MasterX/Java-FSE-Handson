public class FinancialForecast {

    // Recursive method to calculate future value
    public static double calculateFutureValue(double initial, double rate, int years) {
        if (years == 0) {
            return initial;  // Base case
        }
        return calculateFutureValue(initial, rate, years - 1) * (1 + rate);
    }

    // Optimized version using tail recursion
    public static double calculateFutureValueTail(double value, double rate, int years) {
        if (years == 0) return value;
        return calculateFutureValueTail(value * (1 + rate), rate, years - 1);
    }

    public static void main(String[] args) {
        double initial = 1000.0;
        double rate = 0.10; // 10% annual growth
        int years = 5;

        double futureValue = calculateFutureValue(initial, rate, years);
        System.out.println("Future Value (Normal Recursion): $" + futureValue);

        double futureTail = calculateFutureValueTail(initial, rate, years);
        System.out.println("Future Value (Tail Recursion): $" + futureTail);
    }
}
