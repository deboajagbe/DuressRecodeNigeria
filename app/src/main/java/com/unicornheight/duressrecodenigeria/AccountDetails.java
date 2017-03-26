package com.unicornheight.duressrecodenigeria;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountDetails extends AppCompatActivity {
    TextView balanceView;
    TextView savingBalance;
    TextView currentBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_details);
        balanceView = (TextView) findViewById(R.id.balanceText);
        savingBalance = (TextView) findViewById(R.id.savingbalanceText);
        currentBalance = (TextView) findViewById(R.id.currentbalanceText);
        String balance = getIntent().getStringExtra("Balance");
        balanceView.setText( "# " + balance);
        savingBalance.setText( "# " + balance);
        currentBalance.setText( "# " + balance);
    }
}
