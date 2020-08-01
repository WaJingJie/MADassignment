package sg.edu.np.mad.madassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.braintreepayments.cardform.view.CardForm;

import java.util.ArrayList;
import java.util.List;

public class Overduepayment extends AppCompatActivity {

    CardForm cardForm;
    Button buy;
    AlertDialog.Builder alertBuilder;
    List<BorrowData> overdueList = new ArrayList<>();
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.overduepayment);

        if(getIntent().getExtras() != null){
            overdueList = (List<BorrowData>) getIntent().getSerializableExtra("list");
        }
        dbHandler = new DBHandler(this,null,null,1);
        cardForm = findViewById(R.id.card_form);
        buy = findViewById(R.id.btnPay);
        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(Overduepayment.this);
        cardForm.getCvvEditText().setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardForm.isValid()) {
                    alertBuilder = new AlertDialog.Builder(Overduepayment.this);
                    alertBuilder.setTitle("Confirm before purchase");
                    alertBuilder.setMessage("Card number: " + cardForm.getCardNumber() + "\n" +
                            "Card expiry date: " + cardForm.getExpirationDateEditText().getText().toString() + "\n" +
                            "Card CVV: " + cardForm.getCvv() + "\n" +
                            "Postal code: " + cardForm.getPostalCode() + "\n" +
                            "Phone number: " + cardForm.getMobileNumber());
                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            boolean result = false;
                            for(int k=0; k< overdueList.size(); k++){
                                result = dbHandler.deleteBorrowedBook(overdueList.get(k).getISBN());
                            }
                            if (result){
                                dialogInterface.dismiss();
                                Toast.makeText(Overduepayment.this, "Thank you for your payment", Toast.LENGTH_LONG).show();
                                Intent homepage = new Intent(Overduepayment.this, StudentHomePage.class);
                                startActivity(homepage);
                            }

                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();

                } else {
                    Toast.makeText(Overduepayment.this, "Please complete the form", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
