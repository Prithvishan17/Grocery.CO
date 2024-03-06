package com.example.groceryco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.example.groceryco.adapter.PlaceYourOrderAdapter;
import com.example.groceryco.model.GroceryModel;
import com.example.groceryco.model.Menu;

public class PlaceYourOrderActivity extends AppCompatActivity {

    private EditText inputName, inputCashAmount,inputCardNumber, inputCardExpiry, inputCardPin, cardDetails ;
    private RecyclerView cartItemsRecyclerView;
    private TextView tvSubtotalAmount, tvTotalAmount, buttonPlaceYourOrder;
    private SwitchCompat switchPayment;
    private boolean isCashOn, isOnlinePaymentOn;
    private PlaceYourOrderAdapter placeYourOrderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_your_order);

        GroceryModel groceryModel = getIntent().getParcelableExtra("GroceryModel");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Order Status");
        actionBar.setDisplayHomeAsUpEnabled(true);

        inputName = findViewById(R.id.inputName);
        inputCashAmount = findViewById(R.id.inputCashAmount);
        inputCardNumber = findViewById(R.id.inputCardNumber);
        inputCardExpiry = findViewById(R.id.inputCardExpiry);
        inputCardPin = findViewById(R.id.inputCardPin);
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder);
        switchPayment = findViewById(R.id.switchPayment);
        cardDetails = findViewById(R.id.cardDetails);

        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView);

        buttonPlaceYourOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceOrderButtonClick(groceryModel);
            }
        });

       switchPayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    inputCashAmount.setVisibility(View.VISIBLE);
//                    tvServiceChargeAmount.setVisibility(View.VISIBLE);
//                    tvServiceCharge.setVisibility(View.VISIBLE);
                    inputCardNumber.setVisibility(View.GONE);
                    inputCardExpiry.setVisibility(View.GONE);
                    inputCardPin.setVisibility(View.GONE);
                    cardDetails.setVisibility(View.GONE);

                    isCashOn = true;
                    isOnlinePaymentOn = false;
                    calculateTotalAmount(groceryModel);

                } else {
                    inputCashAmount.setVisibility(View.GONE);
                    inputCardNumber.setVisibility(View.VISIBLE);
                    inputCardExpiry.setVisibility(View.VISIBLE);
                    inputCardPin.setVisibility(View.VISIBLE);
                    cardDetails.setVisibility(View.VISIBLE);
                    isCashOn = false;
                    isOnlinePaymentOn = true;
                    calculateTotalAmount(groceryModel);
                }
            }
        });
        initRecyclerView(groceryModel);
        calculateTotalAmount(groceryModel);
    }

    private void calculateTotalAmount(GroceryModel groceryModel) {
        float subTotalAmount = 0f;

        for(Menu m : groceryModel.getMenus()) {
            subTotalAmount += m.getPrice() * m.getTotalInCart();
        }

        tvSubtotalAmount.setText("RM "+String.format("%.2f", subTotalAmount));
        tvTotalAmount.setText("RM "+String.format("%.2f", subTotalAmount));
    }

    private void onPlaceOrderButtonClick(GroceryModel groceryModel) {
        if(TextUtils.isEmpty(inputName.getText().toString())) {
            inputName.setError("Please enter name ");
            return;
        } else if(isCashOn && TextUtils.isEmpty(inputCashAmount.getText().toString())) {
            inputCashAmount.setError("Please enter cash amount ");
            return;
        }else if(isOnlinePaymentOn && TextUtils.isEmpty(inputCardNumber.getText().toString())) {
            inputCardNumber.setError("Please enter card number ");
            return;
        }else if( isOnlinePaymentOn && TextUtils.isEmpty(inputCardExpiry.getText().toString())) {
            inputCardExpiry.setError("Please enter card expiry ");
            return;
        }else if( isOnlinePaymentOn && TextUtils.isEmpty(inputCardPin.getText().toString())) {
            inputCardPin.setError("Please enter card pin/cvv ");
            return;
        }
        //start success activity..
        Intent i = new Intent(PlaceYourOrderActivity.this, OrderSuccessActivity.class);
        i.putExtra("DrinksModel", groceryModel);
        startActivityForResult(i, 1000);
    }

    private void initRecyclerView(GroceryModel groceryModel) {
        cartItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        placeYourOrderAdapter = new PlaceYourOrderAdapter(groceryModel.getMenus());
        cartItemsRecyclerView.setAdapter(placeYourOrderAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == 1000) {
            setResult(Activity.RESULT_OK);
            finish();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home :
                finish();
            default:
                //do nothing
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(Activity.RESULT_CANCELED);
        finish();
    }

}