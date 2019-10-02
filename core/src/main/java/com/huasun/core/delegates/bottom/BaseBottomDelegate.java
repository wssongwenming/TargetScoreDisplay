package com.huasun.core.delegates.bottom;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.huasun.core.R;
import com.huasun.core.R2;
import com.huasun.core.delegates.LatteDelegate;
import com.huasun.core.ui.launcher.ILauncherListener;
import com.huasun.core.util.log.LatteLogger;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * author:songwenming
 * Date:2019/9/25
 * Description:
 */
public abstract class BaseBottomDelegate extends LatteDelegate implements View.OnClickListener {

    private final ArrayList<LatteDelegate> ITEM_DELEGATES=new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS=new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,LatteDelegate> ITEMS=new LinkedHashMap<>();
    //当前Fragment是哪一个
    private int mCurrentDelegate=0;
    //进入页面后第一个展示的delegate
    private int mIndexDelegate=0;
    private int mClickedColor= Color.RED;
    //思路，建立父类的抽象方法就是为了在子类中赋值，setItems是起始源头，setItems->ITEMS
    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar=null;
    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }
    public abstract  int setIndexDelegate();
    @ColorInt
    public abstract int setClickedColor();
    private ILauncherListener mILauncherListener=null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILauncherListener){
            mILauncherListener=(ILauncherListener) activity;

        }
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate=setIndexDelegate();
        if(setClickedColor()!=0){
            mClickedColor=setClickedColor();
        }
        final ItemBuilder builder=ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items=setItems(builder);
        ITEMS.putAll(items);
        for(Map.Entry<BottomTabBean,LatteDelegate>item:ITEMS.entrySet()){
            final BottomTabBean key=item.getKey();
            final LatteDelegate value=item.getValue();
            TAB_BEANS.add(key);
            ITEM_DELEGATES.add(value);
        }
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size=ITEMS.size();
        //for循环显示下面的tab按钮
        for(int i=0;i<size;i++){
            //加载布局管理器,将xml布局转换为view对象,一个一个塞到mBottomBar内
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomBar);
            final RelativeLayout item=(RelativeLayout)mBottomBar.getChildAt(i);
            //设置每个item的点击事件
            item.setTag(i);//代替了setId()
            item.setOnClickListener(this);
            final IconTextView itemIcon= (IconTextView) item.getChildAt(0);
            final AppCompatTextView itemTitle= (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bean=TAB_BEANS.get(i);
            //初始化数据
            itemIcon.setText(bean.getIcon());
            itemTitle.setText(bean.getTitle());
            if(i==mIndexDelegate)
            {
                itemIcon.setTextColor(mClickedColor);
                itemTitle.setTextColor(mClickedColor);
            }
        }
        //@param a the array into which the elements of the list are to
        // be stored, if it is big enough; otherwise, a new array of the
        // same runtime type is allocated for this purpose.
        // @return an array containing the elements of the list
        //public <T> T[] toArray(T[] a)
        //显示上部的Fragment集合
        final SupportFragment[] delegateArray=ITEM_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelegate,delegateArray);
    }

    private void resetColor(){
        final int count=mBottomBar.getChildCount();
        for(int i=0;i<count;i++)
        {
            final RelativeLayout item=(RelativeLayout)mBottomBar.getChildAt(i);
            final IconTextView itemIcon=(IconTextView)item.getChildAt(0);
            itemIcon.setTextColor(getResources().getColor(R.color.Apricot));
            final AppCompatTextView itemTitle=(AppCompatTextView)item.getChildAt(1);
            itemTitle.setTextColor(getResources().getColor(R.color.darkRed));

        }
    }

    @Override
    public void onClick(View v) {
        final int tag=(int)v.getTag();
        resetColor();
        final RelativeLayout item=(RelativeLayout) v;
        final IconTextView itemIcon=(IconTextView)item.getChildAt(0);
        itemIcon.setTextColor(mClickedColor);
        final AppCompatTextView itemTitle=(AppCompatTextView)item.getChildAt(1);
        itemTitle.setTextColor(mClickedColor);
        //show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
        showHideFragment(ITEM_DELEGATES.get(tag),ITEM_DELEGATES.get(mCurrentDelegate));
        mCurrentDelegate=tag;
    }

    public ArrayList<LatteDelegate> getITEM_DELEGATES() {
        return ITEM_DELEGATES;
    }

    public ArrayList<BottomTabBean> getTAB_BEANS() {
        return TAB_BEANS;
    }

    public LinkedHashMap<BottomTabBean, LatteDelegate> getITEMS() {
        return ITEMS;
    }
}
