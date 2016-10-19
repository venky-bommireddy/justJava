package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean hasWhippedCream  = false;
    boolean hasChocolate = false;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText editText = (EditText)findViewById(R.id.name_field);
        name = editText.getText().toString();
       int price = calculatePrice();
        String priceMessage = createOrderSummary(price);
       // priceMessage = priceMessage + "\nThank you!";
//       displayMessage(priceMessage);
//
       Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:"));

        emailIntent.putExtra(Intent.EXTRA_SUBJECT,"JustJava submit order to venky");
        emailIntent.putExtra(Intent.EXTRA_TEXT,priceMessage);
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent.createChooser(emailIntent,""));
        }
    }

    private String calculatePrice(int quantity, int perCup)
    {
        int price = quantity * perCup;
        String priceMessage = "Total: $" + price;
        priceMessage = priceMessage + "\nThank you!";

        return priceMessage;
    }

    private String createOrderSummary(int price)
    {
        String priceMessage = "";
        priceMessage = "Name: " + name;
        priceMessage += "\nAddWhippedCream?" + hasWhippedCream;
        priceMessage += "\nChocolate?" + hasChocolate;
        priceMessage += "\nQuantity:" + quantity;
        priceMessage  += "\nTotal:" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    private int calculatePrice()
    {
        int basePrice = 5;
        if(hasWhippedCream){
            basePrice += 1;
        }
        if(hasChocolate)
        {
            basePrice += 2;
        }
        return basePrice * quantity;
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void onCheckBoxClipped(View view)
    {
        CheckBox checkBox = (CheckBox)findViewById(R.id.checkbox_checked);
        hasWhippedCream = checkBox.isChecked();
    }

    public void onChocolateCheckBoxClipped(View view)
    {
        CheckBox checkBox = (CheckBox)findViewById(R.id.chocolate_checkbox);
        hasChocolate = checkBox.isChecked();
    }
    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
