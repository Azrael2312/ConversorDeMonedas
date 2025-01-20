import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import org.json.JSONObject;

public class ConversorDeMonedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String apiKey = "EXCHANGE_RATE_API_KEY" // Coloca tu API key de ExchangeRate-API aquí.
        boolean ejecutar = true;

        System.out.println("¡Bienvenido al Conversor de Monedas!");
        System.out.println("Convierta fácilmente entre diferentes monedas.\n");

        while (ejecutar) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Convertir de USD a EUR");
            System.out.println("2. Convertir de USD a GBP");
            System.out.println("3. Convertir de USD a JPY");
            System.out.println("4. Convertir de USD a AUD");
            System.out.println("5. Convertir de USD a CAD");
            System.out.println("6. Convertir de USD a MXN");
            System.out.println("7. Convertir de USD a PEN (Soles Peruanos)");
            System.out.println("8. Salir de la aplicación");
            System.out.print("Opción: ");

            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    convertirMoneda(apiKey, "USD", "EUR");
                    break;
                case 2:
                    convertirMoneda(apiKey, "USD", "GBP");
                    break;
                case 3:
                    convertirMoneda(apiKey, "USD", "JPY");
                    break;
                case 4:
                    convertirMoneda(apiKey, "USD", "AUD");
                    break;
                case 5:
                    convertirMoneda(apiKey, "USD", "CAD");
                    break;
                case 6:
                    convertirMoneda(apiKey, "USD", "MXN");
                    break;
                case 7:
                    convertirMoneda(apiKey, "USD", "PEN");
                    break;
                case 8:
                    System.out.println("Gracias por usar el Conversor de Monedas. ¡Hasta pronto!");
                    ejecutar = false;
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
                    break;
            }
            System.out.println();
        }

        scanner.close();
    }

    public static void convertirMoneda(String apiKey, String fromCurrency, String toCurrency) {
        try {
            // URL para la API
            String urlStr = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + fromCurrency + "/" + toCurrency;

            // Crear conexión HTTP
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            // Verificar si la conexión fue exitosa
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                System.out.println("Error al obtener los datos. Código de respuesta: " + responseCode);
                return;
            }

            // Leer la respuesta de la API
            Scanner responseScanner = new Scanner(url.openStream());
            StringBuilder responseString = new StringBuilder();
            while (responseScanner.hasNext()) {
                responseString.append(responseScanner.nextLine());
            }
            responseScanner.close();

            // Parsear JSON de la respuesta
            JSONObject responseJson = new JSONObject(responseString.toString());
            double conversionRate = responseJson.getDouble("conversion_rate");

            // Solicitar cantidad al usuario
            Scanner scanner = new Scanner(System.in);
            System.out.print("Ingrese la cantidad en " + fromCurrency + ": ");
            double cantidad = scanner.nextDouble();

            // Calcular la conversión
            double resultado = cantidad * conversionRate;
            System.out.printf("Tasa de conversión: 1 %s = %.2f %s%n", fromCurrency, conversionRate, toCurrency);
            System.out.printf("Resultado: %.2f %s = %.2f %s%n", cantidad, fromCurrency, resultado, toCurrency);

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        }
    }
}
