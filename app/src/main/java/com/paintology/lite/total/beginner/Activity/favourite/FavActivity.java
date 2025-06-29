package com.paintology.lite.total.beginner.Activity.favourite;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;

import com.paintology.lite.total.beginner.Activity.BaseActivity;
import com.paintology.lite.total.beginner.Activity.MyConstantsKt;
import com.paintology.lite.total.beginner.Activity.utils.ExtensionsKt;
import com.paintology.lite.total.beginner.BuildConfig;
import com.paintology.lite.total.beginner.Model.OperationAfterLogin;
import com.paintology.lite.total.beginner.R;
import com.paintology.lite.total.beginner.ads.callbacks.BannerCallBack;
import com.paintology.lite.total.beginner.ads.enums.NativeType;
import com.paintology.lite.total.beginner.databinding.ActivityFavBinding;
import com.paintology.lite.total.beginner.util.CommunityInterface;
import com.paintology.lite.total.beginner.util.ContextKt;
import com.paintology.lite.total.beginner.util.FireUtils;
import com.paintology.lite.total.beginner.util.FirebaseUtils;
import com.paintology.lite.total.beginner.util.StringConstants;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class FavActivity extends BaseActivity implements CommunityInterface {

    StringConstants constants = new StringConstants();
    public static CommunityInterface obj_cmunity;

    public static FavActivity favActivity;

    public ActivityFavBinding binding;
    PageAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        MyConstantsKt.checkForIntroVideo(this, StringConstants.intro_progress);

        favActivity = this;

        obj_cmunity = this;
        binding.customToolbar.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.customToolbar.tvTitle.setText(getString(R.string.favourites));
        binding.customToolbar.tvTitle.setTextSize(19f);

        binding.customToolbar.ivMenu.setVisibility(View.VISIBLE);
        binding.customToolbar.ivMenu.setOnClickListener(v -> {
            ExtensionsKt.showPopupMenu(binding.customToolbar.ivMenu, R.menu.new_common_menu, new Function1<MenuItem, Unit>() {
                @Override
                public Unit invoke(MenuItem menuItem) {
                    MyConstantsKt.commonMenuClick(FavActivity.this, menuItem, StringConstants.intro_favorite);
                    return null;
                }
            });
        });
        viewPagerAdapter = new PageAdapter(
                getSupportFragmentManager());
        binding.viewPager2.setAdapter(viewPagerAdapter);
        binding.viewPager2.setOffscreenPageLimit(4);


        binding.tabLayout.setupWithViewPager(binding.viewPager2);


    }

    @Override
    public void ReflectColor(int code) {

    }

    @Override
    public void ShowProfileIcon() {

    }

    @Override
    public boolean isLoggedIn(OperationAfterLogin _operationAfterLogin) {
        if (constants.getBoolean(constants.IsGuestUser, this)) {
            FireUtils.openLoginScreen(this, true);
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void showToolTip() {

    }

    @Override
    public void DisableAllView(int typeToEnable) {

    }

    @Override
    public void enlargeImageView(String _url) {
        try {
            FirebaseUtils.logEvents(this, constants.double_tap_image_community);
            ContextKt.showEnlargeImage(this, _url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showHideFab(boolean needToShown) {

    }

    @Override
    public void hideSearchBar() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}