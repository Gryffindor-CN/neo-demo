# 设计模式学习

- 语言：JAVA

> 设计模式是把双刃剑，正确地使用它可以提高系统的适应性，误用则会反过来降低系统的适应性。
> 我们学习设计模式，并不是为了遥远的将来而打算，而是将它当作是一种有益的技巧，因为它可以帮助我们从全新的角度审视我们每天所编写的代码，从而帮助我们开发出更容易复用和扩展的应用。

## 1 设计模式分类

GoF在《设计模式：可复用面向对象软件的基础》中对23种设计模式分为三类：创建型设计模式、结构型设计模式、行为型设计模式。
本例子也结合各种设计模式的特性，有不同的分类，具体如下。

|          | 创建型   | 结构型               | 行为型               |
| :------- | :------ | :------------------- | :------------------- |
| 练下手   | -        | [Adapter(适配式模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/adapter)  | [Iterator(迭代器模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/iterator) |
| 交给子类 | [Factory method(工厂方法模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/factoryMethod) | - | [Template Method(模板方法模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/templateMethod) |
| 生成实例 | [Singleton(单例模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/singleton)<br>[Prototype(原型模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/prototype)<br>[Builer(生成器模式)](https://github.com/305983806/neo-demo/tree/master/design-pattern/builder)<br>Abstract Factory(抽象工厂模式) | - | - |
| 分开考虑 | Bridge(桥接模式) | - | Strategy(策略模式) |
| 一致性   | - | Composite(组合模式)<br>Decorator(装饰模式) | - |
| 访问数据结构 | - | - | Visitor(访问者模式)<br>Chain of Responsibility(职责链模式) |
| 简单化   | - | Facade(外观模式)  | Mediator(中介者模式) |
| 管理状态 | - | -  | Observer(观察者模式)<br>Memento(备忘模式)<br>State(状态模式) |
| 避免浪费 | - | Flyweight(享元模式)<br>Proxy(代理模式) | - |
| 用类来表现 | - | - | Command(命令模式)<br>Interpreter(解释器模式) |

## 2 面向对象设计原则
如何同时提高代码的可维护性和可利用性是面向对象设计需要解决的核心问题之一。面向对象设计原则为支持可维护性复用而诞生，这些原则蕴含在很多设计模式中，它们是从许多设计方案中总结出的指导性思想。请看以下表格：

| 名称 | 定义 | 使用频率 |
| ------ | ------ | ------ |
| 单一职责原则 | 一个类只负责一个功能领域中的相应职责 | ★★★★☆ |
| 开闭原则 | 软件实体应对扩展开放，而对修改关闭 | ★★★★★ |
| 里氏代换原则 | 所有引用基类对象的地方都能够透明地使用其他子类的对象 | ★★★★★ |
| 依赖倒转原则 | 抽象不应该依赖于细节，细节应该依赖于抽象 | ★★★★★ |
| 接口隔离原则 | 使用多个专门的接口，而不使用单一的总接口 | ★★☆☆☆ |
| 合成复用原则 | 尽量使用对象组合，而不是继承来达到复用的目的 | ★★★★☆ |
| 迪米特法则 | 一个软件实体应当尽可能少地与其他实体发生相互作用 | ★★★☆☆ |

### 2.1 单一职责原则

单一职责原则：一个类（大到模块、小到方法）承担的职责越多，它被复用的可能性就越小；而且一个类承担的职责过多，就相当于将这些职责耦合在一起，当其中一个职责变化时，可能会影响其他职责的动作，因此要将这些职责进行分离。将不同的职责封装在不同的类中，即将不同的变化原因封装在不同的类中，如果多个职责总是同时发生改变，则可将它们封装在同一个类中。

单一职责原则是实现 **高内聚、低耦合** 的指导方针，它是最简单但又最难运用的原则，需要设计人员发现类的不同职责并将其分离，而发现类的多重职责需要设计人员具有较强的分析设计能力和相关实践经验。

#### 请看例子

某程序员针对客户信息图形统计模块提出了以下接口设计方案：

```java
public interface CustomerDataChart {
    // 连接数据库
    Connection getConnection();
    // 查询所有客户信息
    List findCustomers();
    // 创建图表
    void createChart();
    // 显示图表
    void displayChart();
}
```

`CustomerDatachart` 类承担了太多职责，既包含与数据库相关的方法，又包含与图表生成和显示相关的方法。如果在其他类中也需要连接数据库或者使用 `findcustomers()`方法查询客户信息，则难以实现代码的重用。无论是修改数据库存连接方式还是修改图表显示方式都需要修改该类，它不止一个引起它变化的原因，违背了单一职责原则。因此需要对该类进行拆分，使其满足单一职责原则，可以拆分为如下三个类：

```java
public abstract class CustomerDataChart {
    private CustomerDao dao;

    public abstract void createChart();
    public abstract void displaychart();
}
```

**CustomerDataChart** 负责图表的生成和显示，包含方法 `createChart()` 和 `displayChart()`。

```java
public class CustomerDao {
    private DBUtil util;

    public List findCustomers() { return null; }
}
```

**CustomerdAO** 负责操作数据库存中的 Customer 表，包含对 Customer 表的增删改查等方法。

```java
public class DBUtil {
    public Connection getConnection() { return null; }
}
```

负责连接数据库。

### 2.3 里氏代换原则
在程序中尽量使用基类类型来对对象进行定义，而在运行时再确定其子类类型，用子类对象来替换父类对象。