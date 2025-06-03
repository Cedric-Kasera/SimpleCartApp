package com.example.cartapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class Cart extends AppCompatActivity {
    TextView cartSummary;
    Button btnCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        btnCheckout = findViewById(R.id.btnCheckout);
        cartSummary = findViewById(R.id.cartDetails);

        List<CartItem> items = CartManager.getInstance().getCartItems();
        StringBuilder summary = new StringBuilder();

        if (items.isEmpty()) {
            summary.append("Your cart is empty.");
        } else {
            for (CartItem item : items) {
                summary.append(item.getName())
                        .append(" - ")
                        .append(item.getPrice())
                        .append(" x ")
                        .append(item.getQuantity())
                        .append("\n");
            }
        }

        cartSummary.setText(summary.toString());

        loadCartItems();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Cart.this, CheckOut.class);
                    startActivity(intent);

                    Toast.makeText(getApplicationContext(), "Checkout button clicked", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(Cart.this, "Failed to open checkout: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadCartItems() {
        CartManager cartManager = CartManager.getInstance();
        List<CartItem> items = cartManager.getCartItems();
        LinearLayout container = findViewById(R.id.cartItemsContainer);
        TextView txtTotal = findViewById(R.id.txtTotal);
        TextView emptyText = findViewById(R.id.cartDetails);

        container.removeAllViews(); // Clear previous items
        double total = 0.0;

        if (items.isEmpty()) {
            emptyText.setVisibility(View.VISIBLE);
            txtTotal.setText("Total: $0.00");
            return;
        } else {
            emptyText.setVisibility(View.GONE);
        }

        for (CartItem item : items) {
            View itemView = getLayoutInflater().inflate(R.layout.item_cart, null);

            TextView txtItemName = itemView.findViewById(R.id.txtItemName);
            TextView txtItemDetails = itemView.findViewById(R.id.txtItemDetails);

            txtItemName.setText(item.getName());

            double price = Double.parseDouble(item.getPrice().replace("$", "").trim());
            int qty = item.getQuantity();
            double subtotal = price * qty;
            total += subtotal;

            txtItemDetails.setText("$" + price + " x " + qty + " = $" + String.format("%.2f", subtotal));

            container.addView(itemView);
        }

        txtTotal.setText("Total: $" + String.format("%.2f", total));
    }

}
