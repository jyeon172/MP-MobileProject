// Generated by view binder compiler. Do not edit!
package com.example.project.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.project.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemDetailBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ConstraintLayout MainDetailView;

  @NonNull
  public final TextView chatTextview;

  @NonNull
  public final Button chattingButton;

  @NonNull
  public final TextView commentCnt;

  @NonNull
  public final ImageButton commentImg;

  @NonNull
  public final View contentEndLine;

  @NonNull
  public final Button detailCommentButton;

  @NonNull
  public final EditText detailCommentView;

  @NonNull
  public final TextView detailContentView;

  @NonNull
  public final TextView detailDateView;

  @NonNull
  public final TextView detailEmailView;

  @NonNull
  public final ImageView detailImageView;

  @NonNull
  public final RecyclerView detailRecyclerView;

  @NonNull
  public final TextView detailTitleView;

  @NonNull
  public final Toolbar detailToolbar;

  @NonNull
  public final ImageButton favoriteButton;

  @NonNull
  public final ImageButton favoriteButton2;

  @NonNull
  public final TextView favoriteTextview;

  @NonNull
  public final ImageButton userBtn;

  private ItemDetailBinding(@NonNull ConstraintLayout rootView,
      @NonNull ConstraintLayout MainDetailView, @NonNull TextView chatTextview,
      @NonNull Button chattingButton, @NonNull TextView commentCnt, @NonNull ImageButton commentImg,
      @NonNull View contentEndLine, @NonNull Button detailCommentButton,
      @NonNull EditText detailCommentView, @NonNull TextView detailContentView,
      @NonNull TextView detailDateView, @NonNull TextView detailEmailView,
      @NonNull ImageView detailImageView, @NonNull RecyclerView detailRecyclerView,
      @NonNull TextView detailTitleView, @NonNull Toolbar detailToolbar,
      @NonNull ImageButton favoriteButton, @NonNull ImageButton favoriteButton2,
      @NonNull TextView favoriteTextview, @NonNull ImageButton userBtn) {
    this.rootView = rootView;
    this.MainDetailView = MainDetailView;
    this.chatTextview = chatTextview;
    this.chattingButton = chattingButton;
    this.commentCnt = commentCnt;
    this.commentImg = commentImg;
    this.contentEndLine = contentEndLine;
    this.detailCommentButton = detailCommentButton;
    this.detailCommentView = detailCommentView;
    this.detailContentView = detailContentView;
    this.detailDateView = detailDateView;
    this.detailEmailView = detailEmailView;
    this.detailImageView = detailImageView;
    this.detailRecyclerView = detailRecyclerView;
    this.detailTitleView = detailTitleView;
    this.detailToolbar = detailToolbar;
    this.favoriteButton = favoriteButton;
    this.favoriteButton2 = favoriteButton2;
    this.favoriteTextview = favoriteTextview;
    this.userBtn = userBtn;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemDetailBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemDetailBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_detail, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemDetailBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      ConstraintLayout MainDetailView = (ConstraintLayout) rootView;

      id = R.id.chatTextview;
      TextView chatTextview = ViewBindings.findChildViewById(rootView, id);
      if (chatTextview == null) {
        break missingId;
      }

      id = R.id.chattingButton;
      Button chattingButton = ViewBindings.findChildViewById(rootView, id);
      if (chattingButton == null) {
        break missingId;
      }

      id = R.id.commentCnt;
      TextView commentCnt = ViewBindings.findChildViewById(rootView, id);
      if (commentCnt == null) {
        break missingId;
      }

      id = R.id.commentImg;
      ImageButton commentImg = ViewBindings.findChildViewById(rootView, id);
      if (commentImg == null) {
        break missingId;
      }

      id = R.id.contentEndLine;
      View contentEndLine = ViewBindings.findChildViewById(rootView, id);
      if (contentEndLine == null) {
        break missingId;
      }

      id = R.id.detailCommentButton;
      Button detailCommentButton = ViewBindings.findChildViewById(rootView, id);
      if (detailCommentButton == null) {
        break missingId;
      }

      id = R.id.detailCommentView;
      EditText detailCommentView = ViewBindings.findChildViewById(rootView, id);
      if (detailCommentView == null) {
        break missingId;
      }

      id = R.id.detailContentView;
      TextView detailContentView = ViewBindings.findChildViewById(rootView, id);
      if (detailContentView == null) {
        break missingId;
      }

      id = R.id.detailDateView;
      TextView detailDateView = ViewBindings.findChildViewById(rootView, id);
      if (detailDateView == null) {
        break missingId;
      }

      id = R.id.detailEmailView;
      TextView detailEmailView = ViewBindings.findChildViewById(rootView, id);
      if (detailEmailView == null) {
        break missingId;
      }

      id = R.id.detailImageView;
      ImageView detailImageView = ViewBindings.findChildViewById(rootView, id);
      if (detailImageView == null) {
        break missingId;
      }

      id = R.id.detailRecyclerView;
      RecyclerView detailRecyclerView = ViewBindings.findChildViewById(rootView, id);
      if (detailRecyclerView == null) {
        break missingId;
      }

      id = R.id.detailTitleView;
      TextView detailTitleView = ViewBindings.findChildViewById(rootView, id);
      if (detailTitleView == null) {
        break missingId;
      }

      id = R.id.detail_toolbar;
      Toolbar detailToolbar = ViewBindings.findChildViewById(rootView, id);
      if (detailToolbar == null) {
        break missingId;
      }

      id = R.id.favoriteButton;
      ImageButton favoriteButton = ViewBindings.findChildViewById(rootView, id);
      if (favoriteButton == null) {
        break missingId;
      }

      id = R.id.favoriteButton2;
      ImageButton favoriteButton2 = ViewBindings.findChildViewById(rootView, id);
      if (favoriteButton2 == null) {
        break missingId;
      }

      id = R.id.favoriteTextview;
      TextView favoriteTextview = ViewBindings.findChildViewById(rootView, id);
      if (favoriteTextview == null) {
        break missingId;
      }

      id = R.id.userBtn;
      ImageButton userBtn = ViewBindings.findChildViewById(rootView, id);
      if (userBtn == null) {
        break missingId;
      }

      return new ItemDetailBinding((ConstraintLayout) rootView, MainDetailView, chatTextview,
          chattingButton, commentCnt, commentImg, contentEndLine, detailCommentButton,
          detailCommentView, detailContentView, detailDateView, detailEmailView, detailImageView,
          detailRecyclerView, detailTitleView, detailToolbar, favoriteButton, favoriteButton2,
          favoriteTextview, userBtn);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
