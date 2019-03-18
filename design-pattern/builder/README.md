# 建造者模式

## 1 思路要点

### 1.1 谁知道什么

在面向对象编程中，“谁知道什么” 是非常 重要的。也就是说，我们需要在编程时注意哪个类可以使用哪个方法以及使用这个方法到底好不好。

在 [**sample**](https://github.com/305983806/neo-demo/tree/master/design-pattern/builder/sample) 中，Director 类不知道自己使用的究竟是 Builder 类的哪个子类，不论是将 **TextBuilder** 的实例传递给 **Director**，还是将 **HtmlBuilder** 类的实例传递给 **Director**，它都可以正常工作，原因正是 **Director** 类不知道 **Builder** 类的具体子类。

正是因为不知道才能够替换，正是因为可以替换，组件才具有高价值。作为设计 人员，我们必须时刻关注这种 “可替换性”。

### 1.2 设计时能够决定的事情和不能决定的事情

在 **Builder** 类中，需要声明编辑文档（实现功能）所必须的所有方法。**Director**