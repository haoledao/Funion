package com.fen.fund.ui;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.fen.fund.model.enums.FundTypeEnum;
import com.fen.fund.ui.base.BaseActivity;
import com.qmuiteam.qmui.skin.QMUISkinHelper;
import com.qmuiteam.qmui.skin.QMUISkinValueBuilder;
import com.qmuiteam.qmui.skin.SkinWriter;
import com.qmuiteam.qmui.widget.tab.QMUITabSegment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.ivLogo) ImageView ivLogo;
    @BindView(R.id.btnAdd) ImageButton btnAdd;
    @BindView(R.id.btnSearch) View btnSearch;
    @BindView(R.id.tsMain) QMUITabSegment tsMain;
    @BindView(R.id.vpMain) ViewPager vpMain;

    private Map<FundTypeEnum, View> viewMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initActivity();
    }

    @Override
    protected boolean initService() {
        return false;
    }

    @Override
    protected void initActivity() {

    }

    @OnClick({R.id.btnAdd, R.id.btnSearch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                break;
            case R.id.btnSearch:
                break;
        }
    }

    private View getPageView(FundTypeEnum type) {
        View view = viewMap.get(type);
        if (view == null) {
            TextView textView = new TextView(this);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            textView.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
            textView.setText("这是第 " + (type.getPosition() + 1) + " 个 Item 的内容区");
            QMUISkinHelper.setSkinValue(textView, new SkinWriter(){
                @Override
                public void write(QMUISkinValueBuilder builder) {
                    builder.textColor(R.attr.colorPrimaryDark);
                }
            });
            view = textView;
            viewMap.put(type, view);
        }
        return view;
    }

}
