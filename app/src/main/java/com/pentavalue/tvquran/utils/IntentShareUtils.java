package com.pentavalue.tvquran.utils;

import android.content.Intent;
import android.net.Uri;

/**
 * @author Mahmoud Turki
 */
public class IntentShareUtils {

    public static Intent openCall(String phoneNumber) {
        String uri = "tel:" + phoneNumber;
        return new Intent(Intent.ACTION_CALL, Uri.parse(uri));
    }

    public static Intent openDial(String phoneNumber) {
        String uri = "tel:" + phoneNumber;
        return new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
    }

    public static Intent openEmail(String emailTo) {
        //need this to prompts email client only;
        return new Intent(Intent.ACTION_SEND)
                .setType("message/rfc822")
                .putExtra(Intent.EXTRA_EMAIL, new String[]{emailTo});
    }

    public static Intent openURL(String webPage) {
        if (!webPage.startsWith("https://") && !webPage.startsWith("http://")) {
            webPage = "http://" + webPage;
        }
        return new Intent(Intent.ACTION_VIEW, Uri.parse(webPage));
    }
}
