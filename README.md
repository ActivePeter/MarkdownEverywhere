# MarkdownEverywhere
[https://github.com/ActivePeter/MarkdownEverywhere](https://github.com/ActivePeter/MarkdownEverywhere)

 a project to reach a goal of markdown wiki notes everywhere

由于自己有在本地记笔记的习惯，同时又有两个笔记本，需要进行md文件同步，并且希望通过自己的服务器网站查阅到我的笔记，我准备开始做这个程序。

大概原理是，通过git仓库，我们可以同步.md文件。然后服务端通过某些手段，比如jenkins 或者是python脚本，定时监听我的仓库，如果有变更，就自动更新数据到服务端，然后我再通过vue前端去访问后端，后端读取目录文件，

## 当前部署的我的知识库（demo）

------->  [传送门](https://hanbaoaaa.xyz/hanbaoNote/)

## 当前已经具有的功能

1.可以配置一个仓库

2.通过webhook来提醒服务端更新仓库

3.支持无限层树结构

4.完美支持移动端和电脑端。使用了better scroll库。防止浏览器滚动操作出问题。

5.对ios13的safri做了移动端检测支持

6.可以查看当前的commit版本

7.获取文章请求做了校验。版本不一致会刷新当前列表

8.提供了刷新按钮

9.侧边栏动态的隐藏

## Current Progress（v0.1已经完成）

![OK4_1__7GD2MN_9_KK__4DF.png](https://i.loli.net/2020/06/22/ZRoG6UA8eSkX1j4.png)





> ## Project setup（front）
>
> 问题描述：
> Unexpected end of JSON input while parsing near '…"
>
> 解决办法：
> （1）npm install --registry=https://registry.npm.taobao.org --loglevel=silly
> （2） npm cache clean --force
> （3） npm install
>
> ```
> npm install
> ```
>
> ### Compiles and hot-reloads for development
>
> ```
> npm run serve
> ```
>
> ### Compiles and minifies for production
>
> ```
> npm run build
> ```
>
> ### Customize configuration
>
> See [Configuration Reference](https://cli.vuejs.org/config/).



## Operational process（back）

初始化运行，会调用ServletContextLTest ，然后会加载notemanager 单例，

单例加载过程中先读取配置文件。如果没有配置文件，则不工作，

配置文件 需要配置gitee仓库的地址，

然后在前端设置仓库地址，或者已有配置文件，那么就开始clone仓库，

由于hook等会引起一个线程的数据更新，更新过程中重新构建对象，对象构建完成后。

