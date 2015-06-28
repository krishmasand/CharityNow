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
    private String amount;
    private String name;

    public SendEmailASyncTask(Context context, String amount, String name) {
        this.context = context;
        this.amount = amount;
        this.name = name;
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
            email.setSubject("Donate to "+name+"!");
            email.setHtml("<html>\n" +
                    "<head>\n" +
                    "\t<title></title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<p><span class=\"sg-image\" style=\"float: none; display: block; text-align: center;\"><img height=\"212\" src=\"https://marketing-image-production.s3.amazonaws.com/uploads/440b0cbc44ef8bc79c39bf571942ed0c4ab59445ce98635c4dc8d21ba8e1bc15c3334fc0f038c1bd3f6fa535061f07d3e04bc05cf2983917712e1561d931ad7b.png\" width=\"1000\" /></span></p>\n" +
                    "\n" +
                    "<p style=\"text-align: center;\">&nbsp;</p>\n" +
                    "\n" +
                    "<hr />\n" +
                    "<h3 style=\"font-weight: 400; line-height: 1.2em; color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif;\"><span style=\"font-size:28px;\"><strong>Donate to your charity now!</strong></span></h3>\n" +
                    "\n" +
                    "<p style=\"color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 13px; line-height: 20.7999992370605px;\"><span style=\"font-size:14px;\">We found that the charity of your choice has a Venmo account that you can use to donate to. Use the below link to donate the amount of your choice.</span></p>\n" +
                    "\n" +
                    "<p style=\"color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 13px; line-height: 20.7999992370605px;\"><span style=\"font-size:14px;\">https://venmo.com/?txn=pay&amp;recipients=malarianomore&amp;amount="+amount+"&amp;note=for%20charity&amp;audience=public</span></p>\n" +
                    "\n" +
                    "<p style=\"color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 13px; line-height: 20.7999992370605px;\">&nbsp;</p>\n" +
                    "\n" +
                    "<p style=\"color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 13px; line-height: 20.7999992370605px;\"><span style=\"font-size:14px;\">Thanks,</span></p>\n" +
                    "\n" +
                    "<p style=\"color: rgb(34, 34, 34); font-family: proxima-nova, Lato, 'Helvetica Neue', Helvetica, Arial, sans-serif; font-size: 13px; line-height: 20.7999992370605px;\"><span style=\"font-size:14px;\"><span style=\"line-height: 1.6em;\">CharityNow</span></span></p>\n" +
                    "\n" +
                    "<div>&nbsp;</div>\n" +
                    "</body>\n" +
                    "<link href=\"chrome-extension://iebboopaeangfpceklajfohhbpkkfiaa/deluminate.css\" media=\"screen\" rel=\"stylesheet\" /></html>\n");

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

