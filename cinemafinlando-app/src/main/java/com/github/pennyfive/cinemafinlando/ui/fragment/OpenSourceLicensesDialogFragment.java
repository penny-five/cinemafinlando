package com.github.pennyfive.cinemafinlando.ui.fragment;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.webkit.WebView;

import com.github.pennyfive.cinemafinlando.R;

/**
 *
 */
public class OpenSourceLicensesDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new Builder(getActivity());
        builder.setTitle(R.string.about_open_source_licenses_text);
        WebView webView = new WebView(getActivity());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setTextZoom(60);
        webView.loadUrl("file:///android_asset/licenses.html");
        builder.setView(webView);
        return builder.create();
    }
}
