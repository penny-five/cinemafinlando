package com.github.pennyfive.cinemafinlando.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.pennyfive.cinemafinlando.BuildConfig;
import com.github.pennyfive.cinemafinlando.CinemaFinlandoApplication.InjectUtils;
import com.github.pennyfive.cinemafinlando.R;
import com.github.pennyfive.cinemafinlando.ui.activity.generic.ToolbarActivity;
import com.github.pennyfive.cinemafinlando.ui.fragment.OpenSourceLicensesDialogFragment;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 *
 */
public class AboutActivity extends ToolbarActivity {
    @InjectView(R.id.version) TextView versionTextView;
    @InjectView(R.id.email) TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        InjectUtils.injectViews(this);

        versionTextView.setText(BuildConfig.VERSION_NAME);
        emailTextView.setText(BuildConfig.CONTACT_EMAIL);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getToolbar().setTitle(R.string.pref_title_about);
    }

    @OnClick(R.id.api_url)
    public void onApiUriClicked() {
        Uri apiUri = Uri.parse(getString(R.string.about_finnkino_api_url));
        startActivity(new Intent(Intent.ACTION_VIEW, apiUri));
    }

    @OnClick(R.id.email)
    public void onEmailClicked() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{BuildConfig.CONTACT_EMAIL});
        startActivity(intent);
    }

    @OnClick(R.id.open_source_licenses)
    public void onOpenSourceLicensesClicked() {
        new OpenSourceLicensesDialogFragment().show(getSupportFragmentManager(), null);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
