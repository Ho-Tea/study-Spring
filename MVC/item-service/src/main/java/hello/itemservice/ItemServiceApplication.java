package hello.itemservice;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);

	}

}
