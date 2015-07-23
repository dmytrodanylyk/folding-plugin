### Android File Grouping v1.0

![](screenshots/Preview.png)

### Description

This plugin is very helpful in Android Development. It can display your files as a group of different folders in project structure view.

**Note:**

- It does NOT move files.
- It does NOT create folders.

Naming rules: folder name -> file name part till underscore

Before grouping

```
res/
    layout/
      chat_activity.xml
      chat_toolbar.xml
      chat_item.xml
      chat_share_view.xml
      home_activity.xml
      home_toolbar.xml
      home_fragment_sign_in.xml
      home_fragment_sign_up.xml
```

After grouping

```
res/
    layout/
      chat/
        chat_activity.xml
        chat_toolbar.xml
        chat_item.xml
        chat_share_view.xml
      home/
        home_activity.xml
        home_toolbar.xml
        home_fragment_sign_in.xml
        home_fragment_sign_up.xml
```

**Note:** The Android project view defines its own structure and does not allow modifying the structure through any extensions. Make sure your are in Project structure view, NOT Android.

![](screenshots/Project.png)