# Fab-Morph-Layout
Example of compound view, you can read more [here](http://deadswine.com/uncategorized/android-custom-view-02-compound-view/).

Currently suporting API 16 with diffrent animations than in lolipop and above

[![VIDEO - CLICK ME](http://deadswine.com/wp-content/uploads/2015/12/DeadswineViews-FAB-Morph.mp4)](http://deadswine.com/wp-content/uploads/2015/12/DeadswineViews-FAB-Morph.mp4)

### How to use

In xml pass any layout containing fab and with id=fab and any view with id=morphTarget


```xml
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <android.support.design.widget.FloatingActionButton
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="right|bottom"
        android:layout_marginBottom="32dp"
        android:layout_marginRight="16dp"
        app:fabSize="normal"
        />

    <LinearLayout
        android:id="@+id/morphTarget"
        android:layout_width="match_parent"
        android:layout_height="64dp"
        android:layout_gravity="bottom"
        android:background="?attr/colorAccent"
        android:gravity="center_vertical"
        android:orientation="horizontal"
        android:visibility="visible">
        
        <!-- Add any views you want here-->
        
    </LinearLayout>
</FrameLayout>
```

Use getTargetView() with casting in orther to acces your target view

```java
 LinearLayout myTargetLayout = (LinearLayout) mDeadswineFabMorphLayout.getTargetView();
```
### License


```
Copyright 2015 Deadswine Studio

Licensed to the Apache Software Foundation (ASF) under one or more contributor
license agreements. See the NOTICE file distributed with this work for
additional information regarding copyright ownership. The ASF licenses this
file to you under the Apache License, Version 2.0 (the "License"); you may not
use this file except in compliance with the License. You may obtain a copy of
the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
License for the specific language governing permissions and limitations under
the License.
```
