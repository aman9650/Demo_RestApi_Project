import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.CoursePrice());

		// Printing data from a complictaed JSON file

		// Print no of courses returned by API
		System.out.println("Print no of courses returned by API");
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Print Purchase Amount
		System.out.println("Print Purchase Amount");
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// Print title of the first course
		System.out.println("Print title of the first course");
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		// Print title of the Second course
		System.out.println("Print title of the Second course");
		String titlScndCourse = js.get("courses[2].title");
		System.out.println(titlScndCourse);

		// Print All the course titles and their respective prices
		System.out.println("Print All the course titles and their respective prices");
		for (int i = 0; i < count; i++) {
			String title = js.get("courses[" + i + "].title");
			int price = js.getInt("courses[" + i + "].price");
			System.out.println(title + "-->" + price);
		}

		// Print number of copies sold by RPA course
		System.out.println("Print number of copies sold by RPA course");
		for (int i = 0; i < count; i++) {
			String Coursetitle = js.get("courses[" + i + "].title");
			if (Coursetitle.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses[" + i + "].copies");
				System.out.println("Copies->" + copies);
				break;
			}
		}

	}
}
