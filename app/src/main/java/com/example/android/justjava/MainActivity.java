package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

        int quantity = 0;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        int calculatePrice(boolean whippedCream,boolean chocolate){
            int basePrice = 5;

            if(whippedCream)
                basePrice = basePrice + 1;
            if(chocolate)
                basePrice = basePrice + 2;

            return quantity * basePrice;
        }

    /**
     * Create summary of the order.
     *
     * @param priceValue of the order
     * @param getName to get name of the user
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate is whether or not the user wants chocolate topping
     * @return text summary
     */
        String createOrderSummary(int priceValue, Editable getName, boolean addWhippedCream, boolean addChocolate){
            String priceMessage = "Name: " + getName + "\nAdd whipped cream? " + addWhippedCream + "\nAdd chocolate? " + addChocolate + "\nQuantity: " + quantity + "\nTotal: $" + priceValue + "\nThank you!";
            return priceMessage;
        }

        /**
         * This method is called when the order button is clicked.
         */
        public void submitOrder(View view) {

            //Get name of the user
            EditText nameEditText = (EditText) findViewById(R.id.name);
            Editable getName = nameEditText.getText();

            // Figure out if the user wants chocolate topping
            CheckBox whippedcreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream);
            boolean hasWhippedCream = whippedcreamCheckBox.isChecked();


            // Figure out if the user wants chocolate topping
            CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
            boolean hasChocolate = chocolateCheckBox.isChecked();

            int price = calculatePrice(hasWhippedCream,hasChocolate);

            String priceMessage = createOrderSummary(price,getName,hasWhippedCream,hasChocolate);


            //SENDING AN EMAIL INTENT
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + getName);
            intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

//            displayMessage(priceMessage);
        }

        public void increment(View view ){
            quantity = quantity + 1;
                display(quantity);
        }

        public void decrement(View view ){
            if(quantity==0) {
                Toast.makeText(this, "Quantity cannot be less than zero", Toast.LENGTH_SHORT).show();
                return;
            }
            quantity = quantity - 1;
            display(quantity);
        }

        /**
         * This method displays the given quantity value on the screen.
         */
        private void display(int number) {
            TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
            quantityTextView.setText("" + number);
        }


//        /**
//         * This method displays the given text on the screen.
//         */
//        private void displayMessage(String message) {
//            TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//            orderSummaryTextView.setText(message);
//        }

}