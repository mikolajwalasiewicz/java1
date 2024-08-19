
package zadanie1;
import java.io.*;
import java.util.*;

public class Zadanie1 {
    public static void main(String[] args) {
        // Zadanie 1
        try {
            File file = new File("liczba1.txt");
            Scanner scanner = new Scanner(file);
            List<Integer> numbers = new ArrayList<>();
            
            while (scanner.hasNextLine()) {
                int number = Integer.parseInt(scanner.nextLine());
                numbers.add(number);
            }
            scanner.close();
            
            // Obliczenia
            int sum = 0;
            for (int num : numbers) {
                sum += num;
            }
            double average = (double) sum / numbers.size();
            double geometricMean = calculateGeometricMean(numbers);
            
            // Wypisanie wyników
            System.out.println("Suma: " + sum);
            System.out.println("Średnia arytmetyczna: " + average);
            System.out.println("Średnia geometryczna: " + geometricMean);
            
            // Zapis do plików
            savePrimeNumbers(numbers);
            savePerfectNumbers(numbers);
            
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
            e.printStackTrace();
        }
        
        // Zadanie 2
        try {
            File file = new File("liczby2.txt");
            Scanner scanner = new Scanner(file);
            List<Integer> numbers = new ArrayList<>();
            
            while (scanner.hasNextLine()) {
                int number = Integer.parseInt(scanner.nextLine());
                numbers.add(number);
            }
            scanner.close();
            
            // Zapis liczb półpierwszych
            saveSemiprimeNumbers(numbers);
            
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
            e.printStackTrace();
        }
        
        // Zadanie 3
        try {
            File fileA = new File("liczby3a.txt");
            File fileB = new File("liczby3b.txt");
            Scanner scannerA = new Scanner(fileA);
            Scanner scannerB = new Scanner(fileB);
            Set<Integer> numbersA = new HashSet<>();
            Set<Integer> numbersB = new HashSet<>();
            
            // Wczytywanie liczb z plików
            while (scannerA.hasNextLine()) {
                int number = Integer.parseInt(scannerA.nextLine());
                numbersA.add(number);
            }
            scannerA.close();
            
            while (scannerB.hasNextLine()) {
                int number = Integer.parseInt(scannerB.nextLine());
                numbersB.add(number);
            }
            scannerB.close();
            
            // Obliczenia
            int totalDigits = getTotalDigits(numbersA);
            int minNumberA = Collections.min(numbersA);
            int commonNumbersCount = countCommonNumbers(numbersA, numbersB);
            Map<Integer, Integer> digitCounts = countDigits(numbersA);
            
            // Zapis do pliku wynikowego
            PrintWriter writer = new PrintWriter("wynik3.txt");
            writer.println("a) Liczba cyfr w pliku liczby3a.txt: " + totalDigits);
            writer.println("b) Najmniejsza liczba w pliku liczby3a.txt: " + minNumberA);
            writer.println("c) Liczba liczb występujących jednocześnie w obu plikach: " + commonNumbersCount);
            writer.println("d) Liczba liczb kończących się odpowiednio cyframi:");
            for (Map.Entry<Integer, Integer> entry : digitCounts.entrySet()) {
                writer.println("   - Kończących się cyfrą " + entry.getKey() + ": " + entry.getValue());
            }
            writer.close();
            
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
            e.printStackTrace();
        }
    }
    
    private static double calculateGeometricMean(List<Integer> numbers) {
        double product = 1;
        for (int num : numbers) {
            product *= num;
        }
        return Math.pow(product, 1.0 / numbers.size());
    }
    
    private static boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
    
    private static boolean isPerfect(int num) {
        int sum = 1;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                sum += i;
                if (i != num / i) {
                    sum += num / i;
                }
            }
        }
        return sum == num;
    }
    
    private static void savePrimeNumbers(List<Integer> numbers) {
        try {
            PrintWriter writer = new PrintWriter("wynik1P.txt");
            for (int num : numbers) {
                if (isPrime(num)) {
                    writer.println(num);
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static void savePerfectNumbers(List<Integer> numbers) {
        try {
            PrintWriter writer = new PrintWriter("wynik1D.txt");
            for (int num : numbers) {
                if (isPerfect(num)) {
                    writer.println(num);
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static boolean isSemiprime(int num) {
        if (num <= 3) return false;
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0 && isPrime(i) && isPrime(num / i) && (num / i) != i) {
                return true;
            }
        }
        return false;
    }
    
    private static void saveSemiprimeNumbers(List<Integer> numbers) {
        try {
            PrintWriter writer = new PrintWriter("wynik2.txt");
            for (int num : numbers) {
                if (isSemiprime(num)) {
                    writer.println(num);
                }
            }
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private static int getTotalDigits(Set<Integer> numbers) {
        int totalDigits = 0;
        for (int num : numbers) {
            totalDigits += String.valueOf(num).length();
        }
        return totalDigits;
    }
    
    private static int countCommonNumbers(Set<Integer> numbersA, Set<Integer> numbersB) {
        Set<Integer> commonNumbers = new HashSet<>(numbersA);
        commonNumbers.retainAll(numbersB);
        return commonNumbers.size();
    }
    
    private static Map<Integer, Integer> countDigits(Set<Integer> numbers) {
        Map<Integer, Integer> digitCounts = new HashMap<>();
        for (int num : numbers) {
            int lastDigit = num % 10;
            digitCounts.put(lastDigit, digitCounts.getOrDefault(lastDigit, 0) + 1);
        }
        return digitCounts;
    }
}