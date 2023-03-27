#devTools ?? ?????? ?? ??? ??
spring.devtools.livereload.enabled = true
spring.devtools.restart.enabled=false

#jsp ??? ??? ??? ??
spring.mvc.view.prefix=/WEB-INF/views
spring.mvc.view.suffix=.jsp


#??? ??
spring.datasource.url=jdbc:mysql://localhost:3306/study?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
spring.datasource.username=jhyoon
spring.datasource.password=990220



#????? mapper xml
mybatis.mapper-locations=classpath:sqlmapper/*.xml
mybatis.configuration.map-underscore-to-camel-case=true
