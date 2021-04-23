package com.thoughtbot.expandablerecyclerview.sample;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.thoughtbot.expandablerecyclerview.sample.dynamicallyaddchild.ExpandDynamicallyAddChildActivity;
import com.thoughtbot.expandablerecyclerview.sample.expand.ExpandActivity;
import com.thoughtbot.expandablerecyclerview.sample.multicheck.MultiCheckActivity;
import com.thoughtbot.expandablerecyclerview.sample.multitype.MultiTypeActivity;
import com.thoughtbot.expandablerecyclerview.sample.multitypeandcheck.MultiTypeCheckGenreActivity;
import com.thoughtbot.expandablerecyclerview.sample.singlecheck.SingleCheckActivity;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button expand = (Button) findViewById(R.id.expand_button);
    expand.setOnClickListener(navigateTo(ExpandActivity.class));

    Button multiSelect = (Button) findViewById(R.id.multi_check_button);
    multiSelect.setOnClickListener(navigateTo(MultiCheckActivity.class));

    Button singleSelect = (Button) findViewById(R.id.single_check_button);
    singleSelect.setOnClickListener(navigateTo(SingleCheckActivity.class));

    Button mixedSelect = (Button) findViewById(R.id.mixedtype_button);
    mixedSelect.setOnClickListener(navigateTo(MultiTypeActivity.class));

    Button mixedTypeAndCheck = (Button) findViewById(R.id.mixedtype_check_button);
    mixedTypeAndCheck.setOnClickListener(navigateTo(MultiTypeCheckGenreActivity.class));

    Button dynamicallyAddChildButton = (Button) findViewById(R.id.expand_dynamically_add_child_button);
    dynamicallyAddChildButton.setOnClickListener(navigateTo(ExpandDynamicallyAddChildActivity.class));
  }

  public OnClickListener navigateTo(final Class<?> clazz) {
    return new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, clazz);
        startActivity(intent);
      }
    };
  }
}
