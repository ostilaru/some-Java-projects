# 1: vue-cli 脚手架初始化项目
node + webpack + 镜像

node_modules: 项目依赖文件夹

public: 一般放置一些静态资源（图片），注意，放在public文件夹中的静态资源，webpack在打包的时候会原封不动地打包到dist文件夹

src: 源代码文件夹
    assets: 一般也放置静态资源（一般放置多个组件共用的静态资源），注意，放在assets文件夹里面的静态资源
            webpack打包的时候会把静态资源当做成一个模块，打包在JS文件里面

    components文件夹: 一般放置的是非路由组件（全局组件）

    App.vue: 唯一的根组件
    main.js: 程序入口文件，也是整个程序中最先执行的文件

babel.config.js: 配置文件（babel相关）

package.json: 项目‘身份证’，记录项目叫做什么，项目中有哪些依据，项目怎么运行

package-lock.json: 缓存性文件

# 2. 项目的其他配置

## 2.1 项目运行起来的时候，让浏览器自动打开(只支持vue-cli 5 之后的脚手架版本)
-- package.json
"scripts": {
    "serve": "vue-cli-service serve -- open", <----
    "build": "vue-cli-service build",
    "lint": "vue-cli-service lint"
  }

## 2.2 eslint校验功能关闭
-- 在根目录下创建一个 vue.config.js 配置文件
比如：声明变量但是不使用，eslint会报错
    module.exports = defineConfig({
    transpileDependencies: true,
    // 关闭 eslint
    lintOnSave: false
    })

## 2.3 src文件夹简写方法，配置别名 @

"paths": {
      "@/*": [
        "src/*"
      ]
},

@ 代表 src

# 3. 项目路由的分析
vue-router
前端所谓路由：KV键值对
key: URL （地址栏中的路径）
value: 相应的路由组件
注意: 项目是上中下结构

路由组件: 
    Home首页路由组件，Search路由组件, login登录路由组件, register注册路由组件
    非路由组件:   Header【首页、登录页】
                Footer【在首页、搜索页】, 在登录页没有

# 4. 完成非路由组件Header与Footer业务
在开发项目过程中，不再以 HTML + CSS 为主，主要是业务、逻辑
开发项目的时候：
    1. 书写静态页面（HTML + CSS）
    2. 拆分组件
    3. 获取服务器的数据动态展示
    4. 完成相应的动态业务逻辑

注意1：创建组件的时候，组件结构 + 组件的样式 + 图片资源

注意2：项目采用的是 less 样式，浏览器不识别 less 样式，需要通过less, less-loader 进行处理 less，把 less样式变为 css 样式，浏览器才能识别

注意3: 如果想让组件识别 less 样式，需要在 style 标签的身上加上 lang=less

## 4.1 使用组件的步骤（非路由组件）
-- 创建或者定义
-- 引入
-- 注册
-- 使用

## 4.2 在 Vue 项目中引入 ElementUI 组件库的步骤
-- 安装 npm install element-ui
-- 引入 在 main.js 中引入 Element-UI
    import Vue from 'vue'
    import ElementUI from 'element-ui'
    import 'element-ui/lib/theme-chalk/index.css'

    Vue.use(ElementUI)
-- 使用 在 .vue 文件中使用 Element-UI
    <el-button type="primary">主要按钮</el-button>


# 5. 路由组件的搭建
vue-router
路由组件应该有四个：
    1. Home首页路由组件
    2. Search搜索路由组件
    3. Login登录路由组件
    4. Register注册路由组件
-components文件夹: 一般放置的是非路由组件（全局组件）
-pages | views文件夹: 一般放置的是路由组件（局部组件）

## 5.1 配置路由
项目中配置的路由一般放置在router文件夹中

## 5.2 总结
路由组件与非路由组件的区别：
    1. 路由组件一般放在 pages | views 文件夹中，非路由组件一般放在 components 文件夹中
    2. 路由组件一般需要在 router 文件夹中进行注册（使用的即为组件的名字），非路由组件使用时以标签的形式使用
    3. 注册完路由，不管是路由组件还是非路由组件，身上都有$router、$route两个属性

$route: 一般获取路由信息【路径、query、params等】
$router: 一般进行编程式导航进行路由跳转

## 5.3 路由跳转
路由的跳转有两种形式：
    声明式导航 router-link，可以进行路由的跳转
    编程式导航 push | replace，可以进行路由的跳转

    编程式导航：声明式导航能做的，编程式导航都能做
    但是编程式导航除了可以进行路由跳转，还可以做一些其他的业务逻辑