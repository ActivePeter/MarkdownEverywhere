# MarkdownEverywhere
 a project to reach a goal of markdown wiki notes everywhere

由于自己有在本地记笔记的习惯，同时又有两个笔记本，需要进行md文件同步，并且希望通过自己的服务器网站查阅到我的笔记，我准备开始做这个程序。

大概原理是，通过git仓库，我们可以同步.md文件。然后服务端通过某些手段，比如jenkins 或者是python脚本，定时监听我的仓库，如果有变更，就自动更新数据到服务端，然后我再通过vue前端去访问后端，后端读取目录文件，