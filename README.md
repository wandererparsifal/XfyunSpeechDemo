# XfyunSpeechDemo

## 讯飞语音 Demo

Demo 使用 Android Studio 3.0 开发，JDK 版本 8
<p>
Demo 分为两个 Module，app 和 speechDemo。其中 speechDemo 是讯飞官方 Demo，功能较全；而 app 重点封装了语音播报和语音监听两个功能。两个 Module 均可独立使用，没有依赖关系。
<p>
由于讯飞 SDK 存在使用限制（每日 500 次），请在使用前自行下载并替换 SDK。SDK 替换流程如下：
<p>
1. 到 http://aiui.xfyun.cn/default/index 注册账号。登陆后点击“我的应用”，点击“新建应用”，名称、分类、，描述自行填写，应用平台选择 Android。
<br/>
2. 创建好后，选中应用。“语义 ”一栏中显示“您还没有给该情景模式添加技能”。点击“添加技能”，添加“天气”（Demo 中只做了天气的支持，有其他需要可自行选择）。
<br/>
3. 点击“我的技能”，点击创建新技能，技能名称“command”，别名“肯定否定词”。意图列表添加“negative”，对应的“语料 ”栏中添加“不”，“不用”，“不需要”，“不了”。意图列表添加“positive”，对应的“语料 ”栏中添加“是”，“好”，“嗯”，“对”，“是的”，“好的”，“可以”。点击“发布”，名称填“PNCommand”。
<br/>
4. 回到“我的应用”，添加技能，可以看到自定义技能中多出了“command”,选中。
<br/>
5. 点击“SDK下载”，文件名类似“Android_aiui_soft_1125_59f140ae”，其中 <font color="#E32636">59f140ae</font> 是应用 ID，将“<font color="#007FFF">Android_aiui_soft_1125_59f140ae/libs/</font>”文件夹下的“<font color="#007FFF">armeabi</font>”，“<font color="#007FFF">armeabi-v7a</font>”，“<font color="#007FFF">Msc.jar</font>”拷贝到“<font color="#007FFF">XfyunSpeechDemo/app/libs</font>”，替换掉原文件，“<font color="#007FFF">Android_aiui_soft_1125_59f140ae/sample/speechDemo/libs</font>”文件夹下的所有文件替换到“<font color="#007FFF">XfyunSpeechDemo/speechDemo/libs</font>”。
<br/>
6. 修改应用 ID，修改“<font color="#007FFF">XfyunSpeechDemo/app/src/main/res/values/strings.xml</font>”和“<font color="#007FFF">XfyunSpeechDemo/speechDemo/src/main/res/values/strings.xml</font>”中的“&lt;string name="app_id"&gt;59f140ae&lt;/string&gt;”一行，将 <font color="#E32636">59f140ae</font> 改为你自己的应用 ID，不要使用 Windows 记事本修改任何文件。
