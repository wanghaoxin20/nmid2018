@功能
---------------------------------------------
#控制台的简易租车系统
---------------------------------------------



@rent_record
---------------------------------------------
#租车所有的信息存放文件夹



@bin/cars.properties
---------------------------------------------
#所有车辆信息的配置文件



@src/seconedweek/Start.java
---------------------------------------------
#租车系统的开始的入口类



@src/seconedweek.entity.car
---------------------------------------------
#该包下为汽车的实体类对象
---------------------------------------------
@Car.java
#所有车的父类
#定义了基本属性carId, carName, rentFee
#可通过子类重写getCarType()方法获取子类的车辆类型
--------------------------------------------
@PassengerCar.java  
#载客车类，继承自Car
#具有属性maxManned,最大载人数
#toString 输出规范
#静态变量CarType = "PassengerCar"
--------------------------------------------
@CarryCar.java
#载货车类，继承自Car
#具有属性maxCarry，最大载货量
#toString 输出规范
#静态变量CarType = "CarryCar"
--------------------------------------------
@PickupCar.java
#皮卡车类，继承自Car
#具有属性maxCarry和maxManned
#toString 输出规范
#静态变量CarType = "PickupCar"



@src/seconedweek.entity.record
--------------------------------------------
#该包下为租车记录的实体对象
--------------------------------------------
@RentRecord.java
#租车记录类
#继承serializable接口，可写入文件或读取
#定义了租车的各种信息
#add 方法添加单次租车信息，包括租车号，租车时间和租车数量
#makeTotal 对该次租车数据进行结算，不同车型通过该车的CarType进行结算
#toString 对该次租车记录输出进行规范



@src/seconedweek.util
--------------------------------------------
#该包下为工具类
--------------------------------------------
@FileUtil.java
#文件工具类
#write_Record 将租车记录写入rent_record文件夹中
#read_record 从文件中读取租车记录



@src/seconedweek.biz
---------------------------------------------
#该包下主要为系统的逻辑处理类
---------------------------------------------
@CarFatory.java
#车工厂类
#getAllCar 读取配置文件中的车辆信息，再通过信息中车辆的类型实例化车辆，返回所有车辆的集合
#getCar 通过车辆的id号生成该车辆的实例
---------------------------------------------
@CarRentSystem.java
#系统的主要事务处理类
#init 系统初始化，通过getCars获取所有车辆实例
#systemStart 系统运行，展示菜单选项，并对选择进行处理
#showMenu  显示菜单
#showCar 展示所有车辆信息
#rentService 租车服务的逻辑处理，租车成功将租车记录写入文件
#showRecord 从rent_record文件夹中获取所有租车记录进行显示
#isNum 判断输入的字符串是否为数字
