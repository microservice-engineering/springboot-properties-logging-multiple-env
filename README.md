[参考文章]: http://tengj.top/2017/02/28/springboot2/

1. 默认的取值方式是通过value来实现的，然后把这个值定义到application.properties文件中

    @Value("${com.dudu.name}")
    private  String name;

1. 如果需要取的值很多，这样一个个写value比较麻烦，我们可以通过定义一个Config的bean来统一封装这些配置文件里面的属性

    //步骤一：首先在resource下面定义一个test.properties文件
    com.vgc.type=prod
    com.vgc.provider=easypark

    //步骤二：定义了封装这个properties的实体类
    package com.didspace.config;
    
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.context.annotation.PropertySource;
    import lombok.Data;
    
    @Data
    @ConfigurationProperties(prefix="com.vgc")//把“ccom.vgc“中这个开头的字段拿出来
    @PropertySource("classpath:test.properties")//这里需要指定需要使用的配置文件
    public class ConfigBean {
    
    	private String type;//注意，这里需要跟步骤一中的命名要匹配，即type和provider
    	private String provider;
    }

    //步骤三：申明加载的配置文件实体类 - EnableConfigurationProperties
    @SpringBootApplication
    @EnableConfigurationProperties({ConfigBean.class, MultipleProperities.class})//用EnableConfigurationProperties来指定刚定义好的实体类，可以使用多个
    public class Chapter1Application {
    
    	public static void main(String[] args) {
    	    SpringApplication.run(Chapter1Application.class, args);
    	}
    
    }

    //步骤四：最终的使用的地方
    @RestController
    public class HelloController {
    	
    	@Value("${com.vgc.env}")
    	private String mEnv;
    	
    	@Autowired
    	ConfigBean mConfigBean;//使用这个bean
    	
    //	@Autowired
    //	MultipleProperities mDevconfigBean;
    
        @RequestMapping("/hello")
        public String index() {
        	System.out.print("dev env is: " + mConfigBean.getType());//调用这个方法
            return "Hello,World!";
        }
    
    }

1. 不同的环境需要不同的配置文件

当应用程序需要部署到不同运行环境时，一些配置细节通常会有所不同，最简单的比如日志，生产日志会将日志级别设置为WARN或更高级别，并将日志写入日志文件，而开发的时候需要日志级别为DEBUG，日志输出到控制台即可。

如果按照以前的做法，就是每次发布的时候替换掉配置文件，这样太麻烦了，Spring Boot的Profile就给我们提供了解决方案，命令带上参数就搞定。

这里我们来模拟一下，只是简单的修改端口来测试

在Spring Boot中多环境配置文件名需要满足application-{profile}.properties的格式，其中{profile}对应你的环境标识，比如：

- application-dev.properties：开发环境
- application-prod.properties：生产环境

想要使用对应的环境，只需要在application.properties中使用spring.profiles.active属性来设置，值对应上面提到的{profile}，这里就是指dev、prod这2个。

当然你也可以用命令行启动的时候带上参数：

    #当前我们定义的是prod的配置文件,其他配置跟上面一样
    spring.profiles.active=prod


[参考文章]: http://tengj.top/2017/04/05/springboot7/

SLF4J——Simple Logging Facade For Java，它是一个针对于各类Java日志框架的统一Facade抽象。Java日志框架众多——常用的有java.util.logging, log4j, logback，commons-logging, Spring框架使用的是Jakarta Commons Logging API (JCL)。而SLF4J定义了统一的日志抽象接口，而真正的日志实现则是在运行时决定的——它提供了各类日志框架的binding。

Logback是log4j框架的作者开发的新一代日志框架，它效率更高、能够适应诸多的运行环境，同时天然支持SLF4J。

默认情况下，Spring Boot会用Logback来记录日志，并用INFO级别输出到控制台。在运行应用程序和其他例子时，你应该已经看到很多INFO级别的日志了。

假如maven依赖中添加了spring-boot-starter-logging：

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
    

那么，我们的Spring Boot应用将自动使用logback作为应用日志框架，Spring Boot启动的时候，由org.springframework.boot.logging.Logging-Application-Listener根据情况初始化并使用。

    //但是呢，实际开发中我们不需要直接添加该依赖，你会发现spring-boot-starter其中包含了 spring-boot-starter-logging，该依赖内容就是 Spring Boot 默认的日志框架 logback。

从上图可以看到，日志输出内容元素具体如下：

- 时间日期：精确到毫秒
- 日志级别：ERROR, WARN, INFO, DEBUG or TRACE
- 进程ID
- 分隔符：--- 标识实际日志的开始
- 线程名：方括号括起来（可能会截断控制台输出）
- Logger名：通常使用源代码的类名
- 日志内容

    //日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。
    //Spring Boot中默认配置ERROR、WARN和INFO级别的日志输出到控制台。您还可以通过启动您的应用程序–debug标志来启用“调试”模式（开发的时候推荐开启）,以下两种方式皆可：

文件输出

    #默认情况下，Spring Boot将日志输出到控制台，不会写到日志文件。如果要编写除控制台输出之外的日志文件，则需在application.properties中设置logging.file或logging.path属性。
    
    logging.file，设置文件，可以是绝对路径，也可以是相对路径。如：logging.file=my.log
    logging.path，设置目录，会在该目录下创建spring.log文件，并写入日志内容，如：logging.path=/var/log
    如果只配置 logging.file，会在项目的当前路径下生成一个 xxx.log 日志文件。
    如果只配置 logging.path，在 /var/log文件夹生成一个日志文件为 spring.log
    
    #注：二者不能同时使用，如若同时使用，则只有logging.file生效
    
    默认情况下，日志文件的大小达到10MB时会切分一次，产生新的日志文件，默认级别为：ERROR、WARN、INFO


[参考文档]: http://www.jb51.net/article/123862.htm

    //步骤一：pom文件添加依赖		
    <dependency>
      	<groupId>org.projectlombok</groupId>
      	<artifactId>lombok</artifactId>
      	<version>1.16.18</version>
      	<scope>provided</scope>
    </dependency>

    //步骤二：添加@Data注解，里面包含了Getter+Setter方法，所以不用给每个变量去定义这些get和set方法
    @Data
    @ConfigurationProperties(prefix="com.vgc")
    public class ConfigBean {
    	private String env;
    	private String provider;
    }

    //步骤三：在使用的地方进行声明，但是会发现无法找到get和set方法
    @Autowired
    ConfigBean configBean;
    
    // ++++++++=这里面需要额外的安装一个步骤：http://www.jb51.net/article/123862.htm
    //需要在sts中安装lombok
    192:1.16.18 alex$ java -jar lombok-1.16.18.jar
    192:1.16.18 alex$ pwd
    /Users/alex/.m2/repository/org/projectlombok/lombok/1.16.18
    192:1.16.18 alex$  

    #安装完成之后，在/Applications/STS.app/Contents/Eclipse目录下会有lombok.jar文件，而且在STS.ini后面添-javaagent:../Eclipse/lombok.jar
    192:Eclipse alex$ ls
    STS.ini                         dropins                         lombok.jar                      plugins
    artifacts.xml                   features                        open_source_licenses.txt        readme
    configuration                   license.txt                     p2
    192:Eclipse alex$ pwd
    /Applications/STS.app/Contents/Eclipse
    192:Eclipse alex$  
    
    ##############
    192:Eclipse alex$ cat STS.ini
    -startup
    ../Eclipse/plugins/org.eclipse.equinox.launcher_1.4.0.v20161219-1356.jar
    --launcher.library
    ../Eclipse/plugins/org.eclipse.equinox.launcher.cocoa.macosx.x86_64_1.1.551.v20171108-1834
    -product
    org.springsource.sts.ide
    --launcher.defaultAction
    openFile
    -vmargs
    -Dosgi.requiredJavaVersion=1.8
    --add-modules=ALL-SYSTEM
    -Xms40m
    -Dosgi.module.lock.timeout=10
    -Xverify:none
    -XstartOnFirstThread
    -Dorg.eclipse.swt.internal.carbon.smallFonts
    -Xdock:icon=../Resources/sts.icns
    -Xmx1200m
    -javaagent:../Eclipse/lombok.jar
    
    192:Eclipse alex$         

    //完成以上步骤之后，发现这个时候已经存在哪些get方法
    @Autowired
    	ConfigBean configBean;
    
        @RequestMapping("/hello")
        public String index(){
        	System.out.print(configBean.getProvider());
            return "Hello,World!";
        }
