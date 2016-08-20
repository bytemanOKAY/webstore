# webstore

Проект піднято на сайті: http://byteman-webstore.herokuapp.com
```
Admin:
Login: admin
Password: admin
User:
Login: user
Password: user
```
Витрачено годин: ~35 год

Дамп бази даних знаходиться у корені проекту.

Для запуску на локальному ПК (Eclipse + Tomcat):
У налаштуваннях конфігурації tomcat, у вкладці Arguments, в полі VM arguments додайте строку ```-Dspring.profiles.active="dev"```

Для зміни максимально допустимого розміру зображення для товару перейдіть у каталог WebContent/WEB-INF/ та у файлі dispatcher-servlet.xml у фрагменті
```xml
	<bean id="multipartResolver"
		class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
		 <!-- setting maximum upload size = 3 MB -->
		<property name="maxUploadSize" value="3145728" /> 
	</bean>
```	
Замініть значення атрибуту value на розмір у байтах.

Для редагування підключення до бази даних потрібно перейти у каталог WebContent/WEB-INF/ та у файлі database-prod.xml замініть у фрагменті:
```xml
<bean class="java.net.URI" id="dbUrl">
		<constructor-arg value="#{systemEnvironment['DATABASE_URL']}" />
</bean>
```
значення value на посилання на адресу підключення до своєї бази даних.


Реалізовано: 
* Реєстрація\Авторизація користувача.
* Функціонал для адміністратора: заблокувати\розблокувати\видалити юзера, додати\редагувати\видалити товар.
* Корзина: додати\видалити товар.
* Відгук про товар( з оцінкою 1-5) та обрахунок середньої оцінки товару.
* Пошук товару за його назвою( непрацююче автодоповнення, результат запиту не відображається у пропозиціях під полем вводу).

Використані бібліотеки:
* Bootstrap - для оформлення сторінки.
* Apache Tiles - для створення основи сторінки( меню та футера) і підстановки під час роботи програми в тіло сторінки певного контенту * який запитує юзер.
* Spring framework - для огранізації об'єктів та зв'язків між ними, автозв'язки сервісів, репозиторіїв.
* Spring Security - для полегшення авторизації користувача, контролю сессії, контролю доступу залежно від прав юзера.
* Spring Web MVC - для привязки методів до URL, витягування даних з URL, передачі об'єктів до веб сторінки.
* Hibernate - для зберігання об'єктів у формі таблиць до бази даних.
Commons fileupload\io - для обробки переданих файлів з форм.