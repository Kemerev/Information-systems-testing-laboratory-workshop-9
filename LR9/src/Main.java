import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

class Person {
	String name;
	LocalDate birthdate;
	String country;

	public Person(String name, int year, int month, int day, String country) {
		this.name = name;
		this.birthdate = LocalDate.of(year, month, day);
		this.country = country;
	}

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", birthdate=" + birthdate.format(DateTimeFormatter.ISO_DATE) +
				", country='" + country + '\'' +
				'}';
	}
}

public class Main {

	public static void main(String[] args) throws ParseException {
		List<Person> persons = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		Scanner scanner = new Scanner(System.in);
		System.out.println("Введите количество людей:");
		int n = Integer.parseInt(scanner.nextLine());
		for (int i = 0; i < n; ++i) {
			System.out.println("Введите данные для человека №" + (i + 1));
			String[] line = scanner.nextLine().split(" ");
			if (line.length == 5) {
				Person p = createPersonFromInput(line);
				if (p != null) {
					persons.add(p);
				} else {
					System.err.println("Неверный формат данных.");
				}
			} else {
				System.err.println("Неверное количество частей в строке.");
			}
		}
		System.out.println("\nСписок людей:");
		printList(persons);

		System.out.print("\nВведите страну для поиска: ");
		printByCountry(persons, scanner.nextLine());

		System.out.print("\nОтсортировать по...\n1 - Имени;\n2 - Стране;\n3 - Дате рождения.\nВыберите опцию: ");
		Comparator<Person> comparator = null;
		switch (Integer.parseInt(scanner.nextLine())) {
			case 1 -> comparator = Comparator.comparing(o -> o.name);
			case 2 -> comparator = Comparator.comparing(o -> o.country);
			case 3 -> comparator = Comparator.comparing(o -> o.birthdate);
			default -> {}
		}
		if (comparator != null) {
			persons.sort(comparator);
			System.out.println("\nОтсортированный список:");
			printList(persons);
		}
	}

	private static Person createPersonFromInput(String[] line) throws ParseException {
		LocalDate birthdate = LocalDate.of(Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]));
		return new Person(line[0], birthdate.getYear(), birthdate.getMonthValue(), birthdate.getDayOfMonth(), line[4]);
	}

	private static void printList(List<Person> list) {
		for (Person p : list) {
			System.out.printf("%s: %s %s%n", p.name, p.birthdate, p.country);
		}
	}

	private static void printByCountry(List<Person> list, String country) {
		System.out.println();
		for (Person p : list) {
			if (p.country.equalsIgnoreCase(country)) {
				System.out.printf("%s: %s %s%n", p.name, p.birthdate, p.country);
			}
		}
	}
}