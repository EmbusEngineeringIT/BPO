package bpo.crazygamerzz.com.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

/**
 * Created by Sandeep on 20/11/17.
 */

public class DialogUtils {

    public static ProgressDialog showProgressDialog(Context context, String message){

        ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleSmall);
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage(message);
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;

    }

}
