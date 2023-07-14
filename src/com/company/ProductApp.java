package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductApp {
    private static final String API_URL = "https://dummyjson.com/products";
    private static final String X_CONS_ID = "1234567";
    private static final String USER_KEY = "faY738sH";
    private static int n;

    public static void main(String[] args) {
        try{
            String jsonResponse =  sendRequest();
            JSONArray productsArray = extractProductsArray(jsonResponse);
            assert productsArray != null;
            Product[] products = createProductArray(productsArray);
            selectionSortByRating(products);
            displayProducts(products);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void selectionSortByRating(Product[] ignoredProducts) {
    }

    private static JSONArray extractProductsArray(String jsonResponse) {
        return null;
    }

    private static String sendRequest() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestProperty("X-CONS_ID",X_CONS_ID);
        conn.setRequestProperty("user_key",USER_KEY);
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    private static Product[] createProductArray(JSONArray productsArray) {
        int length = productsArray.length();
        Product[] products = new Product[length];

        for (int i = 0; i < length; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (products[j].getRating() < products[minIndex].getRating()) {
                    minIndex = j;
                }
            }
            Product temp = products[minIndex];
            products[minIndex] = products[i];
            products[i] = temp;
        }
        return products;
    }

    private static void displayProducts(Product[] products) {
        for(Product ignored : products) {
            System.out.println();
        }
    }

    public static void setN(int n) {
        ProductApp.n = n;
    }

    static class Product {
        private final String name;
        private final double rating;

        public Product(String name, double rating) {
            this.name = name;
            this.rating = rating;
        }

        public String getName() {
            return name;
        }

        public double getRating() {
            return rating;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "name='" + name + '\'' +
                    ", rating=" + rating +
                    '}';
        }
    }

    private static class JSONArray {
        public int length() {
            return 0;
        }
    }
}
