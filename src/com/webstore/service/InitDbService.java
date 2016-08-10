package com.webstore.service;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.webstore.entity.Item;
import com.webstore.entity.Role;
import com.webstore.entity.User;
import com.webstore.repository.ItemRepository;
import com.webstore.repository.RoleRepository;
import com.webstore.repository.UserRepository;

@Service
public class InitDbService {
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ItemRepository itemRepository;

	@PostConstruct
	public void init() {
		if (roleRepository.findByName("ROLE_USER") == null) {
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepository.save(roleUser);

			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);

			User userAdmin = new User();
			userAdmin.setEnabled(true);
			userAdmin.setName("admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);

			User user = new User();
			user.setEnabled(true);
			user.setName("user");
			user.setPassword(encoder.encode("user"));
			List<Role> roles1 = new ArrayList<>();
			roles1.add(roleUser);
			user.setRoles(roles1);
			userRepository.save(user);

			User user1 = new User();
			user1.setEnabled(false);
			user1.setName("user1");
			user1.setPassword(encoder.encode("user1"));
			List<Role> roles2 = new ArrayList<>();
			roles2.add(roleUser);
			user1.setRoles(roles2);
			userRepository.save(user1);

			Item item = new Item();
			item.setImage(getImageFromURL("http://img5.itbox.ua/prod_img/5/U0033345_big.jpg"));
			item.setName("Процессор AMD FX-6300 (FD6300WMHKBOX)");
			item.setPrice(2802);
			item.setDescriptionShort("AM3+, 6 ядер, 3.50GHz, нет, 6MB, L3: 8MB, 32nm, 95W, BOX");
			item.setDescriptionLong(
					"Шестиядерный процессор AMD FX-6300 (6300WMHKBOX) отличается полностью измененной и улучшенной архитектурой,"
							+ " поддерживает одновременную обработку нескольких потоков, что является аналогом поддержки технологии Hyper-Threading."
							+ " Частота процессора составляет — 3.5 ГГц Процессор имеет 6 МБ кэша второго уровня и 8 МБ кеша третьего уровня и"
							+ " поддерживает новые энергосберегающие технологии. Процессор поддерживает разгон, позволяющий повышать уровень "
							+ "производительности и скорости работы, не в последнюю очередь благодаря технологии AMD Turbo CORE."
							+ " Утилита AMD OverDrive поможет быстро и легко отрегулировать производительность процессора. "
							+ "Особенности AMD FX-6300 (6300WMHKBOX):<br><br> - 32 технологический процесс <br><br>- Поддержка AMD Turbo CORE "
							+ "<br><br>- Частота 3.5 ГГц <br><br>- Теплопакет 95 Вт ");
			itemRepository.save(item);

			Item item1 = new Item();
			item1.setImage(getImageFromURL("http://img1.itbox.ua/prod_img/1/U0169131_big.jpg"));
			item1.setName("Цифровой фотоаппарат Nikon D5200 kit 18-55VRII + 55-200VRII (VBA350K011)");
			item1.setPrice(15921);
			item1.setDescriptionShort(
					"разрешение матрицы - 24.1 Mpx, размер матрицы - 23.5 x 15.6 мм, фокусное растояние - 27 - 83 мм, светосила объектива - f/3.5, зум - зум 3х, размер ЖК-экрана - 3\", тип карт памяти - SD/SDHC/SDXC, видеосъемка - 1920 x 1080, 60 к/сек, формат записи изображения - JPEG, RAW, тип питания - аккумулятор Li-Ion, цвет корпуса - черный ");
			item1.setDescriptionLong(
					"Цифровой фотоаппарат Nikon D5200 kit 18-55VRII + 55-200VRII (VBA350K011) От увлекательных фотографий в видео высокого качества в формате Full HD, эта цифровая зеркальная фотокамера с одним объективом разработана для развития ваших творческих способностей. Удобный монитор с переменным углом наклона расширяет ваше креативное видение, предоставляя возможность просмотра изображений с разных ракурсов. Датчик КМОП формата DX разрешения 24,1 мегапикселя позволяет получать чрезвычайно детализированные изображения, а усовершенствованная 39-точечная система автофокусировки всегда позволяет сконцентрироваться именно на нужном объекте.  Создавайте высокодетализированные фотографии и видеоматериалы. Изображение можно обрезать без потери детализации и получать отличные снимки большого формата. Расширяйте свои творческие возможности с помощью универсального монитора, который можно вращать, наклонять и поворачивать. Делайте уникальные снимки, замечательные автопортреты под любым углом. Записывайте чрезвычайно детализированные видео в формате Full HD, которыми вы обязательно захотите поделиться. Почувствуйте все преимущества видео (макс. 60i / 50i) вместе со встроенным стерео микрофоном. Передавайте изображения прямо из камеры D5200 на смартфоны и планшетные ПК, работающих на платформах Android ™ и iOS ™ ¹, а также дистанционно управляйте фотокамерой с помощью своего мобильного устройства. ЖК-дисплей с высоким разрешением. Рассматривайте мельчайшие детали фотографий и видеороликов на 7,5 см (3,0 дюйма) ЖК-дисплее с разрешением 921 тыс. Точек с переменным углом наклона, широким углом обзора и высоким уровнем контрастности. Легко компонуйте изображения или применяйте к ним специальные эффекты независимо от условий съемки. Интеллектуальный дизайн. Благодаря маленькому легкому корпусу и чрезвычайно эргономичному дизайну снимать этой камерой удивительно удобно. Благодаря надежной ручке фотокамеру очень удобно держать в руках. Понятные меню и сенсорные кнопки для функций, которые часто используются (например, \"D-видео\" или \"Live View\"), ускоряют и упрощают работу с фотокамерой. Беспроводные устройства дистанционного управления Nikon WR-R10 и WR-T10 используются как дополнительные аксессуары и позволяют управлять основными функциями фотокамеры на расстоянии, даже если между вами и фотокамерой находятся объекты. Эти устройства идеально подходят для съемки автопортретов и позволяют избежать дрожания фотокамеры во время спуска затвора. Объективы NIKKOR. Почувствуйте все преимущества легендарных объективов NIKKOR от Nikon и разрешения фотокамеры в 24 мегапикселя. Создавайте снимки с яркими цветами и впечатляющей контрастностью. Снимайте видео высокой четкости и экспериментируйте с кинематографическими эффектами. ");
			itemRepository.save(item1);

			Item item2 = new Item();
			item2.setImage(getImageFromURL("http://img8.itbox.ua/prod_img/8/U0165568_big.jpg"));
			item2.setName("Мобильный телефон Apple iPhone SE 16Gb Space Grey (MLLN2RK/A/MLLN2UA/A)");
			item2.setPrice(13998);
			item2.setDescriptionShort(
					"количество SIM-карт - 1 SIM, форм-фактор - моноблок, диагональ экрана - 4.0\", тип дисплея - IPS, разрешение экрана - 640 x 1136, процессор - Apple A9, частота процессора - 1.84 GHz, оперативная память - 2 Gb, встроенная память - 16 Gb, основная камера МР - 12, операционная система - iOS 9");
			item2.setDescriptionLong(
					"Мобильный телефон Apple iPhone SE 16Gb Space Grey — самый мощный 4‑дюймовый смартфон в истории. Чтобы создать его, Apple взяли за основу полюбившийся дизайн и полностью поменяли содержание. Установили тот же передовой процессор A9, что и на iPhone 6s, и камеру 12 Мп для съёмки невероятных фотографий и видео 4K. А благодаря Live Photos любой ваш снимок буквально оживёт. Результат? Небольшой iPhone с огромными возможностями. Любимый дизайн. И новые причины его полюбить. iPhone SE — это не просто невероятно популярный дизайн. Это следующий шаг в его развитии. Корпус лёгкого, компактного и удобного телефона сделан из гладкого матированного алюминия. На великолепном 4‑дюймовом дисплее Retina всё выглядит невероятно чётко и ярко. А завершают картину матовые скошенные края и логотип из нержавеющей стали. Самые мощные 4 дюйма в истории смартфонов. В основе iPhone SE лежит A9 — тот же передовой процессор, что установлен на iPhone 6s. Его 64‑битная архитектура уровня настольных компьютеров гарантирует потрясающую скорость работы и отклика. А графика уровня игровых консолей полностью погружает в мир любимых игр и приложений. Этот мощный процессор просто создан для максимальной производительности. Невероятно эффективный сопроцессор M9. Сопроцессор движения M9 встроен непосредственно в процессор A9 и напрямую взаимодействует с компасом, гироскопом и акселерометром. Это расширяет возможности по сбору фитнес-данных — например, количества шагов и пройденного расстояния. Включить Siri также стало намного проще, вам даже не придётся брать iPhone в руки. Просто скажите: «Привет, Siri». Единственная камера, которая вам понадобится. Ваши фотографии, сделанные при помощи 12-мегапиксельной камеры iSight, будут чёткими и детальными — прямо как на iPhone 6s. Кроме того, вы можете снимать и редактировать отличное видео 4K с разрешением в четыре раза выше, чем HD‑видео 1080p. Live Photos. Оживите момент. Благодаря Live Photos ваши фотографии буквально придут в движение и зазвучат. Просто коснитесь снимка в любой точке и удерживайте, чтобы увидеть несколько секунд, записанных до и после съёмки. HD-камера FaceTime со вспышкой Retina Flash. Украшение ваших селфи. Дисплей Retina — это не только экран вашего iPhone, но и вспышка HD-камеры FaceTime. Благодаря особой технологии вспышка Retina Flash в три раза ярче обычной и позволяет снимать отличные селфи даже ночью и при плохом освещении. А вспышка True Tone подстраивается под окружающее освещение и обеспечивает натуральные цвета и естественный тон кожи. Медиатека iCloud. Ваши фото и видео — всегда с вами. В медиатеке iCloud хранятся последние версии всех ваших фотографий и видеозаписей. Все внесённые вами изменения автоматически отображаются на всех ваших устройствах, поэтому вы можете в любой момент посмотреть снимки, сделанные на прошлой неделе или в прошлом году. Где бы вы ни находились. Улучшенная безопасность. Достаточно одного прикосновения. Touch ID легко и безопасно разблокирует iPhone SE при помощи отпечатка вашего пальца. Это идеальный пароль, который невозможно подобрать, забыть или потерять. 4G LTE и Wi-Fi стали быстрее. Просматривайте веб-страницы, загружайте игры и приложения и смотрите видео в потоковом режиме через сети Wi-Fi 802.11ac и 4G LTE ещё быстрее, чем на iPhone 5s. Используйте большее количество диапазонов LTE для эффективного роуминга. И подключайте Apple Watch, внешние динамики и другие устройства через Bluetooth. Программная и аппаратная части. Разработаны друг для друга. На iPhone SE установлена iOS 9 — самая совершенная в мире операционная система с удобным интерфейсом, потрясающими функциями и уникальными принципами безопасности. Она безупречно выглядит и отлично работает на iPhone. Теперь даже самые простые задачи будут увлекательными.");
			itemRepository.save(item2);

			Item item3 = new Item();
			item3.setImage(getImageFromURL("http://img9.itbox.ua/prod_img/9/U0158989_big.jpg"));
			item3.setName("GSM-модем Huawei E3531i-1 3G USB (E3531i-1)");
			item3.setPrice(749);
			item3.setDescriptionShort("USB, HSPA+ HSUPA UMTS/WCDMA EDGE/GPRS, Wi-Fi	150 Мбит/c");
			item3.setDescriptionLong(
					"GSM-модем Huawei E3531i-1 3G USB (E3531i-1) имеет интегрированную антенну, а также реализует технологию разнесенного приема для 3G UMTS частот, что значительно улучшает прием внутри зданий и в зонах со слабым сигналом. HSDPA модем Huawei подключается к ноутбуку или персональному компьютеру через быстрый и удобный USB интерфейс.Управление 3G модемом, ведется с помощью встроенной утилиты, которую можно установить при первом подключении модема. Максимальная скорость получения данных: HSPA+ до 21.6 Мбит/с HSUPA до 5.76 Мбит/с UMTS до 384 Кбит/с EDGE до 236.8 Кбит/с GPRS до 85.6 Кбит/с Поддерживаемые ОС: Windows XP SP3, Vista SP1/SP2, 7, 8, 8.1 (исключая RT версию) Mac OS X10.7, 10.8, 10.9");
			itemRepository.save(item3);

			Item item4 = new Item();
			item4.setImage(getImageFromURL("http://img9.itbox.ua/prod_img/9/U0158989_big.jpg"));
			item4.setName("GSM-модем Huawei E3531i-1 3G USB (E3531i-1)");
			item4.setPrice(749);
			item4.setDescriptionShort("USB, HSPA+ HSUPA UMTS/WCDMA EDGE/GPRS, Wi-Fi	150 Мбит/c");
			item4.setDescriptionLong(
					"GSM-модем Huawei E3531i-1 3G USB (E3531i-1) имеет интегрированную антенну, а также реализует технологию разнесенного приема для 3G UMTS частот, что значительно улучшает прием внутри зданий и в зонах со слабым сигналом. HSDPA модем Huawei подключается к ноутбуку или персональному компьютеру через быстрый и удобный USB интерфейс.Управление 3G модемом, ведется с помощью встроенной утилиты, которую можно установить при первом подключении модема. Максимальная скорость получения данных: HSPA+ до 21.6 Мбит/с HSUPA до 5.76 Мбит/с UMTS до 384 Кбит/с EDGE до 236.8 Кбит/с GPRS до 85.6 Кбит/с Поддерживаемые ОС: Windows XP SP3, Vista SP1/SP2, 7, 8, 8.1 (исключая RT версию) Mac OS X10.7, 10.8, 10.9");
			itemRepository.save(item4);

			Item item5 = new Item();
			item5.setImage(getImageFromURL("http://img9.itbox.ua/prod_img/9/U0158989_big.jpg"));
			item5.setName("GSM-модем Huawei E3531i-1 3G USB (E3531i-1)");
			item5.setPrice(749);
			item5.setDescriptionShort("USB, HSPA+ HSUPA UMTS/WCDMA EDGE/GPRS, Wi-Fi	150 Мбит/c");
			item5.setDescriptionLong(
					"GSM-модем Huawei E3531i-1 3G USB (E3531i-1) имеет интегрированную антенну, а также реализует технологию разнесенного приема для 3G UMTS частот, что значительно улучшает прием внутри зданий и в зонах со слабым сигналом. HSDPA модем Huawei подключается к ноутбуку или персональному компьютеру через быстрый и удобный USB интерфейс.Управление 3G модемом, ведется с помощью встроенной утилиты, которую можно установить при первом подключении модема. Максимальная скорость получения данных: HSPA+ до 21.6 Мбит/с HSUPA до 5.76 Мбит/с UMTS до 384 Кбит/с EDGE до 236.8 Кбит/с GPRS до 85.6 Кбит/с Поддерживаемые ОС: Windows XP SP3, Vista SP1/SP2, 7, 8, 8.1 (исключая RT версию) Mac OS X10.7, 10.8, 10.9");
			itemRepository.save(item5);
		}
	}

	private byte[] getImageFromURL(String imageUrl) {

		URL url;
		byte[] bytes = null;
		try {
			url = new URL(imageUrl);
			InputStream is = url.openStream();
			if (is != null) {
				bytes = IOUtils.toByteArray(is);
				is.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bytes;
	}
}