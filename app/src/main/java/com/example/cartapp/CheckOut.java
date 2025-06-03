package com.example.cartapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class CheckOut extends AppCompatActivity {
    EditText inputName, inputAddress, inputCity, inputZip;
    RadioGroup paymentOptions;
    Button confirmButton;
    TextView orderItemsTextView, orderTotalTextView;
    CartManager cartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        cartManager = CartManager.getInstance();

        inputName = findViewById(R.id.inputName);
        inputAddress = findViewById(R.id.inputAddress);
        inputCity = findViewById(R.id.inputCity);
        inputZip = findViewById(R.id.inputZip);
        paymentOptions = findViewById(R.id.paymentOptions);
        orderItemsTextView = findViewById(R.id.orderItems);
        orderTotalTextView = findViewById(R.id.orderTotal);
        confirmButton = findViewById(R.id.btnConfirmOrder);

        loadCartDetails();

        confirmButton.setOnClickListener(v -> {
            if (validateInput()) {
                String paymentMethod = getSelectedPaymentMethod();
                Toast.makeText(this,
                        "✅ Order placed with " + paymentMethod + "!\nThank you, " + inputName.getText().toString(),
                        Toast.LENGTH_LONG).show();

                // Optionally: Clear cart
                cartManager.clearCart();
                finish(); // Close activity
            }
        });

    }

    private String getSelectedPaymentMethod() {
        int selectedId = paymentOptions.getCheckedRadioButtonId();
        RadioButton selectedButton = findViewById(selectedId);
        return selectedButton.getText().toString();
    }

    private boolean validateInput() {
        if (inputName.getText().toString().isEmpty()) {
            inputName.setError("Name required");
            return false;
        }
        if (inputAddress.getText().toString().isEmpty()) {
            inputAddress.setError("Address required");
            return false;
        }
        if (inputCity.getText().toString().isEmpty()) {
            inputCity.setError("City required");
            return false;
        }
        if (inputZip.getText().toString().isEmpty()) {
            inputZip.setError("ZIP Code required");
            return false;
        }
        if (paymentOptions.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a payment method", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void loadCartDetails() {
        List<CartItem> items = cartManager.getCartItems();
        StringBuilder itemDetails = new StringBuilder();
        double total = 0.0;

        for (CartItem item : items) {
            double price = Double.parseDouble(item.getPrice().replace("$", "").trim()); //Parse the string returned to double

            itemDetails.append(item.getQuantity())
                    .append("x ")
                    .append(item.getName())
                    .append(" - $")
                    .append(String.format("%.2f", price * item.getQuantity()))
                    .append("\n");
            total += price * item.getQuantity();
        }

        orderItemsTextView.setText(itemDetails.toString().trim());
        orderTotalTextView.setText("Total: $" + String.format("%.2f", total));
    }
}