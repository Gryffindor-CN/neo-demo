# Iterator(迭代器模式)

## 为什么要引入 Iterator 设计模式？
为什么一定要考虑引入 **Iterator** 这种复杂的设计模式呢？如果是数组，直接使用 for 循环语句进行遍历处理不就可以了吗？为什么还要集合之外引入 **Iterator** 这个角色呢？

一个重要的理由是，引入 **Iterator** 后可以将遍历与实现分享开来。在 **Sample** 例子中，请看下面的代码：
```java
while (iterator.hasNext()) {
    Book b = (Book) iterator.next();
    System.out.println(b.getName());
}
```
这里只使用了 **Iterator** 的 `hasNext` 方法和 `next` 方法，并没有调用 **BookShelf** 的方法。也就是说，这里的 `while` 循环并不依赖于 **BookShelf** 的实现。

如果编写 **BookShelf** 的开发人员决定放弃用数组来管理书本，而是用 `java.util.Vector` 取而代之，会怎样呢？不管 **BookShelf** 如何变化，只要 **BookShelf** 的 `iterator` 方法能正确地返回 **Iterator** 的实例（也就是说，返回的 **Iterator** 类的实例没有问题，`hasNext` 和 `next` 方法都可以正常工作），即使不对上面的 `while` 循环做任何修改，代码都可以正常工作。

这对于 **BookShelf** 的调用者来说真是太方便了。设计模式的作用就是帮助我们编写可利用的类。所谓 “可复用”，就是指将类实现为 “组件”，当一个组件发生改变时，不需要对其他的组件进行修改或是只需要很小的修改即可应对。

## 扩展学习
在示例程序 **sample** 中，当书的数量超过最初指定的书架容量时，就无法继续向书架中添加书本了。我们尝试不使用数组，而是用 `java.util.ArrayList` 修改程序，确保当书的数量超过最初指定的书架容量时也能继续向书架中添加书本。（Tips：此时，我们可放弃使用 **BookShelf** 和 **BookShelfIterator** 类，新建一组对应的实现类。）