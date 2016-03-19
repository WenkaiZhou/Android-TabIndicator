
# TabIndicator for Android
**[English](https://github.com/xuehuayous/Android-TabIndicator)** **[中文](https://github.com/xuehuayous/Android-TabIndicator/blob/master/README-zh.md)**

TabIndicator 是一个强大的底部导航控件，并且提供了许多配置方法来达到您的显示效果和需求。  

**简单示例**  
![TabIndicator Simple Demo](https://raw.githubusercontent.com/xuehuayous/Android-TabIndicator/master/tab_indicator_sample.gif)

**Page导航示例**  
![TabIndicator Simple Demo](https://raw.githubusercontent.com/xuehuayous/Android-TabIndicator/master/tab_page_indicator_sample.gif)

**增强Page导航示例**   
![TabIndicator Simple Demo](https://raw.githubusercontent.com/xuehuayous/Android-TabIndicator/master/tab_page_indicator_ex_sample.gif)

## 在项目中使用 TabIndicator

如果您的项目使用 Gradle 构建, 只需要在您的`build.gradle`文件添加下面一行到 `dependencies` :

```
	compile 'com.kevin:tabindicator:1.0.2'
```

## 简单使用 ##

### 在layout.xml 中配置LoopView ###
在Layout文件添加`com.kevin.tabindicator.TabIndicator` 

    <com.kevin.tabindicator.TabIndicator
        android:id="@+id/tab_indicator_act_ti"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:background="@drawable/tabbg"
        kevin:tabSelectedColor="#3F51B5"
        kevin:tabSelectedIcons="@array/qq_selected_icon"
        kevin:tabTextSize="12sp"
        kevin:tabUnselectedColor="#555555"
        kevin:tabUnselectedIcons="@array/qq_normal_icon" />

### 在代码中配置 ###

	TabIndicator mTabIndicator = (TabIndicator) this.findViewById(R.id.tab_indicator_act_ti);
    mTabIndicator.setOnTabSelectedListener(new TabPageIndicatorEx.OnTabSelectedListener() {

        @Override
        public void onTabSelected(int index) {
            mTextView.setText("Page"+(index+1));
            }
        });

## 更多配置 ##

### XML 配置 ###

在XML中使用TabIndicator，可以有如下配置：

    <declare-styleable name="TabIndicator">
        <!-- 图标数组,应用在单图标渐变 -->
        <attr name="tabIcons" format="reference" />
        <!-- 选中图标数组 -->
        <attr name="tabSelectedIcons" format="reference" />
        <!-- 未选中图标数组 -->
        <attr name="tabUnselectedIcons" format="reference" />
        <!-- 文字数组 -->
        <attr name="tabLabels" format="reference" />
        <!-- 选中时颜色 -->
        <attr name="tabSelectedColor" format="reference|color" />
        <!-- 未选中时颜色 -->
        <attr name="tabUnselectedColor" format="reference|color" />
        <!-- 文字字体大小 -->
        <attr name="tabTextSize" format="dimension" />
        <!-- 条目边距 -->
        <attr name="tabItemPadding" format="dimension" />
        <!-- 是否渐变切换 -->
        <attr name="tabGradualChange" format="boolean" />
        <!-- 指示点大小 -->
        <attr name="TabIndicatorSize" format="dimension" />
    </declare-styleable>

### 在代码中配置 ###

	// 设置ViewPager
    mTabPageIndicatorEx.setViewPager(mViewPager);
	// 设置指定位置指示点展示与否
    mTabPageIndicatorEx.setIndicateDisplay(2, true);
	// 设置切换监听
    mTabPageIndicatorEx.setOnTabSelectedListener(new TabPageIndicatorEx.OnTabSelectedListener() {

        @Override
        public void onTabSelected(int index) {
                mViewPager.setCurrentItem(index, false);
        }
    });


## License

    Copyright 2015 Kevin zhou

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.