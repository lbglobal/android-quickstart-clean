## app/build.gradle 的一般设置

1.加入了 so 库 abi 输出的限定。加这个的原因是因为有些第三方 SDK 有编译出 x64 的，有些第三方 SDK 则没有，如果刚好这两个第三方 SDK 都要用，那么在 x64 机器上会导致加载 so 库的错误。要解决这个问题，要么是通通提供 x64 的 so 库，要么是通通不提供，仅提供 x86 的 so 库。

```gr
ndk {
    abiFilters 'armeabi', 'armeabi-v7a', 'x86'
}
```

2.多 dex 分包应该是需要的。现在的库数量和第三方 SDK 真是越搞越多了，没这个真不行。

```gr
multiDexEnabled true
```

3.既然 MultiDex 都用上了，不开大点内存，也是不行的。

```gr
dexOptions {
    javaMaxHeapSize "4g"
}
```

4.要编译第三方提供的 aar 包，得把目录加入到编译环境里面去。

```groovy
repositories {
    flatDir {
        dirs 'libs'
    }
}
```



## 自定义 Application 的简洁写法

自定义的 Application 类在任何时候都应该保持精简，别整一个成大胖子似的，又 get 又 set，实在是恶心的不行，我见过好多项目都喜欢在自定义的 Application 类里写各种全局变量。我认为在 onCreate 里面注意下程序进程的判断，把各种初始化代码下发到 AppRuntimeInitializer 类里边就够了，以后就不要在 Application 类里边加代码。

```java
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.APPLICATION_ID.equals(getCurProcessName(this))) {
            AppRuntimeInitializer.INSTANCE.initRuntime(this);
        }

        x.Ext.init(this);
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
```



## BaseActivity 的简洁写法

BaseActivity 是另外一个容易膨胀的地方，所以要仔细的控制里边的代码逻辑。我认为有设置状态栏颜色、设置是否透明状态栏、权限请求回调这三个方法就够了，再多就慢慢乱了。至于为什么避免使用 XXXActivity.this 这种写法，完全是因为打字时输入 mActivity 只输入一个字母 m 然后代码提示就出来了，直接回车上屏。如果使用 XXXActivity.this 这种写法，那么还需要多按一个 Shift 键切换大小写。

```java

public class BaseActivity extends AppCompatActivity {
    protected Activity mActivity; // 给子类用的，避免使用 XXXActivity.this 这种写法

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        x.view().inject(this);

        mActivity = this;
        setStatusBarColor(R.color.colorPrimaryDark);
    }

    protected void setStatusBarColor(int colorResId) {
    }

    protected void setTranslucentStatus(boolean on) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        AppRuntimeInitializer.INSTANCE.dealOnRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
```



## BaseViewHolder 的简洁写法

这里只考虑 RecyclerView 的 ViewHolder，ListView 建议不要再用了。

```java
public abstract class BaseViewHolder extends RecyclerView.ViewHolder {

    protected Context mContext;

    public BaseViewHolder(View itemView) {
        super(itemView);

        mContext = itemView.getContext();

        x.view().inject(this, itemView);
    }
}
```



## 自定义一个带小红点的 RadioButton

这就是程序的首页底部的四个 Tab 或是五个 Tab，别告诉我你的程序不需要的底部的 Tab。小红点是经常的需求，这个直接画出来。

```java
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(isShow) {
            canvas.drawCircle((getWidth()/2+topDrawableWidth/2), offset, radius, paint);
        }
    }
```



## styles.xml 的一般设置

1.为了兼容 v19（也就是 Android 4.4）的透明状态栏，得单独在自定义主题  AppTheme 加上

```xm
<item name="android:windowTranslucentStatus">true</item>
```

2.有一个底部弹出动画，也是比较流行，把它加上以备不时之需。

```xm
    <!-- 仿 iOS 底部弹出动画 -->
    <style name="BottomDialogAnim" parent="android:Animation">
        <item name="@android:windowEnterAnimation">@anim/dialog_enter</item>
        <item name="@android:windowExitAnimation">@anim/dialog_exit</item>
    </style>
```



## package分包设置

这个就个人不同而语了，看具体的项目情况。
