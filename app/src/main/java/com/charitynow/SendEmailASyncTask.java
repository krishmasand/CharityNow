package com.charitynow;

/**
 * Created by Radhir on 6/28/15.
 */
import android.os.AsyncTask;

import com.sendgrid.SendGrid;
import com.sendgrid.SendGrid.Email;
import com.sendgrid.SendGridException;
import android.content.Context;
import com.sendgrid.*;
import java.io.IOException;

import org.json.JSONException;


/**
 * ASyncTask that composes and sends email
 */
public class SendEmailASyncTask extends AsyncTask<Void, Void, Void> {

    public Context context;
    private String msgResponse;

    public SendEmailASyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... params) {

        try {
            SendGrid sendgrid = new SendGrid("Radduby", "Welcome!2");

            SendGrid.Email email = new SendGrid.Email();

            // Get values from edit text to compose email
            // TODO: Validate edit texts


            email.addTo(new String[]{"krishmasand@gmail.com", "Radduby@gmail.com"});
            email.setCc(new String[]{"Radduby@gmail.com"});
            email.setFrom("Radduby@gmail.com");
            email.setSubject("Donate to MalariaNoMore!");
            email.setText("https://venmo.com/?txn=pay&recipients=malarianomore&amount=0.5&note=for%20charity&audience=public");

            // Send email, execute http request
            SendGrid.Response response = sendgrid.send(email);
            msgResponse = response.getMessage();


        } catch (SendGridException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

