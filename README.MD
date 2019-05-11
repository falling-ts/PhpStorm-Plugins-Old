# PhpStorm Plugins

## 前世因果

作为一名程序猿，我患有 **「代码规范强迫症」**，其中 **「use 导入类优雅排序症」** 已经是晚期。

什么是 **「use 导入类优雅排序症」**？

我们看下面代码

```php
<?php

namespace App\Http\Controllers\NLP;

use App\Http\Controller;
use App\Models\Role;
use Illuminate\Support\Str;
use Illuminate\View\View;
use Illuminate\Http\JsonResponse;
use App\Models\Action;
use Illuminate\Http\Request;

class ActionController extends Controller
{
    // 省略...
}
```

use 导入 `参差不齐`，看的我针眼都快长出来，完全没有心情做开发呀。不给它排好序，心里难受。

```php
<?php

namespace App\Http\Controllers\NLP;

use App\Models\Role;
use App\Models\Action;
use App\Http\Controller;
use Illuminate\View\View;
use Illuminate\Support\Str;
use Illuminate\Http\Request;
use Illuminate\Http\JsonResponse;

class ActionController extends Controller
{
    // 省略...
}
```

大家看看这个，多舒服呀。但，这是我一行一行复制粘贴排出来的。好麻烦，好费力。

> 排：当导入的类太多，或者要排的 php 文件太多，简直给自己增加工作量；
> 
> 不排：我会发「病」的，看着参差不齐的 use 导入类，没心情开发项目。


```
就在这种情况背景下，我硬刚了两天 IntelliJ 和 Java，终于开发出来了一个一键式 use 类导入优雅排序。现在分享出来，给同样有这种「病」的童鞋使用。
```

## 安装

### 插件包地址

插件包地址 --> [PhpStorm-Plugins-v1.0](https://github.com/zgh-yuanshang/PhpStorm-Plugins/releases)

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/3C3SIFpzPE.png!large)

### 安装步骤

- 打开 PhpStorm 设置

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/1mLGZMNiB3.png!large)

- 选择 Plugins 的 Install Plugins form Disk...

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/OMN1hJGjDF.png!large)

- 选择下载好的 PhpStorm-Plugins.jar

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/s6o0ABGkqb.png!large)

- 最后，重启 PhpStorm

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/QOXU5gD1jo.png!large)

### 安装效果

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/uCBU1qG6uh.png!large)

在 PhpStorm 菜单栏右侧多出一个 Yuanshang 的工具集，里面暂有一个工具 Use Format。

## 使用

Use Format：利用正则规则和冒泡排序，对 use 导入类进行优雅排序。

### 第一种情况：简单情况

排序前：

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/ohWjY5B6j1.png!large)

`Alt + F` 或者鼠标点击 Use Format，进行排序。排序后：

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/VKXirO53tc.png!large)

### 第二种情况：复杂情况

排序工具，会删除从 `<?php` 到 use 导入类最后一行，中间的注释等非必要内容，并在 use 导入代码集前后加一换行。

排序前

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/X5pWf9FVyQ.png!large)

排序后

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/VKXirO53tc.png!large)

### 第三种情况：非类文件中 use 导入

排序前

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/54zMEKx2sE.png!large)

排序后

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/IZ0Pqe2xje.png!large)

## 异常

### 当没有打开文件，或鼠标焦点没有在文档上时

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/mekCruzJsh.png!large)

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/wmu51Sr4O2.png!large)

### 当打开非 php 文件，或 php 文件没有用 use 导入功能时

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/C9wrWCRO3Z.png!large)

![Laravel](https://iocaffcdn.phphub.org/uploads/images/201905/11/27709/OD6b05fha5.png!large)

## 最后

插件 GitHub 开源地址 ---> [传送门](https://github.com/zgh-yuanshang/PhpStorm-Plugins)

欢迎大家 Star。

对了，使用过程中，如果出现排序乱码，`ctrl + z` 回一下，然后欢迎提交问题。在评论区发表就可以。