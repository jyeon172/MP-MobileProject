// Generated by view binder compiler. Do not edit!
package com.example.project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.project.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final FloatingActionButton addFab;

  @NonNull
  public final TextView logoutTextView;

  @NonNull
  public final RecyclerView mainRecyclerView;

  private ActivityMainBinding(@NonNull RelativeLayout rootView,
      @NonNull FloatingActionButton addFab, @NonNull TextView logoutTextView,
      @NonNull RecyclerView mainRecyclerView) {
    this.rootView = rootView;
    this.addFab = addFab;
    this.logoutTextView = logoutTextView;
    this.mainRecyclerView = mainRecyclerView;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addFab;
      FloatingActionButton addFab = ViewBindings.findChildViewById(rootView, id);
      if (addFab == null) {
        break missingId;
      }

      id = R.id.logoutTextView;
      TextView logoutTextView = ViewBindings.findChildViewById(rootView, id);
      if (logoutTextView == null) {
        break missingId;
      }

      id = R.id.mainRecyclerView;
      RecyclerView mainRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (mainRecyclerView == null) {
        break missingId;
      }

      return new ActivityMainBinding((RelativeLayout) rootView, addFab, logoutTextView,
          mainRecyclerView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
