import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

public class ATMServer {

    private static ATMService atmService = new ATMService();

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", ATMServer::handleRequest);

        server.start();

        System.out.println("ATM Server started!");
        System.out.println("Open http://localhost:8080");
    }

    private static void handleRequest(HttpExchange exchange)
            throws IOException {

        String path = exchange.getRequestURI().getPath();

        // =========================
        // 1. LOGIN API
        // =========================

        if (path.equals("/api/login")) {

            if (!exchange.getRequestMethod().equals("POST")) {
                sendResponse(exchange, 405,
                    "{\"message\":\"Method not allowed\"}");
                return;
            }

            Map<String, String> data = readFormData(exchange);

            String accountNo = data.get("accountNo");
            String pin = data.get("pin");

            Account account = null;

            if (accountNo != null && pin != null) {
                account = atmService.login(accountNo, pin);
            }

            if (account != null) {

                String response =
                    "{\"success\":true,\"name\":\"" +
                    escapeJson(account.getName()) + "\"}";

                sendResponse(exchange, 200, response);

            } else {

                sendResponse(exchange, 401,
                    "{\"success\":false,\"message\":\"Invalid account number or PIN\"}");
            }
        }

        // =========================
        // 2. CHECK BALANCE API
        // =========================

        else if (path.equals("/api/balance")) {

            if (!exchange.getRequestMethod().equals("POST")) {
                sendResponse(exchange, 405,
                    "{\"message\":\"Method not allowed\"}");
                return;
            }

            Map<String, String> data = readFormData(exchange);

            String accountNo = data.get("accountNo");

            Account foundAccount = findAccount(accountNo);

            if (foundAccount != null) {

                sendResponse(exchange, 200,
                    "{\"success\":true,\"balance\":" +
                    foundAccount.getBalance() + "}");

            } else {

                sendResponse(exchange, 404,
                    "{\"success\":false,\"message\":\"Account not found\"}");
            }
        }

        // =========================
        // 3. DEPOSIT MONEY API
        // =========================

        else if (path.equals("/api/deposit")) {

            if (!exchange.getRequestMethod().equals("POST")) {
                sendResponse(exchange, 405,
                    "{\"message\":\"Method not allowed\"}");
                return;
            }

            Map<String, String> data = readFormData(exchange);

            String accountNo = data.get("accountNo");
            String amountText = data.get("amount");

            if (accountNo == null || amountText == null) {
                sendResponse(exchange, 400,
                    "{\"success\":false,\"message\":\"Missing account number or amount\"}");
                return;
            }

            try {

                double amount = Double.parseDouble(amountText);

                if (!Double.isFinite(amount) || amount <= 0) {
                    sendResponse(exchange, 400,
                        "{\"success\":false,\"message\":\"Enter a valid amount\"}");
                    return;
                }

                Account foundAccount = findAccount(accountNo);

                if (foundAccount == null) {
                    sendResponse(exchange, 404,
                        "{\"success\":false,\"message\":\"Account not found\"}");
                    return;
                }

                // Call existing ATMService method
                atmService.deposit(foundAccount, amount);

                sendResponse(exchange, 200,
                    "{\"success\":true,\"message\":\"Deposit successful\"}");

            } catch (NumberFormatException e) {

                sendResponse(exchange, 400,
                    "{\"success\":false,\"message\":\"Invalid amount\"}");
            }
        }

        // =========================
        // 4. SERVE FRONTEND FILES
        // =========================

        else {

            if (!exchange.getRequestMethod().equals("GET")) {
                sendResponse(exchange, 405,
                    "{\"message\":\"Method not allowed\"}");
                return;
            }

            String relativePath = path.equals("/")
                ? "login.html"
                : path.substring(1);

            Path root = Paths.get("frontend")
                .toAbsolutePath().normalize();

            Path file = root.resolve(relativePath).normalize();

            if (!file.startsWith(root) || !Files.isRegularFile(file)) {
                sendResponse(exchange, 404,
                    "{\"message\":\"File not found\"}");
                return;
            }

            String contentType = "text/plain; charset=UTF-8";

            if (relativePath.endsWith(".html")) {
                contentType = "text/html; charset=UTF-8";
            }

            else if (relativePath.endsWith(".css")) {
                contentType = "text/css; charset=UTF-8";
            }

            else if (relativePath.endsWith(".js")) {
                contentType = "application/javascript; charset=UTF-8";
            }

            exchange.getResponseHeaders().set(
                "Content-Type", contentType
            );

            byte[] response = Files.readAllBytes(file);

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        }
    }

    // =========================
    // READ FORM DATA
    // =========================

    private static Map<String, String> readFormData(
            HttpExchange exchange) throws IOException {

        String body = new String(
            exchange.getRequestBody().readAllBytes(),
            StandardCharsets.UTF_8
        );

        Map<String, String> data = new HashMap<>();

        for (String pair : body.split("&")) {

            String[] parts = pair.split("=", 2);

            if (parts.length == 2) {

                String key = URLDecoder.decode(
                    parts[0], StandardCharsets.UTF_8
                );

                String value = URLDecoder.decode(
                    parts[1], StandardCharsets.UTF_8
                );

                data.put(key, value);
            }
        }

        return data;
    }

    // =========================
    // FIND ACCOUNT
    // =========================

    private static Account findAccount(String accountNo) {

        if (accountNo == null) {
            return null;
        }

        ArrayList<Account> accounts = CSVManager.readAccounts();

        for (Account account : accounts) {

            if (account.getAccountNo().equals(accountNo)) {
                return account;
            }
        }

        return null;
    }

    // =========================
    // ESCAPE JSON TEXT
    // =========================

    private static String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r");
    }

    // =========================
    // SEND RESPONSE
    // =========================

    private static void sendResponse(
            HttpExchange exchange,
            int status,
            String response) throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
            "Content-Type", "application/json; charset=UTF-8"
        );

        exchange.sendResponseHeaders(status, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }
    }
}