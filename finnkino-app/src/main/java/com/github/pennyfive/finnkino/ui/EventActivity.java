package com.github.pennyfive.finnkino.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.pennyfive.finnkino.FinnkinoApplication.InjectUtils;
import com.github.pennyfive.finnkino.FinnkinoIntents;
import com.github.pennyfive.finnkino.R;
import com.github.pennyfive.finnkino.api.model.Event;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.InjectView;

/**
 *
 */
public class EventActivity extends Activity {
    @Inject Picasso picasso;
    @InjectView(R.id.image) ImageView eventImageView;
    @InjectView(R.id.name) TextView eventNameView;
    @InjectView(R.id.production_year) TextView productionYearView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        InjectUtils.inject(this);

        Event event = getIntent().getParcelableExtra(FinnkinoIntents.EXTRA_EVENT);

        picasso.load(event.getImageUrl(Event.SIZE_LANDSCAPE_LARGE)).into(eventImageView);
        eventNameView.setText(event.getTitle());
        productionYearView.setText(String.valueOf(event.getProductionYear()));
    }
}
