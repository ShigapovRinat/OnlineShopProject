package ru.itis.onlineShop.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import ru.itis.onlineShop.dto.BasketDto;
import ru.itis.onlineShop.dto.GoodDto;
import ru.itis.onlineShop.models.Basket;
import ru.itis.onlineShop.models.Good;
import ru.itis.onlineShop.models.Person;
import ru.itis.onlineShop.repositories.BasketRepository;
import ru.itis.onlineShop.repositories.PersonsRepository;
import ru.itis.onlineShop.services.BasketService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Component
public class BasketServiceImpl implements BasketService {

    @Autowired
    private PersonsRepository personsRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Override
    public List<BasketDto> getBasket(String email) {
        Optional<Person> optionalPerson = personsRepository.find(email);
        if (optionalPerson.isPresent()) {
            Set<Basket> basket = optionalPerson.get().getBasket();
            List<BasketDto> basketDtoList = new ArrayList<>();
            basket.forEach(bask -> {
                basketDtoList.add(BasketDto.builder()
                        .goodDto(GoodDto.from(bask.getGood()))
                        .quantityGood(bask.getQuantityGood())
                        .build());
            });
            return basketDtoList;
        } else throw new IllegalStateException("Пользователь не найден");
    }

    @Override
    public void addBasket(String email, BasketDto basketDto) {
        Good good = Good.builder()
                .id(basketDto.getGoodDto().getId())
                .title(basketDto.getGoodDto().getTitle())
                .description(basketDto.getGoodDto().getDescription())
                .price(basketDto.getGoodDto().getPrice())
                .type(basketDto.getGoodDto().getType())
                .build();
        Optional<Person> optionalPerson = personsRepository.find(email);
        if (optionalPerson.isPresent()) {
            Basket basket = Basket.builder()
                    .person(optionalPerson.get())
                    .good(good)
                    .quantityGood(basketDto.getQuantityGood())
                    .build();
            Optional<Basket> basketOptional = basketRepository.findByPersonIdAndGoodId(basket.getPerson().getId(),
                    basket.getGood().getId());
            if (!basketOptional.isPresent()) {
                basketRepository.save(basket);
            } else if (!basketDto.getQuantityGood().equals(basketOptional.get().getQuantityGood())) {
                basketRepository.updateQuantity(basketOptional.get().getId(), basket.getQuantityGood());
            } else throw new IllegalStateException("Товар уже находиться в корзине");
    } else throw new IllegalStateException("Пользователь не найден");

}

    @Override
    public void deleteBasket(String email, BasketDto basketDto) {
        Optional<Person> optionalPerson = personsRepository.find(email);
        if (optionalPerson.isPresent()) {
            if (!basketRepository.findByPersonIdAndGoodId
                    (optionalPerson.get().getId(), basketDto.getGoodDto().getId()).isPresent()) {
                basketRepository.deleteByPersonIdAndGoodId(optionalPerson.get().getId(), basketDto.getGoodDto().getId());
            } else throw new IllegalStateException("Товара в корзине нет");
        } else throw new IllegalStateException("Пользователь не найден");
    }

    @Override
    public void deleteAllBasket(String email) {
        Optional<Person> optionalPerson = personsRepository.find(email);
        if (optionalPerson.isPresent()) {
            basketRepository.deletePersonAll(optionalPerson.get().getId());
        } else throw new IllegalStateException("Пользователь не найден");
    }
}
