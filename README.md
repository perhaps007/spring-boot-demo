## SpringBoot Demo

>基于mybatis.generator.plugin实现自动生成实体类、mapper和xml     

## 快速上手
1. 修改sc/main/resources/builder/generatorConfig.xml的配置信息
    ```
    jdbcConnection->
    connectionURL：数据源
    userId：用户名
    password：密码
    ```
2. idea中在右边依次点开`maven`->`SpringbootDemo（项目名）`
`Plugins`->`myvatis-generator`,双击`myvatis-generator:generate`即可生成对应的实体、mapper和xml
3. 如果要修改生成文件的路径，请在pom调整以下参数
    ```
    targetJavaProject
    targetMapperPackage
    targetModelPackage
    targetResourcesProject
    targetXMLPackage
    ```

## 进度
- [x] mybatis-generator-plugin
- [x] reids
- [x] tk.mybatis



